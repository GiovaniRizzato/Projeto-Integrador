package br.edu.udc.simulador.janela.view;

import javax.swing.table.AbstractTableModel;
import br.edu.udc.ed.lista.vetor.Vetor;
import br.edu.udc.simulador.controle.Computador;
import br.edu.udc.simulador.processo.Processo;

public class TabelaProcessos extends AbstractTableModel implements AttView {

	private static final long serialVersionUID = -2962043956136706401L;
	private final String nomeColunas[] = new String[] { "PID", "Prioridade", "Posicao Intrução Ataul",
			"Estado do processo" };
	private final Class<?> tipoColunas[] = new Class[] { Integer.class, String.class, String.class, String.class };
	private Vetor<Processo> todoProcessos;

	public TabelaProcessos() {

		super();
		this.atualizaProcessos();
	}

	private void atualizaProcessos() {

		// Refaz a listaTodos
		this.todoProcessos = new Vetor<Processo>();

		for (Processo processo : Computador.getInstancia().listaTodos()) {
			this.todoProcessos.adiciona(processo);
		}
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
		return 4;
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
				return "SO";
			}
		case 2:
			return this.todoProcessos.obtem(rowIndex).getInicioPrograma();
		case 3:
			return "Pronto";
		}

		return null;
	}
}