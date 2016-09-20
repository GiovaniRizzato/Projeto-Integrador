package br.edu.udc.simulador.dominio.ed;

public interface Iterator<T> {

	// NAVEGA��O
	public void anterior();

	public void proximo();

	public boolean temProximo();

	public boolean temAnterior();

	// MANIPUL�AO DE DADOS
	public T getDado();

	// MODIFICA��O DA ESTRUTURA DE DADOS
	public void adicionaDepois(T elementoAdicionado);

	public void adicionaAntes(T elementoAdicionado);

	public void remove();
}
