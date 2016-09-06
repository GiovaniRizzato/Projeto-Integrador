package br.edu.udc.simuladorED;

import java.util.Random;

public class ED_Vetor<T> {

	@SuppressWarnings("unchecked")
	private T vetor[] = (T[]) new Object[100];
	// Inicializando um array de Object com capacidade 100.

	private int quantidade = 0;

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

	public void adiciona(T object, int posicao) {
		if (!this.posicaoOcupada(posicao) && posicao != this.quantidade)

			throw new IndexOutOfBoundsException("Posição Invalida");

		// desloca todos os vetor para a direita a partir da posicao
		for (int i = this.quantidade - 1; i >= posicao; i -= 1)
			this.vetor[i + 1] = this.vetor[i];

		this.vetor[posicao] = object;
		this.quantidade++;
	}

	public Object obtem(int posicao) {
		if (!this.posicaoOcupada(posicao)) {
			throw new IndexOutOfBoundsException("Posição Invalida");
		}
		return this.vetor[posicao];
	}

	private boolean posicaoOcupada(int posicao) {
		return posicao >= 0 && posicao < this.quantidade;
	}

	public void remove(int posicao) {
		if (!this.posicaoOcupada(posicao))
			throw new IndexOutOfBoundsException("Posição Invalida");

		for (int i = posicao; i < this.quantidade - 1; i++)
			this.vetor[i] = this.vetor[i + 1];

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

	@SuppressWarnings("unchecked")
	private void verificaCapacidade() {
		if (this.quantidade == this.vetor.length) {
			final Object[] novaArray = new Object[this.vetor.length * 2];

			for (int i = 0; i < this.vetor.length; i++) {
				novaArray[i] = this.vetor[i];
			}

			this.vetor = (T[]) novaArray;
		}
	}

	public void shuffle() {

		final Random random = new Random();

		for (int i = 0; i < this.quantidade; i++) {
			final int posicaoTroca = Math.abs(random.nextInt() % this.quantidade);

			// Troca a posição "i" por uma posição randomica do vetor,
			// garantindo que cada elemento foi trocado ao menos uma vez.
			T bufferTroca = vetor[i];
			vetor[i] = vetor[posicaoTroca];
			vetor[posicaoTroca] = bufferTroca;
		}
	}

	@Override
	public ED_Vetor<T> clone() {
		ED_Vetor<T> clone = new ED_Vetor<T>();

		for (int i = 0; i < this.quantidade; i++) {
			clone.adiciona(this.vetor[i]);
		}

		return clone;
	}

	/**@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {

		for (int i = 0; i < this.quantidade; i++) {
			if (((ED_Vetor<T>) obj).obtem(i).equals(vetor[i])) {// se algum
																// elemento for
																// diferente
				return false;
			}
		}

		return false;
	}**/
}