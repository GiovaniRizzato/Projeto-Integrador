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

public class DialogAcoes extends JDialog {

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
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DialogAcoes dialog = new DialogAcoes("");
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
	public DialogAcoes(String titulo) {
		setTitle(titulo);
		;
		SpringLayout layout = new SpringLayout();
		// setBounds(100, 100, 450, 300);
		setBounds(100, 100, 285, 135);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(layout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel textPID = new JLabel("PID");
		layout.putConstraint(SpringLayout.NORTH, textPID, 0, SpringLayout.NORTH, contentPanel);
		layout.putConstraint(SpringLayout.WEST, textPID, 10, SpringLayout.WEST, contentPanel);
		contentPanel.add(textPID);

		JSpinner pid = new JSpinner();
		layout.putConstraint(SpringLayout.NORTH, pid, 6, SpringLayout.SOUTH, textPID);
		layout.putConstraint(SpringLayout.EAST, pid, -177, SpringLayout.EAST, contentPanel);
		layout.putConstraint(SpringLayout.WEST, pid, 10, SpringLayout.WEST, contentPanel);
		contentPanel.add(pid);

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
