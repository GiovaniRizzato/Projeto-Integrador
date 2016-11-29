package br.edu.udc.simulador.janela.view;

import java.awt.Graphics;
import javax.swing.JPanel;

import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.janela.SiloDeCor;
import br.edu.udc.simulador.processo.Processo;

public class ViewMemoria extends JPanel implements AttView {
	private static final long serialVersionUID = 1L;

	private Processo[] todosProcessos;

	public ViewMemoria() {
		Computador.getInstancia().adicionaView(this);
	}

	@Override
	public void atualizar() {
		this.todosProcessos = Computador.getInstancia().listaTodos();
		repaint();
	}

	@Override
	public void paint(Graphics grafico) {
		super.paint(grafico);

		grafico.drawRect(10, 10, 400, 60);

		if (this.todosProcessos == null) {
			return;
		}

		for (int i = 0; i < this.todosProcessos.length; i++) {
			final Processo processo = this.todosProcessos[i];

			// Calculo de cordenadas de pintura
			final int posicaoInicial = processo.getInicioPrograma() + 10;
			final int tamanho = processo.getTamanho();

			// atualização pintura do grafico
			grafico.setColor(SiloDeCor.getIntancia().obtem(processo.getPID()));
			grafico.fillRect(posicaoInicial, 10, tamanho, 60);
		}
	}
}
