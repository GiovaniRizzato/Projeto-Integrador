package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Canvas;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class Interface0_1 extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface0_1 frame = new Interface0_1();
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
	public Interface0_1() {
		super("Simulador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu Processos = new JMenu("Processos");
		menuBar.add(Processos);
		
		JMenuItem Adicionar = new JMenuItem("Adicionar");
		Adicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String obj1 = JOptionPane.showInputDialog(null,"vai se fuder software");
			}
		});
		Processos.add(Adicionar);
		
		JMenuItem Finalizar = new JMenuItem("Finalizar");
		Processos.add(Finalizar);
		
		JMenuItem Pausa = new JMenuItem("Pausar");
		Processos.add(Pausa);
		
		JMenuItem Retomar = new JMenuItem("Resumir");
		Processos.add(Retomar);
		
		JMenu Hardware = new JMenu("Hardware");
		menuBar.add(Hardware);
		
		JMenuItem AlteraClockCpu = new JMenuItem("Altera clock CPU");
		Hardware.add(AlteraClockCpu);
		
		JMenuItem AlteraClockEs = new JMenuItem("Altera clock E/S 1");
		Hardware.add(AlteraClockEs);
		
		JMenuItem AlteraClockEs_1 = new JMenuItem("Altera clock E/S 2");
		Hardware.add(AlteraClockEs_1);
		
		JMenuItem AlteraClockEs_2 = new JMenuItem("Altera clock E/S 3");
		Hardware.add(AlteraClockEs_2);
		
		JMenuItem Defragmenta = new JMenuItem("Desfragmenta");
		Hardware.add(Defragmenta);
		
		JMenu Ajuda = new JMenu("Ajuda");
		menuBar.add(Ajuda);
		
		JMenuItem Manual = new JMenuItem("Manual");
		Ajuda.add(Manual);
		
		JMenuItem Sobre = new JMenuItem("Sobre");
		Ajuda.add(Sobre);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Detalhes", null, panel, null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"Nome", "pid", "Status", "CPU", "Memoria"
			}
		));
		panel.add(table);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Grafico", null, panel_1, null);
		
		Canvas canvas = new Canvas();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 417, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(canvas, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
	}

}
