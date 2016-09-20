package br.edu.udc.simulador.dominio.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.edu.udc.simulador.dominio.Processo;
import br.edu.udc.simulador.dominio.SimuladorSO;

public class SimuladorSOTest {

	@Test
	public void criaProcesso() {
		
		SimuladorSO simulador = new SimuladorSO(100, 50, 30, 10);
		// clock CPU = 100, ClockES1 = 50, ClockES2 = 30, ClockES3 = 10.
		
		assertEquals("Não deve ter nenhum processo ativo", 0, simulador.qtdProcessosAtivos());
		
		simulador.criaNovoProcesso(Processo.prioridade.ALTA, 40, 40, 0, 0, 0);// CPU
		simulador.criaNovoProcesso(Processo.prioridade.MEDIA, 40, 10, 30, 0, 0);// ES1
		simulador.criaNovoProcesso(Processo.prioridade.MEDIA, 40, 10, 0, 30, 0);// ES2
		simulador.criaNovoProcesso(Processo.prioridade.MEDIA, 40, 10, 0, 0, 30);// ES3
		simulador.criaNovoProcesso(Processo.prioridade.BAIXA, 40, 20, 10, 10, 0);// BALANCEADO
		Processo[] processosSO = simulador.listaTodos();
		
		assertEquals("Deve ter 5 processos neste momento", 5, simulador.qtdProcessosAtivos());
		
		Processo[] processosMANUAL = new Processo[5];
		processosMANUAL[0] = new Processo(0, Processo.prioridade.ALTA, 40, 40, 0, 0, 0);
		processosMANUAL[1] = new Processo(1, Processo.prioridade.MEDIA, 40, 10, 30, 0, 0);
		processosMANUAL[2] = new Processo(2, Processo.prioridade.MEDIA, 40, 10, 0, 30, 0);
		processosMANUAL[3] = new Processo(3, Processo.prioridade.MEDIA, 40, 10, 0, 0, 30);
		processosMANUAL[4] = new Processo(4, Processo.prioridade.BAIXA, 40, 20, 10, 10, 0);
		
		assertArrayEquals("Processos criados igualmente", processosMANUAL, processosSO);
	}
	
	@Test
	public void matarProcesso(){
		
		SimuladorSO simulador = new SimuladorSO(100, 50, 30, 10);
		// clock CPU = 100, ClockES1 = 50, ClockES2 = 30, ClockES3 = 10.
		
		assertEquals("Não deve ter nenhum processo ativo", 0, simulador.qtdProcessosAtivos());
		
		simulador.criaNovoProcesso(Processo.prioridade.ALTA, 10, 10, 0, 0, 0);
		assertEquals("Deve ter apenas o processo que irá processado", 1, simulador.qtdProcessosAtivos());
		
		simulador.processaFilas();
		assertEquals("Deveria ter sido excluido", 0, simulador.qtdProcessosAtivos());
		
		SimuladorSO.EstatisticaSO estatistica = simulador.getEstatisticas();
		assertEquals("Quantidade de memoria é diferente", (Integer) 10, estatistica.qtdMemoria.obtem(0));
		assertEquals("Clocks de CPU processador e diferente", (Integer) 10, estatistica.tempoDeCPU.obtem(0));
		assertEquals("Clocks de ES1 processador e diferente", (Integer) 0, estatistica.tempoDeES1.obtem(0));
		assertEquals("Clocks de ES2 processador e diferente", (Integer) 0, estatistica.tempoDeES2.obtem(0));
		assertEquals("Clocks de ES3 processador e diferente", (Integer) 0, estatistica.tempoDeES3.obtem(0));
	}
	
	@Test
	public void PausarProcesso(){
		
		SimuladorSO simulador = new SimuladorSO(100, 50, 30, 10);
		// clock CPU = 100, ClockES1 = 50, ClockES2 = 30, ClockES3 = 10.
		
		assertEquals("Não deve ter nenhum processo ativo", 0, simulador.qtdProcessosAtivos());
		
		simulador.criaNovoProcesso(Processo.prioridade.ALTA, 10, 10, 0, 0, 0);
		assertEquals("Deve ter apenas o processo que irá processado", 1, simulador.qtdProcessosAtivos());
		
		final int pid = 0;
		simulador.pausarProcesso(pid);
		
		assertEquals("Não deve existir processso ativo", 0, simulador.listaTodos().length);
	}
	
	//TODO Test JUnit de puasar processo
	
	//TODO Test Junit de continuar processo pausado
}
