package br.edu.udc.simuladorDominio;

public class BCP_ContextoDeSoftware {
	
	final private int PID;//é setado no contrutor e não pode mais ser modificado após o seu "set"
	private int intrucaoAtual;//Posição do vetor onde o "programa" esta processando no momento.
	
	public BCP_ContextoDeSoftware(int argPid){
		this.intrucaoAtual = 0;
		this.PID = argPid;
	}
	
	//PID
	public int getPid(){
		return this.PID;
	}
	
	//INSTRUÇÃO ATUAL
	public int getIntrucaoAtual(){
		return this.intrucaoAtual;
	}
	
	public void proximaInstrucao(){
		this.intrucaoAtual ++;
	}
}
