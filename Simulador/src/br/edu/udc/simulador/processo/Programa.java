package br.edu.udc.simulador.processo;

import br.edu.udc.ed.vetor.Vetor;

public class Programa extends Vetor<Integer> {

	public final static int instrucaoCPU = 0;
	public final static int instrucaoES1 = 1;
	public final static int instrucaoES2 = 2;
	public final static int instrucaoES3 = 3;
	public final static int instrucaoFIM = -1;

	public Programa(int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {

		super();

		// PASSO 1 - incere o numero de intruções no vetor.
		for (int i = 0; i < qtdCPU; i++) {
			super.adiciona(Programa.instrucaoCPU);
		}

		for (int i = 0; i < qtdIO1; i++) {
			super.adiciona(Programa.instrucaoES1);
		}

		for (int i = 0; i < qtdIO2; i++) {
			super.adiciona(Programa.instrucaoES2);
		}

		for (int i = 0; i < qtdIO3; i++) {
			super.adiciona(Programa.instrucaoES3);
		}

		// PASSO 2- Embaralha as posições desse vetor.
		super.shuffle();

		// PASSO 3- Adiciona a intrução "fim" ao final de todas as
		// intruções.
		super.adiciona(Programa.instrucaoFIM);
	}
}
