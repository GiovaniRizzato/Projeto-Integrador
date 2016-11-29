package br.edu.udc.simulador.janela.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import br.edu.udc.simulador.janela.SiloDeCor;

public class ViewMemoria extends JPanel implements AttView{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void atualizar() {
		
		repaint();
	}
	
	@Override
	public void paint(Graphics grafico){
		super.paint(grafico);
		
				grafico.drawRect(10,10, 61, 40);
			int x=10;
			final int z=5;
			
			
			SiloDeCor silo = SiloDeCor.getIntancia();
			silo.adiciona(0, Color.RED);
			silo.adiciona(1, Color.blue);
			silo.adiciona(2, Color.green);
			silo.adiciona(3, Color.yellow);
			silo.adiciona(4, Color.cyan);
			silo.adiciona(5, Color.gray);
			
			int e=0;
			for(int i=0;i<6;i++){
				
				grafico.setColor(silo.obtem(i));
				grafico.fillRect(10, x, 60, z);
				x+=z;
				
				if(i==5 && e<=6){
					e++;
					i=0;
				}
				/*grafico.setColor(silo.obtem(pid));
				grafico.fillRect(40, x, 60, z);
				x+=z;*/
			}
			
	}

}
