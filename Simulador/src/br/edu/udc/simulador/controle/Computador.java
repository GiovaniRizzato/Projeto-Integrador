package br.edu.udc.simulador.controle;

import java.util.concurrent.Semaphore;

import br.edu.udc.ed.iteradores.Iterador;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.ed.lista.encadeada.ListaEncadeada;
import br.edu.udc.ed.lista.vetor.Vetor;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.janela.view.AttView;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.so.EstatisticaSO;
import br.edu.udc.simulador.so.SimuladorSO;

public class Computador {

	private static Computador instancia;

	private Lista<AttView> listaViews = new ListaEncadeada<>();

	private SimuladorSO simulador;
	private Hardware hardware;

	// Controle de sincronismo
	Semaphore semafaro = new Semaphore(1);
	Boolean execultando = true;

	public static Computador getInstancia() {
		if (Computador.instancia == null) {
			Computador.instancia = new Computador();
		}

		return Computador.instancia;
	}

	private Computador() {
		this.hardware = new Hardware(10, 10, 10, 10, 50);
		this.simulador = new SimuladorSO(hardware, 10, 0.6F, 0.3F);
		this.criaThread(1000);
	}

	private void criaThread(int tempoEspera) {
		new Runnable() {

			@Override
			public void run() {
				while (execultando) {
					Computador.this.semafaro.acquireUninterruptibly();

					Computador.this.simulador.execultarProcessos();
					Computador.this.atualizaViews();

					Computador.this.semafaro.release();
				}
			}
		};
	}

	public SimuladorSO getSimulador() {
		return this.simulador;
	}

	public void criaProcesso(Processo.Prioridade prioridade, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {
		this.semafaro.acquireUninterruptibly();

		this.simulador.criaNovoProcesso(prioridade, qtdCPU, qtdIO1, qtdIO2, qtdIO3);
		this.atualizaViews();

		this.semafaro.release();
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

	public Processo[] listaTodos() {
		return this.simulador.listaTodosAtivos();
	}

	public Vetor<Integer> getInstruçoesCPU() {
		this.semafaro.acquireUninterruptibly();

		EstatisticaSO estatisticas = this.simulador.getEstatisticas();

		this.semafaro.release();

		return estatisticas.tempoDeCPU;
	}

	public void finalizarProcesso(int pid) {
		this.semafaro.acquireUninterruptibly();

		this.simulador.sinalFinalizacao(pid);

		this.semafaro.release();
	}

	public void fimSimuladcao() {
		this.semafaro.acquireUninterruptibly();

		this.execultando = false;

		this.semafaro.release();
	}
}