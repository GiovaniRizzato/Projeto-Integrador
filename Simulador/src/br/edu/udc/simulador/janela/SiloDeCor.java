package br.edu.udc.simulador.janela;

import java.awt.Color;
import br.edu.udc.ed.mapa.Mapa;

public class SiloDeCor {
	
	private Mapa<Integer, Color> silo = new Mapa<>();
	
	private static SiloDeCor instanciaUnica;
	
	private SiloDeCor(){}//Tornar o contrutor privado
	
	public static SiloDeCor getIntancia(){
		if(instanciaUnica == null){
			return new SiloDeCor();
		}
		
		return instanciaUnica;
	}
	
	public void adiciona(Integer pid, Color cor){
		this.silo.adiciona(pid, cor);
	}
	
	public void remove(Integer pid){
		//TODO
	}
	
	public Color obtem(Integer pid){
		return this.silo.obtem(pid);
	}
}
