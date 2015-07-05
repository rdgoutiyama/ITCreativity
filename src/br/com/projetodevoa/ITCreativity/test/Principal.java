package br.com.projetodevoa.ITCreativity.test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import org.openni.SensorType;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Controllers.ControllerDepth;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionRegion;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.MonitorOptions;
import br.com.projetodevoa.ITCreativity.Devices.DeviceResolutions.Depth.DepthDeviceResolution;
import br.com.projetodevoa.ITCreativity.Devices.control.DepthDevice;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawCircle2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawRect2D;

public class Principal {

	private DepthDevice device;
	private InteractionMonitor monitor;
	private DrawRect2D rect;
	private DrawCircle2D circle;
	InteractionRegion ir;
	
	private int x;
	private int y;
	Double[][] varianciaMap;
	public Principal(){
		//Iniciar dispositivo:
		device = new DepthDevice(DepthDeviceResolution.RES640_480,SensorType.DEPTH);
		monitor = InteractionMonitor.makeDevice(device);
 
		/*
		 * 
		 *  Device camera=new CameraDevice();
			InteractionMonitor monitor=new InteractionMonitor();
			circleangle2D botao=new circleangle2D(10,10,50,50);
			EventListener changeSlide=new ClickEventListener();
			botao.addEventListener (changeSlide,new ClickEvent());
			monitor.addInteractionRegion(botao,camera)
			monitor.start()
		 */
		
	
			
		JFrame jframe = new JFrame();
		jframe.setBounds(0,0, 650,490);
		jframe.setResizable(false);
		jframe.setLocation(0,0);
		jframe.add(monitor.getComponent());
		
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		
		monitor.getComponent().addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				 
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
//				  int x = (int)rect.getRectangle2D().getX(); 
//				  int y = (int)rect.getRectangle2D().getY();
//				  int w=arg0.getX()-x;
//				  int h=arg0.getY()-y;
//				  rect.getRectangle2D().setFrame(x, y, w, h);
////				  rect.draw();
//				dragging = true;
			}
		});
		
		
		monitor.getComponent().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
	 
				 
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {

			 
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				++contadorMousePressed;
				if(arg0.getButton() == MouseEvent.BUTTON1 && 
						   contadorMousePressed == 1){
					
					Thread t = new Thread(new Runnable() {
						
						@Override
						public void run() {
							monitor.doAction(MonitorOptions.CALIBRATE);
							System.out.println("calibrado");
							
						}
					});
					t.start();

			
					contadorMousePressed = 0;
				}else
					if(arg0.getButton() == MouseEvent.BUTTON3 && contadorMousePressed == 1){
//						monitor.doAction(MonitorOptions.START_MONITORING);
						
						Double media[][] = ((ControllerDepth)monitor).getDepthMapMedia();
						for(int x = 0; x < 640; x++){
							for(int y = 0; y < 480; y++){
								System.out.println(x + "," + y + "  " + media[x][y]);
							}	
						}
						contadorMousePressed = 0;
					}
				
			}
		});
 
		
		/* IMPLEMENTAR */
		monitor.doAction(MonitorOptions.START_DEVICE_CAPTURE);	
		
	 
	}
	private boolean dragging = false;
	private boolean mouseReleased = false;
	private int contadorMousePressed = 0;
	
	public static void main(String[] args) {
		new Principal();
	}


}
