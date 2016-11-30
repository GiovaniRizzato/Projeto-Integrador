package br.edu.udc.simulador.controle;

import java.util.concurrent.Semaphore;

import br.edu.udc.ed.iteradores.Iterador;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.ed.lista.encadeada.ListaEncadeada;
import br.edu.udc.ed.lista.vetor.Vetor;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.janela.Interface0_1;
import br.edu.udc.simulador.janela.view.AttView;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.processo.Processo.Prioridade;
import br.edu.udc.simulador.so.EstatisticaSO;
import br.edu.udc.simulador.so.SistemaOperacional;
import br.edu.udc.simulador.so.SistemaOperacional.Estrategia;
import br.edu.udc.simulador.controle.janela.EstrategiaSelect;

public class Computador {

	public static void main(String[] args) {
		Interface0_1.main(null);
	}

	private static Computador instancia;

	private Lista<AttView> listaViews = new ListaEncadeada<>();

	private SistemaOperacional simulador;
	private Hardware hardware;

	// Controle de sincronismo
	Semaphore semafaro = new Semaphore(1);
	Boolean execultando = true;
	Thread thread;

	public static Computador getInstancia() {
		if (Computador.instancia == null) {
			Computador.instancia = new Computador();
		}

		return Computador.instancia;
	}

	public Computador() {
		this.hardware = new Hardware(50, 20, 20, 20, 100);
		Estrategia estrategia = EstrategiaSelect.escolha();
		this.simulador = new SistemaOperacional(10, 0.6F, 0.3F, estrategia, this.hardware);

		this.thread = this.criaThread(10000);
		this.thread.start();
	}

	private Thread criaThread(int tempoEspera) {
		return new Thread() {

			@Override
			public void run() {
				while (execultando) {
					Computador.this.semafaro.acquireUninterruptibly();

					Computador.this.simulador.execultarProcessos();
					Computador.this.atualizaViews();

					Computador.this.semafaro.release();
					
					try {
						Thread.sleep(tempoEspera);
					} catch (InterruptedException e) {
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

	public void criaProcesso(Prioridade prioridade, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {
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