package br.edu.udc.simulador.janela;

import java.awt.Color;
import br.edu.udc.ed.mapa.Mapa;
import br.edu.udc.simulador.so.SistemaOperacional;

public class Cores {

	private Mapa<Integer, Color> silo = new Mapa<>();

	private static Cores instanciaUnica;

	private Cores() {
	}// Tornar o contrutor privado

	public static Cores getIntancia() {
		if (instanciaUnica == null) {
			Cores.instanciaUnica = new Cores();
			Cores.instanciaUnica.adiciona(SistemaOperacional.POSICAO_MEMORIA_VAZIA, Color.white);
			Cores.instanciaUnica.adiciona(SistemaOperacional.PID_SO, Color.red);
		}

		return Cores.instanciaUnica;
	}

	public void adiciona(Integer pid, Color cor) {
		this.silo.adiciona(pid, cor);
	}

	public Color obtem(Integer pid) {
		return this.silo.obtem(pid);
	}

	public boolean contem(Integer pid) {
		return this.silo.contem(pid);
	}
}
