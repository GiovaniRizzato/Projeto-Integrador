package br.edu.udc.simulador.so;

import br.edu.udc.ed.vetor.ConjuntoAmostral;

// Armazenamento de dados estatisticos
// Podem ser declaradas por outras classes para "copiar" as estatisiticas
public class EstatisticaSO {
	public ConjuntoAmostral<Integer> qtdMemoria = new ConjuntoAmostral<>();
	public ConjuntoAmostral<Integer> tempoDeCPU = new ConjuntoAmostral<>();
	public ConjuntoAmostral<Integer> tempoDePronto = new ConjuntoAmostral<>();
	public ConjuntoAmostral<Integer> tempoDeES1 = new ConjuntoAmostral<>();
	public ConjuntoAmostral<Integer> tempoDeES2 = new ConjuntoAmostral<>();
	public ConjuntoAmostral<Integer> tempoDeES3 = new ConjuntoAmostral<>();
	public ConjuntoAmostral<Integer> tempoDeEsperaES1 = new ConjuntoAmostral<>();
	public ConjuntoAmostral<Integer> tempoDeEsperaES2 = new ConjuntoAmostral<>();
	public ConjuntoAmostral<Integer> tempoDeEsperaES3 = new ConjuntoAmostral<>();

	@Override
	public EstatisticaSO clone() {
		EstatisticaSO clonado = new EstatisticaSO();
		// [Professor] como fazer para que não precise de casting
		clonado.qtdMemoria = this.qtdMemoria.clone();
		clonado.tempoDeCPU = this.qtdMemoria.clone();
		clonado.tempoDePronto = this.qtdMemoria.clone();
		clonado.tempoDeES1 = this.qtdMemoria.clone();
		clonado.tempoDeES2 = this.qtdMemoria.clone();
		clonado.tempoDeES3 = this.qtdMemoria.clone();
		clonado.tempoDeEsperaES1 = this.qtdMemoria.clone();
		clonado.tempoDeEsperaES2 = this.qtdMemoria.clone();
		clonado.tempoDeEsperaES3 = this.qtdMemoria.clone();

		return clonado;
	}
}