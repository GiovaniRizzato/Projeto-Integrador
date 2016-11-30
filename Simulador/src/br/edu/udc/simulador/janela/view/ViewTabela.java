package br.edu.udc.simulador.janela.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.janela.SiloDeCor;
import br.edu.udc.simulador.processo.Processo;

public class ViewTabela extends JPanel implements AttView {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private ViewTabelaModel tbModel;
	private Processo[] todoProcessos;

	public ViewTabela() {
		setLayout(new BorderLayout(0, 0));

		tbModel = new ViewTabelaModel();
		table = new JTable();
		table.setModel((TableModel) tbModel);
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		table.getColumnModel().getColumn(0).setCellRenderer( new DefaultTableCellRenderer() {
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getTableCellRenderer(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		        //super.getTableCellRenderer(table, value, isSelected, hasFocus, row, col);
				
		       // if (consulta no table model ou controler para saber a cor ) {
			//	final Processo processo = this.todoProcessos[];
				//setBackground( SiloDeCor.getIntancia().obtem(processo.getPID()));
		        
		        return this;
		    }
		});

		Computador.getInstancia().adicionaView(this);
	}

	public void atualizar() {
		this.todoProcessos = Computador.getInstancia().listaTodos();
		tbModel.atualizar(this.todoProcessos);
	}
}

class ViewTabelaModel extends AbstractTableModel {

	private static final long serialVersionUID = 1;
	private final String columnNames[] = new String[] { "cor", "pid", "prioridade", "Posiçao de inicio", "tamanho",
			"PosicaoIntruçãoAtaul", "Estado do processo", };
	private final Class<?> columnTypes[] = new Class[] { Object.class, String.class, String.class, int.class, int.class,
			String.class, String.class };

	Processo[] todoProcessos;

	public Class<?> getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	public void atualizar(Processo[] processos) {
		this.todoProcessos = processos;
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return this.todoProcessos.length;
	}
	public void mudaCorTabela(int rowIndex){
		final Processo processo = this.todoProcessos[rowIndex];
		SiloDeCor.getIntancia().obtem(processo.getPID());
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// this.todoProcessos[rowIndex] processo na lista rowIndex
		switch (columnIndex) {
		case 0:
			final Processo processo = this.todoProcessos[rowIndex];
			SiloDeCor.getIntancia().obtem(processo.getPID());
			return "";
		case 1:
			return this.todoProcessos[rowIndex].getPID();
		case 2:
			final Processo.Prioridade prioridade = this.todoProcessos[rowIndex].getPrioridade();
			if (prioridade != null) {
				return this.todoProcessos[rowIndex].getPrioridade();
			} else {
				return "SO";
			}
		case 3:
			return this.todoProcessos[rowIndex].getInicioPrograma();
		case 4:
			return this.todoProcessos[rowIndex].getTamanho();
		case 5:
			return this.todoProcessos[rowIndex].posicaoIntrucaoAtual();
		case 6:
			return "Pronto";
		}

		return null;

	}

}