package br.edu.udc.simulador.controle;

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

	public final static String textoPrioridadeAlta = "Alta";
	public final static String textoPrioridadeMedia = "Media";
	public final static String textoPrioridadeBaixa = "Baixa";

	public static Computador getInstancia() {
		if (Computador.instancia == null) {
			Computador.instancia = new Computador();
		}

		return Computador.instancia;
	}

	private Computador() {
		this.hardware = new Hardware(10, 10, 10, 10, 50);
		this.simulador = new SimuladorSO(hardware, 10, 0.6F, 0.3F);
	}

	public void criaProcesso(String prioridade, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3) {
		switch (prioridade) {
		case Computador.textoPrioridadeAlta:
			this.simulador.criaNovoProcesso(Processo.Prioridade.ALTA, qtdCPU, qtdIO1, qtdIO2, qtdIO3);
			break;

		case Computador.textoPrioridadeMedia:
			this.simulador.criaNovoProcesso(Processo.Prioridade.MEDIA, qtdCPU, qtdIO1, qtdIO2, qtdIO3);
			break;

		case Computador.textoPrioridadeBaixa:
			this.simulador.criaNovoProcesso(Processo.Prioridade.BAIXA, qtdCPU, qtdIO1, qtdIO2, qtdIO3);
			break;
		}

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

	public Processo[] listaTodos() {
		return this.simulador.listaTodosAtivos();
	}

	public Vetor<Integer> getInstru�oesCPU() {

		EstatisticaSO estatisticas = this.simulador.getEstatisticas();
		Vetor<Integer> vetor = estatisticas.tempoDeCPU;
		
		for (int i = 0; i < estatisticas.tempoDeCPU.tamanho(); i++) {
			System.out.println(estatisticas.tempoDeCPU.obtem(i));
		}
		
		return estatisticas.tempoDeCPU;
	}
}