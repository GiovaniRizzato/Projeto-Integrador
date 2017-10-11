package br.edu.udc.simulador.so.escalonador;

import br.edu.udc.ed.fila.Fila;
import br.edu.udc.ed.fila.encadeada.FilaEncadeada;
import br.edu.udc.ed.iteradores.Iterador;
import br.edu.udc.ed.iteradores.IteradorManipulador;
import br.edu.udc.ed.lista.Lista;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo;

public abstract class EscalonadorProcessos {

	// Abstração dos estados
	protected Processo processando;

	protected Float porcentagemCPUAlta;
	protected Float porcentagemCPUMedia;
	protected Float porcentagemCPUBaixa;

	// referencia
	protected Hardware hardware;
	protected Lista<Processo> listaPrincipal;

	public EscalonadorProcessos(Float porceAlta, Float porceMedia, Float porceBaixa, Lista<Processo> listaPrincipal,
			Hardware hardware) {
		this.hardware = hardware;

		this.porcentagemCPUAlta = porceAlta;
		this.porcentagemCPUMedia = porceMedia;
		this.porcentagemCPUBaixa = porceBaixa;
		this.listaPrincipal = listaPrincipal;
	}
	
	public Processo processoAtivo() {
		return this.processando;
	}

	public void execultarProcessos() {
		this.escalonar();

		for (Iterador<Processo> it = this.listaPrincipal.inicio(); it.temProximo(); it.proximo()) {
			final Processo processo = it.getDado();
			processo.incrementaTempoReal();
		}
	}

	protected abstract void escalonar();

	protected Fila<Processo> filtroIntrucaoAtual(int intrucaoFiltro) {
		Fila<Processo> fila = new FilaEncadeada<>();

		IteradorManipulador<Processo> it = this.listaPrincipal.inicio();
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

	protected Fila<Processo> filtroPrioridade(Processo.Prioridade prioridade) {
		Fila<Processo> fila = new FilaEncadeada<>();

		IteradorManipulador<Processo> it = this.listaPrincipal.inicio();
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
}
