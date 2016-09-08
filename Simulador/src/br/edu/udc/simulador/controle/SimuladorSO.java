package br.edu.udc.simulador.controle;

import br.edu.udc.simulador.dominio.Hardware;
import br.edu.udc.simulador.dominio.Processo;
import br.edu.udc.simulador.dominio.ed.Fila;
import br.edu.udc.simulador.dominio.ed.Vetor;

public class SimuladorSO {

	private Processo processadoNoMomento;
	private Fila<Processo> filaPronto;
	private Fila<Processo> filaEsperaES[];// 3

	private Vetor<Processo> listaPausado;
	// pois pode ser romovido de qualquer lugar da lista, ao contratrio da fila.

	private Hardware hardware;

	// VARIAVEIS DE LOGICA
	private int proximoPidDisponivel;

	public void criaNovoProcesso() {
		
	}
}
