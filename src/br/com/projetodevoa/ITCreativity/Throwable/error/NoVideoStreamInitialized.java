package br.com.projetodevoa.ITCreativity.Throwable.error;

public class NoVideoStreamInitialized extends Error{
	public NoVideoStreamInitialized(){
		super("Nenhum \"Device\" instanciado, ou seja, nenhum dispositivo iniciado. Certifique-se de que o dispositivo não foi desconectado. ");
	}
}
