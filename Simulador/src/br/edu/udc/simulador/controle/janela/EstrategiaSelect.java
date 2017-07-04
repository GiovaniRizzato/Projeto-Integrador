package br.edu.udc.simulador.controle.janela;

import javax.management.RuntimeErrorException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.edu.udc.simulador.so.SistemaOperacional.Estrategia;

public class EstrategiaSelect{
	
	public static Estrategia escolha() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Estrategia de alocação:"));
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("First-fit");
		model.addElement("Best-fit");
		model.addElement("Worst-fit");
		JComboBox<String> comboBox = new JComboBox<String>(model);
		panel.add(comboBox);

		int result = JOptionPane.showConfirmDialog(null, panel, "Estrategia", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			switch (comboBox.getSelectedItem().toString()) {
			case "First-fit": {
				return Estrategia.FIRST_FIT;
			}

			case "Best-fit": {
				return Estrategia.BEST_FIT;
			}

			case "Worst-fit": {
				return Estrategia.WORST_FIT;
			}
			}

			throw new RuntimeErrorException(null, "Inconsistencia na contrução do DropDown");
		} else {
			return null;
		}
	}
}