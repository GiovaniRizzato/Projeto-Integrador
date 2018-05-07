package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

public class DialogAlteraE_S1 extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	public static Frame nomeDialog;
	private int result = CANCEL;
	public final static int OK = 1;
	public final static int CANCEL = 0;
	private static int valor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogAlteraClock dialog = new DialogAlteraClock("Altera Clock");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setResizable(false);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogAlteraE_S1(String titulo) {
		setTitle(titulo);
		
		SpringLayout layout = new SpringLayout();
		// setBounds(100, 100, 450, 300);
		setBounds(100, 100, 356, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(layout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setResizable(false);
		
		JLabel numeroDeInstrucoesText = new JLabel("Numero de instrucoes");
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstrucoesText, 0, SpringLayout.NORTH, contentPanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, numeroDeInstrucoesText, 0, SpringLayout.HORIZONTAL_CENTER, contentPanel);
		contentPanel.add(numeroDeInstrucoesText);

		JSpinner numeroDeInstruaoes = new JSpinner();
		layout.putConstraint(SpringLayout.NORTH, numeroDeInstruaoes, 6, SpringLayout.SOUTH, numeroDeInstrucoesText);
		layout.putConstraint(SpringLayout.WEST, numeroDeInstruaoes, 139, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.EAST, numeroDeInstruaoes, 78, SpringLayout.WEST, numeroDeInstrucoesText);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, numeroDeInstrucoesText, 0, SpringLayout.HORIZONTAL_CENTER, contentPanel);
		contentPanel.add(numeroDeInstruaoes);

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
					valor = (int) numeroDeInstruaoes.getValue();
					//TODO altera E/S 1
					result = OK;
					
					setVisible(false);
				}
			});
		}
		{
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

	public int getResult() {
		return result;
	}

	public int getValor() {
		if (result == OK)
			return valor;
		return 0;
	}

}
