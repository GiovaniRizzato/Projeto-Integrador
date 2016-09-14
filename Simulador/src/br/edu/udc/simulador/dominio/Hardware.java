package br.edu.udc.simulador.dominio;

public class Hardware {

	private int clockCPU;
	private int clockES[] = new int[3];

	public Hardware(int clockCPU, int clockES1, int clockES2, int clockES3) {
		this.clockCPU = clockCPU;
		this.clockES[0] = clockES1;
		this.clockES[1] = clockES2;
		this.clockES[2] = clockES3;
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

	public int usarHardware(int qtdClockMaximo, int tipoDeIntrucao, Processo programa) {

		int qtdClocksRestantes = qtdClockMaximo;
		for (int i = 0; i < qtdClockMaximo; i++) {

			if (programa.intrucaoAtual() == tipoDeIntrucao) {
				programa.proximaIntrucao();
				qtdClocksRestantes--;
			} else {
				return qtdClocksRestantes;
			}
		}
		
		return qtdClocksRestantes;
	}
}
