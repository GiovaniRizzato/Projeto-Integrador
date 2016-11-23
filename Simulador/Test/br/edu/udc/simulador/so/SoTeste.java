package br.edu.udc.simulador.so;

import static org.junit.Assert.*;

import org.junit.Test;

import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo.Prioridade;

public class SoTeste {

	@Test
	public void criaProcesso_casoFeliz() {
		
		Computador pc = Computador.getIntancia();
		SistemaOperacional so = new SistemaOperacional(10, 0.6F, 0.3F, pc.getHardware());

		so.criaNovoProcesso(Prioridade.ALTA, 3, 0, 0, 0);
		so.criaNovoProcesso(Prioridade.ALTA, 7, 0, 0, 0);
		so.criaNovoProcesso(Prioridade.ALTA, 4, 0, 0, 0);
		so.criaNovoProcesso(Prioridade.ALTA, 7, 0, 0, 0);
		so.criaNovoProcesso(Prioridade.ALTA, 2, 0, 0, 0);
		so.criaNovoProcesso(Prioridade.ALTA, 7, 0, 0, 0);

		assertEquals(true, true);
	}
	
	@Test
	public void criaProcesso_desalocaMemoria() {

		Computador pc = Computador.getIntancia();
		SistemaOperacional so = new SistemaOperacional(10, 0.6F, 0.3F, pc.getHardware());

		so.criaNovoProcesso(Prioridade.ALTA, 3, 0, 0, 0);
		so.criaNovoProcesso(Prioridade.ALTA, 4, 0, 0, 0);
		so.criaNovoProcesso(Prioridade.ALTA, 2, 0, 0, 0);
		so.criaNovoProcesso(Prioridade.BAIXA, 26, 0, 0, 0);
		
		so.execultarProcessos();

		assertEquals(true, true);
		
		so.desfragmentacaoMemoria();
		
		assertEquals(true, true);
	}
}
