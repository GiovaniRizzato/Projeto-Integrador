package br.edu.udc.simulador.dominio.ed;

public class Fila<T> {

	private class No {

		public T dado;
		@SuppressWarnings("unused")
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
	}

	public void remover() {

		if (this.inicio == this.fim) {// tem apenas um elemento ou null
			this.inicio = this.fim = null;
		} else {
			this.fim = this.fim.anterior;
			this.fim.proximo = null;
		}
	}

	public T consulta() {
		return this.fim.dado;
	}

}
