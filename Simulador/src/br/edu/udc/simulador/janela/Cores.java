package br.edu.udc.simulador.janela;

import java.awt.Color;
import br.edu.udc.ed.mapa.Mapa;
import br.edu.udc.simulador.so.SimuladorSO;

public class Cores {

	private Mapa<Integer, Color> silo = new Mapa<>();

	private static Cores instanciaUnica;

	private Cores() {
	}// Tornar o contrutor privado

	public static Cores getIntancia() {
		if (instanciaUnica == null) {
			Cores.instanciaUnica = new Cores();
			Cores.instanciaUnica.adiciona(SimuladorSO.posicaoMemoriaVazia, Color.white);
			Cores.instanciaUnica.adiciona(SimuladorSO.pidSO, Color.red);
		}

		return Cores.instanciaUnica;
	}

	public void adiciona(Integer pid, Color cor) {
		this.silo.adiciona(pid, cor);
	}

	public void remove(Integer pid) {
		// TODO
	}

	public Color obtem(Integer pid) {
		return this.silo.obtem(pid);
	}
}
