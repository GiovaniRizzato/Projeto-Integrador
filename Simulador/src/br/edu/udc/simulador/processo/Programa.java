package br.edu.udc.simulador.processo;

import br.edu.udc.ed.vetor.Vetor;

public class Programa {
	
	public final static int instrucaoCPU = 0;
	public final static int instrucaoES1 = 1;
	public final static int instrucaoES2 = 2;
	public final static int instrucaoES3 = 3;
	public final static int instrucaoFIM = -1;
	
	public static Vetor<Integer> criaPrograma(int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {

		Vetor<Integer> programa = new Vetor<>();

		// PASSO 1 - incere o numero de intruções no vetor.
		for (int i = 0; i < qtdCPU; i++) {
			programa.adiciona(Programa.instrucaoCPU);
		}

		for (int i = 0; i < qtdIO1; i++) {
			programa.adiciona(Programa.instrucaoES1);
		}

		for (int i = 0; i < qtdIO2; i++) {
			programa.adiciona(Programa.instrucaoES2);
		}

		for (int i = 0; i < qtdIO3; i++) {
			programa.adiciona(Programa.instrucaoES3);
		}

		// PASSO 2- Embaralha as posições desse vetor.
		programa.shuffle();

		// PASSO 3- Adiciona a intrução "fim" ao final de todas as
		// intruções.
		programa.adiciona(Programa.instrucaoFIM);

		return programa;
	}
}
