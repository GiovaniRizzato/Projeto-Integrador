package br.edu.udc.simulador.processo;

import br.edu.udc.simulador.processo.Processo.DadosEstatisticos;
import br.edu.udc.simulador.processo.Processo.prioridade;

class ContextoSoftware {
	public final Integer pid;
	public prioridade prioridade;
	public Integer PosicaoInstrucaoAtual = 0;
	public DadosEstatisticos dadosEstatisticos = new Processo.DadosEstatisticos();

	public ContextoSoftware(int pid, prioridade prioridade) {
		this.pid = pid;
		this.prioridade = prioridade;
	}
}
