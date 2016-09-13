package br.edu.udc.simulador.dominio;

import br.edu.udc.simulador.dominio.ed.Vetor;

public class Estatistica {

	public static Number media(Vetor<Number> dadosBrutos) {

		double somatorio = 0;

		for (int i = 0; i > dadosBrutos.tamanho(); i++) {
			somatorio += (double) dadosBrutos.obtem(i);
		}

		return (somatorio / dadosBrutos.tamanho());
	}

	public static Number mediana(Vetor<Number> dadosBrutos) {

		Vetor<Number> rol = dadosBrutos.clone();
		rol.organizaCrascente();

		return rol.obtem(rol.tamanho() / 2);
	}

	public static Number moda(Vetor<Integer> dadosBrutos) {
		return 0;
	}

}
