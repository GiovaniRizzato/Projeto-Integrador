package br.edu.udc.simulador.janela.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import br.edu.udc.ed.lista.vetor.Vetor;
import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.janela.Cores;
import br.edu.udc.simulador.processo.Processo;

public class TabelaProcessos extends JTable implements AttView {

	private static final long serialVersionUID = -6646298935512121137L;
	final private AbstractTabelaProcessos abstractTable;

	public TabelaProcessos() {
		super();
		this.abstractTable = new AbstractTabelaProcessos();
		this.setModel(abstractTable);
		Computador.getInstancia().adicionaView(this);

		this.setDefaultRenderer(Object.class, new MyRenderer());

		// O renderizador não tem comportamento para renderizar linhas
		// selecionadas
		this.setCellSelectionEnabled(false);
	}

	@Override
	public void atualizar() {
		this.abstractTable.atualizar();
	}

	private class AbstractTabelaProcessos extends AbstractTableModel implements AttView {
		private static final long serialVersionUID = -2962043956136706401L;
		private final String nomeColunas[] = new String[] { "PID", "Prioridade", "Posicao Intrução Ataul",
				"Estado do processo", "Cor" };
		private final Class<?> tipoColunas[] = new Class[] { Integer.class, String.class, String.class, String.class,
				Color.class };
		private Vetor<Processo> todoProcessos;

		public AbstractTabelaProcessos() {
			super();
			this.atualizaProcessos();
		}

		private void atualizaProcessos() {

			// Refaz a listaTodos
			this.todoProcessos = new Vetor<Processo>();

			for (Processo processo : Computador.getInstancia().listaTodos()) {
				this.todoProcessos.adiciona(processo);
			}

			this.fireTableDataChanged();
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return this.tipoColunas[columnIndex];
		}

		@Override
		public String getColumnName(int column) {
			return this.nomeColunas[column];
		}

		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public void atualizar() {

			this.atualizaProcessos();
			this.fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return this.todoProcessos.tamanho();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return this.todoProcessos.obtem(rowIndex).getPID();
			case 1:
				final Processo.Prioridade processo = this.todoProcessos.obtem(rowIndex).getPrioridade();
				if (processo != null) {
					return this.todoProcessos.obtem(rowIndex).getPrioridade();
				} else {
					return "Sistema Operacional";
				}
			case 2:
				return this.todoProcessos.obtem(rowIndex).getInicioPrograma();
			case 3:
				return "Pronto";
			case 4:
				return "";
			}

			return null;
		}
	}

	private class MyRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1462283599671200399L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);

			final Color corLinha = Cores.getIntancia().obtem((Integer) table.getValueAt(row, 0));

			if (column == 4) {
				component.setBackground(corLinha);
				return component;
			} else {
				component.setBackground(table.getBackground());
			}

			return component;
		}
	}
}