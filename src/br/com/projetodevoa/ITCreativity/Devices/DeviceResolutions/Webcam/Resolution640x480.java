package br.com.projetodevoa.ITCreativity.Devices.DeviceResolutions.Webcam;

import javax.media.CaptureDeviceInfo;
import javax.media.Format;
import javax.media.protocol.CaptureDevice;

public class Resolution640x480 implements InterfaceFormats {

	@Override
	public Format getFormat(CaptureDeviceInfo captureDeviceInfo) {
		
		for(Format f : captureDeviceInfo.getFormats()){
			if(f.toString().contains("640x480")){
				return f;
			}
		}
		
		throw new NullPointerException("O dispositivo não suporta a resolução 640x480. O padrão será 320x240");
	}

	@Override
	public int getX() {
		return 640;
	}

	@Override
	public int getY() {
		return 480;
	}

}
