package br.edu.udc.simulador.controle;

import br.edu.udc.ed.iteradores.Iterador;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.ed.lista.encadeada.ListaEncadeada;
import br.edu.udc.ed.lista.vetor.Vetor;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.janela.MainMenu;
import br.edu.udc.simulador.janela.view.AttView;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.processo.Processo.Prioridade;
import br.edu.udc.simulador.so.EstatisticaSO;
import br.edu.udc.simulador.so.SistemaOperacional;
import br.edu.udc.simulador.so.SistemaOperacional.Estrategia;
import br.edu.udc.simulador.controle.janela.EstrategiaSelect;

public class Computador {

	public static void main(String[] args) {
		MainMenu.main(null);
	}

	private static Computador instancia;

	private Lista<AttView> listaViews = new ListaEncadeada<>();

	private SistemaOperacional simulador;
	private Hardware hardware;

	// Controle de sincronismo
	private Boolean fimDoPrograma = false;
	private Thread thread;

	public static Computador getInstancia() {
		if (Computador.instancia == null) {
			Computador.instancia = new Computador();
		}

		return Computador.instancia;
	}

	public Computador() {
		this.hardware = new Hardware(10, 10, 10, 10, 400);
		Estrategia estrategia = EstrategiaSelect.escolha();
		this.simulador = new SistemaOperacional(10, 0.6F, 0.3F, estrategia, this.hardware);

		this.thread = this.criaThread(1000);
		this.thread.start();
	}

	private Thread criaThread(long tempoEspera) {
		return new Thread() {

			@Override
			public void run() {
				while (!fimDoPrograma) {
					Computador.this.execultarProcessos();
					Computador.this.atualizaViews();

					try {
						Thread.sleep(tempoEspera);
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		};
	}

	public SistemaOperacional getSimulador() {
		return this.simulador;
	}

	public Hardware getHardware() {
		return this.hardware;
	}

	private synchronized void execultarProcessos() {
		Computador.this.simulador.execultarProcessos();
		Computador.this.atualizaViews();
	}

	public synchronized void criaProcesso(Prioridade prioridade, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {
		this.simulador.criaNovoProcesso(prioridade, qtdCPU, qtdIO1, qtdIO2, qtdIO3);
		this.atualizaViews();
	}

	public void adicionaView(AttView view) {
		this.listaViews.adiciona(view);
		view.atualizar();
	}

	public void atualizaViews() {

		Iterador<AttView> it = this.listaViews.inicio();

		while (it.temProximo()) {
			final AttView view = it.getDado();
			view.atualizar();

			it.proximo();
		}
	}

	public Processo[] listaTodosAtivos() {
		return this.simulador.listaTodosAtivos();
	}

	public Processo[] listaTodosPausados() {
		return this.simulador.listaTodosPausados();
	}

	public Processo processoEmAndamento() {
		return this.simulador.getEmProcessamento();
	}

	public synchronized Vetor<Integer> getInstruçoesCPU() {

		EstatisticaSO estatisticas = this.simulador.getEstatisticas();
		return estatisticas.tempoDeCPU;
	}

	public synchronized void finalizarProcesso(int pid) {
		this.simulador.sinalFinalizacao(pid);
	}

	public synchronized void fimSimulacao() {
		this.fimDoPrograma = true;
	}

	public synchronized void touglePlay() {

		try {
			this.thread.wait();
		} catch (InterruptedException e) {
			this.thread.notify();
		}
	}
}