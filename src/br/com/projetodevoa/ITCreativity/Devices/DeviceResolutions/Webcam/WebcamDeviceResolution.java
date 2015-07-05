package br.com.projetodevoa.ITCreativity.Devices.DeviceResolutions.Webcam;

import javax.media.CaptureDeviceInfo;
import javax.media.Format;

public enum WebcamDeviceResolution {
	RES160_120(new Resolution160x120()),
	RES320_240(new Resolution320x240()), 
	RES352_288(new Resolution352x288()),  
	RES640_480(new Resolution640x480());
 
	private InterfaceFormats format;
	
	private WebcamDeviceResolution(InterfaceFormats format){
		this.format = format;
	}
	
	public Format getFormat(CaptureDeviceInfo captureDeviceInfo){ 
		return format.getFormat(captureDeviceInfo); 
	}
	
	public int getX(){ return format.getX(); }
	
	public int getY(){ return format.getY(); }
}
