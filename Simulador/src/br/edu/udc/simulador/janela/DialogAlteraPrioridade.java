package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

public class DialogAlteraPrioridade extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	public static Frame nomeDialog;
	private int result = CANCEL;
	public final static int OK = 1;
	public final static int CANCEL = 0;
	private static int retonoPID;
	
	private final static String prioridadeAlta="Alta";
	private final static String prioridadeMedia="Media";
	private final static String prioridadeBaixa="Baixa";
	
	private String nomesPrioridade[] = { prioridadeAlta, prioridadeMedia,
			prioridadeBaixa };
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogAlteraPrioridade dialog = new DialogAlteraPrioridade("Altera Clock");
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
	public DialogAlteraPrioridade(String titulo) {
		setTitle(titulo);
		;
		SpringLayout layout = new SpringLayout();
		// setBounds(100, 100, 450, 300);
		setBounds(100, 100, 356, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(layout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel textPID = new JLabel("PID");
		layout.putConstraint(SpringLayout.NORTH, textPID, 0, SpringLayout.NORTH,contentPanel);
		layout.putConstraint(SpringLayout.WEST, textPID, 20, SpringLayout.WEST, contentPanel);
		contentPanel.add(textPID);

		JSpinner pid = new JSpinner();
		layout.putConstraint(SpringLayout.NORTH, pid, 6, SpringLayout.SOUTH, textPID);
		layout.putConstraint(SpringLayout.WEST, pid, 5, SpringLayout.WEST, contentPanel);
		layout.putConstraint(SpringLayout.EAST, pid, 28, SpringLayout.EAST, textPID);
		contentPanel.add(pid);

		JLabel alteraPrioridadeText = new JLabel("Prioriadade");
		layout.putConstraint(SpringLayout.NORTH, alteraPrioridadeText, 0, SpringLayout.NORTH, textPID);
		contentPanel.add(alteraPrioridadeText);

		JComboBox<?> alteraPrioridade = new JComboBox(nomesPrioridade);
		layout.putConstraint(SpringLayout.NORTH, alteraPrioridade, 6, SpringLayout.SOUTH, alteraPrioridadeText);
		layout.putConstraint(SpringLayout.WEST, alteraPrioridadeText, 0, SpringLayout.WEST, alteraPrioridade);
		layout.putConstraint(SpringLayout.WEST, alteraPrioridade, -83, SpringLayout.EAST, contentPanel);
		layout.putConstraint(SpringLayout.EAST, alteraPrioridade, -30, SpringLayout.EAST, contentPanel);
		contentPanel.add(alteraPrioridade);

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
					retonoPID = (int) pid.getValue();

					result = OK;
					//System.out.print(getResult());
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

	public int getPID() {
		if (result == OK)
			return retonoPID;
		return 0;
	}

}
