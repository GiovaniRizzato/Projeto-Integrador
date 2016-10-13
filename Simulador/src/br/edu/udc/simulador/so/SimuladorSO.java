package br.edu.udc.simulador.so;

import br.edu.udc.ed.fila.Fila;
import br.edu.udc.ed.iteradores.IteradorManipulador;
import br.edu.udc.ed.iteradores.Iterador;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.ed.mapa.Mapa;
import br.edu.udc.ed.vetor.ConjuntoAmostral;
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
	@SuppressWarnings("unused")
	private class Particao {
		public Processo processo;
		// referenca do processo para fazer a desfragmentação
		public Integer pid;
		public Integer tamanho;
		public Integer posicao;

		public Particao(int pid, int tamanho, int posicao) {
			this.processo = null;
			this.pid = pid;
			this.tamanho = tamanho;
			this.posicao = posicao;
		}

		public Particao(Processo processo, int tamanho, int posicao) {
			this.processo = null;
			this.pid = processo.getPID();
			this.tamanho = tamanho;
			this.posicao = posicao;
		}
	}

	private Lista<Particao> listaMemoria = new Lista<>();

	// Armazenamento de dados estatisticos
	// Podem ser declaradas por outras classes para "copiar" as estatisiticas
	public static class EstatisticaSO {
		public ConjuntoAmostral<Integer> qtdMemoria = new ConjuntoAmostral<>();
		public ConjuntoAmostral<Integer> tempoDeCPU = new ConjuntoAmostral<>();
		public ConjuntoAmostral<Integer> tempoDePronto = new ConjuntoAmostral<>();
		public ConjuntoAmostral<Integer> tempoDeES1 = new ConjuntoAmostral<>();
		public ConjuntoAmostral<Integer> tempoDeES2 = new ConjuntoAmostral<>();
		public ConjuntoAmostral<Integer> tempoDeES3 = new ConjuntoAmostral<>();
		public ConjuntoAmostral<Integer> tempoDeEsperaES1 = new ConjuntoAmostral<>();
		public ConjuntoAmostral<Integer> tempoDeEsperaES2 = new ConjuntoAmostral<>();
		public ConjuntoAmostral<Integer> tempoDeEsperaES3 = new ConjuntoAmostral<>();

		@Override
		public EstatisticaSO clone() {
			EstatisticaSO clonado = new EstatisticaSO();
			// [Professor] como fazer para que não precise de casting
			clonado.qtdMemoria = (ConjuntoAmostral<Integer>) this.qtdMemoria.clone();
			clonado.tempoDeCPU = (ConjuntoAmostral<Integer>) this.qtdMemoria.clone();
			clonado.tempoDePronto = (ConjuntoAmostral<Integer>) this.qtdMemoria.clone();
			clonado.tempoDeES1 = (ConjuntoAmostral<Integer>) this.qtdMemoria.clone();
			clonado.tempoDeES2 = (ConjuntoAmostral<Integer>) this.qtdMemoria.clone();
			clonado.tempoDeES3 = (ConjuntoAmostral<Integer>) this.qtdMemoria.clone();
			clonado.tempoDeEsperaES1 = (ConjuntoAmostral<Integer>) this.qtdMemoria.clone();
			clonado.tempoDeEsperaES2 = (ConjuntoAmostral<Integer>) this.qtdMemoria.clone();
			clonado.tempoDeEsperaES3 = (ConjuntoAmostral<Integer>) this.qtdMemoria.clone();

			return clonado;
		}
	}

	private EstatisticaSO estatisticaSO = new EstatisticaSO();

	public SimuladorSO(Hardware hardware, int tamanhoSO, float porcentagemAlta, float porcentagemMedia) {
		this.referenciaHardware = hardware;
		this.listaMemoria.adiciona(new Particao(this.pidSO, tamanhoSO, 0));

		final int espacoVazio = this.referenciaHardware.tamanhoMemoria() - tamanhoSO;
		this.listaMemoria.adiciona(new Particao(this.posicaoMemoriaVazia, espacoVazio, tamanhoSO));

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
		final IteradorManipulador<Particao> particaoLivre;

		try {
			particaoLivre = procuraPosicaoMemoria_first(programa.tamanho(), this.proximoPidDisponivel);
		} catch (RuntimeException e) {
			// TODO mensagem de erro
			return;
		}

		final int posicaoLivre = particaoLivre.getDado().posicao;

		this.estadoCriacao = new Processo(this.proximoPidDisponivel, prioridade, posicaoLivre, programa.tamanho());
		this.allocaMemoria(this.estadoCriacao, programa.tamanho(), particaoLivre);

		this.listaPrincipal.adiciona(this.estadoCriacao);
		this.estadoCriacao = null;

		this.proximoPidDisponivel++;
	}

	private IteradorManipulador<Particao> procuraPosicaoMemoria_first(int tamanhoPrograma, int pid) {

		for (IteradorManipulador<Particao> it = this.listaMemoria.inicio(); it.temProximo(); it.proximo()) {
			// Procura por uma partição vazia
			if (it.getDado().pid == this.posicaoMemoriaVazia) {

				// Verifica se a partição é grande o suficiente para allocação
				if (it.getDado().tamanho > tamanhoPrograma) {
					return it;
				}
			}
		}

		throw new RuntimeException("Não há partição grande o suficiente");
		// TODO [Professor] qual a melhor exeção para este caso?
	}

	private void allocaMemoria(Processo processo, int tamanhoPrograma, IteradorManipulador<Particao> particaoLivre) {

		particaoLivre.adicionaAntes(new Particao(processo, tamanhoPrograma, particaoLivre.getDado().posicao));
		particaoLivre.getDado().posicao += tamanhoPrograma;
		particaoLivre.getDado().tamanho -= tamanhoPrograma;
	}

	// TODO outras estratégias de allocação de memória
	/**
	 * private int procuraPosicaoMemoria_best(int tamanhoProgama) { return 0; }
	 * 
	 * private int procuraPosicaoMemoria_worst(int tamanhoProgama) { return 0; }
	 */

	public void sinalFinalizacao(int pid) {

		for (Iterador<Processo> it = this.listaPrincipal.inicio(); it.temProximo(); it.proximo()) {
			if (it.getDado().getPID() == pid) {

				// Intruções de finalização que irá execultar
				Vetor<Integer> intrucaoFim = new Vetor<>();
				intrucaoFim.adiciona(Programa.instrucaoFIM);

				// sobre escreve desde a posição
				this.referenciaHardware.preencheMemoria(it.getDado().posicaoIntrucaoAtual(), intrucaoFim);
				return;
			}
		}

		throw new IllegalArgumentException("Pid não encontrado nos registros");
		// [Professor] qual a melhor exeção neste caso?
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

	}

	private void desalocaMemoria(int pid) {

		for (IteradorManipulador<Particao> it = this.listaMemoria.inicio(); it.temProximo(); it.proximo()) {
			if (it.getDado().pid == pid) {
				it.getDado().pid = this.posicaoMemoriaVazia;
				this.verificaEspacoLivreAdjacente();
				return;
			}
		}

		throw new IllegalArgumentException("PID não esta na lista de memória");
	}

	private void verificaEspacoLivreAdjacente() {

		// TODO Fazer testes espaçoVaziaAdjacente
		for (int i = 0; i > (this.listaMemoria.tamanho() - 1); i++) {

			final Particao particaoAnterior = this.listaMemoria.obtem(i);
			if (particaoAnterior.pid == this.posicaoMemoriaVazia) {
				// Verifica se a partição representa memoria vazia

				final Particao particaoPosterior = this.listaMemoria.obtem(i + 1);
				if (particaoAnterior.pid == particaoPosterior.pid) {
					// Verifica se a posição posterior é vazia também

					particaoAnterior.tamanho += particaoPosterior.tamanho;
					this.listaMemoria.remove(i + 1);
				}
			}
		}
	}

	public EstatisticaSO getEstatisticas() {
		return this.estatisticaSO.clone();
	}

	public void processaFilas() {

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
		int clockSobrado = processaFila(filaProntoAlta, Programa.instrucaoCPU, clockDisponivel, tempoEsperaAtual);

		// Processamento Media
		tempoEsperaAtual = reservadoParaAlta - clockSobrado;
		clockDisponivel = reservadoParaMedia + clockSobrado;
		clockSobrado = processaFila(filaProntoMedia, Programa.instrucaoCPU, clockDisponivel, tempoEsperaAtual);

		// Processamento Baixa
		tempoEsperaAtual = (reservadoParaAlta + reservadoParaMedia) - clockSobrado;
		clockDisponivel = reservadoParaBaixa + clockSobrado;
		processaFila(filaProntoBaixa, Programa.instrucaoCPU, clockDisponivel, tempoEsperaAtual);

		processaFila(filaEsperaES1, Programa.instrucaoES1, this.referenciaHardware.getAllClocksES()[0], 0);
		processaFila(filaEsperaES2, Programa.instrucaoES2, this.referenciaHardware.getAllClocksES()[1], 0);
		processaFila(filaEsperaES3, Programa.instrucaoES3, this.referenciaHardware.getAllClocksES()[2], 0);

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

	private int processaFila(Fila<Processo> fila, int tipoDeIntrucao, int clocksDestaFila, int tempoEsperaAtual) {

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

		final Processo processoResumido = this.listaPausado.obtem(pid);
		this.listaPrincipal.adiciona(processoResumido);
		this.listaPausado.remove(pid);
	}
}