package br.edu.udc.simulador.processo;

import br.edu.udc.ed.lista.Lista;
import br.edu.udc.ed.vetor.Vetor;

class ContextoMemoria {
	public Vetor<Integer> programa = new Vetor<Integer>();

	@SuppressWarnings("unused")
	private class espacoAllocado {
		public Integer posicaoInicial;
		public Integer quantidade;

		public espacoAllocado(int posicao, int qtd) {
			this.posicaoInicial = posicao;
			this.quantidade = qtd;
		}
	}

	public espacoAllocado memoriaPrograma;
	public Lista<espacoAllocado> memoriaAdicial = new Lista<>();
	//TODO metodos para adicionar e remover memoria adicional

	public ContextoMemoria(int qtdeMemoria, int endMemoria, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {

		this.criaPrograma(qtdCPU, qtdIO1, qtdIO2, qtdIO3);
		this.memoriaPrograma = new espacoAllocado(endMemoria, qtdeMemoria);
	}

	private void criaPrograma(int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {
		// PASSO 1 - incere o numero de intruções no vetor.
		for (int i = 0; i < qtdCPU; i++) {
			this.programa.adiciona(Processo.instrucaoCPU);
		}

		for (int i = 0; i < qtdIO1; i++) {
			this.programa.adiciona(Processo.instrucaoES1);
		}

		for (int i = 0; i < qtdIO2; i++) {
			this.programa.adiciona(Processo.instrucaoES2);
		}

		for (int i = 0; i < qtdIO3; i++) {
			this.programa.adiciona(Processo.instrucaoES3);
		}

		// PASSO 2- Embaralha as posições desse vetor.
		this.programa.shuffle();

		// PASSO 3- Adiciona a intrução "fim" ao final de todas as
		// intruções.
		this.programa.adiciona(Processo.instrucaoFIM);
	}
}
