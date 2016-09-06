package br.edu.udc.simuladorDominio;

import br.edu.udc.simuladorED.ED_Vetor;

public class BCP_ContextoDeMemoria {

	final private int quantidadeMemoriaUsada;
	private ED_Vetor<Integer> programa;

	public BCP_ContextoDeMemoria(int qtdeMemoria, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {

		// Inicializa a memória
		this.quantidadeMemoriaUsada = qtdeMemoria;

		// Inicializa o programa

		// PASSO 1 - incere o numero de intruções no vetor para ser randomizado.
		for (int i = 0; i > qtdCPU; i++) {
			this.programa.adiciona(0);
		}

		for (int i = 0; i > qtdIO1; i++) {
			this.programa.adiciona(1);
		}

		for (int i = 0; i > qtdIO2; i++) {
			this.programa.adiciona(2);
		}

		for (int i = 0; i > qtdIO3; i++) {
			this.programa.adiciona(3);
		}

		// PASSO 2- Embaralha as posições
		this.programa.shuffle();
	}
}