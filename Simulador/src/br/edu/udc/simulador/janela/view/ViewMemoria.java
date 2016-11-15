package br.edu.udc.simulador.janela.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import br.edu.udc.simulador.Computador;

public class ViewMemoria extends JPanel implements AttView{
	private static final long serialVersionUID = 1L;
	protected Computador computador;
	
	public ViewMemoria(Computador computador) {
		super();
		
		this.computador = computador;
	}
	@Override
	public void atualizar() {
		
		repaint();
	}
	
	@Override
	public void paint(Graphics grafico){
		super.paint(grafico);
			grafico.drawRect(19, 19, 61, 400);
			int x=20;
			int y=0;
			int z=5;
			for(int i=0;i<10;i++){
				
				grafico.setColor(Color.RED);
				grafico.fillRect(20, x, 60, z);
				x+=z;
			}
			
	}

}
