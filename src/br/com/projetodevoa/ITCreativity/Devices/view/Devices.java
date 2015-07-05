package br.com.projetodevoa.ITCreativity.Devices.view;


/**
 * <h1> Webcam Device </h1>
 * <p>
 * A Classe Devices é uma classe abstrata responsável pela modelo de um dispositivo. É a classe
 * mais específica dos dispositivos.
 * </p>
 * 
 * @author Rodrigo Junior Utiyama
 * @version 1.0
 * @since 2014-11-30
 */
public abstract class Devices {
 
 	
//	public abstract void disconnect();
	
//	public abstract void start();
	
//	public abstract void stop();

	/**
	 * Retorna as informações sobre o dispositivo, como resolução, URI entre outros.
	 * @return string informação do dispositivo atual.
	 */
	public abstract String getDeviceInfo();
 
//	public abstract Devices getDevice();
	
//	public abstract InteractionMonitor getMonitor();

}
