package br.edu.udc.simulador.so.escalonador;

import br.edu.udc.ed.fila.Fila;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.processo.Programa;

public class TimeSharing extends EscalonadorProcessos {

	public TimeSharing(Float porceAlta, Float porceMedia, Float porceBaixa, Lista<Processo> listaPrincipal,
			Hardware hardware) {
		super(porceAlta, porceMedia, porceBaixa, listaPrincipal, hardware);
	}

	@Override
	protected void escalonar() {

		final int reservadoParaAlta = (int) (super.hardware.getClockCPU() * this.porcentagemCPUAlta);
		final int reservadoParaMedia = (int) (super.hardware.getClockCPU() * this.porcentagemCPUMedia);
		final int reservadoParaBaixa = (int) (super.hardware.getClockCPU() * this.porcentagemCPUBaixa);

		// I-O - (necessario ir primeiro pois o filtro de prioridades assume que
		// todos os processos de I/O foram retirados e não trata tal condição
		// ,ou seja, filtro de prioridade verifica APENAS a prioridade
		Fila<Processo> filaEsperaES1 = super.filtroIntrucaoAtual(Programa.instrucaoES1);
		Fila<Processo> filaEsperaES2 = super.filtroIntrucaoAtual(Programa.instrucaoES2);
		Fila<Processo> filaEsperaES3 = super.filtroIntrucaoAtual(Programa.instrucaoES3);
		// PRIORIDADE
		Fila<Processo> filaProntoAlta = super.filtroPrioridade(Processo.Prioridade.ALTA);
		Fila<Processo> filaProntoMedia = super.filtroPrioridade(Processo.Prioridade.MEDIA);
		Fila<Processo> filaProntoBaixa = super.filtroPrioridade(Processo.Prioridade.BAIXA);

		// Processamento Alta
		int tempoEsperaAtual = 0;
		int clockDisponivel = reservadoParaAlta;
		int clockSobrado = this.execultaFila(filaProntoAlta, Programa.instrucaoCPU, clockDisponivel, tempoEsperaAtual);

		// Processamento Media
		tempoEsperaAtual = reservadoParaAlta - clockSobrado;
		clockDisponivel = reservadoParaMedia + clockSobrado;
		clockSobrado = this.execultaFila(filaProntoMedia, Programa.instrucaoCPU, clockDisponivel, tempoEsperaAtual);

		// Processamento Baixa
		tempoEsperaAtual = (reservadoParaAlta + reservadoParaMedia) - clockSobrado;
		clockDisponivel = reservadoParaBaixa + clockSobrado;
		this.execultaFila(filaProntoBaixa, Programa.instrucaoCPU, clockDisponivel, tempoEsperaAtual);

		// processamento de ES's
		final int clockES1 = super.hardware.getAllClocksES()[0];
		final int clockES2 = super.hardware.getAllClocksES()[1];
		final int clockES3 = super.hardware.getAllClocksES()[2];
		this.execultaFila(filaEsperaES1, Programa.instrucaoES1, clockES1, 0);
		this.execultaFila(filaEsperaES2, Programa.instrucaoES2, clockES2, 0);
		this.execultaFila(filaEsperaES3, Programa.instrucaoES3, clockES3, 0);

		// Para que as filas possam deixar de existir, mas os processo não
		super.listaPrincipal.adiciona(filaProntoAlta);
		super.listaPrincipal.adiciona(filaProntoMedia);
		super.listaPrincipal.adiciona(filaProntoBaixa);
		super.listaPrincipal.adiciona(filaEsperaES1);
		super.listaPrincipal.adiciona(filaEsperaES2);
		super.listaPrincipal.adiciona(filaEsperaES3);
	}

	private int execultaFila(Fila<Processo> fila, int tipoDeIntrucao, int clocksDestaFila, int tempoEsperaAtual) {

		int clockRestante = clocksDestaFila;
		while (fila.tamanho() != 0 && clockRestante != 0) {

			final int clockIndividual = (int) (clockRestante / fila.tamanho());
			clockRestante = 0;// para servir de accomulador

			for (int i = 0; i < fila.tamanho(); i++) {

				// faz "copia" do processo para processamento
				super.processando = fila.remove();
				// remove ela dos registros da fila, pois esta em
				// "processamento"

				super.processando.incrementaEspera(tempoEsperaAtual, tipoDeIntrucao);
				// Incrementa as variaveis de estatistica
				final int posicaoIntrucaoAnterior = super.processando.posicaoIntrucaoAtual();
				// Para fazer a estatistica de intruções feitas

				int clockNaoUsadosNestaOperacao = super.hardware.usarProcessamentoHardware(clockIndividual,
						tipoDeIntrucao, this.processando);
				// faz o processamento

				// Coletando todos as intruções feitas para fazer a estatistica
				final int posicaoIntrucaoPosterior = super.processando.posicaoIntrucaoAtual();
				final int quantidadeIntrucoes = posicaoIntrucaoPosterior - posicaoIntrucaoAnterior - 1;
				
				if (quantidadeIntrucoes > 0) {
					final Integer[] intrucoesExecultadas = new Integer[quantidadeIntrucoes];
					for (int j = 0; j < quantidadeIntrucoes; j++) {
						final int instrucao = super.hardware.getPosicaoMemoria(posicaoIntrucaoAnterior + j);
						intrucoesExecultadas[j] = instrucao;
					}

					super.processando.incrementaEstatisticaProcessada(intrucoesExecultadas);
				}

				final int instrucaoAtual = super.hardware.getPosicaoMemoria(this.processando.posicaoIntrucaoAtual());

				if (instrucaoAtual == tipoDeIntrucao) {
					// se mesmo depois do processamento pertencer a esta
					// fila(permance neste estado)
					fila.adiciona(this.processando);

				} else {
					if (instrucaoAtual != Programa.instrucaoFIM) {
						// agora passa a ser parte de outra fila(outro estado)
						super.listaPrincipal.adiciona(this.processando);

					} else {
						// fim do programa
						Computador.getInstancia().getSimulador().matarProcesso(this.processando);
					}
				}

				this.processando = null;
				tempoEsperaAtual += (clockIndividual - clockNaoUsadosNestaOperacao);
				clockRestante += clockNaoUsadosNestaOperacao;
			} // END FOR
		} // END WHILE

		return clockRestante;
	}
}
