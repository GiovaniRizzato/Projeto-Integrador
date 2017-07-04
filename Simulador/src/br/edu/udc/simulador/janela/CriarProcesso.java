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
		this.setBounds(100, 100, 450, 300);
		this.setResizable(false);

		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());

		final JPanel contentPane = new JPanel();
		final SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		this.getContentPane().add(contentPane, BorderLayout.CENTER);

		JLabel prioridade = new JLabel("Prioridade");
		layout.putConstraint(SpringLayout.WEST, prioridade, 10, SpringLayout.WEST, contentPane);
		contentPane.add(prioridade);

		JComboBox<Processo.Prioridade> prioridadeComboBox = new JComboBox<>();
		prioridadeComboBox.addItem(Processo.Prioridade.ALTA);
		prioridadeComboBox.addItem(Processo.Prioridade.MEDIA);
		prioridadeComboBox.addItem(Processo.Prioridade.BAIXA);
		layout.putConstraint(SpringLayout.NORTH, prioridadeComboBox, 6, SpringLayout.SOUTH, prioridade);
		layout.putConstraint(SpringLayout.WEST, prioridadeComboBox, 0, SpringLayout.WEST, prioridade);
		contentPane.add(prioridadeComboBox);

		JLabel numeroDeInstrucoesCPU = new JLabel("Numero de Instruçoes do CPU");
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesCPU, 0, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesCPU, -10, SpringLayout.EAST, contentPane);
		contentPane.add(numeroDeInstrucoesCPU);

		JSpinner clockCPU = new JSpinner();
		layout.putConstraint(SpringLayout.NORTH, clockCPU, 0, SpringLayout.NORTH, prioridadeComboBox);
		layout.putConstraint(SpringLayout.WEST, clockCPU, 237, SpringLayout.EAST, prioridadeComboBox);
		contentPane.add(clockCPU);

		JLabel numeroDeInstrucoesES1 = new JLabel("Numero de Instruçoes de Entrada e saida 1");
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesES1, 14, SpringLayout.SOUTH, clockCPU);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesES1, 0, SpringLayout.EAST, contentPane);
		contentPane.add(numeroDeInstrucoesES1);

		JSpinner clockES1 = new JSpinner();
		layout.putConstraint(SpringLayout.EAST, clockCPU, 0, SpringLayout.EAST, clockES1);
		layout.putConstraint(SpringLayout.NORTH, clockES1, 6, SpringLayout.SOUTH, numeroDeInstrucoesES1);
		layout.putConstraint(SpringLayout.EAST, clockES1, -64, SpringLayout.EAST, contentPane);
		contentPane.add(clockES1);

		JLabel numeroDeInstrucoesES2 = new JLabel("Numero de Instruçoes de Entrada e saida 2");
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesES2, 10, SpringLayout.SOUTH, clockES1);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesES2, 0, SpringLayout.EAST, contentPane);
		contentPane.add(numeroDeInstrucoesES2);

		JSpinner clockES2 = new JSpinner();
		layout.putConstraint(SpringLayout.WEST, clockES1, 0, SpringLayout.WEST, clockES2);
		layout.putConstraint(SpringLayout.NORTH, clockES2, 6, SpringLayout.SOUTH, numeroDeInstrucoesES2);
		layout.putConstraint(SpringLayout.EAST, clockES2, -64, SpringLayout.EAST, contentPane);
		contentPane.add(clockES2);

		JLabel numeroDeInstrucoesES3 = new JLabel("Numero de Instruçoes de Entrada e saida 3");
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesES3, 10, SpringLayout.SOUTH, clockES2);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstrucoesES3, 0, SpringLayout.EAST, contentPane);
		contentPane.add(numeroDeInstrucoesES3);

		JSpinner clockES3 = new JSpinner();
		layout.putConstraint(SpringLayout.WEST, clockES2, 0, SpringLayout.WEST, clockES3);
		layout.putConstraint(SpringLayout.NORTH, clockES3, 6, SpringLayout.SOUTH, numeroDeInstrucoesES3);
		layout.putConstraint(SpringLayout.WEST, clockES3, -124, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.EAST, clockES3, -64, SpringLayout.EAST, contentPane);
		contentPane.add(clockES3);

		JButton btnCor = new JButton("Cor");
		layout.putConstraint(SpringLayout.NORTH, btnCor, 0, SpringLayout.NORTH, numeroDeInstrucoesES2);
		layout.putConstraint(SpringLayout.WEST, btnCor, 0, SpringLayout.WEST, prioridade);
		contentPane.add(btnCor);

		JPanel demonstracaoCorPanel = new JPanel();
		layout.putConstraint(SpringLayout.NORTH, demonstracaoCorPanel, 0, SpringLayout.NORTH, numeroDeInstrucoesES2);
		layout.putConstraint(SpringLayout.WEST, demonstracaoCorPanel, 6, SpringLayout.EAST, btnCor);
		layout.putConstraint(SpringLayout.SOUTH, demonstracaoCorPanel, -9, SpringLayout.SOUTH, btnCor);
		layout.putConstraint(SpringLayout.EAST, demonstracaoCorPanel, 21, SpringLayout.EAST, btnCor);
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
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
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
