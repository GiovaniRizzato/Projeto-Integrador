package br.edu.udc.simulador.janela;

import java.awt.Color;
import br.edu.udc.ed.mapa.Mapa;
import br.edu.udc.simulador.so.SimuladorSO;

public class SiloDeCor {

	private Mapa<Integer, Color> silo = new Mapa<>();

	private static SiloDeCor instanciaUnica;

	private SiloDeCor() {
	}// Tornar o contrutor privado

	public static SiloDeCor getIntancia() {
		if (instanciaUnica == null) {
			SiloDeCor.instanciaUnica = new SiloDeCor();
			SiloDeCor.instanciaUnica.adiciona(SimuladorSO.posicaoMemoriaVazia, Color.white);
			SiloDeCor.instanciaUnica.adiciona(SimuladorSO.pidSO, Color.red);
		}

		return SiloDeCor.instanciaUnica;
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
