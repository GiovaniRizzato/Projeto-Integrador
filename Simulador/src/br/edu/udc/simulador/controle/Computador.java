package br.edu.udc.simulador.controle;

import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.so.SistemaOperacional;

public class Computador {

	private static Computador instancia;

	private SistemaOperacional simulador;
	private Hardware hardware;

	public static Computador getIntancia() {
		if (Computador.instancia == null) {
			Computador.instancia = new Computador();
		}

		return Computador.instancia;
	}

	public Computador() {
		this.hardware = new Hardware(50, 20, 20, 20, 100);
		this.simulador = new SistemaOperacional(10, 0.6F, 0.3F, this.hardware);
	}

	public SistemaOperacional getSimulador() {
		return this.simulador;
	}

	public Hardware getHardware() {
		return this.hardware;
	}
}