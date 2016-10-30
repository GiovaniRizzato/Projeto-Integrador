package br.edu.udc.simulador.janela;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import br.edu.udc.simulador.controle.Controle;

public class ViewTabela extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private ViewTabelaModel tbModel;
	
	public ViewTabela(Controle controle){
		setLayout(new BorderLayout(0,0));
		
		tbModel = new ViewTabelaModel(controle);
		table = new JTable();
		table.setModel((TableModel) tbModel);
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	public void Atualizar(){
		tbModel.Atualizar();
	}
}
class ViewTabelaModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1;
	private final String columnNames[]=new String[]{"pid","Nome","Memoria","CPU","Status"};
	private final Class<?> columnTypes[] = new Class[]{Integer.class,String.class,Integer.class,Integer.class,String.class};
	
	private Controle controle;
	public ViewTabelaModel(Controle controle) {
		// TODO Auto-generated constructor stub
		this.controle = controle;
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

	public void Atualizar() {
		// TODO Auto-generated method stub
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return /*controle.qtdProcesso*/0;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}