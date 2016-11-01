package br.edu.udc.simulador.so;

import javax.crypto.IllegalBlockSizeException;

import br.edu.udc.ed.fila.Fila;
import br.edu.udc.ed.iteradores.IteradorManipulador;
import br.edu.udc.ed.iteradores.Iterador;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.ed.mapa.Mapa;
import br.edu.udc.ed.vetor.Vetor;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.processo.Programa;

public class SimuladorSO {

	// Abstração de estados do processo
	private Processo processando;
	private Processo estadoCriacao;
	private Processo estadoFinalizacao;

	// Armazenamento de processos
	private Lista<Processo> listaPrincipal = new Lista<>();
	private Mapa<Integer, Processo> listaPausado = new Mapa<>();

	// Referencias
	private Hardware referenciaHardware;

	// Veriaveis lógicas
	private final int posicaoMemoriaVazia = -1;
	private final int pidSO = posicaoMemoriaVazia + 1;
	private Integer proximoPidDisponivel = this.pidSO + 1;

	private Float porcentagemCPUAlta;
	private Float porcentagemCPUMedia;
	private Float porcentagemCPUBaixa;

	// Gerencianmento de memória
	private Lista<Particao> listaMemoria = new Lista<>();
	private Lista<Particao> listaMemoriaVazia = new Lista<>();

	private EstatisticaSO estatisticaSO = new EstatisticaSO();

	public SimuladorSO(Hardware hardware, int tamanhoSO, float porcentagemAlta, float porcentagemMedia) {
		this.referenciaHardware = hardware;
		this.listaMemoria.adiciona(new Particao(this.pidSO, tamanhoSO, 0));

		final int espacoVazio = this.referenciaHardware.tamanhoMemoria() - tamanhoSO;
		this.listaMemoria.adiciona(new Particao(this.posicaoMemoriaVazia, espacoVazio, tamanhoSO));
		this.listaMemoriaVazia.adiciona(this.listaMemoria.obtem(0));

		this.porcentagemCPUAlta = porcentagemAlta;
		this.porcentagemCPUMedia = porcentagemMedia;
		this.porcentagemCPUBaixa = ((porcentagemAlta + porcentagemMedia) - 1);
	}

	public int qtdProcessosAtivos() {
		return this.listaPrincipal.tamanho();
	}

	public int qtdProcessosPausados() {
		return this.listaPausado.tamanho();
	}
	
	public EstatisticaSO getEstatisticas() {
		return this.estatisticaSO.clone();
	}

	public Processo[] listaTodos() {

		Processo[] processos = new Processo[this.listaPrincipal.tamanho()];

		Iterador<Processo> it = this.listaPrincipal.inicio();
		for (int i = 0; i < this.listaPrincipal.tamanho(); i++) {
			processos[i] = it.getDado();
			it.proximo();
		}

		return processos;
	}

	public void criaNovoProcesso(Processo.prioridade prioridade, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {

		final Programa programa = new Programa(qtdCPU, qtdIO1, qtdIO2, qtdIO3);
		final int tamanhoPrograma = qtdCPU + qtdIO1 + qtdIO2 + qtdIO3;
		final IteradorManipulador<Particao> particaoLivre;

		try {
			particaoLivre = procuraPosicaoMemoria_first(tamanhoPrograma);
		} catch (IllegalBlockSizeException e) {
			// TODO mensagem de erro
			return;
		}

		final int posicaoLivre = particaoLivre.getDado().posicao;

		this.estadoCriacao = new Processo(this.proximoPidDisponivel, prioridade, posicaoLivre, tamanhoPrograma);
		this.allocaMemoria(this.estadoCriacao, tamanhoPrograma, particaoLivre);
		this.referenciaHardware.preencheMemoria(posicaoLivre, programa.toVetor());

		this.listaPrincipal.adiciona(this.estadoCriacao);
		this.estadoCriacao = null;

		this.proximoPidDisponivel++;
	}

	private IteradorManipulador<Particao> procuraPosicaoMemoria_first(int tamanhoPrograma)
			throws IllegalBlockSizeException {

		for (IteradorManipulador<Particao> it = this.listaMemoria.inicio(); it.temProximo(); it.proximo()) {
			// Procura por uma partição vazia
			if (it.getDado().pid == this.posicaoMemoriaVazia) {

				// Verifica se a partição é grande o suficiente para allocação
				if (it.getDado().tamanho >= tamanhoPrograma) {
					return it;
				}
			}
		}

		throw new IllegalBlockSizeException("Não há partição livre grande o suficiente");
	}

	private IteradorManipulador<Particao> procuraPosicaoMemoria_best(int tamanhoPrograma) {
		// TODO refazer
		return null;
	}

	private IteradorManipulador<Particao> procuraPosicaoMemoria_worst(int tamanhoPrograma) {
		// TODO refazer
		return null;
	}

	private void allocaMemoria(Processo processo, int tamanhoPrograma, IteradorManipulador<Particao> particaoLivre) {

		particaoLivre.adicionaAntes(new Particao(processo, tamanhoPrograma, particaoLivre.getDado().posicao));

		final int tamanhoAtualizadoLivre = particaoLivre.getDado().tamanho - tamanhoPrograma;
		if (tamanhoAtualizadoLivre == 0) {// patição agora é "nula"
			particaoLivre.remove();
		} else {
			particaoLivre.getDado().tamanho = tamanhoAtualizadoLivre;
			particaoLivre.getDado().posicao += tamanhoPrograma;

			this.reordenaListaMemoriaVazia();
		}
	}

	private void reordenaListaMemoriaVazia() {
		// TODO Auto-generated method stub

	}

	public void alteraPrioridade(int pid, Processo.prioridade novaPrioridade) {
		// TODO altera prioridade
	}

	public void sinalFinalizacao(int pid) {

		for (Iterador<Processo> it = this.listaPrincipal.inicio(); it.temProximo(); it.proximo()) {
			if (it.getDado().getPID() == pid) {

				// "Sequencia" intruções de finalização que irá execultar
				Vetor<Integer> intrucaoFim = new Vetor<>();
				intrucaoFim.adiciona(Programa.instrucaoFIM);

				// sobre escreve desde a posição
				this.referenciaHardware.preencheMemoria(it.getDado().posicaoIntrucaoAtual(), intrucaoFim);
				return;
			}
		}

		throw new IllegalArgumentException("Pid não encontrado");
	}

	private void matarProcesso() {

		Processo.DadosEstatisticos estatistica;
		estatistica = this.estadoFinalizacao.getDadosEstatisticos();

		this.estatisticaSO.qtdMemoria.adiciona(estatistica.qtdMemoria);
		this.estatisticaSO.tempoDeCPU.adiciona(estatistica.CPU);
		this.estatisticaSO.tempoDePronto.adiciona(estatistica.pronto);
		this.estatisticaSO.tempoDeEsperaES1.adiciona(estatistica.esperaES[0]);
		this.estatisticaSO.tempoDeEsperaES2.adiciona(estatistica.esperaES[1]);
		this.estatisticaSO.tempoDeEsperaES3.adiciona(estatistica.esperaES[2]);
		this.estatisticaSO.tempoDeES1.adiciona(estatistica.ES[0]);
		this.estatisticaSO.tempoDeES2.adiciona(estatistica.ES[1]);
		this.estatisticaSO.tempoDeES3.adiciona(estatistica.ES[2]);

		this.desalocaMemoria(this.estadoFinalizacao.getPID());

		this.estadoFinalizacao = null;
		// Forçando o garbege collector a deleta-lo
	}

	public void desfragmentacaoMemoria() {
		this.desfragmentacaoMemoria(this.listaMemoria.inicio());
	}

	private void desfragmentacaoMemoria(IteradorManipulador<Particao> inicio) {

		IteradorManipulador<Particao> it_vazio;
		for (it_vazio = inicio; it_vazio.temProximo(); it_vazio.proximo()) {
			if (it_vazio.getDado().pid == this.posicaoMemoriaVazia) {
				// Encontrou uma posição vazia

				IteradorManipulador<Particao> it_realocados;
				for (it_realocados = it_vazio; it_realocados.temProximo(); it_realocados.proximo()) {
					// Percorre todod as partições a frente para "realocalas"
					// sem espaços

					final Particao realocada = it_realocados.getDado();
					if (realocada.pid != this.posicaoMemoriaVazia) {
						// A partição é um programa e deve ser realocado
						final Processo processo = realocada.processo;
						final int tamanhoPrograma = processo.getInicioPrograma() - processo.getInicioPrograma();
						final Programa programa = this.programaNaParticao(realocada);

						this.allocaMemoria(processo, tamanhoPrograma, it_vazio);
						this.referenciaHardware.preencheMemoria(it_vazio.getDado().posicao, programa.toVetor());
					} else {
						// encontrou mais uma posição vazia na memória
						this.desfragmentacaoMemoria(it_realocados);
					} // END ELSE partiçãoVazia
				} // END FOR realocando programas
			} // END IF procurando vazio
		} // END FOR procurando vazio
	}// END método

	private Programa programaNaParticao(Particao particao) {
		Programa programa = new Programa();
		for (int i = 0; i > particao.tamanho; i++) {
			final int posicaoIntrucao = particao.posicao + i;
			programa.adicionaIntrucao(this.referenciaHardware.getPosicaoMemoria(posicaoIntrucao));
		}

		return programa;
	}

	private void desalocaMemoria(int pid) {

		for (IteradorManipulador<Particao> it = this.listaMemoria.inicio(); it.temProximo(); it.proximo()) {
			if (it.getDado().pid == pid) {
				it.getDado().pid = this.posicaoMemoriaVazia;
				//"marca" como vazio
				
				this.verificaEspacoLivreAdjacente();
				this.reordenaListaMemoriaVazia();
				return;
			}
		}

		throw new IllegalArgumentException("PID não esta na lista de memória");
	}

	private void verificaEspacoLivreAdjacente() {

		// TODO Fazer testes espaçoVaziaAdjacente
		for (int i = 1; i > (this.listaMemoria.tamanho()); i++) {

			final Particao particaoAnterior = this.listaMemoria.obtem(i - 1);
			if (particaoAnterior.pid == this.posicaoMemoriaVazia) {
				// Verifica se a partição representa memoria vazia

				final Particao particaoPosterior = this.listaMemoria.obtem(i);
				if (particaoPosterior.pid == this.posicaoMemoriaVazia) {
					// Verifica se a posição posterior é vazia também

					particaoAnterior.tamanho += particaoPosterior.tamanho;
					this.listaMemoria.remove(i);// posterior
				} // END if partiçãoPosterior
			} // END if partiçãoAnterior
		} // END for
	}// END método

	public void execultarProcessos() {

		final int reservadoParaAlta = (int) (this.referenciaHardware.getClockCPU() * this.porcentagemCPUAlta);
		final int reservadoParaMedia = (int) (this.referenciaHardware.getClockCPU() * this.porcentagemCPUMedia);
		final int reservadoParaBaixa = (int) (this.referenciaHardware.getClockCPU() * this.porcentagemCPUBaixa);

		// I-O - (necessario ir primeiro pois o filtro de prioridades assume que
		// todos os processos de I/O foram retirados e não trata tal condição
		// ,ou seja, filtro de prioridade verifica APENAS a prioridade
		Fila<Processo> filaEsperaES1 = this.filtroIntrucaoAtual(Programa.instrucaoES1);
		Fila<Processo> filaEsperaES2 = this.filtroIntrucaoAtual(Programa.instrucaoES2);
		Fila<Processo> filaEsperaES3 = this.filtroIntrucaoAtual(Programa.instrucaoES3);
		// PRIORIDADE
		Fila<Processo> filaProntoAlta = this.filtroPrioridade(Processo.prioridade.ALTA);
		Fila<Processo> filaProntoMedia = this.filtroPrioridade(Processo.prioridade.MEDIA);
		Fila<Processo> filaProntoBaixa = this.filtroPrioridade(Processo.prioridade.BAIXA);

		// Processamento Alta
		int tempoEsperaAtual = 0;
		int clockDisponivel = reservadoParaAlta;
		int clockSobrado = execultaFila(filaProntoAlta, Programa.instrucaoCPU, clockDisponivel, tempoEsperaAtual);

		// Processamento Media
		tempoEsperaAtual = reservadoParaAlta - clockSobrado;
		clockDisponivel = reservadoParaMedia + clockSobrado;
		clockSobrado = execultaFila(filaProntoMedia, Programa.instrucaoCPU, clockDisponivel, tempoEsperaAtual);

		// Processamento Baixa
		tempoEsperaAtual = (reservadoParaAlta + reservadoParaMedia) - clockSobrado;
		clockDisponivel = reservadoParaBaixa + clockSobrado;
		execultaFila(filaProntoBaixa, Programa.instrucaoCPU, clockDisponivel, tempoEsperaAtual);

		execultaFila(filaEsperaES1, Programa.instrucaoES1, this.referenciaHardware.getAllClocksES()[0], 0);
		execultaFila(filaEsperaES2, Programa.instrucaoES2, this.referenciaHardware.getAllClocksES()[1], 0);
		execultaFila(filaEsperaES3, Programa.instrucaoES3, this.referenciaHardware.getAllClocksES()[2], 0);

		// Para que as filas possam deixar de existir, mas os processo não
		this.listaPrincipal.adiciona(filaProntoAlta.toVetor());
		this.listaPrincipal.adiciona(filaProntoMedia.toVetor());
		this.listaPrincipal.adiciona(filaProntoBaixa.toVetor());
		this.listaPrincipal.adiciona(filaEsperaES1.toVetor());
		this.listaPrincipal.adiciona(filaEsperaES2.toVetor());
		this.listaPrincipal.adiciona(filaEsperaES3.toVetor());
	}

	private Fila<Processo> filtroIntrucaoAtual(int intrucaoFiltro) {
		Fila<Processo> fila = new Fila<>();

		IteradorManipulador<Processo> it = listaPrincipal.inicio();
		while (it.temProximo()) {
			Processo atual = ((Processo) it.getDado());
			if (this.referenciaHardware.getPosicaoMemoria(atual.posicaoIntrucaoAtual()) == intrucaoFiltro) {

				fila.adiciona(atual);// adiciona a fila, para manter contato
				it.remove();// remove o elemento da lista
			} else {
				it.proximo();
			}
		}

		return fila;
	}

	private Fila<Processo> filtroPrioridade(Processo.prioridade prioridade) {
		Fila<Processo> fila = new Fila<>();

		IteradorManipulador<Processo> it = listaPrincipal.inicio();
		while (it.temProximo()) {
			Processo atual = ((Processo) it.getDado());
			if (atual.getPrioridade().equals(prioridade)) {

				fila.adiciona(atual);// adiciona a fila
				it.remove();// remove o elemento da lista
			} else {
				it.proximo();
			}
		}

		return fila;
	}

	private int execultaFila(Fila<Processo> fila, int tipoDeIntrucao, int clocksDestaFila, int tempoEsperaAtual) {

		int clockRestante = clocksDestaFila;
		while (fila.tamanho() != 0 && clockRestante != 0) {

			final int clockIndividual = (int) (clockRestante / fila.tamanho());
			clockRestante = 0;// para servir de accomulador

			for (int i = 0; i < fila.tamanho(); i++) {

				// faz "copia" do processo para processamento
				this.processando = fila.consultaProximoElemento();

				// remove ela dos registros da fila, pois esta em
				// "processamento"
				fila.remove();

				this.processando.incrementaEspera(tempoEsperaAtual, tipoDeIntrucao);
				// incrementa as variaveis de estatistica
				// TODO fazer estatistica de espera APÓS ter sido execultado

				int clockNaoUsadosNestaOperacao = this.referenciaHardware.usarProcessamentoHardware(clockIndividual,
						tipoDeIntrucao, this.processando);
				// faz o processamento

				final int instrucaoAtual = this.referenciaHardware
						.getPosicaoMemoria(this.processando.posicaoIntrucaoAtual());

				if (instrucaoAtual == tipoDeIntrucao) {
					// se mesmo depois do processamento pertencer a esta
					// fila(permance neste estado)
					fila.adiciona(this.processando);

				} else {
					if (instrucaoAtual != Programa.instrucaoFIM) {
						// agora passa a ser parte de outra fila(outro estado)
						this.listaPrincipal.adiciona(this.processando);

					} else {
						// fim do programa
						this.estadoFinalizacao = this.processando;
						this.processando = null;
						this.matarProcesso();
					}
				}

				this.processando = null;
				tempoEsperaAtual += (clockIndividual - clockNaoUsadosNestaOperacao);
				clockRestante += clockNaoUsadosNestaOperacao;
			} // END FOR
		} // END WHILE

		return clockRestante;
	}

	public void pausarProcesso(int pid) {
		for (IteradorManipulador<Processo> it = this.listaPrincipal.inicio(); it.temProximo(); it.proximo()) {

			final int pidProcesso = it.getDado().getPID();
			if (pidProcesso == pid) {
				this.listaPausado.adiciona(pidProcesso, it.getDado());
				it.remove();
				return;
			}
		}

		throw new IllegalArgumentException("Nenhum processo com este PID está ativo");
	}

	public void resumePorcesso(int pid) {

		if (!this.listaPausado.contem(pid)) {
			throw new IllegalArgumentException("Nenhum processo com este PID está pausado");
		}

		this.listaPrincipal.adiciona(this.listaPausado.obtem(pid));
		this.listaPausado.remove(pid);
	}
}