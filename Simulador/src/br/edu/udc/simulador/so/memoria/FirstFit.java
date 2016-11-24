package br.edu.udc.simulador.so.memoria;

import br.edu.udc.ed.iteradores.IteradorManipulador;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.so.SimuladorSO;

public class FirstFit extends GerenciadorMemoria {

	public FirstFit(int tamanhoSO, int tamanhoMemoria, Hardware hardware) {
		super(tamanhoSO, tamanhoMemoria, hardware);
	}

	@Override
	public IteradorManipulador<Particao> procuraPosicaoVazia(int tamanhoPrograma) {

		for (IteradorManipulador<Particao> it = this.listaMemoria.inicio(); it.temProximo(); it.proximo()) {
			// Procura por uma partição vazia
			if (it.getDado().getPid() == SimuladorSO.POSICAO_MEMORIA_VAZIA) {

				// Verifica se a partição é grande o suficiente para allocação
				if (it.getDado().getTamanho() >= tamanhoPrograma) {
					return it;
				}
			}
		}

		throw new RuntimeException("Não há partição livre grande o suficiente");
	}

	@Override
	public void desalocaMemoria(int pid) {
		
		for (IteradorManipulador<Particao> it = super.listaMemoria.inicio(); it.temProximo(); it.proximo()) {
			final Particao particao = it.getDado();
			if (particao.getPid() == pid) {
				particao.setProcesso(null);
				// "marca" como vazio

				this.listaMemoriaVazia.adiciona(particao);

				this.verificaEspacoLivreAdjacente();
				return;
			}
		}

		throw new IllegalArgumentException("PID não esta na lista de memória");
	}
}
