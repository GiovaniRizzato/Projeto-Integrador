package br.edu.udc.simulador.controle;

import br.edu.udc.simulador.dominio.Processo.prioridade;

public class SimuladorSOTest {

	public static void main(String[] args) {
		
		SimuladorSO simulador = new SimuladorSO();
		
		simulador.criaNovoProcesso(prioridade.ALTA,5,5,5,5,5);
		simulador.criaNovoProcesso(prioridade.ALTA,5,5,5,5,5);
		simulador.criaNovoProcesso(prioridade.ALTA,5,5,5,5,5);
		
		simulador.criaNovoProcesso(prioridade.MEDIA,5,5,5,5,5);
		
		simulador.criaNovoProcesso(prioridade.BAIXA,5,5,5,5,5);
		
		System.out.println("Deu certo");
	}
}
