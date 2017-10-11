package br.edu.udc.simulador.so;

import br.edu.udc.ed.iteradores.IteradorManipulador;
import br.edu.udc.ed.iteradores.Iterador;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.ed.lista.encadeada.ListaEncadeada;
import br.edu.udc.ed.lista.vetor.Vetor;
import br.edu.udc.ed.mapa.Mapa;
import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.processo.Programa;
import br.edu.udc.simulador.so.escalonador.EscalonadorProcessos;
import br.edu.udc.simulador.so.escalonador.TimeSharing;
import br.edu.udc.simulador.so.memoria.BestFit;
import br.edu.udc.simulador.so.memoria.FirstFit;
import br.edu.udc.simulador.so.memoria.GerenciadorMemoria;
import br.edu.udc.simulador.so.memoria.WorstFit;

public class SistemaOperacional {

	// Escolha de estratégia
	public enum Estrategia {
		FIRST_FIT, BEST_FIT, WORST_FIT;
	}

	// Abstração de estados do processo
	private Processo estadoCriacao;
	private Processo estadoFinalizacao;

	// Armazenamento de processos
	private Lista<Processo> listaPrincipal = new ListaEncadeada<>();
	private Mapa<Integer, Processo> listaPausado = new Mapa<>();

	// Gerenciador
	GerenciadorMemoria gerenciador;
	EscalonadorProcessos escalonador;

	// Veriaveis lógicas
	public final static int POSICAO_MEMORIA_VAZIA = -1;
	public final static int PID_SO = POSICAO_MEMORIA_VAZIA + 1;
	private Integer proximoPidDisponivel = SistemaOperacional.PID_SO + 1;

	private EstatisticaSO estatisticaSO = new EstatisticaSO();

	public SistemaOperacional(int tamanhoSO, float porcAlta, float porcMedia, Estrategia estrategia,
			Hardware hardware) {

		switch (estrategia) {
		case FIRST_FIT: {
			this.gerenciador = new FirstFit(tamanhoSO, hardware.tamanhoMemoria(), hardware);
			break;
		}
		case BEST_FIT: {
			this.gerenciador = new BestFit(tamanhoSO, hardware.tamanhoMemoria(), hardware);
		}
		case WORST_FIT: {
			this.gerenciador = new WorstFit(tamanhoSO, hardware.tamanhoMemoria(), hardware);
		}
		}

		final Float porcBaixa = (1 - (porcAlta + porcMedia));
		if (porcBaixa < 0 || porcBaixa > 1) {
			throw new IllegalArgumentException("Porcentagens inconcistentes");
		}
		this.escalonador = new TimeSharing(porcAlta, porcMedia, porcBaixa, listaPrincipal, hardware);
	}

	public EstatisticaSO getEstatisticas() {
		return this.estatisticaSO.clone();
	}

	public int qtdProcessos() {
		return (this.listaPrincipal.tamanho() + this.listaPausado.tamanho());
	}

	public int getProximoPid() {
		return this.proximoPidDisponivel;
	}

	public Processo[] listaTodosAtivos() {

		Processo[] processos = new Processo[this.listaPrincipal.tamanho()];

		Iterador<Processo> it = this.listaPrincipal.inicio();
		for (int i = 0; i < this.listaPrincipal.tamanho(); i++) {
			processos[i] = it.getDado();
			it.proximo();
		}

		return processos;
	}

	public Processo[] listaTodosPausados() {

		Processo[] processos = new Processo[this.listaPausado.tamanho()];

		final Vetor<Processo> vetor = this.listaPausado.todosValores();
		Iterador<Processo> it = vetor.inicio();
		for (int i = 0; i < vetor.tamanho(); i++) {
			processos[i] = it.getDado();
			it.proximo();
		}

		return processos;
	}

	public Processo getEmProcessamento() {
		return this.escalonador.processoAtivo();
	}

	public void criaNovoProcesso(Processo.Prioridade prioridade, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {

		final Programa programa = new Programa(qtdCPU, qtdIO1, qtdIO2, qtdIO3);

		// posicao 0 pois sera setado dentro do alocação de memoria
		this.estadoCriacao = new Processo(this.proximoPidDisponivel, prioridade, 0, programa.tamanho());

		this.gerenciador.allocaMemoria(this.estadoCriacao, programa);

		this.listaPrincipal.adiciona(this.estadoCriacao);
		this.estadoCriacao = null;

		this.proximoPidDisponivel++;
	}

	public void execultarProcessos() {
		this.escalonador.execultarProcessos();
	}

	public void alteraPrioridade(int pid, Processo.Prioridade novaPrioridade) {

		for (IteradorManipulador<Processo> it = this.listaPrincipal.inicio(); it.temProximo(); it.proximo()) {
			final Processo processo = it.getDado();
			if (processo.getPID() == pid) {
				processo.setPrioridade(novaPrioridade);
			}
		}

		throw new IllegalArgumentException("Pid não encontrado");
	}

	public void sinalFinalizacao(int pid) {

		for (Iterador<Processo> it = this.listaPrincipal.inicio(); it.temProximo(); it.proximo()) {
			if (it.getDado().getPID() == pid) {

				// "Sequencia" intruções de finalização que irá execultar
				Programa intrucaoFim = new Programa();
				intrucaoFim.adiciona(Programa.instrucaoFIM);

				// sobre escreve desde a posição
				Hardware hardware = Computador.getInstancia().getHardware();
				hardware.preencheMemoria(it.getDado().posicaoIntrucaoAtual(), intrucaoFim);
				return;
			}
		}

		throw new IllegalArgumentException("Pid não encontrado");
	}

	public void matarProcesso(Processo processo) {

		this.estadoFinalizacao = processo;

		Processo.DadosEstatisticos estatistica = processo.getDadosEstatisticos();
		this.estatisticaSO.qtdMemoria.adiciona(estatistica.qtdMemoria);
		this.estatisticaSO.tempoDeCPU.adiciona(estatistica.CPU);
		this.estatisticaSO.tempoDePronto.adiciona(estatistica.pronto);
		this.estatisticaSO.tempoDeEsperaES1.adiciona(estatistica.esperaES[0]);
		this.estatisticaSO.tempoDeEsperaES2.adiciona(estatistica.esperaES[1]);
		this.estatisticaSO.tempoDeEsperaES3.adiciona(estatistica.esperaES[2]);
		this.estatisticaSO.tempoDeES1.adiciona(estatistica.ES[0]);
		this.estatisticaSO.tempoDeES2.adiciona(estatistica.ES[1]);
		this.estatisticaSO.tempoDeES3.adiciona(estatistica.ES[2]);

		this.gerenciador.desalocaMemoria(this.estadoFinalizacao.getPID());

		this.estadoFinalizacao = null;
		// Forçando o garbege collector a deleta-lo
	}

	public void desfragmentacaoMemoria() {
		this.gerenciador.desfragmentacaoMemoria();
	}

	public void pausarProcesso(int pid) {
		for (IteradorManipulador<Processo> it = this.listaPrincipal.inicio(); it.temProximo(); it.proximo()) {

			final int pidProcesso = it.getDado().getPID();
			if (pidProcesso == pid) {
				this.listaPausado.adiciona(pidProcesso, it.getDado());
				it.remove();
				return;
			}
		}

		throw new IllegalArgumentException("Nenhum processo com este PID está ativo");
	}

	public void resumePorcesso(int pid) {

		if (!this.listaPausado.contem(pid)) {
			throw new IllegalArgumentException("Nenhum processo com este PID está pausado");
		}

		this.listaPrincipal.adiciona(this.listaPausado.obtem(pid));
		this.listaPausado.remove(pid);
	}
}