package br.edu.udc.simulador.dominio;

import br.edu.udc.simulador.dominio.Processo.prioridade;
import br.edu.udc.simulador.dominio.ed.Fila;
import br.edu.udc.simulador.dominio.ed.Vetor;

public class SimuladorSO {

	private Processo processado;

	private Fila<Processo> filaProntoAlta = new Fila<>();
	private Fila<Processo> filaProntoMedia = new Fila<>();
	private Fila<Processo> filaProntoBaixa = new Fila<>();

	private Fila<Processo> filaEsperaES1 = new Fila<>();
	private Fila<Processo> filaEsperaES2 = new Fila<>();
	private Fila<Processo> filaEsperaES3 = new Fila<>();

	private Processo estadoCriacao;
	private Processo estadoFinalizacao;
	//TODO fazer metodo para retirar estatistca do processo

	private Vetor<Processo> listaPausado = new Vetor<>();
	//TODO fazer metodo para pausar e resumir processo

	private Hardware hardware;

	public SimuladorSO(int clockCPU, int clockES1, int clockES2, int clockES3) {
		hardware = new Hardware(clockCPU, clockES1, clockES2, clockES3);
	}

	// VARIAVEIS DE LOGICA
	private int proximoPidDisponivel = 0;
	
	public int qtdProcessosAtivos(){
		return 	this.filaProntoAlta.tamanho() +
				this.filaProntoMedia.tamanho() +
				this.filaProntoBaixa.tamanho() +
				this.filaEsperaES1.tamanho() +
				this.filaEsperaES2.tamanho() +
				this.filaEsperaES3.tamanho();
	}

	public Processo[] listaTodos(){
		Processo[] listaDeTodosProcessos = new Processo[qtdProcessosAtivos()];
		//TODO terminar de fazer metodo que retorna uma lista de todos os processos
		return null;
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

	public void processaFilas() {

		final double porcentagemAlta = 0.6;
		final double porcentagemMedia = 0.3;
		final double porcentagemBaixa = 0.1;

		int clockSobrado = processaFilaPronto(filaProntoAlta, Processo.instrucaoCPU,
				(this.hardware.getClockCPU() * porcentagemAlta));

		clockSobrado = processaFilaPronto(filaProntoMedia, Processo.instrucaoCPU,
				(this.hardware.getClockCPU() * porcentagemMedia) + clockSobrado);

		processaFilaPronto(filaProntoBaixa, Processo.instrucaoCPU,
				(this.hardware.getClockCPU() * porcentagemBaixa) + clockSobrado);

		processaFilaPronto(filaEsperaES1, Processo.instrucaoES1, this.hardware.getAllClocksES()[0]);
		processaFilaPronto(filaEsperaES2, Processo.instrucaoES2, this.hardware.getAllClocksES()[1]);
		processaFilaPronto(filaEsperaES3, Processo.instrucaoES3, this.hardware.getAllClocksES()[2]);
	}

	private int processaFilaPronto(Fila<Processo> fila, int tipoDeIntrucao, double clocksDestaFila) {

		int clockRestante = (int) clocksDestaFila;
		while (fila.tamanho() != 0 && clockRestante != 0) {

			int clockIndividual = (int) (clockRestante / fila.tamanho());
			clockRestante = 0;// para servir de accomulador

			for (int i = 0; i < fila.tamanho(); i++) {

				int clockNaoUsadosNestaOperacao = 0;

				// faz "copia" do processo para processamento
				this.processado = fila.consulta();

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
					break;
				}
				}// END SWITCH

				this.processado = null;
				clockRestante += clockNaoUsadosNestaOperacao;
			} // END FOR

		} // END WHILE

		return (int) clockRestante;
	}
}
