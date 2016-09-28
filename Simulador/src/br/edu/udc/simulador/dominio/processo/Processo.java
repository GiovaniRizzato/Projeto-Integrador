package br.edu.udc.simulador.dominio.processo;

public class Processo {

	public static enum prioridade {
		ALTA, MEDIA, BAIXA;
	}

	public static class DadosEstatisticos {
		public int qtdMemoria = 0;
		public int tempoTotalEmSegundos = 0;
		public int CPU = 0;
		public int pronto = 0;
		public int[] ES = { 0, 0, 0 };
		public int[] esperaES = { 0, 0, 0 };
	}

	public final static int instrucaoCPU = 0;
	public final static int instrucaoES1 = 1;
	public final static int instrucaoES2 = 2;
	public final static int instrucaoES3 = 3;
	public final static int instrucaoFIM = -1;

	private ContextoSoftware contextoSoftware;
	private ContextoMemoria contextoMemoria;

	public Processo(int pid, prioridade prioridade, int qtdeMem, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {
		this.contextoSoftware = new ContextoSoftware(pid, prioridade);
		this.contextoMemoria = new ContextoMemoria(qtdeMem, qtdCPU, qtdIO1, qtdIO2, qtdIO3);

		this.contextoSoftware.dadosEstatisticos.qtdMemoria = qtdeMem;
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

	public void sinalFinalizacao() {
		this.contextoMemoria.programa.sobrepoemPosicao(instrucaoFIM, this.contextoSoftware.instrucaoAtual);
	}

	public DadosEstatisticos getDadosEstatisticos() {
		return this.contextoSoftware.dadosEstatisticos;
	}

	public int getPID() {
		return this.contextoSoftware.pid;
	}

	@Override
	public int hashCode() {
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