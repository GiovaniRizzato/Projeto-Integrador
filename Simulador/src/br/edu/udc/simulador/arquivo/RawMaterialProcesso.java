package br.edu.udc.simulador.arquivo;

import javax.management.RuntimeErrorException;
import br.edu.udc.simulador.processo.Processo.Prioridade;

public class RawMaterialProcesso {

	public Prioridade prioridade;
	public int qtdCPU;
	public int qtdES1;
	public int qtdES2;
	public int qtdES3;

	public RawMaterialProcesso(String linhaArquivo) {
		if (linhaArquivo.matches("((ALTA)|(MEDIA)|(BAIXA))-([0-9])+-([0-9])+-([0-9])+-([0-9])+")) {
			String[] argumentos = linhaArquivo.split("-");

			switch (argumentos[0]) {
			case "ALTA": {
				this.prioridade = Prioridade.ALTA;
				break;
			}
			case "MEDIA": {
				this.prioridade = Prioridade.MEDIA;
				break;
			}

			case "BAIXA": {
				this.prioridade = Prioridade.BAIXA;
				break;
			}
			}

			this.qtdCPU = Integer.parseInt(argumentos[1]);
			this.qtdES1 = Integer.parseInt(argumentos[2]);
			this.qtdES2 = Integer.parseInt(argumentos[3]);
			this.qtdES3 = Integer.parseInt(argumentos[4]);

		} else {
			throw new RuntimeErrorException(null, "Erro na contrução da linha no arquivo");
		}
	}

	public String toString() {
		return (this.prioridade.toString() + "-" + this.qtdCPU + "-" + this.qtdES1 + "-" + this.qtdES2 + "-"
				+ this.qtdES3);
	}
}
