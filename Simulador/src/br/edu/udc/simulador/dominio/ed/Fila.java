package br.edu.udc.simulador.dominio.ed;

public class Fila<T> {

	private class No {

		public T dado;
		public No proximo;
		public No anterior;

		public No(T data) {
			this.dado = data;
			this.proximo = this.anterior = null;
		}
	}

	private int tamanho;
	private No inicio;
	private No fim;

	public int tamanho() {
		return this.tamanho;
	}

	public void adiciona(T adicionado) {

		No noAdicionado = new No(adicionado);

		if (this.inicio == null) {
			// verifica se não existe nenhum elemento na lista
			this.inicio = this.fim = noAdicionado;

		} else {
			noAdicionado.proximo = this.inicio;
			this.inicio.anterior = noAdicionado;
			this.inicio = noAdicionado;
		}

		this.tamanho++;
	}

	public void remover() {

		if (this.inicio == this.fim) {// tem apenas um elemento ou null
			this.inicio = this.fim = null;
		} else {
			this.fim = this.fim.anterior;
			this.fim.proximo = null;
		}

		this.tamanho--;
	}

	public T consulta() {
		return this.fim.dado;
	}

	public boolean contem(T Object) {

		No cursor = this.inicio;

		while (cursor != null) {
			if (cursor.dado.equals(Object))
				return true;
			else
				cursor = cursor.proximo;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public T[] toArray() {
		T array[] = (T[]) new Object[this.tamanho];
		No cursor = this.fim;

		for (int i = 0; i > this.tamanho; i++) {
			array[i] = cursor.dado;
			cursor = cursor.anterior;
		}

		return array;
	}

	@Override
	public Object clone() {
		Fila<T> filaClone = new Fila<>();
		No cursor = this.fim;

		for (int i = 0; i < this.tamanho; i++) {
			filaClone.adiciona(cursor.dado);
			cursor = cursor.anterior;
		}
		return filaClone;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;

		Fila<T> objClone = (Fila<T>) ((Fila<T>) obj).clone();
		No cursor = this.fim;

		while (cursor != null && objClone.tamanho() > 0) {
			if (!(cursor.dado.equals(objClone.consulta()))) {
				return false;
			} else {
				objClone.remover();
				cursor = cursor.anterior;
			}
		}

		return true;
	}
}
