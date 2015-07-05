package br.com.projetodevoa.ITCreativity.Devices.DeviceResolutions.Webcam;

import javax.media.CaptureDeviceInfo;
import javax.media.Format;
import javax.media.protocol.CaptureDevice;

/**
 * <h1> Webcam Device </h1>
 * <p>
 * A Classe WebcamDevice é responsável pela estrutura do dispositivo com
 * imagem capturada via webcam. 
 * </p>
 * 
 * @author Rodrigo Junior Utiyama
 * @version 1.0
 * @since 2014-11-30
 */
public interface InterfaceFormats {
	public Format getFormat(CaptureDeviceInfo captureDeviceInfo);
	public int 	  getX();
	public int    getY();
}
