package br.edu.udc.simuladorED;

public class ED_FilaTeste {
	public static void main(String[] args) {
		ED_Fila<String> lista = new ED_Fila<String>();

		lista.adiciona("olamundo");
		System.out.println(lista.consulta());
		lista.adiciona("olamundooo");
		lista.adiciona("olamundoo");
		System.out.println(lista.consulta());
		System.out.println(lista.tamanho());

	}

	public void adicionasucesso() {

	}
}
