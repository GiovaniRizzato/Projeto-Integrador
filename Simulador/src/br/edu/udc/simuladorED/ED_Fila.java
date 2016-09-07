package br.edu.udc.simuladorED;

public class ED_Fila<T> implements Interface_Lista<T> {

	@SuppressWarnings("unused")
	private class No {

		public T dado;
		public No proximo;
		public No anterior;

		public No(T data) {
			this.dado = data;
		}
	}
	
	public void zueira(){
	}

	private int tamanho;
	private No inicio;
	private No fim;

	@Override
	public int tamanho() {
		return this.tamanho;
	}

	@Override
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

	@Override
	public void remover() {
		
		if (this.inicio == this.fim) {//tem apenas um elemento
			this.inicio = this.fim = null;
		}else{
			this.fim = this.fim.anterior;
			this.fim.proximo = null;
		}
	}

	@Override
	public T consulta() {
		return this.fim.dado;
	}

}
