package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

public class CriaProcessoFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPane = new JPanel();
	private int result = CANCEL;
	public final static int OK = 1;
	public final static int CANCEL = 0;
	
	
	
	private JComboBox<?> estrategiaComboBox;
	private JComboBox<?> prioridadeComboBox;
	
	private final String prioridadeAlta = "Alta";
	private final String prioridadeMedia = "Media";
	private final String prioridadeBaixa = "Baixa";
	
	private final String first_fit = "First-fit";
	private final String best_fit = "Best-fit";
	private final String worst_fit = "Worst-fit";
	
	
	
	private JSpinner clockCPU;
	private JSpinner clockES1;
	private JSpinner clockES2;
	private JSpinner clockES3;
	public int qtdInstrucoes;
	private String nomesPrioridade[]={prioridadeAlta,prioridadeMedia,prioridadeBaixa};
	private String nomesEstrategia[]={first_fit,best_fit,worst_fit};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CriaProcessoFrame frame = new CriaProcessoFrame();
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
	public CriaProcessoFrame() {
		super("Cria Processo");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane,BorderLayout.CENTER);
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
		JLabel prioridade = new JLabel("Prioridade");
		contentPane.add(prioridade);
		
		
		prioridadeComboBox = new JComboBox (nomesPrioridade);
		layout.putConstraint(SpringLayout.NORTH, prioridadeComboBox, 6, SpringLayout.SOUTH, prioridade);
		layout.putConstraint(SpringLayout.WEST, prioridadeComboBox, 0, SpringLayout.WEST, prioridade);
		
		prioridadeComboBox.setMaximumRowCount(3);
		contentPane.add(prioridadeComboBox);
		
		JLabel estrategia = new JLabel("Estrategia de Memoria");
		layout.putConstraint(SpringLayout.NORTH, estrategia, 50, SpringLayout.NORTH, prioridade);
		layout.putConstraint(SpringLayout.WEST, estrategia, 0, SpringLayout.WEST, prioridade);
		contentPane.add(estrategia);
		
		estrategiaComboBox = new JComboBox (nomesEstrategia);
		layout.putConstraint(SpringLayout.NORTH, estrategiaComboBox, 70, SpringLayout.NORTH, prioridade);
		layout.putConstraint(SpringLayout.WEST, estrategiaComboBox, 0, SpringLayout.WEST, prioridade);
		
		estrategiaComboBox.setMaximumRowCount(3);
		contentPane.add(estrategiaComboBox);
		
		
		JLabel numeroDeInstrucoesCPU = new JLabel("Numero de Instruçoes do CPU");
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesCPU, 0, SpringLayout.NORTH, contentPane);
		
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesCPU, -10, SpringLayout.EAST, contentPane);
		contentPane.add(numeroDeInstrucoesCPU);
		
		clockCPU = new JSpinner();
		layout.putConstraint(SpringLayout.NORTH, clockCPU, 0, SpringLayout.NORTH, prioridadeComboBox);
		layout.putConstraint(SpringLayout.WEST, clockCPU, 237, SpringLayout.EAST, prioridadeComboBox);
		contentPane.add(clockCPU);
		
		JLabel numeroDeInstrucoesES1 = new JLabel("Numero de Instruçoes de Entrada e saida 1");
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesES1, 14, SpringLayout.SOUTH, clockCPU);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesES1, 0, SpringLayout.EAST, contentPane);
		contentPane.add(numeroDeInstrucoesES1);
		
		clockES1 = new JSpinner();
		layout.putConstraint(SpringLayout.EAST, clockCPU, 0, SpringLayout.EAST, clockES1);
		layout.putConstraint(SpringLayout.NORTH, clockES1, 6, SpringLayout.SOUTH, numeroDeInstrucoesES1);
		layout.putConstraint(SpringLayout.EAST, clockES1, -64, SpringLayout.EAST, contentPane);
		contentPane.add(clockES1);
		
		JLabel numeroDeInstrucoesES2 = new JLabel("Numero de Instruçoes de Entrada e saida 2");
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesES2, 10, SpringLayout.SOUTH, clockES1);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesES2, 0, SpringLayout.EAST, contentPane);
		contentPane.add(numeroDeInstrucoesES2);
		
		clockES2 = new JSpinner();
		layout.putConstraint(SpringLayout.WEST, clockES1, 0, SpringLayout.WEST, clockES2);
		layout.putConstraint(SpringLayout.NORTH, clockES2, 6, SpringLayout.SOUTH, numeroDeInstrucoesES2);
		layout.putConstraint(SpringLayout.EAST, clockES2, -64, SpringLayout.EAST, contentPane);
		contentPane.add(clockES2);
		
		JLabel numeroDeInstrucoesES3 = new JLabel("Numero de Instruçoes de Entrada e saida 3");
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesES3, 10, SpringLayout.SOUTH, clockES2);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesES3, 0, SpringLayout.EAST, contentPane);
		contentPane.add(numeroDeInstrucoesES3);
		
		clockES3 = new JSpinner();
		layout.putConstraint(SpringLayout.WEST, clockES2, 0, SpringLayout.WEST, clockES3);
		layout.putConstraint(SpringLayout.NORTH, clockES3, 6, SpringLayout.SOUTH, numeroDeInstrucoesES3);
		layout.putConstraint(SpringLayout.WEST, clockES3, -124, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.EAST, clockES3, -64, SpringLayout.EAST, contentPane);
		contentPane.add(clockES3);
		
		JPanel panel = new JPanel();
		layout.putConstraint(SpringLayout.WEST, prioridade, 0, SpringLayout.EAST, panel);
		contentPane.add(panel, BorderLayout.CENTER);
		
		JButton btnCor = new JButton("Cor");
		layout.putConstraint(SpringLayout.NORTH, btnCor, 0, SpringLayout.NORTH, numeroDeInstrucoesES2);
		layout.putConstraint(SpringLayout.WEST, btnCor, 0, SpringLayout.WEST, prioridade);
		contentPane.add(btnCor);
		
		JPanel panel_1 = new JPanel();
		layout.putConstraint(SpringLayout.NORTH, panel_1, 0, SpringLayout.NORTH, numeroDeInstrucoesES2);
		layout.putConstraint(SpringLayout.WEST, panel_1, 6, SpringLayout.EAST, btnCor);
		layout.putConstraint(SpringLayout.SOUTH, panel_1, -9, SpringLayout.SOUTH, btnCor);
		layout.putConstraint(SpringLayout.EAST, panel_1, 21, SpringLayout.EAST, btnCor);
		contentPane.add(panel_1);
		btnCor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				FrameSelectColor frameSelectColor = new FrameSelectColor();
				panel_1.setBackground(frameSelectColor.getColor());
			}
		});
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout( new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//if()//tenho que colocar condicao para ver se foi escolhido prioridade e qtdmemoria numero de clock etc...
					//	return;
					qtdInstrucoes = (int) clockCPU.getValue();
					System.out.printf("%d",qtdInstrucoes);
					
					result = OK;
					setVisible(false);
				}
			});

			
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					result = CANCEL;
					setVisible(false);
				}
				
			});
		}
	}
	
	public int getResult(){
		return result;
	}
}

