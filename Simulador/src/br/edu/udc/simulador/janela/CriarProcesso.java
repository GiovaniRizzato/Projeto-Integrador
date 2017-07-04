package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpringLayout;
import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.processo.Processo;

public class CriarProcesso extends JDialog {

	private static final long serialVersionUID = 4782678778269027968L;

	private Color cor;
	private int result = CANCEL;

	public final static int OK = 1;
	public final static int CANCEL = 0;

	public CriarProcesso(JFrame pai) {

		super(pai, "Cria Processo");
		this.setBounds(100, 100, 450, 225);
		this.setResizable(false);

		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());

		final JPanel contentPane = new JPanel();
		final SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		this.getContentPane().add(contentPane, BorderLayout.CENTER);

		JLabel prioridade = new JLabel("Prioridade");
		contentPane.add(prioridade);

		JComboBox<Processo.Prioridade> prioridadeComboBox = new JComboBox<>();
		layout.putConstraint(SpringLayout.NORTH, prioridadeComboBox, 6, SpringLayout.SOUTH, prioridade);
		layout.putConstraint(SpringLayout.WEST, prioridadeComboBox, 10, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, prioridade, 0, SpringLayout.EAST, prioridadeComboBox);
		prioridadeComboBox.addItem(Processo.Prioridade.ALTA);
		prioridadeComboBox.addItem(Processo.Prioridade.MEDIA);
		prioridadeComboBox.addItem(Processo.Prioridade.BAIXA);
		contentPane.add(prioridadeComboBox);

		JLabel numeroDeInstrucoesCPU = new JLabel("Numero de Instruçoes do CPU");
		contentPane.add(numeroDeInstrucoesCPU);

		JSpinner clockCPU = new JSpinner();
		layout.putConstraint(SpringLayout.NORTH, clockCPU, 28, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.NORTH, prioridade, 0, SpringLayout.NORTH, clockCPU);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesCPU, -6, SpringLayout.WEST, clockCPU);
		layout.putConstraint(SpringLayout.WEST, clockCPU, 374, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, clockCPU, -10, SpringLayout.EAST, contentPane);
		contentPane.add(clockCPU);

		JLabel numeroDeInstrucoesES1 = new JLabel("Numero de Instruçoes de Entrada e saida 1");
		layout.putConstraint(SpringLayout.EAST, prioridadeComboBox, -72, SpringLayout.WEST, numeroDeInstrucoesES1);
		layout.putConstraint(SpringLayout.WEST, numeroDeInstrucoesES1, 160, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, numeroDeInstrucoesCPU, -12, SpringLayout.NORTH, numeroDeInstrucoesES1);
		contentPane.add(numeroDeInstrucoesES1);

		JSpinner clockES1 = new JSpinner();
		layout.putConstraint(SpringLayout.SOUTH, clockCPU, -9, SpringLayout.NORTH, clockES1);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesES1, -6, SpringLayout.WEST, clockES1);
		layout.putConstraint(SpringLayout.WEST, clockES1, 374, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, clockES1, -10, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesES1, 3, SpringLayout.NORTH, clockES1);
		layout.putConstraint(SpringLayout.NORTH, clockES1, 60, SpringLayout.NORTH, contentPane);
		contentPane.add(clockES1);

		JLabel numeroDeInstrucoesES2 = new JLabel("Numero de Instruçoes de Entrada e saida 2");
		layout.putConstraint(SpringLayout.WEST, numeroDeInstrucoesES2, 160, SpringLayout.WEST, contentPane);
		contentPane.add(numeroDeInstrucoesES2);

		JSpinner clockES2 = new JSpinner();
		layout.putConstraint(SpringLayout.WEST, clockES2, 374, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesES2, -6, SpringLayout.WEST, clockES2);
		layout.putConstraint(SpringLayout.EAST, clockES2, -10, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesES2, 3, SpringLayout.NORTH, clockES2);
		layout.putConstraint(SpringLayout.SOUTH, clockES1, -6, SpringLayout.NORTH, clockES2);
		layout.putConstraint(SpringLayout.NORTH, clockES2, 86, SpringLayout.NORTH, contentPane);
		contentPane.add(clockES2);

		JLabel numeroDeInstrucoesES3 = new JLabel("Numero de Instruçoes de Entrada e saida 3");
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesES3, 0, SpringLayout.EAST, numeroDeInstrucoesCPU);
		contentPane.add(numeroDeInstrucoesES3);

		JSpinner clockES3 = new JSpinner();
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesES3, 3, SpringLayout.NORTH, clockES3);
		layout.putConstraint(SpringLayout.NORTH, clockES3, 6, SpringLayout.SOUTH, clockES2);
		layout.putConstraint(SpringLayout.WEST, clockES3, -70, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.EAST, clockES3, -10, SpringLayout.EAST, contentPane);
		contentPane.add(clockES3);

		JButton btnCor = new JButton("Cor");
		layout.putConstraint(SpringLayout.WEST, prioridade, 0, SpringLayout.WEST, btnCor);
		layout.putConstraint(SpringLayout.NORTH, btnCor, 104, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, btnCor, 10, SpringLayout.WEST, contentPane);
		contentPane.add(btnCor);

		JPanel demonstracaoCorPanel = new JPanel();
		layout.putConstraint(SpringLayout.NORTH, demonstracaoCorPanel, 36, SpringLayout.SOUTH, prioridadeComboBox);
		layout.putConstraint(SpringLayout.WEST, demonstracaoCorPanel, 6, SpringLayout.EAST, btnCor);
		layout.putConstraint(SpringLayout.SOUTH, demonstracaoCorPanel, 0, SpringLayout.SOUTH, btnCor);
		layout.putConstraint(SpringLayout.EAST, demonstracaoCorPanel, 0, SpringLayout.EAST, prioridade);
		contentPane.add(demonstracaoCorPanel);
		btnCor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final Color novaCor = JColorChooser.showDialog(null, "Cor para representar o processo", null);

				if (novaCor != null) {
					CriarProcesso.this.cor = novaCor;
					demonstracaoCorPanel.setBackground(novaCor);
				}
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					final int posicaoAtual = prioridadeComboBox.getSelectedIndex();
					final Processo.Prioridade prioridade = prioridadeComboBox.getItemAt(posicaoAtual);

					final int instrucoesCPU = (int) clockCPU.getValue();
					final int instrucoesES1 = (int) clockES1.getValue();
					final int instrucoesES2 = (int) clockES1.getValue();
					final int instrucoesES3 = (int) clockES1.getValue();

					try {
						final Computador computador = Computador.getInstancia();
						computador.criaProcesso(prioridade, instrucoesCPU, instrucoesES1, instrucoesES2, instrucoesES3);

						// Adicionando a cor ao "catalogo" entre cor e processo
						final int proximoPid = Computador.getInstancia().getSimulador().getProximoPid();
						Cores.getIntancia().adiciona(proximoPid, CriarProcesso.this.cor);

					} catch (RuntimeException erro) {
						// TODO substituir por janela de erro
						JOptionPane.showMessageDialog(CriarProcesso.this, erro.getMessage());
						CriarProcesso.this.setVisible(false);
					}

					Computador.getInstancia().atualizaViews();
					CriarProcesso.this.result = OK;
					CriarProcesso.this.setVisible(false);
				}
			});

			JButton cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					CriarProcesso.this.result = CANCEL;
					CriarProcesso.this.setVisible(false);
				}
			});
		}
	}

	public int getResult() {
		return this.result;
	}
}
