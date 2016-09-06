package br.edu.udc.simuladorDominio;

public class BCP_ContextoDeSoftware {
	
	final private int PID;//� setado no contrutor e n�o pode mais ser modificado ap�s o seu "set"
	private int intrucaoAtual;//Posi��o do vetor onde o "programa" esta processando no momento.
	
	public BCP_ContextoDeSoftware(int argPid){
		this.intrucaoAtual = 0;
		this.PID = argPid;
	}
	
	//PID
	public int getPid(){
		return this.PID;
	}
	
	//INSTRU��O ATUAL
	public int getIntrucaoAtual(){
		return this.intrucaoAtual;
	}
	
	public void proximaInstrucao(){
		this.intrucaoAtual ++;
	}
}
