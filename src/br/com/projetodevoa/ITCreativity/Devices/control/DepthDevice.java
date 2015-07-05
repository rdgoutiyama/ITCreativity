package br.com.projetodevoa.ITCreativity.Devices.control;
import org.openni.Device;
import org.openni.OpenNI;
import org.openni.PixelFormat;
import org.openni.SensorType;
import org.openni.VideoMode;
import org.openni.VideoStream;

import br.com.projetodevoa.ITCreativity.Devices.DeviceResolutions.Depth.DepthDeviceResolution;
import br.com.projetodevoa.ITCreativity.Devices.view.Devices;

/**
 * <h1>Depth Device </h1>
 * Classe responsável pela estrutura do dispositivo com sensor de movimentos (DEPTH).
 * Esta classe possui suporte aos dispositivos: Microsoft Kinect [todos] e Asus Xtion [todos].
 * Asus Xtion somente suportará o modo 'DEPTH', para usar em outros modos, será necessário a versão Xtion Pro ou Xtion ProLive.
 *  
 * @author Rodrigo Junior Utiyama
 * @version 1.0
 * @since 2014-11-30
 */

public class DepthDevice extends Devices {

	private VideoStream videoStream;
	private Device dispositivo;
	private static DepthDeviceResolution resolution;
	
	
	/**
	 * Construtor padrão. Não há parâmetros, portanto, o dispositivo iniciará com resolução
	 * '640x480' no modo "DEPTH"
	 */
	public DepthDevice(){
		connect(DepthDeviceResolution.RES640_480, SensorType.DEPTH);
	}
	
	/**
	 * Construtor que instanciará o dispositivo com os valores passados.
	 * 
	 * @param deviceResolution Resolução WIDTH x HEIGHT do stream
	 * @param sensorType 	   Tipo de imagem do sensor (IR [Kinect, Xtion Pro e ProLive], RGB[Kinect, Xtion Pro e Xtion ProLive] ou DEPTH [Todos os dispotivos])
	 */
	public DepthDevice(DepthDeviceResolution deviceResolution, SensorType sensorType){
		connect(deviceResolution, sensorType);
	}
	
	public DepthDeviceResolution getDeviceResolution(){
		return resolution;
	}
	/**
	 * Método privado que poderá ser chamado por ambos os construtores.
	 * 
	 * @param resolution Tipo da resolução
 	 * @param sensorType Tipo do sensor (IR, RGB ou DEPTH)
	 */
	private void connect(DepthDeviceResolution resolution, SensorType sensorType) {
		this.resolution = resolution;
		
		//A classe OpenNI é responsável por inicializar todas as configurações do frameork
		//assim, é inicializado e carregado todo o framework
		OpenNI.initialize();
		
		//A classe Device é responsável por realizar a conexão entre o dispositivo e a aplicação
		//O método open() abre a "conexão" com o dispositivo físico, o método open() pode conter uma URL
		//que permite indicar um arquivo .ONI (simulação) ou a URI do dispotivo físico
		dispositivo = Device.open();
		instanceVideoStream(sensorType, resolution);
	}
	
	/**
	 * Método que ficará responsável por iniciar o vídeo.
	 * Os parâmetros serão os mesmos passados pelos construtores.
	 * 
	 * @param resolution Tipo da resolução
 	 * @param sensorType Tipo do sensor (IR, RGB ou DEPTH)
	 */
	private void instanceVideoStream(SensorType sensorType, DepthDeviceResolution resolution){
		//A classe SensorType.Tipo indica qual o tipo de imagem será captada, ou seja, DEPTH(profundidade)
		//ou COLOR (RGB comum). Ele retorna um valor inteiro constante:
		SensorType tipoDoSensor = sensorType;
		
		int sensorNativeType = 0;
		
		if(tipoDoSensor.equals(SensorType.COLOR) || tipoDoSensor.equals(SensorType.IR)){
			sensorNativeType = PixelFormat.RGB888.toNative();
		}else{
			sensorNativeType = PixelFormat.DEPTH_1_MM.toNative();
		} 
		//Aqui será setada uma nova videoStream, passando o dispositivo e o tipo de sensor:
		videoStream = VideoStream.create(dispositivo, tipoDoSensor);
		
		VideoMode modoVideo = new VideoMode(resolution.getX(), resolution.getY(), 30, sensorNativeType);
		videoStream.setVideoMode(modoVideo);
		videoStream.start();
		
//		depthController = new InteractionMonitorDepth();
//		depthController.setStream(videoStream);
//		
//		HandTracker handTracker = HandTracker.create();
//		handTracker.startGestureDetection(GestureType.HAND_RAISE);
//
//		depthController.setImage("src/images/dotRed.png");
//		depthController.setHandTracker(handTracker);
		
		
	}
	
	/**
	 * Indica o tipo de sensor utilizado e a sua resolução.
	 * @param type Tipo do sensor utilizado (IR, COLOR ou DEPTH), essas são
	 * constantes do Enum SensorType
	 * @param res DepthDeviceResolution é do tipo Enum, indicando 
	 * a resolução do dispositivo.
	 * {@link DepthDeviceResolution} 
	 */
	public void setSensorType(SensorType type, DepthDeviceResolution res){
		disconnect();
		connect(res, type);
	}
	
	/**
	 * Método responsável por finalizar o stream. Observação:  o dispositivo não será desconectado
	 * da porta, mas apenas o processamento de dados da câmera.
	 */
	public void disconnect() {
		if(videoStream instanceof VideoStream){
			videoStream.stop();
			videoStream.destroy();
			videoStream = null;
		}else{
			return;
		}
	}

	/**
	 * Método que será responsável por retornar informações do dispositivo.
	 * @return Informações gerais do dispositivo
	 */
	public String getDeviceInfo() {
		// TODO Auto-generated method stub
		return  "Name: " + dispositivo.getDeviceInfo().getName() + 
				"\nURI: " + dispositivo.getDeviceInfo().getUri() +
				"\nVendor: " + dispositivo.getDeviceInfo().getVendor() +
				"\nUSB Product ID: " + dispositivo.getDeviceInfo().getUsbProductId() + 
				"\nUSB Vendor ID: "  + dispositivo.getDeviceInfo().getVendor(); 
	}

	/**
	 * Método 'get' que retorna um objeto DepthDevice.
	 * @return Objeto do tipo Devices
	 */
	public Device getDevice() {
		return dispositivo;
	}
	
	
	public VideoStream getVideoStream(){
		return videoStream;
	}
	
	public void setVideoStream(VideoStream videoStream){
		this.videoStream = videoStream;
	}
}
