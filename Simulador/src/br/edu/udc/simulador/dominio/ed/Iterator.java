package br.edu.udc.simulador.dominio.ed;

public interface Iterator<T> {

	// NAVEGAÇÃO
	public void anterior();

	public void proximo();

	public boolean temProximo();

	public boolean temAnterior();

	// MANIPULÇAO DE DADOS
	public T getDado();

	// MODIFICAÇÃO DA ESTRUTURA DE DADOS
	public void adicionaDepois(T elementoAdicionado);

	public void adicionaAntes(T elementoAdicionado);

	public void remove();
}
