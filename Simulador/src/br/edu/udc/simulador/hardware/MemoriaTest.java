package br.edu.udc.simulador.hardware;

import static org.junit.Assert.*;

import org.junit.Test;

public class MemoriaTest {

	@Test
	public void allocaMemoria_CasoFeliz() {
		Memoria memoria = new Memoria(100);
		assertEquals(0, memoria.alocaMemoria(2, 10));
		assertEquals(10, memoria.alocaMemoria(2, 10));
		
		assertEquals(80, memoria.getQtdPosicoesLivres());
	}

}
