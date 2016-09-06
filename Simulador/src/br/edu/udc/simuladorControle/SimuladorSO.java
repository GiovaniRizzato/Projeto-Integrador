package br.edu.udc.simuladorControle;

import br.edu.udc.simuladorDominio.BlocoControleProcesso;
import br.edu.udc.simuladorDominio.Hardware;
import br.edu.udc.simuladorED.ED_Fila;
import br.edu.udc.simuladorED.ED_Lista;

public class SimuladorSO {
	
	private BlocoControleProcesso processadoNoMomento;
	private ED_Fila<BlocoControleProcesso> listaEspera[];//3
	
	private ED_Lista<BlocoControleProcesso> listaPausado;//pois pode ser romovido de qualquer lugar da lista, ao contratrio da fila.
	
	private Hardware hardware;
	
	//VARIAVEIS DE LOGICA
	private int proximoPidDisponivel;
}
