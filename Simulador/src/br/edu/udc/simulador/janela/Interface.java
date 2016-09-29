package br.edu.udc.simulador.janela;

import java.util.Scanner;

import br.edu.udc.simulador.*;
import br.edu.udc.simulador.processo.Processo.prioridade;

public class Interface {

	public static void main(String[] args) {

		SimuladorSO simulador = new SimuladorSO(1000000, 0, 0, 0);
		int qtdMemoria, qtdCPU, qtdES1, qtdES2, qtdES3;
		Scanner recursos = new Scanner(System.in);
		Scanner opc = new Scanner(System.in);
		Scanner opcc = new Scanner(System.in);

		do {
			me_nu();
			switch (opc.nextInt()) {

			case 1: {// criaçao de processo
				System.out.printf("qtdmemoria");
				qtdMemoria = recursos.nextInt();
				System.out.printf("qtdcpu");
				qtdCPU = recursos.nextInt();
				System.out.printf("qtdes1");
				qtdES1 = recursos.nextInt();
				System.out.printf("qtdes2");
				qtdES2 = recursos.nextInt();
				System.out.printf("qtdes3");
				qtdES3 = recursos.nextInt();
				submenu();
				do {
					switch (opcc.nextInt()) {// seleciona prioridade de processo
					case 1: {// prioridade alta

						simulador.criaNovoProcesso(prioridade.ALTA, qtdMemoria, qtdCPU, qtdES1, qtdES2, qtdES3);
						break;
					}
					case 2: {// prioridade media

						break;
					}
					case 3: {// prioridade baixa

						break;
					}
					}
				} while (opcc.nextInt() != 0);
				break;
			}
			}

		} while (opc.nextInt() != 0);
	}

	public static void me_nu() {
		System.out.printf("entre com 1 para criar processo");
	}

	public static void submenu() {
		System.out.printf("entre com 1 para criar processo de alta prioridade");
		System.out.printf("entre com 2 para criar processo de media prioridade");
		System.out.printf("entre com 3 para criar processo de baixa prioridade");
	}
}
