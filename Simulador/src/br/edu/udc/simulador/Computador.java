package br.edu.udc.simulador;

import br.edu.udc.ed.lista.Lista;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.janela.AttView;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.processo.Programa;
import br.edu.udc.simulador.so.SimuladorSO;

public class Computador {

	private SimuladorSO simulador;
	private Hardware hardware;
	
	private Lista<AttView> listaView = new Lista<>();
	
	public static final String prioridadeAlta = "Alta";
	public static final String prioridadeMedia = "Media";
	public static final String prioridadeBaixa = "Baixa";

	public static final String first_fit = "First-fit";
	public static final String best_fit = "Best-fit";
	public static final String worst_fit = "Worst-fit";

	public Computador() {
		
		this.hardware = new Hardware(500, 10, 10, 10, 10);
		this.simulador = new SimuladorSO(hardware, 5);

		//System.out.println(Processo.prioridade.ALTA);
	}
	
	public void adicionaView(AttView view){
		listaView.adiciona(view);
		//TODO temos que adicionar adicionafim...
	}
	public void removeView(AttView view){
		//listaView.
		//TODO temos que adicionar pesquisa/consulta... remove.
	}
	/*private void atualizarView(){
		//TODO atualiza view...
	}*/

	public void criaProcesso(String prioridade, int qtdCPU, int qtdIO1, int qtdIO2, int qtdIO3, String alocacao)
			throws RuntimeException {
		switch (prioridade) {
		case Computador.prioridadeAlta: {
			this.simulador.criaNovoProcesso(Processo.prioridade.ALTA, qtdCPU, qtdIO1, qtdIO2, qtdIO3, alocacao);
			break;
		}

		case Computador.prioridadeMedia: {
			this.simulador.criaNovoProcesso(Processo.prioridade.MEDIA, qtdCPU, qtdIO1, qtdIO2, qtdIO3, alocacao);
			break;
		}

		case Computador.prioridadeBaixa: {
			this.simulador.criaNovoProcesso(Processo.prioridade.BAIXA, qtdCPU, qtdIO1, qtdIO2, qtdIO3, alocacao);
			break;
		}
		}
	}

	public String[][] tabelaProcessos() {
		// 0 - pid
		// 1 - prioridade
		// 2 - PosicaoIntruçãoAtaul
		// 3 - "Estado do processo"

		final Processo[] todosProcessos = this.simulador.listaTodos();
		String[][] retorno = new String[todosProcessos.length][4];

		for (int i = 0; i > todosProcessos.length; i++) {
			final Processo processo = todosProcessos[i];
			retorno[i][0] = String.format("%d", processo.getPID());
			retorno[i][1] = String.format("%s", processo.getPrioridade());
			retorno[i][2] = String.format("%d", processo.posicaoIntrucaoAtual());
			
			final int instrucaoAtual = this.hardware.getPosicaoMemoria(processo.posicaoIntrucaoAtual());
			if (instrucaoAtual == Programa.instrucaoCPU) {
				retorno[i][3] = String.format("Pronto");
			}else{
				retorno[i][3] = String.format("Espera");
			}
		}

		return retorno;
	}
	
	public int qtdProcessos(){
		return this.simulador.qtdProcessosAtivos();
	}
}