package br.edu.udc.simulador.janela.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ViewGrafico extends JPanel implements AttView {
	private static final long serialVersionUID = 1L;

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void paint(Graphics grafico){
		super.paint(grafico);
		int a=10;
		int b=10;
		grafico.setColor(Color.red);
		int e = 0;
		for(int i=0;i<6;i++){
		grafico.drawLine(a, b, a, b);
		a+=10;
		if(i==5 &&e<=6){
			e++;
			i=0;
		}
		}
	}

}
