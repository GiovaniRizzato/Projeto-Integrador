package br.edu.udc.simulador.hardware;

import javax.management.RuntimeErrorException;

import br.edu.udc.simulador.processo.Processo;

public class Hardware {

	private int clockCPU;
	private int clockES[] = new int[3];
	private Memoria memoria;
	
	public static int posicaoMemoriaVazia = -1;

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
	
	public int allocarMemoria(int qtdMemoria, int pid){
		
		try{
			return memoria.alocaMemoria(pid, qtdMemoria);
		}catch(IllegalArgumentException e){
			throw new RuntimeErrorException(null, "Não foi possível allocar");
		}
	}
}
