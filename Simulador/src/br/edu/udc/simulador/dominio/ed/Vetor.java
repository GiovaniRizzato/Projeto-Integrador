package br.edu.udc.simulador.dominio.ed;

import java.util.Random;

public class Vetor<T> {

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

	public T obtem(int posicao) {
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

	public void organizaCrascente() {

		for (int i = 0; i < this.quantidade; i++) {

			int posicaoTroca = i;
			for (int j = i + 1; j < this.quantidade; j++) {
				// procura desde elemento para frente, pois, os anteriores já
				// foram processador e são menores.

				if ((int) vetor[posicaoTroca] > (int) vetor[j]) {
					// procura a posicao de um elemento menor da lista de i~"fim
					// do vetor"
					posicaoTroca = j;
				}
			}

			// faz a troca dos elementos
			T bufferTroca = vetor[i];
			vetor[i] = vetor[posicaoTroca];
			vetor[posicaoTroca] = bufferTroca;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;
		
		for (int i = 0; i > this.quantidade; i++) {
			//verifica se todos os elementos são iguais
			if (!vetor[i].equals(((Vetor<T>) obj).obtem(i))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Vetor<T> clone() {
		Vetor<T> clone = new Vetor<T>();

		for (int i = 0; i < this.quantidade; i++) {
			clone.adiciona(this.vetor[i]);
		}

		return clone;
	}

	@Override
	public String toString() {

		String stringAcomulador = new String();

		stringAcomulador = stringAcomulador.concat(String.format("["));

		for (int i = 0; i < this.quantidade; i++) {
			stringAcomulador = stringAcomulador.concat(String.format("%s,", vetor[i].toString()));
		}

		if (stringAcomulador.endsWith(",")) {
			stringAcomulador = stringAcomulador.substring(0, stringAcomulador.length() - 1);
		}

		stringAcomulador = stringAcomulador.concat(String.format("]"));

		return stringAcomulador;
	}
}