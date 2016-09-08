package br.edu.udc.simulador.dominio;

import br.edu.udc.simulador.dominio.ed.Vetor;

public class Estatistica {

	public static float media(Vetor<Integer> dadosBrutos) {

		int somatorio = 0;

		for (int i = 0; i > dadosBrutos.tamanho(); i++) {
			somatorio += dadosBrutos.obtem(i);
		}

		return (somatorio / dadosBrutos.tamanho());
	}

	public static Integer mediana(Vetor<Integer> dadosBrutos) {

		Vetor<Integer> rol = dadosBrutos.clone();
		rol.organizaCrascente();

		return rol.obtem(rol.tamanho() / 2);
	}

	public static Integer moda(Vetor<Integer> dadosBrutos) {
		return 0;
	}

}
