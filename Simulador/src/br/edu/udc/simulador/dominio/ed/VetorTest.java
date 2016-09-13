package br.edu.udc.simulador.dominio.ed;

import static org.junit.Assert.*;

import org.junit.Test;

public class VetorTest {

	@Test
	public void cloneTest() {
		Vetor<Integer> vetorOrdenado = new Vetor<Integer>();

		for (int i = 0; i < 15; i++) {
			vetorOrdenado.adiciona(i);
		}

		Vetor<Integer> vetorClonado = (Vetor<Integer>) vetorOrdenado.clone();

		assertEquals("Deveria ter sido Igual", vetorOrdenado, vetorClonado);
	}

	@Test
	public void shuffleTest() {
		Vetor<Integer> vetorOrdenado = new Vetor<Integer>();

		for (int i = 0; i < 15; i++) {
			vetorOrdenado.adiciona(i);
		}

		Vetor<Integer> vetorEmbaralhado = (Vetor<Integer>) vetorOrdenado.clone();
		vetorEmbaralhado.shuffle();

		assertNotEquals("Deveria ter sido diferente", vetorOrdenado, vetorEmbaralhado);
	}
	
	@Test
	public void shuffleVazioTest() {
		Vetor<Integer> vetor = new Vetor<Integer>();
		vetor.shuffle();

		assertNotEquals("Deveria esta vazio", vetor, vetor);
	}
	
	@Test
	public void organizaTest() {
		Vetor<Integer> vetorOrdenado = new Vetor<Integer>();

		for (int i = 0; i < 15; i++) {
			vetorOrdenado.adiciona(i);
		}

		Vetor<Integer> vetorEmbaralhado = (Vetor<Integer>) vetorOrdenado.clone();
		vetorEmbaralhado.shuffle();
		
		vetorEmbaralhado.organizaCrascente();

		assertEquals("Deveria ter sido diferente", vetorOrdenado, vetorEmbaralhado);
	}
	
	@Test
	public void toStringTest() {
		Vetor<Integer> vetor = new Vetor<Integer>();
		
		//adicionando não sequencialmente;
		vetor.adiciona(3);
		vetor.adiciona(2);
		vetor.adiciona(6);
		vetor.adiciona(15);

		assertEquals("Deveria ter sido diferente", "[3,2,6,15]", vetor.toString());
	}
	
	@Test
	public void toStringVaziaTest() {
		Vetor<Integer> vetor = new Vetor<Integer>();

		assertEquals("Deveria ter sido diferente", "[]", vetor.toString());
	}
}
