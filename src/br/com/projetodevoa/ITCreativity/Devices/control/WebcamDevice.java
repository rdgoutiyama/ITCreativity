package br.com.projetodevoa.ITCreativity.Devices.control;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FormatControl;
import javax.media.control.FrameGrabbingControl;
import javax.media.control.FrameRateControl;
import javax.media.format.VideoFormat;
import javax.media.protocol.CaptureDevice;
import javax.media.protocol.DataSource;
import javax.media.util.BufferToImage;
import javax.swing.Timer;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Controllers.ControllerWebCam;
import br.com.projetodevoa.ITCreativity.Devices.DeviceResolutions.Webcam.WebcamDeviceResolution;
import br.com.projetodevoa.ITCreativity.Devices.view.Devices;

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
 * 
 */
public class WebcamDevice extends Devices  implements ActionListener {
	public static Player player = null;
	private  CaptureDeviceInfo di = null;
	private MediaLocator ml = null;
	private Buffer buf = null;
	private Image img = null;
	private VideoFormat vf = null;
	private BufferToImage btoi = null;
	private Timer t = null;
	private ControllerWebCam controller = null;
	
	private static int FRAMEHEIGHT = 320;
	private static int FRAMEWIDTH  = 240;
	
	/**
	 * Construtor padrão. Instancia a webcam em resolução 320x240
	 */
	public WebcamDevice(){
		this(WebcamDeviceResolution.RES320_240);
	}
	/**
	 * Este construtor instanciará a captura da imagem conforme a resolução
	 * @param resolution Resolução da imagem. Aceitando uma constante {@link WebcamDeviceResolution} enum.
	 */
	public WebcamDevice(WebcamDeviceResolution resolution) {
		String str1 = "vfw:Microsoft WDM Image Capture (Win32):0";
		String str2 = "vfw:Creative WebCam Live! (VFW):0";
		
		di = CaptureDeviceManager.getDevice(str1);
		// System.out.println(di);
		ml = di.getLocator();

		
//		Format format[] = di.getFormats();
		DataSource source = null;
		Manager.setHint(Manager.LIGHTWEIGHT_RENDERER, true);
		try {
			source = Manager.createDataSource(ml);
			Object[] controls = source.getControls();

			// Acessa os controles de formatação
			FormatControl[] fmt = ((CaptureDevice) source).getFormatControls();
			// seleciona o primeiro formato para apresentação
			fmt[0].setFormat(WebcamDeviceResolution.RES640_480.getFormat(di));
			
			FRAMEHEIGHT = resolution.getX();
			FRAMEWIDTH  = resolution.getY();
		} catch (Exception e) {
			System.out.println("Não foi possível acessar o device");
			e.printStackTrace();
		}

		float maxrate = 30;

		try {
			player = Manager.createRealizedPlayer(source);
			FrameRateControl frc = (FrameRateControl) player
					.getControl("javax.media.control.FrameRateControl");
			System.out.println(maxrate = frc.getMaxSupportedFrameRate());
			player.start();
			if (maxrate > 0)
				frc.setFrameRate(1);

		} catch (Exception e) {
			System.out.println("Não foi possível obter o visualizador");
			e.printStackTrace();
		}

		t=new Timer((int)(1000/maxrate),this);
		t.start(); 
	}

	@Override
	public String getDeviceInfo() {
		return  "Name: " + di.getName() + 
				"\nLocator: " + di.getLocator().toString();
	}

	/**
	 * Retorna todos os formatos aceitos pela webcam.
	 * @return Format[] Todos os formatos aceitos em um array do tipo Format.
	 */
	public Format[] getFormats(){
		return di.getFormats();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			// Grab a frame
			FrameGrabbingControl fgc = (FrameGrabbingControl)
			player.getControl("javax.media.control.FrameGrabbingControl");
			buf = fgc.grabFrame();
			
			// Convert it to an image
			btoi = new BufferToImage((VideoFormat)buf.getFormat());
			img = btoi.createImage(buf);

			// show the image
			controller.setImage(img);
		}catch(NullPointerException ex){
			
		}
	}
	
	
	public void setController(ControllerWebCam controller){
		this.controller = controller;
	}

	/**
	 * Retorna um objeto do tipo Player (JMF Framework).
	 	@return player Tipo Player do JMF.
	 */
	public Player getPlayer(){
		return player;
	}
	
	/**
	 * Retorna a largura do frame (imagem capturada)
	 * @return FRAMEWIDTH Constante que contem o valor da resolução da largura atual
	 */
	public int frameWidth(){
		return FRAMEWIDTH;
	}
	
	/**
	 * Retorna a altura do frame (imagem capturada)
	 * @return FRAMEHEIGHT Constante que contem o valor da resolução da altura atual
	 */
	public int frameHeight(){
		return FRAMEHEIGHT;
	}
}
