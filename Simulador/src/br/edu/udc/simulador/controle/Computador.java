package br.edu.udc.simulador.controle;

import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.so.SimuladorSO;
import br.edu.udc.simulador.so.SistemaOperacional;

public class Computador {

	private static Computador instancia;

	@SuppressWarnings("unused")
	private SistemaOperacional simulador;
	private Hardware hardware;

	public static Computador getIntancia() {
		if (Computador.instancia == null) {
			Computador.instancia = new Computador();
		}

		return Computador.instancia;
	}

	public Computador() {
		this.hardware = new Hardware(10, 10, 10, 10, 100);
		this.simulador = new SistemaOperacional(10, 0.6F, 0.3F, this.hardware);
	}

	public SistemaOperacional getSimulador() {
		return this.simulador;
	}

	public Hardware getHardware() {
		return this.hardware;
	}
}