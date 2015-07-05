package br.com.projetodevoa.ITCreativity.Devices.DeviceResolutions.Depth;

/**
 * <h1> Depth Device Resolution - ENUM </h1>
 * <p>
 * Este Enum é responsável por indicar as resoluções disponíveis para captura
 * de um dispositivo de profundidade.
 * </p>
 * 
 * @author Rodrigo Junior Utiyama
 * @version 1.0
 * @since 2014-11-30
 */
public enum DepthDeviceResolution {
	RES320_240(320, 240),
	RES640_480(640,480);
	
	private int x;
	private int y;
	
	private DepthDeviceResolution(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Retorna a altura do dispositivo a ser instanciado
	 * @return x altura do dispositivo a ser instanciado
	 */
	public int getX(){ return x; }
	
	/**
	 * Retorna a largura do dispositivo a ser instanciado
	 * @return y largura do dispositivo a ser instanciado
	 */
	public int getY(){ return y; }
}
