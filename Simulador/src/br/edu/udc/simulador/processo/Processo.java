package br.edu.udc.simulador.processo;

public class Processo {

	public static enum prioridade {
		ALTA, MEDIA, BAIXA;
	}

	public static class DadosEstatisticos {
		public Integer qtdMemoria = 0;
		public Integer tempoTotalEmSegundos = 0;
		public Integer CPU = 0;
		public Integer pronto = 0;
		public Integer[] ES = { 0, 0, 0 };
		public Integer[] esperaES = { 0, 0, 0 };
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

	// TODO estatistica de intruções feitas

	public void proximaIntrucao() {
		this.contextoSoftware.instrucaoAtual++;
	}

	public int posicaoIntrucaoAtual() {
		return this.contextoMemoria.posicaoInicial + this.contextoSoftware.instrucaoAtual;
	}

	public prioridade getPrioridade() {
		return this.contextoSoftware.prioridade;
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