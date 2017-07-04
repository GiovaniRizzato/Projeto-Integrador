package br.edu.udc.simulador.so.memoria;

import br.edu.udc.ed.iteradores.IteradorManipulador;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo;

public class WorstFit extends GerenciadorMemoria {

	private class ParticaoWorst extends Particao {

		public ParticaoWorst(int tamanho, int posicao) {
			super(tamanho, posicao);
		}

		public ParticaoWorst(Processo processo, int tamanho, int posicao) {
			super(processo, tamanho, posicao);
		}

		@Override
		public int comparaCom(Object elemento) {
			if (this == elemento)
				return 0;
			if (elemento == null)
				throw new NullPointerException();
			if (getClass() != elemento.getClass())
				throw new IllegalArgumentException();

			Particao other = (Particao) elemento;
			return other.posicao - super.posicao;
		}
	}

	public WorstFit(int tamanhoSO, int tamanhoMemoria, Hardware hardware) {
		super(tamanhoSO, tamanhoMemoria, hardware);
	}

	@Override
	public Particao contrutorParticao(int tamanho, int posicao) {
		return new ParticaoWorst(tamanho, posicao);
	}

	@Override
	public Particao contrutorParticao(Processo processo, int tamanho, int posicao) {
		return new ParticaoWorst(processo, tamanho, posicao);
	}

	@Override
	public IteradorManipulador<Particao> procuraPosicaoVazia(int tamanhoPrograma) {

		final Particao particaoIdeal = super.listaMemoriaVazia.obtem(0);
		if(particaoIdeal.getTamanho() < tamanhoPrograma){
			throw new RuntimeException("Não há partição livre grande o suficiente");
		}

		// encontra partição na lista principal
		for (IteradorManipulador<Particao> it = super.listaMemoria.inicio(); it.temProximo(); it.proximo()) {

			Particao particao = it.getDado();
			if (particao.equals(particaoIdeal)) {
				return it;
			}
		}

		throw new RuntimeException("Inconsistencia nas listas de memória");
	}
}
