package br.edu.udc.simulador.so.memoria;

import br.edu.udc.ed.colecao.Comparavel;
import br.edu.udc.simulador.processo.Processo;
import br.edu.udc.simulador.so.SistemaOperacional;

abstract class Particao implements Comparavel {
	private Processo processo;
	// referenca do processo para fazer a desfragmentação
	protected Integer tamanho;
	protected Integer posicao;

	public Particao(int tamanho, int posicao) {
		this.processo = null;
		this.tamanho = tamanho;
		this.posicao = posicao;
	}

	public Particao(Processo processo, int tamanho, int posicao) {
		this.processo = processo;
		this.tamanho = tamanho;
		this.posicao = posicao;
	}

	public Processo getProcesso() {
		return this.processo;
	}

	public Integer getPid() {
		if (this.processo != null) {
			return this.processo.getPID();
		} else {
			return SistemaOperacional.POSICAO_MEMORIA_VAZIA;
		}
	}

	public Integer getTamanho() {
		return this.tamanho;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public void setTamanho(Integer tamanho) {
		if (tamanho != null)
			this.tamanho = tamanho;
		else
			throw new NullPointerException("Partição - Tamanho não pode ser null");
	}

	public void setPosicao(Integer posicao) {
		if (posicao != null)
			this.posicao = posicao;
		else
			throw new NullPointerException("Partição - Posição não pode ser null");
	}

	public Integer getPosicao() {
		return this.posicao;
	}

	// @Override de "Comparavel"
	@Override
	public abstract int comparaCom(Object elemento);

	// @Override de "Object"
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((posicao == null) ? 0 : posicao.hashCode());
		result = prime * result + ((processo == null) ? 0 : processo.hashCode());
		result = prime * result + ((tamanho == null) ? 0 : tamanho.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Particao other = (Particao) obj;

		// Posicao
		if (!posicao.equals(other.posicao)) {
			return false;
		}

		// Processo
		if (processo == null) {
			if (other.processo != null) {
				return false;
			}
		} else if (!processo.equals(other.processo)) {
			return false;
		}

		// Tamanho
		if (!tamanho.equals(other.tamanho)) {
			return false;
		}

		return true;
	}

	// Para ajuda no debug
	@Override
	public String toString() {
		return ("Posicao: " + this.posicao.toString() + ", Tamanho: " + this.tamanho + ", PID: " + this.getPid());
	}
}
