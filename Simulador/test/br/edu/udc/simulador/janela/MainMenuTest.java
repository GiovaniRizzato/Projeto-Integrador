package br.edu.udc.simulador.janela;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.processo.Processo.Prioridade;

public class MainMenuTest {

	@Test
	public void testando_inteface() {
		
		MainMenu.main(null);

		final Computador computador = Computador.getInstancia();

		int proximoPid = Computador.getInstancia().getSimulador().getProximoPid();
		Cores.getIntancia().adiciona(proximoPid, Color.RED);
		computador.criaProcesso(Prioridade.ALTA, 50, 0, 0, 0);

		proximoPid = Computador.getInstancia().getSimulador().getProximoPid();
		Cores.getIntancia().adiciona(proximoPid, Color.GREEN);
		computador.criaProcesso(Prioridade.ALTA, 50, 0, 0, 0);

		proximoPid = Computador.getInstancia().getSimulador().getProximoPid();
		Cores.getIntancia().adiciona(proximoPid, Color.BLUE);
		computador.criaProcesso(Prioridade.ALTA, 50, 0, 0, 0);

		fail("Verficiação de interface");
	}
}
