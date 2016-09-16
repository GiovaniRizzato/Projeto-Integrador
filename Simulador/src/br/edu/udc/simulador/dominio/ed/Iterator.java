package br.edu.udc.simulador.dominio.ed;

public interface Iterator<T> {
	
	public void proximo();
	//Fazer com que o Iterator se pocicione no proximo elemento da estrutura
	
	public boolean temProximo();
	//Se o Iterator pode continuar à avançar na lista
	
	public T getDado();
	//Retorna o dado da posição atual da estrutura
}
