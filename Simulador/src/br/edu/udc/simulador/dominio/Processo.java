package br.edu.udc.simulador.dominio;

import br.edu.udc.simulador.dominio.ed.Vetor;

/**
 * Teste para criar processo pausar processo continuar processo matar processo
 * 
 */

public class Processo {

	// DADOS DE PROCESSO QUE PODEM SER "DECLARADOS" POR OUTRAS CLASSES
	public enum prioridade {
		ALTA, MEDIA, BAIXA;
	}

	public class DadosEstatisticos {
		public int tempoTotalEmSegundos = 0;
		public int CPU = 0;
		public int pronto = 0;
		public int[] ES = new int[3];
		public int[] esperaES = new int[3];
	}

	public final static int instrucaoCPU = 0;
	public final static int instrucaoES1 = 1;
	public final static int instrucaoES2 = 2;
	public final static int instrucaoES3 = 3;
	public final static int instrucaoFIM = -1;

	private class ContextoSoftware {
		public final int pid;
		public prioridade prioridade;
		public int instrucaoAtual = 0;
		public DadosEstatisticos dadosEstatisticos = new DadosEstatisticos();

		public ContextoSoftware(int pid, prioridade prioridade) {
			this.pid = pid;
			this.prioridade = prioridade;
		}
	}

	@SuppressWarnings("unused")
	private class ContextoMemoria {
		public final int quantidadeMemoria;
		public Vetor<Integer> programa = new Vetor<Integer>();

		public ContextoMemoria(int qtdeMemoriaRequerida, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {

			this.quantidadeMemoria = qtdeMemoriaRequerida;

			// PASSO 1 - incere o numero de intruções no vetor.
			for (int i = 0; i < qtdCPU; i++) {
				this.programa.adiciona(instrucaoCPU);
			}

			for (int i = 0; i < qtdIO1; i++) {
				this.programa.adiciona(instrucaoES1);
			}

			for (int i = 0; i < qtdIO2; i++) {
				this.programa.adiciona(instrucaoES2);
			}

			for (int i = 0; i < qtdIO3; i++) {
				this.programa.adiciona(instrucaoES3);
			}

			// PASSO 2- Embaralha as posições desse vetor.
			this.programa.shuffle();

			// PASSO 3- Adiciona o fim do programa ao final de todas as
			// intruções.
			this.programa.adiciona(instrucaoFIM);
		}
	}

	private ContextoSoftware contextoSoftware;
	private ContextoMemoria contextoMemoria;

	public Processo(int pid, prioridade prioridade, int qtdeMem, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {
		this.contextoSoftware = new ContextoSoftware(pid, prioridade);
		this.contextoMemoria = new ContextoMemoria(qtdeMem, qtdCPU, qtdIO1, qtdIO2, qtdIO3);
	}

	public void proximaIntrucao() {
		// esta função assume que para ir pra a proxima intrução, foi feito o
		// "processamento", portanto faz as devidas alterações nos dados
		// estatisticos do contexto de software

		switch (this.contextoMemoria.programa.obtem(this.contextoSoftware.instrucaoAtual)) {
		case Processo.instrucaoCPU: {
			this.contextoSoftware.dadosEstatisticos.CPU++;
			break;
		}

		case Processo.instrucaoES1: {
			this.contextoSoftware.dadosEstatisticos.ES[0]++;
			break;
		}

		case Processo.instrucaoES2: {
			this.contextoSoftware.dadosEstatisticos.ES[1]++;
			break;
		}

		case Processo.instrucaoES3: {
			this.contextoSoftware.dadosEstatisticos.ES[2]++;
			break;
		}
		}

		this.contextoSoftware.instrucaoAtual++;
	}

	public int intrucaoAtual() {
		return this.contextoMemoria.programa.obtem(this.contextoSoftware.instrucaoAtual);
	}

	public prioridade getPrioridade() {
		return this.contextoSoftware.prioridade;
	}

	public void mataProcesso() {
		this.contextoMemoria.programa.adiciona(instrucaoFIM, this.contextoSoftware.instrucaoAtual);
	}

	public DadosEstatisticos getDadosEstatisticos() {
		return this.contextoSoftware.dadosEstatisticos;
	}

	public int getPID() {
		return this.contextoSoftware.pid;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		if (this == obj)
			return true;

		if (this.contextoSoftware.pid == ((Processo) obj).getPID())
			return true;
		else
			return false;
	}
}
