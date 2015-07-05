package br.com.projetodevoa.ITCreativity.test;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Scanner;

import javax.swing.JFrame;

import org.openni.SensorType;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionRegion;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.MonitorOptions;
import br.com.projetodevoa.ITCreativity.Devices.DeviceResolutions.Depth.DepthDeviceResolution;
import br.com.projetodevoa.ITCreativity.Devices.control.DepthDevice;
import br.com.projetodevoa.ITCreativity.Events.ChangeSlide;
import br.com.projetodevoa.ITCreativity.Events.ChangeTest;
import br.com.projetodevoa.ITCreativity.Events.ClickEvent;
import br.com.projetodevoa.ITCreativity.Events.SoundEvent;
import br.com.projetodevoa.ITCreativity.Events.model.EventListener;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes3D.DrawCircle3D;

public class TestCircle3D {

	private DepthDevice device;
	private InteractionMonitor monitor;
	private DrawCircle3D circle;
	InteractionRegion ir;
	
	private int x;
	private int y;
	
	private boolean dragging = false;
	private boolean mouseReleased = false;
	private int contadorMousePressed = 0;
	
	public TestCircle3D(){
		//Iniciar dispositivo:
		device = new DepthDevice(DepthDeviceResolution.RES640_480,SensorType.DEPTH);
		monitor = InteractionMonitor.makeDevice(device);
 
		/*
		 * 
		 *  Device camera=new CameraDevice();
			InteractionMonitor monitor=new InteractionMonitor(camera);
			circleangle2D botao=new circleangle2D(10,10,50,50);
			EventListener changeSlide=new ClickEventListener();
			botao.addEventListener (changeSlide,CLICK_EVENT);
			monitor.addInteractionRegion(botao)
			monitor.start()
		 */
		
	
			
		JFrame jframe = new JFrame();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setBounds(0,0, 650,490);
		jframe.setResizable(false);
		jframe.setLocation(0,0);
		jframe.add(monitor.getComponent());
		
		jframe.setVisible(true);
	
		monitor.getComponent().addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				for(InteractionRegion ir : monitor.getInteractionRegions()){
					
					System.out.println("MouseMoved: " + ir.getPivoZ());
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				  int x = (int)circle.getCircle().getX(); 
				  int y = (int)circle.getCircle().getY();
				  int w=arg0.getX()-x;
				  int h=arg0.getY()-y;
				  circle.getCircle().setFrame(x, y, w, h);
				  
				dragging = true;
			}
		});
		
		
		monitor.getComponent().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				  int w=arg0.getX()-x;
				  int h=arg0.getY()-y;
				  
					//Reseta o contador de Mouse Pressionado quando termina um desenho:
					contadorMousePressed = 0;
					//Também informa que o usuário não está mais desenhando
					
					
					if(mouseReleased && dragging){
						circle.getCircle().setFrame(x, y, w, h);
						monitor.addInteractionRegion(circle, device);
					 ;
						
						/* IMPLEMENTAR */

						Scanner s = new Scanner(System.in);
						int op = s.nextInt();
						
						if(op == 0){
							EventListener changeSlide = new ChangeSlide();
							circle.addEventListener(changeSlide, new ClickEvent(device,circle));
						}else
							if(op == 1){
								EventListener changetest = new ChangeTest();
								EventListener changeSlide = new ChangeSlide();
								circle.addEventListener(changetest, new SoundEvent());
								circle.addEventListener(changeSlide, new ClickEvent(device,circle));
							}else{
								EventListener changetest = new ChangeTest();
								circle.addEventListener(changetest, new SoundEvent());
							}
					}
					
					dragging = false;
					mouseReleased = false;
				 
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {

				Point point = arg0.getPoint();
				x = point.x;
				y = point.y;
				
				if(contadorMousePressed == 0){
					circle = new DrawCircle3D(arg0.getX(),arg0.getY(), 100);
					 
					monitor.addInteractionRegion(circle, device);
				 
				}

				contadorMousePressed++;
				dragging = true;
				mouseReleased = true;
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
				System.out.println("...");
			}
		});
 
		
		/* IMPLEMENTAR */
		monitor.doAction(MonitorOptions.START_MONITORING);
		
	 
	}

	
	public static void main(String[] args) {
		new TestCircle3D();
	}
}
