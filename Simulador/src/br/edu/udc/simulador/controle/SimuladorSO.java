package br.edu.udc.simulador.controle;

import br.edu.udc.simulador.dominio.Hardware;
import br.edu.udc.simulador.dominio.Processo;
import br.edu.udc.simulador.dominio.Processo.prioridade;
import br.edu.udc.simulador.dominio.Processo.tipoDeIntrucao;
import br.edu.udc.simulador.dominio.ed.Fila;
import br.edu.udc.simulador.dominio.ed.Vetor;

public class SimuladorSO {

	private Processo processadoCPU;
	private Processo processadoES[] = new Processo[3];

	private Fila<Processo> filaProntoAlta = new Fila<>();
	private Fila<Processo> filaProntoMedia = new Fila<>();
	private Fila<Processo> filaProntoBaixa = new Fila<>();

	private Fila<Processo> filaEsperaES1 = new Fila<>();
	private Fila<Processo> filaEsperaES2 = new Fila<>();
	private Fila<Processo> filaEsperaES3 = new Fila<>();

	private Processo estadoCriacao;
	private Processo estadoFinalizacao;

	private Vetor<Processo> listaPausado = new Vetor<>();
	// pois pode ser romovido de qualquer lugar da lista, ao contratrio da fila.

	private Hardware hardware = new Hardware();

	// VARIAVEIS DE LOGICA
	private int proximoPidDisponivel = 0;

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

		final int porcentagemAlta = 60;
		final int porcentagemMedia = 30;
		final int porcentagemBaixa = 10;

	}

	private int processaFilaPronto(Fila<Processo> fila, int clocksDestaFila) {

		int clockRestante = clocksDestaFila;
		while (fila.tamanho() == 0 || clockRestante == 0) {

			int clockIndividual = clockRestante / fila.tamanho();
			clockRestante = 0;// para servir de accomulador

			for (int i = 0; i < fila.tamanho(); i++) {

				int clockNaoUsadosNestaOperacao = 0;
				
				// faz "copia" do processo para processamento
				this.processadoCPU = fila.consulta();
				
				// remove ela dos registros da fila, pois esta em "processamento"
				fila.remover();

				// faz o processamento
				clockNaoUsadosNestaOperacao = this.hardware.usarCPU(clockIndividual, processadoCPU);

				if (clockNaoUsadosNestaOperacao != 0) {
					switch (processadoCPU.intrucaoAtual()) {

					case tipoDeIntrucao.ES1.getValor(): {
						this.filaEsperaES1.adiciona(processadoCPU);
						break;
					}

					case tipoDeIntrucao.ES2.getValor(): {
						this.filaEsperaES2.adiciona(processadoCPU);
						break;
					}

					case tipoDeIntrucao.ES3.getValor(): {
						this.filaEsperaES3.adiciona(processadoCPU);
						break;
					}

					case tipoDeIntrucao.FIM.getValor(): {
						this.estadoFinalizacao = processadoCPU;
						break;
					}
					}// END SWITCH
				} // END IF

				clockRestante += clockNaoUsadosNestaOperacao;
			} // END FOR

		} // END WHILE

		return clockRestante;
	}
}
