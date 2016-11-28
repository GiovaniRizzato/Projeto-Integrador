package br.edu.udc.simulador.so.memoria;

import br.edu.udc.ed.iteradores.IteradorManipulador;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.ed.lista.Ordenavel;
import br.edu.udc.ed.lista.encadeada.ListaEncadeada;
import br.edu.udc.ed.lista.encadeada.ListaOrdenavel;
import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.processo.Programa;
import br.edu.udc.simulador.so.SimuladorSO;
import br.edu.udc.simulador.so.SistemaOperacional;

public abstract class GerenciadorMemoria {

	final Hardware hardware;

	protected Lista<Particao> listaMemoria = new ListaEncadeada<>();
	protected Ordenavel<Particao> listaMemoriaVazia = new ListaOrdenavel<>();

	public GerenciadorMemoria(int tamanhoSO, int tamanhoMemoria, Hardware hardware) {

		this.hardware = hardware;

		// adicionando SO no contexto de memória do mesmo
		Processo processoSO = new Processo(SimuladorSO.PID_SO, null, 0, tamanhoSO);
		this.listaMemoria.adiciona(this.contrutorParticao(processoSO, tamanhoSO, 0));

		// "adicionando" memoria vazia ao contexto de memória do SO
		final int tamanhoEspacoVazio = tamanhoMemoria - tamanhoSO;
		final Particao particaoMemoriaVazia = this.contrutorParticao(tamanhoEspacoVazio, tamanhoSO);
		this.listaMemoria.adiciona(particaoMemoriaVazia);
		this.listaMemoriaVazia.adiciona(particaoMemoriaVazia);
	}

	public abstract IteradorManipulador<Particao> procuraPosicaoVazia(int tamanhoPrograma);

	public abstract Particao contrutorParticao(int tamanho, int posicao);

	public abstract Particao contrutorParticao(Processo processo, int tamanho, int posicao);

	public void allocaMemoria(Processo processo, Programa programa) throws RuntimeException {

		IteradorManipulador<Particao> particaoLivre = this.procuraPosicaoVazia(programa.tamanho());
		final int posicaoAllocada = this.allocaMemoria(processo, programa.tamanho(), particaoLivre);

		processo.setPosicaoMemoria(posicaoAllocada);
		Computador.getIntancia().getHardware().preencheMemoria(posicaoAllocada, programa);
	}

	protected int allocaMemoria(Processo processo, int tamanhoPrograma, IteradorManipulador<Particao> particaoLivre) {

		final int posicaoAllocada = particaoLivre.getDado().getPosicao();

		processo.setPosicaoMemoria(particaoLivre.getDado().getPosicao());
		Particao particao = this.contrutorParticao(processo, tamanhoPrograma, particaoLivre.getDado().getPosicao());
		particaoLivre.adicionaAntes(particao);

		final int tamanhoAtualizadoLivre = particaoLivre.getDado().getTamanho() - tamanhoPrograma;

		if (tamanhoAtualizadoLivre <= 0) {// patição agora é "nula"
			// remove da lista principal
			this.removeListaMemoriaVazia(particaoLivre.getDado());
			particaoLivre.remove();

		} else {
			particaoLivre.getDado().setTamanho(tamanhoAtualizadoLivre);

			final int posicaoAtualizadaLivre = particaoLivre.getDado().getPosicao() + tamanhoPrograma;
			particaoLivre.getDado().setPosicao(posicaoAtualizadaLivre);

			this.listaMemoriaVazia.organizaCrascente();
		}

		return posicaoAllocada;
	}
	
	public void desalocaMemoria(int pid) {

		for (IteradorManipulador<Particao> it = this.listaMemoria.inicio(); it.temProximo(); it.proximo()) {
			final Particao particao = it.getDado();
			if (particao.getPid() == pid) {
				particao.setProcesso(null);
				// "marca" como vazio

				this.listaMemoriaVazia.adiciona(particao);

				this.verificaEspacoLivreAdjacente();
				this.listaMemoriaVazia.organizaCrascente();

				return;
			}
		}

		throw new IllegalArgumentException("PID não esta na lista de memória");
	}

	protected void removeListaMemoriaVazia(Particao particaoLivre) {

		for (IteradorManipulador<Particao> it = this.listaMemoriaVazia.inicio(); it.temProximo(); it.proximo()) {
			final Particao particaoVazia = it.getDado();
			if (particaoVazia.equals(particaoLivre)) {
				it.remove();
				break;
			}
		}
	}

	public void desfragmentacaoMemoria() {

		// obtenção da PRIMEIRA pocição vazia de memoria
		IteradorManipulador<Particao> itVazio = this.listaMemoria.inicio();
		for (; itVazio.temProximo(); itVazio.proximo()) {
			final Particao particao = itVazio.getDado();

			if (particao.getPid() == SistemaOperacional.POSICAO_MEMORIA_VAZIA) {
				break;
			}
		}

		final Particao particaoVazia = itVazio.getDado();

		for (IteradorManipulador<Particao> it = this.listaMemoria.inicio(); it.temProximo(); it.proximo()) {
			Particao particao = it.getDado();

			if (particao.getPid() == SistemaOperacional.PID_SO) {
				continue;
			}

			if (particao.getPid() != SistemaOperacional.POSICAO_MEMORIA_VAZIA) {
				final Programa programa = this.programaNaParticao(particao);
				final Processo processo = particao.getProcesso();

				this.hardware.preencheMemoria(particaoVazia.getPosicao(), programa);
				this.desalocaMemoria(processo.getPID());

				itVazio.adicionaAntes(this.contrutorParticao(processo, programa.tamanho(), particaoVazia.getPosicao()));

				particaoVazia.setPosicao(particaoVazia.getPosicao() + programa.tamanho());
				particaoVazia.setTamanho(particaoVazia.getTamanho() - programa.tamanho());
			}
		}

		this.verificaEspacoLivreAdjacente();
	}

	protected Programa programaNaParticao(Particao particao) {
		Programa programa = new Programa();
		for (int i = 0; i < particao.getTamanho(); i++) {
			final int posicaoIntrucao = particao.getPosicao() + i;
			programa.adiciona(this.hardware.getPosicaoMemoria(posicaoIntrucao));
		}

		return programa;
	}

	@SuppressWarnings("unchecked")
	protected void verificaEspacoLivreAdjacente() {

		IteradorManipulador<Particao> itAnterior;
		for (itAnterior = this.listaMemoria.inicio(); itAnterior.temProximo(); itAnterior.proximo()) {

			final Particao particaoAnterior = itAnterior.getDado();
			if (particaoAnterior.getPid() == SimuladorSO.POSICAO_MEMORIA_VAZIA) {
				// Verifica se a partição representa memoria vazia

				final IteradorManipulador<Particao> itPosterior = (IteradorManipulador<Particao>) itAnterior.clone();
				itPosterior.proximo();
				final Particao particaoPosterior = itPosterior.getDado();

				if (particaoPosterior == null) {
					// chegou ao fim da lista, mesmo que o fim seja livre
					return;
				}

				if (particaoPosterior.getPid() == SimuladorSO.POSICAO_MEMORIA_VAZIA) {
					// Verifica se a posição posterior é vazia também

					final int tamanhoConjunto = particaoAnterior.getTamanho() + particaoPosterior.getTamanho();
					particaoAnterior.setTamanho(tamanhoConjunto);

					this.removeListaMemoriaVazia(itPosterior.getDado());
					itPosterior.remove();
				} // END if partiçãoPosterior
			} // END if partiçãoAnterior
		} // END for
	}// END método
}
