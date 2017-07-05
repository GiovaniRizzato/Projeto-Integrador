package br.edu.udc.simulador.janela;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import br.edu.udc.simulador.janela.view.TabelaProcessos;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable tabelaProcesso;

	// TableModel tableModel;
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				MainMenu frame = new MainMenu();
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainMenu() {
		this.setTitle("Simulador de Sistema Operacional");

		// Setando imagem na barra de titulo
		final ImageIcon icone = new ImageIcon("ExtensionFiles/icon.png");
		this.setIconImage(icone.getImage());

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// tamanho minimo para redesenhar a memoria de forma correta
		this.setMinimumSize(new Dimension(500, 400));

		// tira a janela do canto da tela
		this.setBounds(100, 100, 0, 0);

		JPanel mainPanel = new JPanel();

		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));

		JPanel desfragmentacaoPanel = new JPanel();
		mainPanel.add(desfragmentacaoPanel, BorderLayout.SOUTH);

		JButton btnDesfragmentar = new JButton("Desfragmentar");
		btnDesfragmentar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		JProgressBar progress_Desfragmentar = new JProgressBar();
		desfragmentacaoPanel.setLayout(new BoxLayout(desfragmentacaoPanel, BoxLayout.X_AXIS));
		desfragmentacaoPanel.add(btnDesfragmentar);
		desfragmentacaoPanel.add(progress_Desfragmentar);

		// Disparando o redimencionamento no inicio
		mainPanel.setSize(mainPanel.getSize());

		JTabbedPane mainTabPane = new JTabbedPane(JTabbedPane.TOP);
		mainPanel.add(mainTabPane, BorderLayout.CENTER);

		JScrollPane tabelaTab = new JScrollPane();
		mainTabPane.addTab("Tabela de processos", new ImageIcon("ExtensionFiles/processo.png"), tabelaTab, null);
		this.tabelaProcesso = new TabelaProcessos();
		tabelaTab.setViewportView(tabelaProcesso);

		JPanel gaficoTab = new JPanel();
		mainTabPane.addTab("Grafico de memoria", new ImageIcon("ExtensionFiles/memoria.png"), gaficoTab, null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnSimulador = new JMenu("Simulador");
		menuBar.add(mnSimulador);

		JMenu menu_Processo = new JMenu("Processo");
		menuBar.add(menu_Processo);

		JMenuItem subMenu_AdicionarProcesso = new JMenuItem("Adicionar processo");
		subMenu_AdicionarProcesso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CriarProcesso(MainMenu.this).setVisible(true);
			}
		});
		menu_Processo.add(subMenu_AdicionarProcesso);

		JMenuItem mntmAlterarPrioridade = new JMenuItem("Alterar Prioridade");
		menu_Processo.add(mntmAlterarPrioridade);

		JMenuItem subMenu_PausarProcesso = new JMenuItem("Pausar processo");
		menu_Processo.add(subMenu_PausarProcesso);

		JMenuItem subMenu_DespausarProcesso = new JMenuItem("Despausar processo");
		menu_Processo.add(subMenu_DespausarProcesso);

		JMenuItem subMenu_EncerrarProcesso = new JMenuItem("Encerrar Processo");
		menu_Processo.add(subMenu_EncerrarProcesso);

		JMenu menu_Hardware = new JMenu("Hardware");
		menuBar.add(menu_Hardware);

		JMenuItem mntmAlterarVelocidadeHardware = new JMenuItem("Alterar velocidade Hardware");
		menu_Hardware.add(mntmAlterarVelocidadeHardware);

		JButton btnPlay = new JButton();
		final ImageIcon iconePlay = new ImageIcon("ExtensionFiles/play.png");
		btnPlay.setIcon(iconePlay);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		menuBar.add(btnPlay);

		JButton btnPause = new JButton();
		final ImageIcon iconePause = new ImageIcon("ExtensionFiles/pause.png");
		btnPause.setIcon(iconePause);
		menuBar.add(btnPause);
	}
}
