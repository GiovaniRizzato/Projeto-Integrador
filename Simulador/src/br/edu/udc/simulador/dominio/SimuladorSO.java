package br.edu.udc.simulador.dominio;

import br.edu.udc.simulador.dominio.processo.Processo;

import br.edu.udc.simulador.dominio.ed.Fila;//Produto dos filtros
import br.edu.udc.simulador.dominio.ed.IteradorManipulador;
import br.edu.udc.simulador.dominio.ed.Iterator;//Produção dos filtros
import br.edu.udc.simulador.dominio.ed.Lista;//Armazenamento principal dos processos
import br.edu.udc.simulador.dominio.ed.TabelaEspalhameto;//Processos pausados
import br.edu.udc.simulador.dominio.ed.Vetor;//Dados estatisticos

public class SimuladorSO {

	private Processo processado;

	private Lista<Processo> listaPrincipal = new Lista<>();

	private Processo estadoCriacao;
	private Processo estadoFinalizacao;

	private TabelaEspalhameto<Processo> listaPausado = new TabelaEspalhameto<>();

	private Hardware hardware;

	// Podem ser declaradas por outras classes para "tirar" as estatisiticas
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

	public SimuladorSO(int clockCPU, int clockES1, int clockES2, int clockES3) {
		hardware = new Hardware(clockCPU, clockES1, clockES2, clockES3);
	}

	// VARIAVEL DE LOGICA
	private int proximoPidDisponivel = 0;

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

	public void criaNovoProcesso(Processo.prioridade prioridade, int qtdMemoria, int qtdCPU, int qtdES1, int qtdES2,
			int qtdES3) {

		this.estadoCriacao = new Processo(this.proximoPidDisponivel, prioridade, qtdMemoria, qtdCPU, qtdES1, qtdES2,
				qtdES3);

		this.listaPrincipal.adiciona(this.estadoCriacao);
		this.estadoCriacao = null;

		this.proximoPidDisponivel++;
	}

	public void sinalFinalizacao(int pid) {

		for (Iterator<Processo> it = this.listaPrincipal.inicio(); it.temProximo(); it.proximo()) {
			if (it.getDado().getPID() == pid) {
				it.getDado().sinalFinalizacao();
			}
		}

		throw new IllegalArgumentException("Pid não encontrado nos registros");
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

		this.estadoFinalizacao = null;
		// Forçando o garbege collector a deleta-lo
	}

	public EstatisticaSO getEstatisticas() {
		return this.estatisticaSO;
	}

	public void processaFilas() {

		final double porcentagemAlta = 0.6;
		final double porcentagemMedia = 0.3;
		final double porcentagemBaixa = 0.1;

		// I-O - (necessario ir primeiro pois o filtro de prioridades assume que
		// todos os processos de I/O foram retirados e não trata tal condição
		// ,ou seja, filtro de prioridade verifica APENAS a prioridade
		Fila<Processo> filaEsperaES1 = this.filtroIntrucaoAtual(Processo.instrucaoES1);
		Fila<Processo> filaEsperaES2 = this.filtroIntrucaoAtual(Processo.instrucaoES2);
		Fila<Processo> filaEsperaES3 = this.filtroIntrucaoAtual(Processo.instrucaoES3);

		// PRIORIDADE
		Fila<Processo> filaProntoAlta = this.filtroPrioridade(Processo.prioridade.ALTA);
		Fila<Processo> filaProntoMedia = this.filtroPrioridade(Processo.prioridade.MEDIA);
		Fila<Processo> filaProntoBaixa = this.filtroPrioridade(Processo.prioridade.BAIXA);

		int clockSobrado = processaFila(filaProntoAlta, Processo.instrucaoCPU,
				(this.hardware.getClockCPU() * porcentagemAlta));

		clockSobrado = processaFila(filaProntoMedia, Processo.instrucaoCPU,
				(this.hardware.getClockCPU() * porcentagemMedia) + clockSobrado);

		processaFila(filaProntoBaixa, Processo.instrucaoCPU,
				(this.hardware.getClockCPU() * porcentagemBaixa) + clockSobrado);

		processaFila(filaEsperaES1, Processo.instrucaoES1, this.hardware.getAllClocksES()[0]);
		processaFila(filaEsperaES2, Processo.instrucaoES2, this.hardware.getAllClocksES()[1]);
		processaFila(filaEsperaES3, Processo.instrucaoES3, this.hardware.getAllClocksES()[2]);
		
		// TODO Após ter feito todo o processamento, readiciona os elementos na
		// lista principal
	}

	private Fila<Processo> filtroIntrucaoAtual(int intrucaoAtual) {
		Fila<Processo> fila = new Fila<>();

		for (IteradorManipulador<Processo> it = listaPrincipal.inicio(); it.temProximo(); it.proximo()) {
			Processo atual = ((Processo) it.getDado());
			if (atual.intrucaoAtual() == intrucaoAtual) {

				fila.adiciona(atual);// adiciona a fila, para manter contato
				it.remove();// remove o elemento da lista
			}
		}

		return fila;
	}

	private Fila<Processo> filtroPrioridade(Processo.prioridade prioridade) {
		Fila<Processo> fila = new Fila<>();

		for (IteradorManipulador<Processo> it = listaPrincipal.inicio(); it.temProximo(); it.proximo()) {
			Processo atual = ((Processo) it.getDado());

			if (atual.getPrioridade() == prioridade) {
				fila.adiciona(atual);// adiciona a fila, para manter contato
				it.remove();// remove o elemento da lista
			}
		}

		return fila;
	}

	private int processaFila(Fila<Processo> fila, int tipoDeIntrucao, double clocksDestaFila) {

		int clockRestante = (int) clocksDestaFila;
		while (fila.tamanho() != 0 && clockRestante != 0) {

			int clockIndividual = (int) (clockRestante / fila.tamanho());
			clockRestante = 0;// para servir de accomulador

			for (int i = 0; i < fila.tamanho(); i++) {

				int clockNaoUsadosNestaOperacao = 0;

				// faz "copia" do processo para processamento
				this.processado = fila.consultaProximoElemento();

				// remove ela dos registros da fila, pois esta em
				// "processamento"
				fila.remover();

				clockNaoUsadosNestaOperacao = this.hardware.usarHardware(clockIndividual, tipoDeIntrucao,
						this.processado);
				// faz o processamento

				if (this.processado.intrucaoAtual() == tipoDeIntrucao) {
					// se mesmo depois do processamento pertencer a esta
					// fila(permance neste estado)
					fila.adiciona(this.processado);

				} else {
					if (this.processado.intrucaoAtual() != Processo.instrucaoFIM) {
						// agora passa a ser parte de outra fila(outro estado)
						this.listaPrincipal.adiciona(this.processado);

					} else {
						// fim do programa, irá ser finalizado
						this.estadoFinalizacao = this.processado;
						this.processado = null;
						this.matarProcesso();
					}
				}

				this.processado = null;
				clockRestante += clockNaoUsadosNestaOperacao;
			} // END FOR

		} // END WHILE

		return (int) clockRestante;
	}

	public void pausarProcesso(int pid) {
		// TODO pausar processo
	}

	private boolean contem(int pid) {
		return false;
		// TODO terminar de fazer se o elemento com pid "pid" está no sistema
		// tanto como ativo, e pausado
	}
}