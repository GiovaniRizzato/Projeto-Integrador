package br.edu.udc.simulador;

import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.so.SimuladorSO;

public class Computador {
	
	@SuppressWarnings("unused")
	private SimuladorSO simulador;
	private Hardware hardware;
	
	public Computador(){
		int vetor[] = this.persistencia();
		this.hardware = new Hardware(vetor[0], vetor[1], vetor[2], vetor[3], vetor[4]);
		this.simulador = new SimuladorSO(hardware);
	}
	
	private int[] persistencia() {
		//TODO implementar "pegar" do arquivo
		int vetor[] = new int[5];
		vetor[0] = 5;
		vetor[1] = 5;
		vetor[2] = 5;
		vetor[3] = 5;
		vetor[4] = 5;
		
		return vetor;
	}
}