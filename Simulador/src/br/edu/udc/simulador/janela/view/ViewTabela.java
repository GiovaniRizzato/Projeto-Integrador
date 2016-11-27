package br.edu.udc.simulador.janela.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.processo.Processo;

public class ViewTabela extends JPanel implements AttView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private ViewTabelaModel tbModel;

	public ViewTabela() {
		setLayout(new BorderLayout(0, 0));

		tbModel = new ViewTabelaModel();
		table = new JTable();
		table.setModel((TableModel) tbModel);
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public void atualizar() {
		tbModel.atualizar();
	}
}

class ViewTabelaModel extends AbstractTableModel {

	private static final long serialVersionUID = 1;
	private final String columnNames[] = new String[] { "pid", "prioridade", "PosicaoIntruçãoAtaul",
			"Estado do processo" };
	private final Class<?> columnTypes[] = new Class[] { String.class, String.class, String.class, String.class };

	Processo[] todoProcessos;

	private String tabela[][] = { { "hola", "teste", "teste1", "teste2" }, { "hola", "teste", "teste1", "teste2" },
			{ "hola", "teste", "teste1", "teste2" }, { "hola", "teste", "teste1", "teste2" },
			{ "hola", "teste", "teste1", "teste2" } };

	public Class<?> getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	public void atualizar() {
		Computador computador = Computador.getInstancia();
		Processo[] todoProcessos = computador.listaTodos();
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return tabela.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// this.todoProcessos[rowIndex] processo na lista rowIndex
	/*	switch (columnIndex) {
		case 1:
			return this.todoProcessos[rowIndex].getPID();

		case 2:
			final Processo.Prioridade processo = this.todoProcessos[rowIndex].getPrioridade();
			if (processo != null) {
				return this.todoProcessos[rowIndex].getPrioridade();
			} else {
				return "SO";
			}

		case 3:
			return this.todoProcessos[rowIndex].getInicioPrograma();

		case 4:
			return "Pronto";
		}*/

		return null;

	}

}