package br.edu.udc.simulador.so.memoria;

import br.edu.udc.ed.iteradores.IteradorManipulador;
import br.edu.udc.simulador.hardware.Hardware;
import br.edu.udc.simulador.processo.Processo;

public class FirstFit extends GerenciadorMemoria {

	private class ParticaoFirst extends Particao {

		public ParticaoFirst(int tamanho, int posicao) {
			super(tamanho, posicao);
		}

		public ParticaoFirst(Processo processo, int tamanho, int posicao) {
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
			return super.posicao - other.posicao;
		}
	}

	public FirstFit(int tamanhoSO, int tamanhoMemoria, Hardware hardware) {
		super(tamanhoSO, tamanhoMemoria, hardware);
	}

	@Override
	public Particao contrutorParticao(int tamanho, int posicao) {
		return new ParticaoFirst(tamanho, posicao);
	}

	@Override
	public Particao contrutorParticao(Processo processo, int tamanho, int posicao) {
		return new ParticaoFirst(processo, tamanho, posicao);
	}

	@Override
	public IteradorManipulador<Particao> procuraPosicaoVazia(int tamanhoPrograma) {

		Particao particaoIdeal = null;
		for (IteradorManipulador<Particao> it = super.listaMemoriaVazia.inicio(); it.temProximo(); it.proximo()) {

			// Verifica se a partição é grande o suficiente para allocação
			Particao particao = it.getDado();
			if (particao.getTamanho() >= tamanhoPrograma) {
				particaoIdeal = particao;
				break;
			}
		}

		if (particaoIdeal == null) {
			// não encontrou NENHUMA particao para o processo
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
