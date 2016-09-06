package br.edu.udc.simuladorED;

import static org.junit.Assert.*;

import org.junit.Test;

public class ED_VetorTest {

	@Test
	public void cloneTest() {
		ED_Vetor<Integer> vetorOrdenado = new ED_Vetor<Integer>();

		for (int i = 0; i < 300; i++) {
			vetorOrdenado.adiciona(i);
		}

		ED_Vetor<Integer> vetorEmbaralhado = (ED_Vetor<Integer>) vetorOrdenado.clone();

		assertEquals("Deveria ter sido Igual", vetorOrdenado, vetorEmbaralhado);
	}

	@Test
	public void shuffleTest() {
		ED_Vetor<Integer> vetorOrdenado = new ED_Vetor<Integer>();

		for (int i = 0; i < 300; i++) {
			vetorOrdenado.adiciona(i);
		}

		ED_Vetor<Integer> vetorEmbaralhado = (ED_Vetor<Integer>) vetorOrdenado.clone();
		vetorEmbaralhado.shuffle();

		assertNotEquals("Deveria ter sido diferente", vetorOrdenado, vetorEmbaralhado);
	}
}
