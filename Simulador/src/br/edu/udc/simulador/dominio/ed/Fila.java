package br.edu.udc.simulador.dominio.ed;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLDocument.Iterator;

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

	private class IteradorLista extends Iterator {
		// TODO implementar o iterator para utilizar o iterador e fazer
		// operações de em todos os "No"s da fila

		@Override
		public AttributeSet getAttributes() {
			return null;
		}

		@Override
		public int getEndOffset() {
			return 0;
		}

		@Override
		public int getStartOffset() {
			return 0;
		}

		@Override
		public Tag getTag() {
			return null;
		}

		@Override
		public boolean isValid() {
			return false;
		}

		@Override
		public void next() {
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
		
		// verifica se não existe nenhum elemento na lista
		if (this.inicio == null) {
			this.inicio = this.fim = noAdicionado;

		} else {
			noAdicionado.proximo = this.inicio;
			noAdicionado.proximo.anterior = noAdicionado;
			this.inicio = noAdicionado;
		}

		this.tamanho++;
	}

	public T remover() {
		
		No noRemovido;

		if (this.inicio == this.fim) {// tem apenas um elemento ou null
			noRemovido = this.inicio;
			this.inicio = this.fim = null;
			
		} else {
			noRemovido = this.fim;
			this.fim = this.fim.anterior;
			this.fim.proximo = null;
		}

		this.tamanho--;
		return noRemovido.dado;
	}

	public T consultaProximoElemento() {
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
		//Para que fique na mesma ordem da lista original,
		//deve-se adicionar elementos do fim até o começo.

		for (int i = 0; i < this.tamanho; i++) {
			filaClone.adiciona(cursor.dado);
			cursor = cursor.anterior;
		}
		return filaClone;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		
		//Verificação de segurança
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;
		
		//TODO utilizar o conceito de Iterator assim que implementado
		Fila<T> objClone = (Fila<T>) ((Fila<T>) obj).clone();
		No cursor = this.fim;

		while (cursor != null && objClone.tamanho() > 0) {
			if (!(cursor.dado.equals(objClone.consultaProximoElemento()))) {
				return false;
			} else {
				objClone.remover();
				cursor = cursor.anterior;
			}
		}

		return true;
	}
}
