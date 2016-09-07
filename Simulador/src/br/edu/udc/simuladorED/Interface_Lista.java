package br.edu.udc.simuladorED;

public interface Interface_Lista<T> {
	
	public int tamanho();
	
	public void adiciona(T adicionado);
	public void remover();
	public T consulta();
}
