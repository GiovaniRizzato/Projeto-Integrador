package br.edu.udc.simulador.dominio.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.edu.udc.simulador.dominio.ed.Lista;

public class ListaTest {

	@Test
	public void equalsIguais() {
		Lista<Integer> lista1 = new Lista<>();
		Lista<Integer> lista2 = new Lista<>();
		
		lista1.adiciona(1);
		lista2.adiciona(1);
		
		assertEquals("HUE", lista1, lista2);
	}
	
	@Test
	public void equalsDiferentes() {
		Lista<Integer> lista1 = new Lista<>();
		Lista<Integer> lista2 = new Lista<>();
		
		lista1.adiciona(1);
		lista2.adiciona(2);
		
		lista1.equals(lista2);
		
		assertNotEquals("HUE", lista1, lista2);
	}
}
