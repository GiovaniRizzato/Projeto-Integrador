package br.edu.udc.simulador.dominio.test;

import br.edu.udc.simulador.dominio.SimuladorSO;
import br.edu.udc.simulador.dominio.Processo.prioridade;

public class SimuladorSOTest {

	public static void main(String[] args) {

		SimuladorSO simulador = new SimuladorSO(10, 10, 10, 10);

		simulador.criaNovoProcesso(prioridade.ALTA, 15, 15, 0, 0, 0);
		simulador.criaNovoProcesso(prioridade.ALTA, 15, 15, 0, 0, 0);

		simulador.criaNovoProcesso(prioridade.MEDIA, 5, 5, 5, 5, 5);

		simulador.criaNovoProcesso(prioridade.BAIXA, 5, 5, 5, 5, 5);

		simulador.processaFilas();

		System.out.println("Deu certo");
	}
}
