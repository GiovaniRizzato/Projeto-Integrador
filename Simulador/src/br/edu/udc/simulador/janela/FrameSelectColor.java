package br.edu.udc.simulador.janela;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class FrameSelectColor extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Color color = Color.LIGHT_GRAY;
	public FrameSelectColor(){
		color = JColorChooser.showDialog(FrameSelectColor.this, "Escolher cor", this.color);
	}
	public Color getColor(){
		return this.color;
	}
	
}
