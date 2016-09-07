package br.edu.udc.simuladorED;

public interface Interface_Iterador<T> {
	
	public T dado();
	public T proximo();
	public T anterior();
	
	public boolean temProximo();
	public boolean temAnterior();
	
	public int incereAntes(T adicionado);
	public int incereDepois(T adicionado);
	
	public T remove();
}
