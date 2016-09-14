package br.edu.udc.simulador.dominio.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.edu.udc.simulador.dominio.Processo;
import br.edu.udc.simulador.dominio.Processo.prioridade;
import br.edu.udc.simulador.dominio.ed.Fila;

public class FilaTest {

	@Test
	public void adiciona() {

		Fila<Integer> fila = new Fila<>();

		for (int i = 0; i < 15; i++) {
			fila.adiciona(i);
		}

		assertEquals("Deveria ter tamanho 15", 15, fila.tamanho());
		assertEquals("Deveria ser igual ao primeiro elemento adicionado apenas", (Integer) 0, fila.consulta());
	}

	@Test
	public void adicionarProcesso() {

		Fila<Processo> filaAlta = new Fila<>();
		Fila<Processo> filaMedia = new Fila<>();
		Fila<Processo> filaBaixa = new Fila<>();

		Processo newProcesso = new Processo(0, prioridade.ALTA, 5, 5, 5, 5, 5);

		switch (newProcesso.getPrioridade()) {
		case ALTA: {
			filaAlta.adiciona(newProcesso);
			break;
		}
		case MEDIA: {
			filaMedia.adiciona(newProcesso);
			break;
		}
		case BAIXA: {
			filaBaixa.adiciona(newProcesso);
			break;
		}
		}

		assertEquals("Deveria ter tamanho 1 - Alta", 1, filaAlta.tamanho());
		assertEquals("Deveria ter newProcesso no começo - Alta", newProcesso, filaAlta.consulta());

		assertEquals("Deveria ter tamanho 0 - Media", 0, filaMedia.tamanho());

		assertEquals("Deveria ter tamanho 0 - Baixa", 0, filaBaixa.tamanho());
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
			arrayExcluidos[i] = fila.consulta();
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

		for (int i = 0; i < 5; i++) {
			fila.adiciona(i);
		}
		
		Number[] arrayTest = new Integer[5];
		for (int i = 0; i < 5; i++) {
			arrayTest[i] = i;
		}
		
		assertEquals("Tamanhos são diferentes", fila.tamanho(), arrayTest.length);
		assertArrayEquals("Arrays deveriam ser iguais", fila.toArray(), arrayTest);
	}
}