package br.edu.udc.simulador.dominio.ed;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Fila<T> implements Iterable<T> {

	private class No {

		public T dado;
		public No anterior;
		public No proximo;

		public No(T data) {
			this.dado = data;
			this.anterior = this.proximo = null;
		}
	}

	private class IteradorFila implements Iterator<T> {

		No cursor;

		public IteradorFila(No no) {
			this.cursor = no;
		}

		@Override
		public boolean hasNext() {
			if (cursor != null) {
				return true;
			}
			return false;
		}

		@Override
		public T next() {
			if (this.hasNext()) {
				No current = this.cursor;
				this.cursor = this.cursor.proximo;
				return current.dado;
			}

			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private int tamanho;
	private No fim;
	private No inicio;

	@Override
	public Iterator<T> iterator() {
		return new IteradorFila(this.inicio);
	}

	public int tamanho() {
		return this.tamanho;
	}

	public void adiciona(T adicionado) {

		No noAdicionado = new No(adicionado);

		// verifica se não existe nenhum elemento na lista
		if (this.fim == null) {
			this.fim = this.inicio = noAdicionado;

		} else {
			noAdicionado.anterior = this.fim;
			noAdicionado.anterior.proximo = noAdicionado;
			this.fim = noAdicionado;
		}

		this.tamanho++;
	}

	public T remover() {

		No noRemovido;

		if (this.tamanho == 0)// não há elementos para remover
			return null;
		// TODO trow algum erro de que não exite nenhum para remover

		if (this.fim == this.inicio) {// tem apenas um elemento ou null
			noRemovido = this.fim;
			this.fim = this.inicio = null;

		} else {
			noRemovido = this.inicio;
			this.inicio = this.inicio.proximo;
			this.inicio.anterior = null;
		}

		this.tamanho--;
		return noRemovido.dado;
	}

	public T consultaProximoElemento() {
		return this.inicio.dado;
	}

	public boolean contem(T Object) {

		No cursor = this.fim;

		while (cursor != null) {
			if (cursor.dado.equals(Object))
				return true;
			else
				cursor = cursor.anterior;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public T[] toArray() {

		T array[] = (T[]) new Object[this.tamanho];

		int i = 0;
		for (T Object : this) {
			array[i] = Object;
			i++;
		}
		return array;
	}

	@Override
	public Object clone() {
		Fila<T> filaClone = new Fila<>();
		No cursor = this.inicio;
		// Para que fique na mesma ordem da lista original,
		// deve-se adicionar elementos do fim até o começo.

		for (int i = 0; i < this.tamanho; i++) {
			filaClone.adiciona(cursor.dado);
			cursor = cursor.proximo;
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

		No cursor = this.inicio;
		for (T comparado : ((Fila<T>) obj)) {
			if (!(cursor.dado.equals(comparado))) {
				return false;//se encontrar algum elemento diferente
			}else{
				cursor = cursor.proximo;
			}
		}

		return true;
	}
}
