package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import br.edu.udc.simulador.Computador;
import br.edu.udc.simulador.janela.view.AttView;
import br.edu.udc.simulador.janela.view.ViewMemoria;

public class FrameMemoria extends JFrame implements AttView{
	private static final long serialVersionUID = 1L;
	private ViewMemoria viewMemoria;
	private JPanel contentPane;
	private Computador computador;
	private JScrollPane scrollpane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameMemoria frame = new FrameMemoria();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameMemoria() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		computador = new Computador();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		scrollpane = new JScrollPane();
		viewMemoria = new ViewMemoria(computador);
		scrollpane.setViewportView(viewMemoria);
		viewMemoria.setPreferredSize(new Dimension(800,600));
		contentPane.add(scrollpane);
		
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		
	}
}
