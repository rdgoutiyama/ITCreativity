package br.com.projetodevoa.ITCreativity.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.openni.SensorType;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionRegion;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.MonitorOptions;
import br.com.projetodevoa.ITCreativity.Devices.DeviceResolutions.Depth.DepthDeviceResolution;
import br.com.projetodevoa.ITCreativity.Devices.control.DepthDevice;
import br.com.projetodevoa.ITCreativity.Events.ChangeSlide;
import br.com.projetodevoa.ITCreativity.Events.ClickEvent;
import br.com.projetodevoa.ITCreativity.Events.model.EventListener;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawCircle2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawPolygon2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawRect2D;

public class TestPolygon2D extends JFrame implements MouseListener{

	private DepthDevice device;
	private InteractionMonitor monitor;
	private DrawRect2D rect;
	private DrawCircle2D circle;
	private DrawPolygon2D polygon;
	private InteractionRegion ir;
	
	private int x;
	private int y;
	
	
	
	@Override
	public void paint(Graphics g){
	}
	
	public  TestPolygon2D(){
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
				for(InteractionRegion ir: monitor.getInteractionRegions()){
					System.out.println("Z:["+ir.getPivoZ()+"] " + ir.getShapeDescription());
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
	 
			}
		});
		
		
		monitor.getComponent().addMouseListener(this);
 
		
		/* IMPLEMENTAR */
		monitor.doAction(MonitorOptions.START_MONITORING);
		
	 
	}
	
	
	
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		Point point = arg0.getPoint();
		x = point.x;
		y = point.y;
		  int w=arg0.getX()-x;
		  int h=arg0.getY()-y;
		  
			//Reseta o contador de Mouse Pressionado quando termina um desenho:
			//Também informa que o usuário não está mais desenhando
			
			
			if(mouseReleased && dragging){
			
				polygon.addXYArch(x,y);
				EventListener changeSlide = new ChangeSlide();
				polygon.addEventListener(changeSlide, new ClickEvent(device,polygon));
				/* IMPLEMENTAR */
//
//				Scanner s = new Scanner(System.in);
//				int op = s.nextInt();
//				
//				if(op == 0){
//					EventListener changeSlide = new ChangeSlide();
//					polygon.addEventListener(changeSlide, new ClickEvent(device,rect));
//				}else
//					if(op == 1){
//						EventListener changetest = new ChangeTest();
//						EventListener changeSlide = new ChangeSlide();
//						polygon.addEventListener(changetest, new MoveEvent());
//						polygon.addEventListener(changeSlide, new ClickEvent(device,rect));
//					}else{
//						EventListener changetest = new ChangeTest();
//						polygon.addEventListener(changetest, new MoveEvent());
//					}
			}
			
			dragging = false;
			mouseReleased = false;
		
			
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1){
			BufferedImage image = null;
	    	try{
	    		image = ImageIO.read(new File("src/images/dotRed.png"));
	    	}catch(Exception ex){
	    		
	    	}
	    	
	    	Point point = arg0.getPoint();
			x = point.x;
			y = point.y;
			
			if(contadorMousePressed == 0){
				polygon = new DrawPolygon2D(x,y);
				contadorMousePressed++;
				polygon.setShapeColor(Color.yellow);
				monitor.addInteractionRegion(polygon, device);
			}

			dragging = true;
			mouseReleased = true;
		}
		if(arg0.getButton() == MouseEvent.BUTTON2){
			contadorMousePressed = 0;
		}
		if(arg0.getButton() == MouseEvent.BUTTON3){
			for(int x = 0; x < polygon.getXArchs().size(); x++){
				System.out.println("X: " + polygon.getXArch(x) + "  Y: " + polygon.getYArch(x));
			}
		}
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
		
	}
	
	
	
	
	private boolean dragging = false;
	private boolean mouseReleased = false;
	private int contadorMousePressed = 0;
	
	public static void main(String[] args) {
		new TestPolygon2D();
	}


}
