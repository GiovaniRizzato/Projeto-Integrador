package br.edu.udc.simulador.dominio.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.edu.udc.simulador.dominio.ed.Fila;

public class FilaTest {

	@Test
	public void adiciona() {

		Fila<Integer> fila = new Fila<>();

		for (int i = 0; i < 15; i++) {
			fila.adiciona(i);
		}

		assertEquals("Deveria ter tamanho 15", 15, fila.tamanho());
		assertEquals("Deveria ser igual ao primeiro elemento adicionado apenas", (Integer) 0,
				fila.consultaProximoElemento());
	}

	@Test
	public void removerEConsultar() {

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

	@Test
	@SuppressWarnings("unchecked")
	public void cloneTest() {

		Fila<Integer> filaPrimeira = new Fila<>();

		for (int i = 0; i < 15; i++) {
			filaPrimeira.adiciona(i);
		}

		Fila<Integer> filaSegunda = (Fila<Integer>) filaPrimeira.clone();

		assertEquals("Deveria ser igual para as duas filas", filaSegunda.tamanho(), filaPrimeira.tamanho());
		assertEquals("Duas filas deveriam ser iguais", filaSegunda, filaPrimeira);
	}

	@Test
	public void toArrayTest() {

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
	public void iteradorTest() {

		Fila<Integer> fila = new Fila<>();

		Number[] arrayFOR = new Integer[5];
		for (int i = 0; i < 5; i++) {
			arrayFOR[i] = i;
			fila.adiciona(i);
		}

		Number[] arrayITERADOR = new Integer[5];
		int i = 0;

		for (Integer numero : fila) {
			arrayITERADOR[i] = numero;
			i++;
		}

		assertEquals("Tamanhos são diferentes", arrayFOR.length, arrayITERADOR.length);
		assertArrayEquals("Eles são diferentes", arrayFOR, arrayITERADOR);

		for (Integer numero : fila) {
			numero++;
			// TODO [PROFESSOR] Posso utilizar For Each desta forma para alterar
			// algum paramtro dentro da fila? há alguma forma?
		}
	}
}