package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Interface0_3 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface0_3 frame = new Interface0_3();
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
	public Interface0_3() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnProceso = new JMenu("Proceso");
		menuBar.add(mnProceso);
		
		JMenuItem mntmAdicionar = new JMenuItem("Adicionar");
		mnProceso.add(mntmAdicionar);
		
		JMenuItem mntmFinalizar = new JMenuItem("Finalizar");
		mnProceso.add(mntmFinalizar);
		
		JMenuItem mntmPausa = new JMenuItem("Pausar");
		mnProceso.add(mntmPausa);
		
		JMenuItem mntmRetomar = new JMenuItem("Resumir");
		mnProceso.add(mntmRetomar);
		
		JMenu mnHardware = new JMenu("Hardware");
		menuBar.add(mnHardware);
		
		JMenuItem mntmAlteraClockCpu = new JMenuItem("Altera clock CPU");
		mnHardware.add(mntmAlteraClockCpu);
		
		JMenuItem mntmAlteraClockEs = new JMenuItem("Altera clock E/S 1");
		mnHardware.add(mntmAlteraClockEs);
		
		JMenuItem mntmAlteraClockEs_1 = new JMenuItem("Altera clock E/S 2");
		mnHardware.add(mntmAlteraClockEs_1);
		
		JMenuItem mntmAlteraClockEs_2 = new JMenuItem("Altera clock E/S 3");
		mnHardware.add(mntmAlteraClockEs_2);
		
		JMenu mnEstatistica = new JMenu("Estatistica");
		menuBar.add(mnEstatistica);
		
		JMenuItem mntmGrafico = new JMenuItem("Grafico");
		mnEstatistica.add(mntmGrafico);
		
		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);
		
		JMenuItem mntmManual = new JMenuItem("Manual");
		mnAjuda.add(mntmManual);
		
		JMenuItem mntmSobre = new JMenuItem("Sobre");
		mnAjuda.add(mntmSobre);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
