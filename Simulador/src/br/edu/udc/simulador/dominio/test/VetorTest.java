package br.edu.udc.simulador.dominio.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.edu.udc.simulador.dominio.ed.Vetor;

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
		
		//adicionando n�o sequencialmente;
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
	
	@Test
	public void toArrayTest(){
		
		Vetor<Integer> vetor = new Vetor<Integer>();
		Integer[] array = new Integer[4];
		
		vetor.adiciona(3);
		array[0] = 3;
		vetor.adiciona(2);
		array[1] = 2;
		vetor.adiciona(6);
		array[2] = 6;
		vetor.adiciona(15);
		array[3] = 15;
		
		assertArrayEquals("Elementos deverias ser iguais", array, vetor.toArray());
	}
}
