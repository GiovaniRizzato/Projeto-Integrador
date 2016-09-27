package br.edu.udc.simulador.dominio.processo;

import br.edu.udc.simulador.dominio.processo.Processo.DadosEstatisticos;
import br.edu.udc.simulador.dominio.processo.Processo.prioridade;

class ContextoSoftware {
	public final int pid;
	public prioridade prioridade;
	public int instrucaoAtual = 0;
	public DadosEstatisticos dadosEstatisticos = new Processo.DadosEstatisticos();

	public ContextoSoftware(int pid, prioridade prioridade) {
		this.pid = pid;
		this.prioridade = prioridade;
	}
}
