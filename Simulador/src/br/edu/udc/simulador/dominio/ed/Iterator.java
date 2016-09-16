package br.edu.udc.simulador.dominio.ed;

public interface Iterator<T> {
	
	public void proximo();
	//Fazer com que o Iterator se pocicione no proximo elemento da estrutura
	
	public boolean temProximo();
	//Se o Iterator pode continuar � avan�ar na lista
	
	public T getDado();
	//Retorna o dado da posi��o atual da estrutura
}
