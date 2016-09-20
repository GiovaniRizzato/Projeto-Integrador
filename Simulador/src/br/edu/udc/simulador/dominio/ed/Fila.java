package br.edu.udc.simulador.dominio.ed;

/**
 * ------------------------------------------------------------------------------
 * Sobre nomes de atributos:
 * 
 * Para melhor abstratir o conceito de fila, pensa-se na fila real como exemplo
 * da fila de pessoas esperando para ser atendidas em um banco:
 * 
 * O indivio que acaba de chegar, vai para o "fim" da fila e o elemento à sua
 * frente (mais proximo de ser atendido é o "proximo"; O individuo que vai ser
 * atendido (por consequancia, removido da fila), é o elemento que esta no
 * "começo" da fila seguido pelo seu "anterior".
 * 
 * Desta forma vai ser nomeado os atributos e sub-atributos desta classe.
 * ------------------------------------------------------------------------------
 * Sobre Iterator:
 * 
 * O Iterator concreto implementado nesta classe foi feito para que seja
 * execultado operações nos objetos contidos na estrutura sem que seja alterado
 * a estrutura em sí ,OU SEJA, nenhuma operação de lista como: Adiciona, Exclui
 * será feita pelo Iterator intencionalmente para garantir as propriedades de
 * Fila.
 * ------------------------------------------------------------------------------
 * 
 * @category Estrutura de dados - concreta
 * @param Object
 * @author Giovani Rizzato<giovanirizzato@gmail.com>
 */

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

	private class IteradorFila implements Iterator<T> {

		No cursor;

		public IteradorFila(No no) {
			this.cursor = no;
		}

		@Override
		public void proximo() {
			this.cursor = this.cursor.anterior;
		}
		// TODO comentario de porque os dois estão trocados
		// pois para utilização do Iterador, fica mais legivel desta forma

		@Override
		public void anterior() {
			this.cursor = this.cursor.proximo;
		}

		@Override
		public boolean temProximo() {
			if (this.cursor != null) {
				return true;
			}
			return false;
		}

		@Override
		public boolean temAnterior() {
			if (this.cursor != null) {
				return true;
			}
			return false;
		}

		@Override
		public T getDado() {
			return this.cursor.dado;
		}

		@Override
		public void adicionaDepois(T noAdicionado) {
			throw new RuntimeException("Estruturas em fila não suporta adição por iterador");
			// TODO [PROFESSOR] estas utilizações de exeção estam corretas ?
		}

		@Override
		public void adicionaAntes(T noAdicionado) {
			throw new RuntimeException("Estruturas em fila não suporta adição por iterador");

		}

		@Override
		public void remove() {
			throw new RuntimeException("Estruturas em fila não suporta remoção por iterador");
		}
	}

	public Iterator<T> inicio() {
		return new IteradorFila(this.inicio);
	}

	public Iterator<T> fim() {
		return new IteradorFila(this.fim);
	}

	private int tamanho;
	private No fim;
	private No inicio;

	public int tamanho() {
		return this.tamanho;
	}

	public void adiciona(T adicionado) {

		if (adicionado == null)
			throw new NullPointerException();

		No noAdicionado = new No(adicionado);

		// verifica se não existe nenhum elemento na lista
		if (this.fim == null) {
			this.fim = this.inicio = noAdicionado;

		} else {
			noAdicionado.proximo = this.fim;
			noAdicionado.proximo.anterior = noAdicionado;
			this.fim = noAdicionado;
		}

		this.tamanho++;
	}

	public T remover() {

		No noRemovido;

		if (this.tamanho == 0)// não há elementos para remover
			throw new RuntimeException("Fila está vazia! Não há elementos para remover");
		// TODO [PROFESSOR] esta utilização de exeção esta correta ?

		if (this.fim == this.inicio) {// tem apenas um elemento ou null
			noRemovido = this.fim;
			this.fim = this.inicio = null;

		} else {
			noRemovido = this.inicio;
			this.inicio = this.inicio.anterior;
			this.inicio.proximo = null;
		}

		this.tamanho--;
		return noRemovido.dado;
	}

	public T consultaProximoElemento() {

		if (this.inicio == null) {// Não há elementos na lista
			throw new NullPointerException("Lista vazia, não há elementos para retornar");

		} else {// há, ao menos, um elemento na lista
			return this.inicio.dado;
		}
	}

	public boolean contem(T Object) {
		
		if(Object == null){
			throw new NullPointerException();
		}

		No cursor = this.fim;

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

		No cursor = this.inicio;
		for (int i = 0; i < this.tamanho; i++) {
			array[i] = cursor.dado;
			cursor = cursor.anterior;
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

		No cursor = this.inicio;
		Iterator<T> it = ((Fila<T>) obj).inicio();

		while (it.temProximo()) {
			if (!(cursor.dado.equals(it.getDado()))) {
				return false;
			}

			cursor = cursor.anterior;
			it.proximo();
		}

		return true;
	}
}
