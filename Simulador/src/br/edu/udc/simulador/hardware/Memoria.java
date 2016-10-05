package br.edu.udc.simulador.hardware;

class Memoria {

	private Integer posicoes[];
	private Integer qtdPosicoesLivres;

	public Memoria(int tamanho) {
		this.posicoes = new Integer[tamanho];
		for (int i = 0; i < tamanho; i++) {
			this.posicoes[i] = Hardware.posicaoMemoriaVazia;
		}

		this.qtdPosicoesLivres = tamanho;
	}

	public int alocaMemoria(int pid, int qtdRequerida) {
		if (qtdPosicoesLivres < qtdRequerida) {
			throw new IllegalArgumentException("Não há mais memoria suficiente");
		}

		for (int i = 0; i < this.posicoes.length; i++) {
			// percorre o vetor procurando uma posição vazia
			if (posicoes[i] == Hardware.posicaoMemoriaVazia) {
				if (verificaEspacoVazio(i, qtdRequerida)) {
					preencheMemoria(i, qtdRequerida, pid);
					this.qtdPosicoesLivres -= qtdRequerida;
					return i;
				}
			}
		}
		
		throw new IllegalArgumentException("Não há partição grande o suficiente para allocar");
	}

	private void preencheMemoria(int posicao, int tamanho, int pid) {
		for (int i = posicao; i < (posicao + tamanho); i++) {
			posicoes[i] = pid;
		}
	}

	private boolean verificaEspacoVazio(int posicao, int tamanho) {
		for (int i = posicao; i < (posicao + tamanho); i++) {
			if (posicoes[i] != Hardware.posicaoMemoriaVazia)
				return false;
		}
		return true;
	}

	public Integer[] getMemoria() {
		return this.posicoes;
	}

	public int getQtdPosicoesLivres() {
		return this.qtdPosicoesLivres;
	}

}
