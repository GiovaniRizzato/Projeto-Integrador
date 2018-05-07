package br.edu.udc.simulador.arquivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import br.edu.udc.ed.lista.vetor.Vetor;

public class Arquivos {

	public static void main(String[] args) throws IOException {
		File arquivo = null;
		try {
			arquivo = new File("processos.txt");

			Vetor<RawMaterialProcesso> vetor = new Vetor<>();
			vetor.adiciona(new RawMaterialProcesso("ALTA-10-11-12-13"));
			vetor.adiciona(new RawMaterialProcesso("MEDIA-10-11-12-13"));
			vetor.adiciona(new RawMaterialProcesso("BAIXA-10-11-12-13"));
			
			gravaArquivo(arquivo, vetor);
			System.out.println(arquivo.getPath());
			Vetor<String> linhasArquivo = Arquivos.leArquivo(arquivo);

			for (int i = 0; i < linhasArquivo.tamanho(); i++) {
				System.out.println(new RawMaterialProcesso(linhasArquivo.obtem(i)).toString());
			}

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Arquivo não encontrado");
		}
	}

	private static Vetor<String> leArquivo(File arquivo) throws IOException {

		FileInputStream fileStream = new FileInputStream(arquivo);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(fileStream));
		Vetor<String> linhas = new Vetor<>();

		String linhaLida = null;
		while ((linhaLida = buffer.readLine()) != null) {
			linhas.adiciona(linhaLida);
		}

		buffer.close();
		return linhas;
	}

	public static void gravaArquivo(File arquivo, Vetor<RawMaterialProcesso> vetor) throws IOException {

		FileWriter fWriter = new FileWriter(arquivo, arquivo.exists());
		PrintWriter pWriter = new PrintWriter(fWriter);

		for (int i = 0; i < vetor.tamanho(); i++) {
			pWriter.println(vetor.obtem(i).toString());
		}

		pWriter.close();
	}
}
