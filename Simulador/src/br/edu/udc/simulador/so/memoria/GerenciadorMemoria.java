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

public abstract class GerenciadorMemoria {

	final Hardware hardware;

	protected Lista<Particao> listaMemoria = new ListaEncadeada<>();
	protected Ordenavel<Particao> listaMemoriaVazia = new ListaOrdenavel<>();

	public GerenciadorMemoria(int tamanhoSO, int tamanhoMemoria, Hardware hardware) {
		
		this.hardware = hardware;
		
		// adicionando SO no contexto de mem�ria do mesmo
		Processo processoSO = new Processo(SimuladorSO.PID_SO, null, 0, tamanhoSO);
		this.listaMemoria.adiciona(new Particao(processoSO, tamanhoSO, 0));

		// "adicionando" memoria vazia ao contexto de mem�ria do SO
		final int tamanhoEspacoVazio = tamanhoMemoria - tamanhoSO;
		final Particao particaoMemoriaVazia = new Particao(tamanhoEspacoVazio, tamanhoSO);
		this.listaMemoria.adiciona(particaoMemoriaVazia);
		this.listaMemoriaVazia.adiciona(particaoMemoriaVazia);
	}

	public abstract IteradorManipulador<Particao> procuraPosicaoVazia(int tamanhoPrograma);

	public abstract void desalocaMemoria(int pid);

	public void allocaMemoria(Processo processo, Programa programa) throws RuntimeException {

		IteradorManipulador<Particao> particaoLivre = this.procuraPosicaoVazia(programa.tamanho());
		final int posicaoAllocada = this.allocaMemoria(processo, programa.tamanho(), particaoLivre);

		processo.setPosicaoMemoria(posicaoAllocada);
		Computador.getIntancia().getHardware().preencheMemoria(posicaoAllocada, programa);
	}

	protected int allocaMemoria(Processo processo, int tamanhoPrograma, IteradorManipulador<Particao> particaoLivre) {

		final int posicaoAllocada = particaoLivre.getDado().getPosicao();

		processo.setPosicaoMemoria(particaoLivre.getDado().getPosicao());
		final Particao particao = new Particao(processo, tamanhoPrograma, particaoLivre.getDado().getPosicao());
		particaoLivre.adicionaAntes(particao);

		final int tamanhoAtualizadoLivre = particaoLivre.getDado().getTamanho() - tamanhoPrograma;

		if (tamanhoAtualizadoLivre <= 0) {// pati��o agora � "nula"
			particaoLivre.remove();
			// TODO SO - remover de this.listaMemoriaVazia tambem
		} else {
			particaoLivre.getDado().setTamanho(tamanhoAtualizadoLivre);

			final int posicaoAtualizadaLivre = particaoLivre.getDado().getPosicao() + tamanhoPrograma;
			particaoLivre.getDado().setPosicao(posicaoAtualizadaLivre);

			this.listaMemoriaVazia.organizaCrascente();
		}

		return posicaoAllocada;
	}

	public void desfragmentacaoMemoria() {
		this.desfragmentacaoMemoria(this.listaMemoria.inicio());
	}

	@SuppressWarnings("unchecked")
	private void desfragmentacaoMemoria(IteradorManipulador<Particao> inicio) {

		IteradorManipulador<Particao> it_vazio;
		for (it_vazio = inicio; it_vazio.temProximo(); it_vazio.proximo()) {
			// TODO erro - reflective
			if (it_vazio.getDado().getPid() == SimuladorSO.POSICAO_MEMORIA_VAZIA) {
				// Encontrou uma posi��o vazia

				// para que fa�a a compara��o com o proximo ao vazio
				IteradorManipulador<Particao> it_realocados = (IteradorManipulador<Particao>) it_vazio.clone();
				if (!it_realocados.temProximo()) {
					return;
				} else {
					it_realocados.proximo();
				}

				for (; it_realocados.temProximo(); it_realocados.proximo()) {
					// Percorre todos as parti��es a frente para "realoca-las"
					// sem espa�os

					final Particao realocada = it_realocados.getDado();
					if (realocada.getPid() != SimuladorSO.POSICAO_MEMORIA_VAZIA) {
						// A parti��o � um programa e deve ser realocado
						final Processo processo = realocada.getProcesso();
						final Programa programa = this.programaNaParticao(realocada);

						// Realoca��o do espa�o de memoria
						this.desalocaMemoria(realocada.getPid());
						this.hardware.preencheMemoria(it_vazio.getDado().getPosicao(), programa);
						this.allocaMemoria(processo, programa.tamanho(), it_vazio);
					} else {
						// Encontrou mais uma posi��o vazia na mem�ria
						this.desfragmentacaoMemoria(it_realocados);
					} // END ELSE parti��oVazia
				} // END FOR realocando programas
			} // END IF procurando vazio
		} // END FOR procurando vazio
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
				// Verifica se a parti��o representa memoria vazia

				final IteradorManipulador<Particao> itPosterior = (IteradorManipulador<Particao>) itAnterior.clone();
				itPosterior.proximo();
				final Particao particaoPosterior = itPosterior.getDado();

				if (particaoPosterior == null) {
					// chegou ao fim da lista, mesmo que o fim seja livre
					return;
				}

				if (particaoPosterior.getPid() == SimuladorSO.POSICAO_MEMORIA_VAZIA) {
					// Verifica se a posi��o posterior � vazia tamb�m
					System.out.println(particaoAnterior + "[]" + particaoPosterior);

					final int tamanhoConjunto = particaoAnterior.getTamanho() + particaoPosterior.getTamanho();
					particaoAnterior.setTamanho(tamanhoConjunto);
					itAnterior.remove();
				} // END if parti��oPosterior
			} // END if parti��oAnterior
		} // END for
	}// END m�todo
}
