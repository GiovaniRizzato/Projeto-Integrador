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
	
	Computador computador = new Computador();
	ViewTabela viewTabela;
//	TableModel tableModel;
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
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu Processos = new JMenu("Processos");
		menuBar.add(Processos);
		
		JMenuItem Adicionar = new JMenuItem("Criar");
		Adicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CriaProcessoFrame processo = new CriaProcessoFrame();
				processo.setVisible(true);
			}
		});
		Processos.add(Adicionar);
		
		JMenu AlterarPrioridade = new JMenu("Alterar prioridade");
		Processos.add(AlterarPrioridade);
		
		JMenuItem Alta = new JMenuItem("para Alta...");
		AlterarPrioridade.add(Alta);
		
		JMenuItem Media = new JMenuItem("para Media...");
		AlterarPrioridade.add(Media);
		
		JMenuItem Baixa = new JMenuItem("para Baixa...");
		AlterarPrioridade.add(Baixa);
		
		JMenuItem Finalizar = new JMenuItem("Finalizar");
		Processos.add(Finalizar);
		
		JMenuItem Pausa = new JMenuItem("Pausar");
		Processos.add(Pausa);
		
		JMenuItem Retomar = new JMenuItem("Retomar");
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
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Detalhes", null, panel, null);
		
		viewTabela = new ViewTabela(computador);
		panel.add(new JScrollPane(viewTabela));
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Grafico", null, panel_1, null);
		
		Canvas canvas = new Canvas();
		panel_1.add(canvas);
		
	}
}

