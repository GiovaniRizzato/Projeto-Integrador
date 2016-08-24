package br.edu.udc.pi.vetor.generalizado;

public class Vetor<T> {
	// Inicializando um array de Object com capacidade 100.
	private Object vetor[] = new Object[100];

	private int quantidade = 0;

	private Object[] novaArray;

	public void adiciona(T object) {
		this.verificaCapacidade();
		
		for (int i = 0; i < vetor.length; i++) {
			if (vetor[i] == null) {
				vetor[i] = object;
				break;
			}
		}

		this.vetor[quantidade] = object;
		this.quantidade++;

	}

	public void adiciona(int posicao, T object) {
		/**
		 * Implementar adicionar na posição, deslocando a posição dos "para frente"
		 */
	}

	@SuppressWarnings("unchecked")
	public Object obtem(int posicao) {
		if (!this.posicaoOcupada(posicao)) {
			throw new IndexOutOfBoundsException("Posição Invalida");
		}
		return (T) this.vetor[posicao];
	}

	private boolean posicaoOcupada(int posicao) {
		return posicao >= 0 && posicao < this.quantidade;
	}

	public void remove(int posicao) {
		if(!this.posicaoOcupada(posicao))
			throw new IndexOutOfBoundsException("Posição Invalida");
		
		for(int i=posicao; i<this.quantidade-1; i++)
			this.vetor[i] = this.vetor[i+1];
		
		this.quantidade--;
	}

	public boolean contem(T object) {
		for (int i = 0; i < this.quantidade; i++) {
			if (object.equals(this.vetor[i])) {
				return true;
			}
		}
		return false;
	}

	public int tamanho() {
		return this.quantidade;
	}
	
	public void adciona(int posicao, T object){
		if(!this.posicaoOcupada(posicao) && posicao != this.quantidade)
			
			throw new IndexOutOfBoundsException("Posição Invalida");
		
	//desloca todos os vetor para a direita a partir da posicao
	for(int i = this.quantidade-1; i>=posicao; i-=1)
		this.vetor[i+1] = this.vetor[i];
	
	this.vetor[posicao] = object;
	this.quantidade++;
	}
	
	private void verificaCapacidade(){
		if(this.quantidade == this.vetor.length){
			final Object[] novaArray = new Object[this.vetor.length*2];
			
			for(int i=0; i < this.vetor.length; i++)
				novaArray[i] = this.vetor[i];
		}
		
		this.vetor = novaArray;
	}
}