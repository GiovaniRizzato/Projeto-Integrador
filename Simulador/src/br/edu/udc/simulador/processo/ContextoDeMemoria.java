package br.edu.udc.simulador.processo;

class ContextoMemoria {

	public Integer posicaoInicial;
	public Integer tamanhoPrograma;

	public ContextoMemoria(int endMemoria, int tamanhoPrograma) {

		this.posicaoInicial = endMemoria;
		this.tamanhoPrograma = tamanhoPrograma;
	}
}
