package br.edu.udc.simulador.processo;

import br.edu.udc.simulador.processo.Processo.DadosEstatisticos;
import br.edu.udc.simulador.processo.Processo.Prioridade;

class ContextoSoftware {
	public final Integer pid;
	public Prioridade prioridade;
	public Integer instrucaoAtual = 0;
	public DadosEstatisticos dadosEstatisticos = new Processo.DadosEstatisticos();

	public ContextoSoftware(int pid, Prioridade prioridade) {
		this.pid = pid;
		this.prioridade = prioridade;
	}
}
