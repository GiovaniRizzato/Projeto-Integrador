package br.edu.udc.simulador.dominio.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.edu.udc.simulador.dominio.ed.Fila;
import br.edu.udc.simulador.dominio.ed.Iterator;

public class FilaTest {

	// ADICIONAR
	@Test
	public void adiciona_CasoFaliz() {

		Fila<Integer> fila = new Fila<>();

		for (int i = 0; i < 15; i++) {
			fila.adiciona(i);
		}

		assertEquals("Deveria ter tamanho 15", 15, fila.tamanho());
		assertEquals("Deveria ser igual ao primeiro elemento adicionado apenas", (Integer) 0,
				fila.consultaProximoElemento());
	}

	@Test(expected = RuntimeException.class)
	public void adiciona_AdicionandoNull() {

		Fila<Integer> fila = new Fila<>();
		fila.adiciona(null);

		fail("RunTime deveria ter sido acionado");
	}

	// REMOVER E CONSULTAR
	@Test
	public void removerEConsultar_CasoFaliz() {

		Fila<Integer> fila = new Fila<>();

		for (int i = 0; i < 15; i++) {
			fila.adiciona(i);
		}

		assertEquals("Deveria ter tamanho 15, para garantir que o adicionar funcionou corretamente", 15,
				fila.tamanho());

		Integer arrayExcluidos[] = new Integer[10];
		final Integer arrayEsperado[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		for (int i = 0; i < 10; i++) {
			arrayExcluidos[i] = fila.consultaProximoElemento();
			fila.remover();
		}

		assertEquals("Deveria ter tamanho 5", 5, fila.tamanho());
		assertArrayEquals("Elementos excluidos deveriam ser", arrayEsperado, arrayExcluidos);
	}

	@Test(expected = RuntimeException.class)
	public void remover_ListaVazia() {
		Fila<Integer> fila = new Fila<>();

		fila.remover();

		fail("Não há elementos para se remover, deveria ter acionado Runtime");
	}

	@Test(expected = NullPointerException.class)
	public void consulta_ListaVazia() {
		Fila<Integer> fila = new Fila<>();

		fila.consultaProximoElemento();

		fail("Não há elementos para se remover, deveria ter acionado Runtime");
	}

	// CONTEM
	@Test
	public void contem_CasoFaliz() {

		Fila<Integer> fila = new Fila<>();

		for (int i = 0; i < 15; i++) {
			fila.adiciona(i);
		}

		assertEquals("A fila contem o elemento 10, mas o método '.contem' não esta reconhecendo", true,
				fila.contem(10));
	}

	@Test
	public void contem_NaoAdicionados() {

		Fila<Integer> fila = new Fila<>();

		for (int i = 0; i < 15; i++) {
			fila.adiciona(i);
		}

		assertEquals("A fila não tem esse elemento", false, fila.contem(-1));
	}

	@Test(expected = NullPointerException.class)
	public void contem_ProcurandoNull() {

		Fila<Integer> fila = new Fila<>();

		for (int i = 0; i < 15; i++) {
			fila.adiciona(i);
		}

		fila.contem(null);

		fail("Procurando null na fila, deve disparar o NullPointer");
	}

	// TO ARRAY
	@Test
	public void toArray_CasoFeliz() {

		Fila<Integer> fila = new Fila<>();
		Number[] arrayTest = new Integer[5];

		for (int i = 0; i < 5; i++) {
			arrayTest[i] = i;
			fila.adiciona(i);
		}

		assertEquals("Tamanhos são diferentes", fila.tamanho(), arrayTest.length);
		assertArrayEquals("Arrays deveriam ser iguais", fila.toArray(), arrayTest);
	}

	@Test
	public void toArray_FilaVazia() {
		Fila<Integer> fila = new Fila<>();
		Number[] arrayTest = {};

		assertEquals("Tamanhos são diferentes", fila.tamanho(), arrayTest.length);
		assertArrayEquals("Arrays deveriam ser iguais", fila.toArray(), arrayTest);
	}

	// CLONE - Garantia de que o método @Override funciona perfeitamente
	@Test
	@SuppressWarnings("unchecked")
	public void clone_CasoFeliz() {

		Fila<Integer> filaPrimeira = new Fila<>();

		for (int i = 0; i < 15; i++) {
			filaPrimeira.adiciona(i);
		}
		Integer[] arrayPrimeiro = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };

		Fila<Integer> filaSegunda = (Fila<Integer>) filaPrimeira.clone();

		assertEquals("Garantindo que os tamanhos são iguais, antes da remoção", filaSegunda.tamanho(),
				filaPrimeira.tamanho());
		assertEquals("Garantindo que são iguais, antes da remoção", filaSegunda, filaPrimeira);

		// Garantindo que são intependentes
		filaSegunda.remover();
		filaSegunda.remover();

		Integer[] arraySegundo = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };

		assertEquals("Tamanho do primeiro deve se manter 15", 15, filaPrimeira.tamanho());
		assertEquals("Tamanho do segundo deve ter diminuido para 13", 13, filaSegunda.tamanho());
		assertArrayEquals("Comparação por valores (1)", arrayPrimeiro, filaPrimeira.toArray());
		assertArrayEquals("Comparação por valores (2)", arraySegundo, filaSegunda.toArray());
		assertNotEquals("Não podem estar iguais neste momento", filaSegunda, filaPrimeira);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void clone_FilaVazia() {
		Fila<Integer> filaPrimeira = new Fila<>();
		Fila<Integer> filaSegunda = (Fila<Integer>) filaPrimeira.clone();

		Integer[] arrayVazio = {};

		assertEquals("Devem ter tamanho = 0 (1)", 0, filaPrimeira.tamanho());
		assertEquals("Devem ter tamanho = 0 (2)", 0, filaSegunda.tamanho());
		assertArrayEquals("Deve conter nenhum elemento (1)", arrayVazio, filaPrimeira.toArray());
		assertArrayEquals("Deve conter nenhum elemento (2)", arrayVazio, filaSegunda.toArray());
		assertEquals("Duas filas deveriam ser iguais", filaSegunda, filaPrimeira);
	}

	// EQUALS - Garantia de que o método @Override funciona perfeitamente
	@Test
	public void equalsIguais() {
		Fila<Integer> fila1 = new Fila<>();
		Fila<Integer> fila2 = new Fila<>();

		for (int i = 0; i < 15; i++) {
			fila1.adiciona(i);
			fila2.adiciona(i);
		}

		assertEquals("Deveria ser igual para as duas filas", fila1, fila2);
	}

	@Test
	public void equalsDiferentes() {
		Fila<Integer> fila1 = new Fila<>();
		Fila<Integer> fila2 = new Fila<>();

		for (int i = 0; i < 15; i++) {
			fila1.adiciona(i);
			fila2.adiciona(15 - i);
		}

		assertNotEquals("Deveria ser igual para as duas filas", fila1, fila2);
	}

	// ITERATOR
	@Test
	public void iteradorTest() {

		Fila<Integer> fila = new Fila<>();

		Number[] arrayFOR = new Integer[5];
		for (int i = 0; i < 5; i++) {
			arrayFOR[i] = i;
			fila.adiciona(i);
		}

		Number[] arrayITERADOR = new Integer[5];
		int i = 0;
		Iterator<Integer> it = fila.inicio();

		while (it.temProximo()) {
			arrayITERADOR[i] = it.getDado();
			it.proximo();
			i++;
		}

		assertEquals("Tamanhos são diferentes", arrayFOR.length, arrayITERADOR.length);
		assertArrayEquals("Eles são diferentes", arrayFOR, arrayITERADOR);
	}
}