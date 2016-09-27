package br.edu.udc.simulador.dominio.processo;

import br.edu.udc.simulador.dominio.ed.Vetor;

class ContextoMemoria {
	public final int quantidadeMemoria;
	public Vetor<Integer> programa = new Vetor<Integer>();

	public ContextoMemoria(int qtdeMemoriaRequerida, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {

		this.quantidadeMemoria = qtdeMemoriaRequerida;

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
