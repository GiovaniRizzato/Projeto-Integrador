package br.edu.udc.simulador.so;

import br.edu.udc.simulador.processo.Processo;

class Particao {
	public Processo processo;
	// referenca do processo para fazer a desfragmentação
	public Integer pid;
	public Integer tamanho;
	public Integer posicao;

	public Particao(int pid, int tamanho, int posicao) {
		this.processo = null;
		this.pid = pid;
		this.tamanho = tamanho;
		this.posicao = posicao;
	}

	public Particao(Processo processo, int tamanho, int posicao) {
		this.processo = null;
		this.pid = processo.getPID();
		this.tamanho = tamanho;
		this.posicao = posicao;
	}
}
