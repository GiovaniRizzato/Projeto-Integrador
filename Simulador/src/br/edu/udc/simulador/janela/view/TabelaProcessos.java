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
		private final Class<?> tipoColunas[] = new Class[] { Integer.class, String.class, Integer.class, String.class,
				Color.class };
		private Vetor<Processo> todosProcessos;
		private int posicaoInicioAtivos;
		private int posicaoInicioPausados;

		public AbstractTabelaProcessos() {
			super();
			this.atualizaProcessos();
		}

		private void atualizaProcessos() {

			Computador pc = Computador.getInstancia();

			if (pc.processoEmAndamento() != null) {
				this.todosProcessos.adiciona(pc.processoEmAndamento());
				this.posicaoInicioAtivos = 1;
			} else {
				this.posicaoInicioAtivos = 0;
			}

			// Refaz a listaTodos
			this.todosProcessos = new Vetor<Processo>();

			for (Processo processo : pc.listaTodosAtivos()) {
				this.todosProcessos.adiciona(processo);
			}

			this.posicaoInicioPausados = this.todosProcessos.tamanho();

			for (Processo processo : pc.listaTodosPausados()) {
				this.todosProcessos.adiciona(processo);
			}

			this.fireTableDataChanged();
		}

		@Override
		public Class<?> getColumnClass(int column) {
			return this.tipoColunas[column];
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
			return this.todosProcessos.tamanho();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {

			Processo processo = this.todosProcessos.obtem(rowIndex);

			switch (columnIndex) {
			case 0:
				return processo.getPID();
			case 1:
				final Processo.Prioridade prioridade = processo.getPrioridade();
				return prioridade;
			case 2:
				return processo.getInicioPrograma();
			case 3:
				if (rowIndex < this.posicaoInicioAtivos)
					return "Em Processamento";
				else if (rowIndex > this.posicaoInicioPausados)
					return "Pausado";
				else
					return "Em espera";
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

			if (column == 4) {
				final Color corLinha = Cores.getIntancia().obtem((Integer) table.getValueAt(row, 0));
				component.setBackground(corLinha);
			}

			return component;
		}
	}
}