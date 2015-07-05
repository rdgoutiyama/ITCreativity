package br.com.projetodevoa.ITCreativity.test;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.Scanner;

import javax.swing.JFrame;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Controllers.ControllerDepth;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionRegion;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.MonitorOptions;
import br.com.projetodevoa.ITCreativity.Devices.control.WebcamDevice;
import br.com.projetodevoa.ITCreativity.Devices.view.Devices;
import br.com.projetodevoa.ITCreativity.Events.ChangeSlide;
import br.com.projetodevoa.ITCreativity.Events.ChangeTest;
import br.com.projetodevoa.ITCreativity.Events.ClickEvent;
import br.com.projetodevoa.ITCreativity.Events.SoundEvent;
import br.com.projetodevoa.ITCreativity.Events.model.EventListener;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawCircle2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawRect2D;

public class PrincipalCamera {

	private WebcamDevice device;
	private InteractionMonitor monitor;
	private DrawRect2D rect;
	private DrawCircle2D circle;
	InteractionRegion ir;
	
	private int x;
	private int y;
	Double[][] varianciaMap;
	public PrincipalCamera(){
		//Iniciar dispositivo:
		device = new WebcamDevice();
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
		jframe.setBounds(0,0, monitor.getWidth(),monitor.getHeight());
		jframe.setResizable(false);
		jframe.setLocation(0,0);
		jframe.add(monitor.getComponent());
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		monitor.getComponent().addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				System.out.println("X: " + arg0.getX() + " Y: " + arg0.getY());
				for(InteractionRegion ir: monitor.getInteractionRegions()){
					System.out.println("Width: " + ir.getWidth() + " H: " + ir.getHeight());
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				  int x = (int)rect.getRectangle2D().getX(); 
				  int y = (int)rect.getRectangle2D().getY();
				  int w=arg0.getX()-x;
				  int h=arg0.getY()-y;
				  rect.getRectangle2D().setFrame(x, y, w, h);
//				  rect.draw();
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
					
					
					if(mouseReleased && dragging && arg0.getButton() == MouseEvent.BUTTON1){
						
						rect.getRectangle2D().setFrame(x, y, w, h);
						monitor.addInteractionRegion(rect, device);
//						rect.draw();
						
						/* IMPLEMENTAR */

						Scanner s = new Scanner(System.in);
						int op = s.nextInt();
						
						if(op == 0){
							EventListener changeSlide = new ChangeSlide();
							rect.addEventListener(changeSlide, new ClickEvent(device,rect));
						}else
							if(op == 1){
								EventListener changetest = new ChangeTest();
								EventListener changeSlide = new ChangeSlide();
								rect.addEventListener(changetest, new SoundEvent());
								rect.addEventListener(changeSlide, new ClickEvent(device,rect));
							}else{
								EventListener changetest = new ChangeTest();
								rect.addEventListener(changetest, new SoundEvent());
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
				
				if(contadorMousePressed == 0 && arg0.getButton() == MouseEvent.BUTTON1){
					rect = new DrawRect2D(new Rectangle2D.Float(x,y,0,0));
					monitor.addInteractionRegion(rect, device);
				}else
					if(arg0.getButton() == MouseEvent.BUTTON3){
						monitor.doAction(MonitorOptions.CALIBRATE);
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
				Point point = arg0.getPoint();
				x = point.x;
				y = point.y;
				
				if(contadorMousePressed == 0 && arg0.getButton() == MouseEvent.BUTTON1){
					rect = new DrawRect2D(new Rectangle2D.Float(x,y,0,0));
					monitor.addInteractionRegion(rect, device);
				}else
					if(arg0.getButton() == MouseEvent.BUTTON3){
						monitor.doAction(MonitorOptions.CALIBRATE);
					} 

				contadorMousePressed++;
				dragging = true;
				mouseReleased = true;
			}
		});
 
		
		/* IMPLEMENTAR */
		monitor.doAction(MonitorOptions.START_MONITORING);	
		
	 
	}
	private boolean dragging = false;
	private boolean mouseReleased = false;
	private int contadorMousePressed = 0;
	
	public static void main(String[] args) {
		new PrincipalCamera();
	}


}
