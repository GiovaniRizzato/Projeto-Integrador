package br.edu.udc.simulador.dominio.ed;

public interface IteradorManipulador<T> extends Iterator<T> {

	// MODIFICAÇÃO DA ESTRUTURA DE DADOS
	public void adicionaDepois(T elementoAdicionado);

	public void adicionaAntes(T elementoAdicionado);

	public void remove();
}
