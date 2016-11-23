package br.edu.udc.simulador.so.escalonador;

import br.edu.udc.ed.fila.Fila;
import br.edu.udc.ed.fila.encadeada.FilaEncadeada;
import br.edu.udc.ed.iteradores.IteradorManipulador;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.processo.Programa;

public class EscalonadorProcessos {

	// Abstração dos estados
	private Processo processando;

	private Float porcentagemCPUAlta;
	private Float porcentagemCPUMedia;
	private Float porcentagemCPUBaixa;

	// referencia
	private Hardware hardware;
	private Lista<Processo> listaPrincipal;

	public EscalonadorProcessos(Float porceAlta, Float porceMedia, Float porceBaixa, Lista<Processo> listaPrincipal,
			Hardware hardware) {
		this.hardware = hardware;

		this.porcentagemCPUAlta = porceAlta;
		this.porcentagemCPUMedia = porceMedia;
		this.porcentagemCPUBaixa = porceBaixa;
		this.listaPrincipal = listaPrincipal;
	}

	public void execultarProcessos() {

		final int reservadoParaAlta = (int) (this.hardware.getClockCPU() * this.porcentagemCPUAlta);
		final int reservadoParaMedia = (int) (this.hardware.getClockCPU() * this.porcentagemCPUMedia);
		final int reservadoParaBaixa = (int) (this.hardware.getClockCPU() * this.porcentagemCPUBaixa);

		// I-O - (necessario ir primeiro pois o filtro de prioridades assume que
		// todos os processos de I/O foram retirados e não trata tal condição
		// ,ou seja, filtro de prioridade verifica APENAS a prioridade
		Fila<Processo> filaEsperaES1 = this.filtroIntrucaoAtual(Programa.instrucaoES1);
		Fila<Processo> filaEsperaES2 = this.filtroIntrucaoAtual(Programa.instrucaoES2);
		Fila<Processo> filaEsperaES3 = this.filtroIntrucaoAtual(Programa.instrucaoES3);
		// PRIORIDADE
		Fila<Processo> filaProntoAlta = this.filtroPrioridade(Processo.Prioridade.ALTA);
		Fila<Processo> filaProntoMedia = this.filtroPrioridade(Processo.Prioridade.MEDIA);
		Fila<Processo> filaProntoBaixa = this.filtroPrioridade(Processo.Prioridade.BAIXA);

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

		// processamento de ES's
		final int clockES1 = this.hardware.getAllClocksES()[0];
		final int clockES2 = this.hardware.getAllClocksES()[1];
		final int clockES3 = this.hardware.getAllClocksES()[2];
		execultaFila(filaEsperaES1, Programa.instrucaoES1, clockES1, 0);
		execultaFila(filaEsperaES2, Programa.instrucaoES2, clockES2, 0);
		execultaFila(filaEsperaES3, Programa.instrucaoES3, clockES3, 0);

		// Para que as filas possam deixar de existir, mas os processo não
		this.listaPrincipal.adiciona(filaProntoAlta);
		this.listaPrincipal.adiciona(filaProntoMedia);
		this.listaPrincipal.adiciona(filaProntoBaixa);
		this.listaPrincipal.adiciona(filaEsperaES1);
		this.listaPrincipal.adiciona(filaEsperaES2);
		this.listaPrincipal.adiciona(filaEsperaES3);
	}

	private Fila<Processo> filtroIntrucaoAtual(int intrucaoFiltro) {
		Fila<Processo> fila = new FilaEncadeada<>();

		IteradorManipulador<Processo> it = this.listaPrincipal.inicio();
		while (it.temProximo()) {
			Processo atual = ((Processo) it.getDado());
			if (this.hardware.getPosicaoMemoria(atual.posicaoIntrucaoAtual()) == intrucaoFiltro) {

				fila.adiciona(atual);// adiciona a fila, para manter contato
				it.remove();// remove o elemento da lista
			} else {
				it.proximo();
			}
		}

		return fila;
	}

	private Fila<Processo> filtroPrioridade(Processo.Prioridade prioridade) {
		Fila<Processo> fila = new FilaEncadeada<>();

		IteradorManipulador<Processo> it = this.listaPrincipal.inicio();
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
				this.processando = fila.remove();
				// remove ela dos registros da fila, pois esta em
				// "processamento"

				this.processando.incrementaEspera(tempoEsperaAtual, tipoDeIntrucao);
				// incrementa as variaveis de estatistica
				// TODO fazer estatistica de espera APÓS ter sido execultado

				int clockNaoUsadosNestaOperacao = this.hardware.usarProcessamentoHardware(clockIndividual,
						tipoDeIntrucao, this.processando);
				// faz o processamento

				final int instrucaoAtual = this.hardware
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
						Computador.getIntancia().getSimulador().matarProcesso(this.processando);
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
