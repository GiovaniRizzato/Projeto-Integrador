package br.edu.udc.pi.simulador;

public abstract class InputOutput {
	
	protected int numeroDeClocks;
	protected String nome;
	boolean isSendoUsado;
	
	public boolean isSendoUsado(){
		return isSendoUsado;
	}
	
	public InputOutput(String processName, int numeroDeClocks) {
		this.numeroDeClocks = numeroDeClocks;
		this.nome = processName;
		this.isSendoUsado = false;
	}
	
	public abstract void execultarProcesso();
}

/**
package edu.udc.psw;

public abstract class FormaGeometrica2D {
	
	//circulo que inscreve a forma geometrica
	
	public abstract float area();
	public abstract float tamHorizontal();
	public abstract float tamVertical();
	public abstract float perimetro();
	public abstract Ponto2D centro();

}
**/
