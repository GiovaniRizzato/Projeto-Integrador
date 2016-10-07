package br.edu.udc.simulador.processo;

class ContextoMemoria {

	public Integer posicaoInicial;
	public Integer posicaoFinal;

	public ContextoMemoria(int endMemoria, int tamanhoPrograma) {

		this.posicaoInicial = endMemoria;
		this.posicaoFinal = endMemoria + tamanhoPrograma;
	}
}
