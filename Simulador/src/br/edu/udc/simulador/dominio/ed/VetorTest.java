package br.edu.udc.simulador.dominio.ed;

import static org.junit.Assert.*;

import org.junit.Test;

public class VetorTest {

	@Test
	public void cloneTest() {
		Vetor<Integer> vetorOrdenado = new Vetor<Integer>();

		for (int i = 0; i < 300; i++) {
			vetorOrdenado.adiciona(i);
		}

		Vetor<Integer> vetorClonado = (Vetor<Integer>) vetorOrdenado.clone();

		assertEquals("Deveria ter sido Igual", vetorOrdenado, vetorClonado);
	}

	@Test
	public void shuffleTest() {
		Vetor<Integer> vetorOrdenado = new Vetor<Integer>();

		for (int i = 0; i < 300; i++) {
			vetorOrdenado.adiciona(i);
		}

		Vetor<Integer> vetorEmbaralhado = (Vetor<Integer>) vetorOrdenado.clone();
		vetorEmbaralhado.shuffle();

		assertNotEquals("Deveria ter sido diferente", vetorOrdenado, vetorEmbaralhado);
	}
	
	@Test
	public void organizaTest() {
		Vetor<Integer> vetorOrdenado = new Vetor<Integer>();

		for (int i = 0; i < 300; i++) {
			vetorOrdenado.adiciona(i);
		}

		Vetor<Integer> vetorEmbaralhado = (Vetor<Integer>) vetorOrdenado.clone();
		vetorEmbaralhado.shuffle();
		
		vetorEmbaralhado.organizaCrascente();

		assertEquals("Deveria ter sido diferente", vetorOrdenado, vetorEmbaralhado);
	}
}
