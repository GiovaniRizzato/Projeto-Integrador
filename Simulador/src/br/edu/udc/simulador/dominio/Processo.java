package br.edu.udc.simulador.dominio;

import br.edu.udc.simulador.dominio.ed.Vetor;

public class Processo {

	public enum prioridade {
		ALTA(0), MEDIA(1), BAIXA(2);

		private final int valor;

		prioridade(int value) {
			this.valor = value;
		}

		public int getValor() {
			return this.valor;
		}
	}

	public enum tipoDeIntrucao {
		CPU(0), ES1(1), ES2(2), ES3(3), FIM(-1);
		
		private final int valor;

		tipoDeIntrucao(int value) {
			this.valor = value;
		}

		public int getValor() {
			return this.valor;
		}
	}

	@SuppressWarnings("unused")
	private class ContextoSoftware {
		public final int pid;
		public prioridade prioridade;
		
		public int instrucaoAtual;
		
		public int estatistica_tempoTotalEmSegundos;
		public int estatistica_CPU;
		public int estatistica_pronto;
		public int[] estatistica_ES = new int[3];
		public int[] estatistica_esperaES = new int[3];

		public ContextoSoftware(int pid, prioridade prioridade) {
			this.pid = pid;
			this.instrucaoAtual = 0;
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
			for (int i = 0; i > qtdCPU; i++)
				this.programa.adiciona(tipoDeIntrucao.CPU.getValor());

			for (int i = 0; i > qtdIO1; i++)
				this.programa.adiciona(tipoDeIntrucao.ES1.getValor());

			for (int i = 0; i > qtdIO2; i++)
				this.programa.adiciona(tipoDeIntrucao.ES2.getValor());

			for (int i = 0; i > qtdIO3; i++)
				this.programa.adiciona(tipoDeIntrucao.ES3.getValor());

			// PASSO 2- Embaralha as posições desse vetor.
			this.programa.shuffle();

			// PASSO 3- Adiciona o fim do programa ao final de todas as
			// intruções.
			this.programa.adiciona(tipoDeIntrucao.FIM.getValor());
		}
	}

	ContextoSoftware contextoSoftware;
	ContextoMemoria contextoMemoria;

	public Processo(int pid, prioridade prioridade, int qtdeMem, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {
		this.contextoSoftware = new ContextoSoftware(pid, prioridade);
		this.contextoMemoria = new ContextoMemoria(qtdeMem, qtdCPU, qtdIO1, qtdIO2, qtdIO3);
	}

	public void proximaIntrucao() {
		// esta função assume que para ir pra a proxima intrução, foi feito o
		// "processamento", portanto faz as devidas alterações nos dados
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
		// função responsavel por fazer a contabilização, fazendo a contagem de
		// "fins" de clock
		this.contextoSoftware.estatistica_tempoTotalEmSegundos++;
	}
	
	public prioridade getPrioridade(){
		return this.contextoSoftware.prioridade;
	}
}
