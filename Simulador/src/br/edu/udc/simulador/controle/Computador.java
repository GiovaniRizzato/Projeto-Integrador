package br.edu.udc.simulador.controle;

import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.so.SimuladorSO;

public class Computador {

	@SuppressWarnings("unused")
	private SimuladorSO simulador;
	private Hardware hardware;

	public Computador() {
		int vetor[] = this.persistencia();
		this.hardware = new Hardware(vetor[0], vetor[1], vetor[2], vetor[3], vetor[4]);
		this.simulador = new SimuladorSO(hardware, vetor[5], 0.6F, 0.3F);
	}

	private int[] persistencia() {
		// TODO implementar "pegar" do arquivo
		int vetor[] = new int[5];
		vetor[0] = 5;// tamanhoMemoria
		vetor[1] = 5;// clockCPU
		vetor[2] = 5;// clockIO1
		vetor[3] = 5;// clockIO2
		vetor[4] = 5;// clockIO3
		vetor[5] = 1;// tamanhoSO

		return vetor;
	}
}