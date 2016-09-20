package br.edu.udc.simulador.dominio;

import br.edu.udc.simulador.dominio.Processo.prioridade;
import br.edu.udc.simulador.dominio.ed.Fila;
import br.edu.udc.simulador.dominio.ed.Iterator;
import br.edu.udc.simulador.dominio.ed.Vetor;

public class SimuladorSO {

	private Processo processado;
	
	//TODO trocar para listas filtradas
	//o conceito de lista filtrada e que existe apenas uma LISTA de processos
	//prioridades são feitas a partir de funções que romovem da lista principal

	private Fila<Processo> filaProntoAlta = new Fila<>();
	private Fila<Processo> filaProntoMedia = new Fila<>();
	private Fila<Processo> filaProntoBaixa = new Fila<>();

	private Fila<Processo> filaEsperaES1 = new Fila<>();
	private Fila<Processo> filaEsperaES2 = new Fila<>();
	private Fila<Processo> filaEsperaES3 = new Fila<>();

	private Processo estadoCriacao;
	private Processo estadoFinalizacao;
	// TODO Marcar processo para ser finalizado

	private Vetor<Processo> listaPausado = new Vetor<>();
	// TODO Fazer metodo para pausar e resumir processo

	// USAR TO-ARRAY PARA ENCONTRAR OS PROCESSOS DENTRO DAS FILAS

	private Hardware hardware;

	public class EstatisticaSO {
		public Vetor<Integer> qtdMemoria = new Vetor<>();
		public Vetor<Integer> tempoDeCPU = new Vetor<>();
		public Vetor<Integer> tempoDePronto = new Vetor<>();
		public Vetor<Integer> tempoDeES1 = new Vetor<>();
		public Vetor<Integer> tempoDeES2 = new Vetor<>();
		public Vetor<Integer> tempoDeES3 = new Vetor<>();
		public Vetor<Integer> tempoDeEsperaES1 = new Vetor<>();
		public Vetor<Integer> tempoDeEsperaES2 = new Vetor<>();
		public Vetor<Integer> tempoDeEsperaES3 = new Vetor<>();
	}

	EstatisticaSO estatisticaSO = new EstatisticaSO();

	public SimuladorSO(int clockCPU, int clockES1, int clockES2, int clockES3) {
		hardware = new Hardware(clockCPU, clockES1, clockES2, clockES3);
	}

	// VARIAVEL DE LOGICA
	private int proximoPidDisponivel = 0;

	public int qtdProcessosAtivos() {
		return this.filaProntoAlta.tamanho() + this.filaProntoMedia.tamanho() + this.filaProntoBaixa.tamanho()
				+ this.filaEsperaES1.tamanho() + this.filaEsperaES2.tamanho() + this.filaEsperaES3.tamanho();
	}

	public Processo[] listaTodos() {

		Processo[] listaDeTodosProcessos = new Processo[qtdProcessosAtivos()];
		int posicaoAtualArray = 0;
		Iterator<Processo> it;

		// TODO utilizar a função "toArray()" das filas

		//listaDeTodosProcessos = filaProntoAlta.toArray();

		// TODO [PROFESSOR] Exeite algum metodo que faz isso?
		// (que eu não precise implementa-las)
		// listaDeTodosProcessos = listaDeTodosProcessos.concatea(filaProntoMedia.toArray());

		it = this.filaProntoAlta.inicio();
		while (it.temProximo()) {
			listaDeTodosProcessos[posicaoAtualArray] = it.getDado();
			posicaoAtualArray++;
			it.proximo();
		}

		it = this.filaProntoMedia.inicio();
		while (it.temProximo()) {
			listaDeTodosProcessos[posicaoAtualArray] = it.getDado();
			posicaoAtualArray++;
			it.proximo();
		}

		it = this.filaProntoBaixa.inicio();
		while (it.temProximo()) {
			listaDeTodosProcessos[posicaoAtualArray] = it.getDado();
			posicaoAtualArray++;
			it.proximo();
		}

		it = this.filaEsperaES1.inicio();
		while (it.temProximo()) {
			listaDeTodosProcessos[posicaoAtualArray] = it.getDado();
			posicaoAtualArray++;
			it.proximo();
		}

		it = this.filaEsperaES2.inicio();
		while (it.temProximo()) {
			listaDeTodosProcessos[posicaoAtualArray] = it.getDado();
			posicaoAtualArray++;
			it.proximo();
		}

		it = this.filaEsperaES3.inicio();
		while (it.temProximo()) {
			listaDeTodosProcessos[posicaoAtualArray] = it.getDado();
			posicaoAtualArray++;
			it.proximo();
		}

		return listaDeTodosProcessos;
	}

	public void criaNovoProcesso(prioridade prioridade, int qtdMemoria, int qtdCPU, int qtdES1, int qtdES2,
			int qtdES3) {

		estadoCriacao = new Processo(proximoPidDisponivel, prioridade, qtdMemoria, qtdCPU, qtdES1, qtdES2, qtdES3);

		switch (estadoCriacao.getPrioridade()) {
		case ALTA: {
			filaProntoAlta.adiciona(estadoCriacao);
			break;
		}
		case MEDIA: {
			filaProntoMedia.adiciona(estadoCriacao);
			break;
		}
		case BAIXA: {
			filaProntoBaixa.adiciona(estadoCriacao);
			break;
		}
		}

		this.proximoPidDisponivel++;
	}

	public void matarProcesso() {

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

		this.estadoFinalizacao = null;
		// Forçando o garbege collector a deleta-lo
	}

	public EstatisticaSO getEstatisticas() {
		return this.estatisticaSO;
	}

	public void processaFilas() {

		final double porcentagemAlta = 0.6;
		final double porcentagemMedia = 0.3;
		final double porcentagemBaixa = 0.1;

		int clockSobrado = processaFila(filaProntoAlta, Processo.instrucaoCPU,
				(this.hardware.getClockCPU() * porcentagemAlta));

		clockSobrado = processaFila(filaProntoMedia, Processo.instrucaoCPU,
				(this.hardware.getClockCPU() * porcentagemMedia) + clockSobrado);

		processaFila(filaProntoBaixa, Processo.instrucaoCPU,
				(this.hardware.getClockCPU() * porcentagemBaixa) + clockSobrado);

		processaFila(filaEsperaES1, Processo.instrucaoES1, this.hardware.getAllClocksES()[0]);
		processaFila(filaEsperaES2, Processo.instrucaoES2, this.hardware.getAllClocksES()[1]);
		processaFila(filaEsperaES3, Processo.instrucaoES3, this.hardware.getAllClocksES()[2]);
	}

	private int processaFila(Fila<Processo> fila, int tipoDeIntrucao, double clocksDestaFila) {

		int clockRestante = (int) clocksDestaFila;
		while (fila.tamanho() != 0 && clockRestante != 0) {

			int clockIndividual = (int) (clockRestante / fila.tamanho());
			clockRestante = 0;// para servir de accomulador

			for (int i = 0; i < fila.tamanho(); i++) {

				int clockNaoUsadosNestaOperacao = 0;

				// faz "copia" do processo para processamento
				this.processado = fila.consultaProximoElemento();

				// remove ela dos registros da fila, pois esta em
				// "processamento"
				fila.remover();

				clockNaoUsadosNestaOperacao = this.hardware.usarHardware(clockIndividual, tipoDeIntrucao, processado);
				// faz o processamento

				switch (processado.intrucaoAtual()) {
				// coloca o processo na respectiva fila de processamento para o
				// momento

				case Processo.instrucaoCPU: {
					switch (this.processado.getPrioridade()) {
					case ALTA: {
						filaProntoAlta.adiciona(processado);
						break;
					}

					case MEDIA: {
						filaProntoMedia.adiciona(processado);
						break;
					}

					case BAIXA: {
						filaProntoBaixa.adiciona(processado);
						break;
					}
					}

					break;
				}

				case Processo.instrucaoES1: {
					this.filaEsperaES1.adiciona(processado);
					break;
				}

				case Processo.instrucaoES2: {
					this.filaEsperaES2.adiciona(processado);
					break;
				}

				case Processo.instrucaoES3: {
					this.filaEsperaES3.adiciona(processado);
					break;
				}

				case Processo.instrucaoFIM: {
					this.estadoFinalizacao = this.processado;
					this.matarProcesso();
					break;
				}
				}// END SWITCH

				this.processado = null;
				clockRestante += clockNaoUsadosNestaOperacao;
			} // END FOR

		} // END WHILE

		return (int) clockRestante;
	}

	public void pausarProcesso(int pid) {
		if(this.contem(pid)){
			
		}
	}

	private boolean contem(int pid) {
		return false;
		//TODO terminar de fazer se o elemento com pid "pid" está no sistema
	}
}
