package br.edu.udc.simulador.hardware;

import br.edu.udc.ed.vetor.Vetor;
import br.edu.udc.simulador.processo.Processo;

public class Hardware {

	private int clockCPU;
	private int clockES[] = new int[3];
	private Memoria memoria;

	public Hardware(int clockCPU, int clockES1, int clockES2, int clockES3, int qtdMemoria) {
		this.clockCPU = clockCPU;
		this.clockES[0] = clockES1;
		this.clockES[1] = clockES2;
		this.clockES[2] = clockES3;
		this.memoria = new Memoria(qtdMemoria);
	}

	public int[] getAllClocksES() {
		return this.clockES;
	}

	public int getClockCPU() {
		return this.clockCPU;
	}

	public void setClockCPU(int clock) {
		this.clockCPU = clock;
	}

	public void setClockES1(int clock) {
		this.clockES[0] = clock;
	}

	public void setClockES2(int clock) {
		this.clockES[1] = clock;
	}

	public void setClockES3(int clock) {
		this.clockES[2] = clock;
	}

	public int getPosicaoMemoria(int posicao) {
		return this.memoria.getPosicaoMemoria(posicao);
	}

	public int usarProcessamentoHardware(int qtdClockMaximo, int tipoDeIntrucao, Processo programa) {

		int qtdClocksRestantes = qtdClockMaximo;
		for (int i = 0; i < qtdClockMaximo; i++) {

			if (this.memoria.getPosicaoMemoria(programa.posicaoIntrucaoAtual()) == tipoDeIntrucao) {
				programa.proximaIntrucao();
				qtdClocksRestantes--;
			} else {
				return qtdClocksRestantes;
			}
		}

		return qtdClocksRestantes;
	}

	public void preencheMemoria(int posicao, Vetor<Integer> programa) throws IllegalArgumentException {

		this.memoria.preencheMemoria(posicao, programa);
	}

	public int tamanhoMemoria() {
		return this.memoria.getMemoria().length;
	}
}
