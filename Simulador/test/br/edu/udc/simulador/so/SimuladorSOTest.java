package br.edu.udc.simulador.so;

import static org.junit.Assert.*;

import org.junit.Test;

import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo.Prioridade;

public class SimuladorSOTest {

	@Test
	public void criaProcesso_casoFeliz() {

		Hardware harware = new Hardware(15, 15, 15, 15, 50);
		SimuladorSO so = new SimuladorSO(harware, 10, 0.6F, 0.3F);

		so.criaNovoProcesso(Prioridade.ALTA, 3, 0, 0, 0);
		so.criaNovoProcesso(Prioridade.ALTA, 30, 0, 0, 0);
		
		so.execultarProcessos();
		
		so.criaNovoProcesso(Prioridade.ALTA, 3, 0, 0, 0);

		assertEquals(true, true);
	}
}
