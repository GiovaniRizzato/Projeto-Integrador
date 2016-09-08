package br.edu.udc.simulador.dominio;

import br.edu.udc.simulador.dominio.ed.Vetor;

public class Processo {

	@SuppressWarnings("unused")
	private class ContextoSoftware {
		public final int pid;
		public int instrucaoAtual;

		public int estatistica_tempoTotalEmSegundos;
		public int estatistica_CPU;
		public int estatistica_pronto;
		public int[] estatistica_ES = new int[3];
		public int[] estatistica_esperaES = new int[3];

		public ContextoSoftware(int argPid) {
			this.pid = argPid;
			this.instrucaoAtual = 0;
		}
	}

	@SuppressWarnings("unused")
	private class ContextoMemoria {
		public final int quantidadeMemoria;
		public Vetor<Integer> programa;

		public ContextoMemoria(int qtdeMemoriaRequerida, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {

			// Inicializa a mem�ria
			this.quantidadeMemoria = qtdeMemoriaRequerida;

			// PASSO 1 - incere o numero de intru��es no vetor.
			for (int i = 0; i > qtdCPU; i++)
				this.programa.adiciona(0);

			for (int i = 0; i > qtdIO1; i++)
				this.programa.adiciona(1);

			for (int i = 0; i > qtdIO2; i++)
				this.programa.adiciona(2);

			for (int i = 0; i > qtdIO3; i++)
				this.programa.adiciona(3);

			// PASSO 2- Embaralha as posi��es desse vetor.
			this.programa.shuffle();
		}
	}

	ContextoSoftware contextoSoftware;
	ContextoMemoria contextoMemoria;

	public Processo(int pid, int qtdeMem, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {
		this.contextoSoftware = new ContextoSoftware(pid);
		this.contextoMemoria = new ContextoMemoria(qtdeMem, qtdCPU, qtdIO1, qtdIO2, qtdIO3);
	}

	public void proximaIntrucao() {
		// esta fun��o assume que para ir pra a proxima intru��o, foi feito o
		// "processamento", portanto faz as devidas altera��es nos dados
		// estatisticos do contexto de software

		switch (this.contextoMemoria.programa.obtem(this.contextoSoftware.instrucaoAtual)) {
		case 0:
			this.contextoSoftware.estatistica_CPU++;

		case 1:
			this.contextoSoftware.estatistica_ES[0]++;

		case 2:
			this.contextoSoftware.estatistica_ES[1]++;

		case 3:
			this.contextoSoftware.estatistica_ES[2]++;
		}

		this.contextoSoftware.instrucaoAtual++;
	}

	public int intrucaoAtual() {
		return this.contextoMemoria.programa.obtem(this.contextoSoftware.instrucaoAtual);
	}

	public void fimDeClock() {
		// fun��o responsavel por fazer a contabiliza��o, fazendo a contagem de
		// "fins" de clock
		this.contextoSoftware.estatistica_tempoTotalEmSegundos++;
	}
}
