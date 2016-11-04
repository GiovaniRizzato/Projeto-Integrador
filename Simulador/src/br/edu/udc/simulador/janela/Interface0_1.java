package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;
import br.edu.udc.simulador.Computador;

import java.awt.Canvas;

public class Interface0_1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane = new JPanel();;
	private JScrollPane scrollTabela = new JScrollPane();
	private Computador computador = new Computador();
	private ViewTabela viewTabela;

	// TableModel tableModel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			Computador computador = new Computador();

			public void run() {
				try {
					Interface0_1 frame = new Interface0_1(computador);
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
	public Interface0_1(Computador computador) {
		super("Simulador");
		this.computador = computador;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu processos = new JMenu("Processos");
		menuBar.add(processos);

		JMenuItem adicionar = new JMenuItem("Criar");
		adicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CriaProcessoFrame processo = new CriaProcessoFrame();
				processo.setVisible(true);
			}
		});
		processos.add(adicionar);

		JMenuItem alterarPrioridade = new JMenuItem("Alterar prioridade");
		processos.add(alterarPrioridade);
		alterarPrioridade.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DialogAlteraPrioridade alteraPrioridadeDialog = new DialogAlteraPrioridade("Altera prioridade");
				alteraPrioridadeDialog.setVisible(true);
			}
		});
		JMenuItem finalizar = new JMenuItem("Finalizar");
		processos.add(finalizar);
		finalizar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DialogAcoes finalizaProcesso = new DialogAcoes("Finalizar processo");
				finalizaProcesso.setVisible(true);
			}
		});

		JMenuItem pausa = new JMenuItem("Pausar");
		processos.add(pausa);
		pausa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DialogAcoes pausaProcesso = new DialogAcoes("Pausar Processo");
				pausaProcesso.setVisible(true);
			}
		});
		JMenuItem retomar = new JMenuItem("Retomar");
		processos.add(retomar);
		retomar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DialogAcoes retomaProcesso = new DialogAcoes("Retomar Processo");
				retomaProcesso.setVisible(true);
			}
		});
		JMenu hardware = new JMenu("Hardware");
		menuBar.add(hardware);

		JMenuItem alteraClockCpu = new JMenuItem("Altera clock CPU");
		hardware.add(alteraClockCpu);
		alteraClockCpu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DialogAlteraClock alteraClock = new DialogAlteraClock("Altera numero de Intruçoes CPU");
				alteraClock.setVisible(true);
			}
		});
		JMenuItem alteraClockEs_1 = new JMenuItem("Altera clock E/S 1");
		hardware.add(alteraClockEs_1);
		alteraClockEs_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DialogAlteraClock alteraClockES1 = new DialogAlteraClock("Altera numero de Intruçoes E/S.1");
				alteraClockES1.setVisible(true);
			}
		});

		JMenuItem alteraClockEs_2 = new JMenuItem("Altera clock E/S 2");
		hardware.add(alteraClockEs_2);
		alteraClockEs_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DialogAlteraClock alteraClockES2 = new DialogAlteraClock("Altera numero de Intruçoes E/S.2");
				alteraClockES2.setVisible(true);
			}
		});
		JMenuItem alteraClockEs_3 = new JMenuItem("Altera clock E/S 3");
		hardware.add(alteraClockEs_3);
		alteraClockEs_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DialogAlteraClock alteraClockES3 = new DialogAlteraClock("Altera numero de Intruçoes E/S.3");
				alteraClockES3.setVisible(true);

			}
		});
		JMenuItem desfragmenta = new JMenuItem("Desfragmenta");
		hardware.add(desfragmenta);
		desfragmenta.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		JMenu Ajuda = new JMenu("Ajuda");
		menuBar.add(Ajuda);

		JMenuItem Manual = new JMenuItem("Manual");
		Ajuda.add(Manual);

		JMenuItem Sobre = new JMenuItem("Sobre");
		Ajuda.add(Sobre);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Detalhes", null, panel, null);

		viewTabela = new ViewTabela(this.computador);
		scrollTabela.setViewportView(viewTabela);

		panel.add(viewTabela);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Grafico", null, panel_1, null);

		Canvas canvas = new Canvas();
		panel_1.add(canvas);

	}
}
