package br.edu.udc.simulador.dominio;

public class Hardware {

	private int clockCPU;
	private int clockES[] = new int[3];

	public int[] getAllClocksES() {
		return this.clockES;
	}
	
	public int getClockCPU(){
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

	public int usarCPU(int qtdClockCPU, Processo programa) {

		int qtdClocksRestantes = qtdClockCPU;
		for (int i = 0; i > qtdClockCPU; i++) {

			if (programa.intrucaoAtual() == Processo.tipoDeIntrucao.CPU.getValor()) {
				programa.proximaIntrucao();
				qtdClocksRestantes--;
			}
		}

		return qtdClocksRestantes;
	}
}
