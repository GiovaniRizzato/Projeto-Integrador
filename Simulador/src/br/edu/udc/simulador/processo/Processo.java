package br.edu.udc.simulador.processo;

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

	private ContextoSoftware contextoSoftware;
	private ContextoMemoria contextoMemoria;

	public Processo(int pid, prioridade prioridade, int endMem, int tamanhoPrograma) {
		this.contextoSoftware = new ContextoSoftware(pid, prioridade);
		this.contextoMemoria = new ContextoMemoria(endMem, tamanhoPrograma);
		this.contextoSoftware.dadosEstatisticos.qtdMemoria = tamanhoPrograma;
	}

	public void incrementaEspera(int tempo, int tipoDeIntrucao) {
		switch (tipoDeIntrucao) {
		case Programa.instrucaoCPU: {
			this.contextoSoftware.dadosEstatisticos.pronto += tempo;
			break;
		}
		case Programa.instrucaoES1: {
			this.contextoSoftware.dadosEstatisticos.esperaES[0] += tempo;
			break;
		}
		case Programa.instrucaoES2: {
			this.contextoSoftware.dadosEstatisticos.esperaES[1] += tempo;
			break;
		}
		case Programa.instrucaoES3: {
			this.contextoSoftware.dadosEstatisticos.esperaES[2] += tempo;
			break;
		}
		}
	}

	public void proximaIntrucao() {
		// esta função assume que para ir pra a proxima intrução, foi feito o
		// "processamento", portanto faz as devidas alterações nos dados
		// estatisticos do contexto de software

		switch (this.intrucaoAtual()) {
		case Programa.instrucaoCPU: {
			this.contextoSoftware.dadosEstatisticos.CPU++;
			break;
		}

		case Programa.instrucaoES1: {
			this.contextoSoftware.dadosEstatisticos.ES[0]++;
			break;
		}

		case Programa.instrucaoES2: {
			this.contextoSoftware.dadosEstatisticos.ES[1]++;
			break;
		}

		case Programa.instrucaoES3: {
			this.contextoSoftware.dadosEstatisticos.ES[2]++;
			break;
		}
		}

		this.contextoSoftware.PosicaoInstrucaoAtual++;
	}

	public int intrucaoAtual() {
		// return
		// this.contextoMemoria.programa.obtem(this.contextoSoftware.PosicaoInstrucaoAtual);
		// TODO intruçãoAtual
		return 0;
	}

	public prioridade getPrioridade() {
		return this.contextoSoftware.prioridade;
	}

	public void sinalFinalizacao() {
		// this.contextoMemoria.programa.sobrepoemPosicao(instrucaoFIM,
		// this.contextoSoftware.PosicaoInstrucaoAtual);
		// TODO sinalFinalização
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