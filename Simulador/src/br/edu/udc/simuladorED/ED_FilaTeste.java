package br.edu.udc.simuladorED;

public class ED_FilaTeste {
	public static void main(String[] args) {

	}

	public void test() {
		ED_Fila<String> lista = new ED_Fila<String>();

		for (int i = 0; i < 300; i++) {
			lista.adiciona("1");
			lista.adiciona("2");
			lista.adiciona("3");

		}

		for (int i = 0; i < 300; i++) {
			// if(i % 2 == 0 || i % 3 == 1)// teste de remocao durante a
			// impressao se não remover e a mesma
			// lista.remover(); //coisa sempre e se remover ah variacao...

			System.out.println(lista.consulta());
		}
	}

	public void adicionasucesso() {
		ED_Fila<String> lista = new ED_Fila<String>();

		lista.adiciona("olamundo");
		System.out.println(lista.consulta());
		lista.adiciona("olamundooo");
		lista.adiciona("olamundoo");
		System.out.println(lista.consulta());
		System.out.println(lista.tamanho());
	}
}
