package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import br.edu.udc.simulador.Computador;

public class ViewTabela extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private ViewTabelaModel tbModel;
	
	public ViewTabela(Computador computador){
		setLayout(new BorderLayout(0,0));
		
		tbModel = new ViewTabelaModel(computador);
		table = new JTable();
		table.setModel((TableModel) tbModel);
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	public void atualizar(){
		tbModel.atualizar();
	}
}
class ViewTabelaModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1;
	private final String columnNames[]=new String[]{"pid","prioridade","PosicaoIntruçãoAtaul","Estado do processo"};
	private final Class<?> columnTypes[] = new Class[]{String.class,String.class,String.class,String.class};
	
	private Computador computador;
	private String tabela[][];
	public ViewTabelaModel(Computador computador) {
		// TODO Auto-generated constructor stub
		this.computador = computador;
		this.tabela = computador.tabelaProcessos();
	}
	
	public Class<?> getColumnClass(int columnIndex){
		return columnTypes[columnIndex];
	}
	
	public String getColumnName(int column){
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void atualizar() {
		// TODO Auto-generated method stub
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return computador.qtdProcessos();
	}

	@Override
	public Object getValueAt(int i, int j) {
		return this.tabela[i][j];
	}
	
}