package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import java.awt.SystemColor;

public class Interfaca0_2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaca0_2 frame = new Interfaca0_2();
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
	public Interfaca0_2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		setJMenuBar(menuBar);
		
		JMenu mnArquivos = new JMenu("Arquivos");
		mnArquivos.setBackground(Color.WHITE);
		menuBar.add(mnArquivos);
		
		JMenuItem mntmNovoProceso = new JMenuItem("Novo Proceso");
		mnArquivos.add(mntmNovoProceso);
		
		JMenuItem mntmEliminaProceso = new JMenuItem("Elimina Proceso");
		mnArquivos.add(mntmEliminaProceso);
		
		JMenu mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
		
		JMenuItem mntmRetomarProceso = new JMenuItem("Retomar Proceso");
		mnEditar.add(mntmRetomarProceso);
		
		JMenuItem mntmPausarProceso = new JMenuItem("Pausar Proceso");
		mnEditar.add(mntmPausarProceso);
		
		JMenuItem mntmAlteraPrioridadeDo = new JMenuItem("Altera Prioridade do proceso");
		mnEditar.add(mntmAlteraPrioridadeDo);
		
		JMenuItem mntmAlteraClockCpu = new JMenuItem("Altera Clock CPU");
		mnEditar.add(mntmAlteraClockCpu);
		
		JMenuItem mntmAlteraClockEs = new JMenuItem("Altera Clock E/S");
		mnEditar.add(mntmAlteraClockEs);
		
		JMenu mnVisualizar = new JMenu("Visualizar");
		mnVisualizar.setBackground(Color.WHITE);
		menuBar.add(mnVisualizar);
		
		JMenuItem mntmGrafico = new JMenuItem("Grafico");
		mnVisualizar.add(mntmGrafico);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
