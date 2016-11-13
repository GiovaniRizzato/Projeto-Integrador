package br.edu.udc.simulador.so;

import br.edu.udc.ed.colecao.Comparavel;
import br.edu.udc.simulador.processo.Processo;

class Particao implements Comparavel {
	private Processo processo;
	// referenca do processo para fazer a desfragmentação
	private Integer pid;
	private Integer tamanho;
	private Integer posicao;

	public Particao(int pid, int tamanho, int posicao) {
		this.processo = null;
		this.pid = pid;
		this.tamanho = tamanho;
		this.posicao = posicao;
	}

	public Particao(Processo processo, int tamanho, int posicao) {
		this.processo = null;
		this.pid = processo.getPID();
		this.tamanho = tamanho;
		this.posicao = posicao;
	}

	public Processo getProcesso() {
		return this.processo;
	}

	public Integer getPid() {
		return this.pid;
	}

	public Integer getTamanho() {
		return this.tamanho;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public void setPid(Integer pid) {
		if (pid != null)
			this.pid = pid;
		else
			throw new NullPointerException("Partição - PID não pode ser null");
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
	public int comparaCom(Object elemento) {
		if (this == elemento)
			return 0;
		if (elemento == null)
			throw new NullPointerException();
		if (getClass() != elemento.getClass())
			throw new IllegalArgumentException();

		Particao other = (Particao) elemento;
		return this.tamanho - other.tamanho;
	}

	// @Override de "Object"
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
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

		// PID
		if (!pid.equals(other.pid)) {
			return false;
		}

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
}
