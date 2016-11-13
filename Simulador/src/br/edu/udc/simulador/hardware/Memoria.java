package br.edu.udc.simulador.hardware;

import br.edu.udc.ed.lista.Lista;

class Memoria {

	private Integer posicoes[];

	public Memoria(int tamanho) {
		this.posicoes = new Integer[tamanho];
	}

	public int getPosicaoMemoria(int posicao) {
		return this.posicoes[posicao];
	}

	public void preencheMemoria(int posicao, Lista<Integer> programa) {
		for (int i = posicao; i < (posicao + programa.tamanho()); i++) {
			posicoes[i] = programa.obtem(i - posicao);
		}
	}

	public Integer[] getMemoria() {
		return this.posicoes;
	}
}
