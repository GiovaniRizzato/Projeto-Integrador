package br.edu.udc.simulador.dominio.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.edu.udc.simulador.dominio.Estatistica;
import br.edu.udc.simulador.dominio.ed.Vetor;

public class EstatisticaTest {
	
	Vetor<Integer> dadosBrutos = new Vetor<>();
	
	public EstatisticaTest(){
		dadosBrutos.adiciona(10);
		dadosBrutos.adiciona(5);
		dadosBrutos.adiciona(5);
		dadosBrutos.adiciona(5);
	}
	
	@Test
	public void media() {
		assertEquals("Media esta errada", (double) (25/4), Estatistica.media(dadosBrutos), 2);
	}
	
	@Test
	public void moda() {
		assertEquals("Moda é 5", 5, Estatistica.moda(dadosBrutos));
	}
	
	@Test
	public void mediana() {
		assertEquals("Meidana é 5", 5, Estatistica.mediana(dadosBrutos));
	}

}
