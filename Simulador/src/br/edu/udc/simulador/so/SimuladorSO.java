package br.edu.udc.simulador.so;

import br.edu.udc.ed.fila.Fila;
import br.edu.udc.ed.iteradores.IteradorManipulador;
import br.edu.udc.ed.iteradores.Iterador;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.ed.vetor.Vetor;
import br.edu.udc.simulador.Computador;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.processo.Programa;

public class SimuladorSO {

	private Processo processando;

	private Lista<Processo> listaPrincipal = new Lista<>();

	private Processo estadoCriacao;
	private Processo estadoFinalizacao;

	private Vetor<Processo> listaPausado = new Vetor<>();
	// TODO Alterar para Chave/Valor

	private Hardware hardware;

	private class Particao {
		public Integer pid;
		public Integer tamanho;
		public Integer posicao;

		public Particao(int pid, int tamanho, int posicao) {
			this.pid = pid;
			this.tamanho = tamanho;
			this.posicao = posicao;
		}
	}

	private Lista<Particao> listaMemoria = new Lista<>();

	private final int posicaoMemoriaVazia = -1;
	private final int pidSO = posicaoMemoriaVazia + 1;

	// Podem ser declaradas por outras classes para "copiar" as estatisiticas
	public static class EstatisticaSO {
		public Vetor<Integer> qtdMemoria = new Vetor<>();
		public Vetor<Integer> tempoDeCPU = new Vetor<>();
		public Vetor<Integer> tempoDePronto = new Vetor<>();
		public Vetor<Integer> tempoDeES1 = new Vetor<>();
		public Vetor<Integer> tempoDeES2 = new Vetor<>();
		public Vetor<Integer> tempoDeES3 = new Vetor<>();
		public Vetor<Integer> tempoDeEsperaES1 = new Vetor<>();
		public Vetor<Integer> tempoDeEsperaES2 = new Vetor<>();
		public Vetor<Integer> tempoDeEsperaES3 = new Vetor<>();
	}

	private EstatisticaSO estatisticaSO = new EstatisticaSO();

	public SimuladorSO(Hardware hardware, int tamanhoSO) {
		this.hardware = hardware;
		this.listaMemoria.adiciona(new Particao(this.pidSO, tamanhoSO, 0));
		this.listaMemoria.adiciona(
				new Particao(this.posicaoMemoriaVazia, this.hardware.tamanhoMemoria() - tamanhoSO, tamanhoSO + 1));
	}

	// VARIAVEL DE LOGICA
	private int proximoPidDisponivel = this.pidSO + 1;
	// pois posições ocupadas teram o valor do PID

	public int qtdProcessosAtivos() {
		return this.listaPrincipal.tamanho();
	}

	public Processo[] listaTodos() {

		Processo[] processos = new Processo[this.listaPrincipal.tamanho()];
		IteradorManipulador<Processo> it = this.listaPrincipal.inicio();

		for (int i = 0; i < this.listaPrincipal.tamanho(); i++) {
			processos[i] = it.getDado();
			it.proximo();
		}

		return processos;
	}

	public void criaNovoProcesso(Processo.prioridade prioridade, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3,
			String alocacao) throws RuntimeException {
		// TODO Fazer case de string = prioridade...
		// TODO switch alocaçao...

		final Vetor<Integer> programa = Programa.criaPrograma(qtdCPU, qtdIO1, qtdIO2, qtdIO3);
		int posicaoAlocada = 0;

		// Area de probabilidade de acontecer "RunTimeExeption"
		switch (alocacao) {
		case Computador.first_fit:
			posicaoAlocada = procuraPosicaoMemoria_first(programa.tamanho(), this.proximoPidDisponivel);
			break;

		/**
		 * case Computador.best_fit: posicaoAlocada =
		 * procuraPosicaoMemoria_best(programa.tamanho(),
		 * this.proximoPidDisponivel); break;
		 * 
		 * case Computador.worst_fit: posicaoAlocada =
		 * procuraPosicaoMemoria_worst(programa.tamanho(),
		 * this.proximoPidDisponivel); break;
		 */
		}
		// Fim área de erro

		this.hardware.preencheMemoria(posicaoAlocada, programa);

		this.estadoCriacao = new Processo(this.proximoPidDisponivel, prioridade, posicaoAlocada, programa.tamanho());

		this.listaPrincipal.adiciona(this.estadoCriacao);
		this.estadoCriacao = null;

		this.proximoPidDisponivel++;
	}

	private int procuraPosicaoMemoria_first(int tamanhoPrograma, int pid) {

		for (IteradorManipulador<Particao> it = this.listaMemoria.inicio(); it.temProximo(); it.proximo()) {
			// Procura por uma partição vazia
			if (it.getDado().pid == this.posicaoMemoriaVazia) {

				// Verifica se a partição é grande o suficiente para allocação
				if (it.getDado().tamanho > tamanhoPrograma) {
					final int posicaoAllocada = it.getDado().posicao;

					// atualiza a posicao vazia
					it.adicionaAntes(new Particao(pid, tamanhoPrograma, it.getDado().posicao));
					it.getDado().posicao += tamanhoPrograma;
					it.getDado().tamanho -= tamanhoPrograma;

					return posicaoAllocada;
				} // END IF tamanho
			} // END IF vazio
		} // END FOR

		throw new RuntimeException("Não há partição grande o suficiente");
	}

	/**
	 * private int procuraPosicaoMemoria_best(int tamanhoProgama) { return 0; }
	 * 
	 * private int procuraPosicaoMemoria_worst(int tamanhoProgama) { return 0; }
	 */

	// TODO outras estratégias de allocação de memória

	public void sinalFinalizacao(int pid) {

		for (Iterador<Processo> it = this.listaPrincipal.inicio(); it.temProximo(); it.proximo()) {
			if (it.getDado().getPID() == pid) {

				// Intruções de finalização que irá execultar
				Vetor<Integer> intrucaoFim = new Vetor<>();
				intrucaoFim.adiciona(Programa.instrucaoFIM);

				// sobre escreve desde a posição
				this.hardware.preencheMemoria(it.getDado().posicaoIntrucaoAtual(), intrucaoFim);
				return;
			}
		}

		throw new IllegalArgumentException("Pid não encontrado nos registros");
		// [Professor] qual a melhor exeção neste caso?
	}

	private void matarProcesso() {

		Processo.DadosEstatisticos estatistica;
		estatistica = this.estadoFinalizacao.getDadosEstatisticos();

		this.estatisticaSO.qtdMemoria.adiciona(estatistica.qtdMemoria);
		this.estatisticaSO.tempoDeCPU.adiciona(estatistica.CPU);
		this.estatisticaSO.tempoDePronto.adiciona(estatistica.pronto);
		this.estatisticaSO.tempoDeEsperaES1.adiciona(estatistica.esperaES[0]);
		this.estatisticaSO.tempoDeEsperaES2.adiciona(estatistica.esperaES[1]);
		this.estatisticaSO.tempoDeEsperaES3.adiciona(estatistica.esperaES[2]);
		this.estatisticaSO.tempoDeES1.adiciona(estatistica.ES[0]);
		this.estatisticaSO.tempoDeES2.adiciona(estatistica.ES[1]);
		this.estatisticaSO.tempoDeES3.adiciona(estatistica.ES[2]);

		// TODO desalocação de momoria

		this.estadoFinalizacao = null;
		// Forçando o garbege collector a deleta-lo
	}

	public EstatisticaSO getEstatisticas() {
		return this.estatisticaSO;
		// [Professor] isto é uma refencia?
	}

	public void processaFilas() {

		final int reservadoParaAlta = (int) (this.hardware.getClockCPU() * 0.6F);
		final int reservadoParaMedia = (int) (this.hardware.getClockCPU() * 0.3F);
		final int reservadoParaBaixa = (int) (this.hardware.getClockCPU() * 0.1F);

		// I-O - (necessario ir primeiro pois o filtro de prioridades assume que
		// todos os processos de I/O foram retirados e não trata tal condição
		// ,ou seja, filtro de prioridade verifica APENAS a prioridade
		Fila<Processo> filaEsperaES1 = this.filtroIntrucaoAtual(Programa.instrucaoES1);
		Fila<Processo> filaEsperaES2 = this.filtroIntrucaoAtual(Programa.instrucaoES2);
		Fila<Processo> filaEsperaES3 = this.filtroIntrucaoAtual(Programa.instrucaoES3);
		// PRIORIDADE
		Fila<Processo> filaProntoAlta = this.filtroPrioridade(Processo.prioridade.ALTA);
		Fila<Processo> filaProntoMedia = this.filtroPrioridade(Processo.prioridade.MEDIA);
		Fila<Processo> filaProntoBaixa = this.filtroPrioridade(Processo.prioridade.BAIXA);

		// Processamento
		int clockSobrado = processaFila(filaProntoAlta, Programa.instrucaoCPU, reservadoParaAlta, 0);

		clockSobrado = processaFila(filaProntoMedia, Programa.instrucaoCPU, reservadoParaMedia + clockSobrado,
				reservadoParaAlta - clockSobrado);

		processaFila(filaProntoBaixa, Programa.instrucaoCPU, reservadoParaBaixa + clockSobrado,
				(reservadoParaAlta + reservadoParaMedia) - clockSobrado);

		processaFila(filaEsperaES1, Programa.instrucaoES1, this.hardware.getAllClocksES()[0], 0);
		processaFila(filaEsperaES2, Programa.instrucaoES2, this.hardware.getAllClocksES()[1], 0);
		processaFila(filaEsperaES3, Programa.instrucaoES3, this.hardware.getAllClocksES()[2], 0);

		// Para que as filas possam deixar de existir, mas os processo não
		this.listaPrincipal.adiciona(filaProntoAlta.toVetor());
		this.listaPrincipal.adiciona(filaProntoMedia.toVetor());
		this.listaPrincipal.adiciona(filaProntoBaixa.toVetor());
		this.listaPrincipal.adiciona(filaEsperaES1.toVetor());
		this.listaPrincipal.adiciona(filaEsperaES2.toVetor());
		this.listaPrincipal.adiciona(filaEsperaES3.toVetor());
	}

	private Fila<Processo> filtroIntrucaoAtual(int intrucaoFiltro) {
		Fila<Processo> fila = new Fila<>();

		IteradorManipulador<Processo> it = listaPrincipal.inicio();
		while (it.temProximo()) {
			Processo atual = ((Processo) it.getDado());
			if (this.hardware.getPosicaoMemoria(atual.posicaoIntrucaoAtual()) == intrucaoFiltro) {

				fila.adiciona(atual);// adiciona a fila, para manter contato
				it.remove();// remove o elemento da lista
			} else {
				it.proximo();
			}
		}

		return fila;
	}

	private Fila<Processo> filtroPrioridade(Processo.prioridade prioridade) {
		Fila<Processo> fila = new Fila<>();

		IteradorManipulador<Processo> it = listaPrincipal.inicio();
		while (it.temProximo()) {
			Processo atual = ((Processo) it.getDado());
			if (atual.getPrioridade().equals(prioridade)) {

				fila.adiciona(atual);// adiciona a fila
				it.remove();// remove o elemento da lista
			} else {
				it.proximo();
			}
		}

		return fila;
	}

	private int processaFila(Fila<Processo> fila, int tipoDeIntrucao, int clocksDestaFila, int tempoEsperaAtual) {

		int clockRestante = clocksDestaFila;
		while (fila.tamanho() != 0 && clockRestante != 0) {

			final int clockIndividual = (int) (clockRestante / fila.tamanho());
			clockRestante = 0;// para servir de accomulador

			for (int i = 0; i < fila.tamanho(); i++) {

				// faz "copia" do processo para processamento
				this.processando = fila.consultaProximoElemento();

				// remove ela dos registros da fila, pois esta em
				// "processamento"
				fila.remove();

				this.processando.incrementaEspera(tempoEsperaAtual, tipoDeIntrucao);
				// incrementa as variaveis de estatistica
				// TODO fazer estatistica de espera APÓS ter sido execultado

				int clockNaoUsadosNestaOperacao = this.hardware.usarProcessamentoHardware(clockIndividual,
						tipoDeIntrucao, this.processando);
				// faz o processamento

				if (this.processando.posicaoIntrucaoAtual() == tipoDeIntrucao) {
					// se mesmo depois do processamento pertencer a esta
					// fila(permance neste estado)
					fila.adiciona(this.processando);

				} else {
					if (this.processando.posicaoIntrucaoAtual() != Programa.instrucaoFIM) {
						// agora passa a ser parte de outra fila(outro estado)
						this.listaPrincipal.adiciona(this.processando);

					} else {
						// fim do programa
						this.estadoFinalizacao = this.processando;
						this.processando = null;
						this.matarProcesso();
					}
				}

				this.processando = null;
				tempoEsperaAtual += (clockIndividual - clockNaoUsadosNestaOperacao);
				clockRestante += clockNaoUsadosNestaOperacao;
			} // END FOR
		} // END WHILE

		return clockRestante;
	}

	public void pausarProcesso(int pid) {
		for (IteradorManipulador<Processo> it = this.listaPrincipal.inicio(); it.temProximo(); it.proximo()) {
			if (it.getDado().getPID() == pid) {
				this.listaPausado.adiciona(it.getDado());
				it.remove();
				return;
			}
		}

		throw new IllegalArgumentException("Nenhum processo com este PID está ativo");
	}

	public void resumePorcesso(int pid) {

		for (int i = 0; i > this.listaPausado.tamanho(); i++) {
			if (this.listaPausado.obtem(i).getPID() == pid) {
				this.listaPrincipal.adiciona(this.listaPausado.obtem(i));
				this.listaPausado.remove(i);
				return;
			}
		}

		throw new IllegalArgumentException("Nenhum processo com este PID está pausado");
	}
}