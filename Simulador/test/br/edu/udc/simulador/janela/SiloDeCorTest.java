package br.edu.udc.simulador.janela;

import java.awt.Color;

import org.junit.Test;

public class SiloDeCorTest {
	//TODO fazer em Singletown... e falar com miguel na sexta
	@Test
	public void obtemCor_casoFeliz() {
		Cores silo = Cores.getIntancia();
		silo.adiciona(2, Color.red);
		silo.adiciona(3, Color.blue);
		silo.adiciona(4, Color.green);
		silo.adiciona(5, Color.cyan);
		
		for(int i=2; i<6; i++){
			System.out.println(silo.obtem(i));
			
		}
	}
}
