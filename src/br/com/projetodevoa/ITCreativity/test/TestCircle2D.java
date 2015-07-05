package br.com.projetodevoa.ITCreativity.test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Scanner;

import javax.swing.JFrame;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionRegion;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.MonitorOptions;
import br.com.projetodevoa.ITCreativity.Devices.control.DepthDevice;
import br.com.projetodevoa.ITCreativity.Devices.control.WebcamDevice;
import br.com.projetodevoa.ITCreativity.Devices.view.Devices;
import br.com.projetodevoa.ITCreativity.Events.ChangeSlide;
import br.com.projetodevoa.ITCreativity.Events.ChangeTest;
import br.com.projetodevoa.ITCreativity.Events.ClickEvent;
import br.com.projetodevoa.ITCreativity.Events.SoundEvent;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawCircle2D;

public class TestCircle2D{
	private DrawCircle2D circle;
	
	public TestCircle2D(){
		Devices device = new DepthDevice();
//		device.start();
		final InteractionMonitor interactionMonitor = InteractionMonitor.makeDevice(device);
		
		
		
		gui(interactionMonitor);
		mouse(interactionMonitor,device);
		interactionMonitor.doAction(MonitorOptions.START_MONITORING);
 
	}

	private void mouse(InteractionMonitor interactionMonitor, Devices device){
		final InteractionMonitor monitor = interactionMonitor;
		final Devices devices = device;
		
		 monitor.getComponent().addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				for(InteractionRegion ir: monitor.getInteractionRegions()){
					System.out.println("Z:["+ir.getPivoZ()+"] " + ir.getShapeDescription() + " W:[" + ir.getWidth()+"] "+ "H:[" + ir.getHeight()+"]");
				}				
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		 monitor.getComponent().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				int x = circle.getXClick();
				int y = circle.getYClick();
				int w=arg0.getX()-x;
				int h=arg0.getY()-y;
 	 
				((DrawCircle2D)circle).getCircle().setFrame(x,y,w,h);
				monitor.addInteractionRegion(circle, devices);
//				circle.draw();
				
			 	
				Scanner s = new Scanner(System.in);
				String op = s.nextLine();
			 	if(op.equals("0")){
			 		circle.addEventListener(new ChangeSlide(), new ClickEvent(devices,circle));
			 	}else{
			 		circle.addEventListener(new ChangeTest(), new SoundEvent());	
			 	}
		 	}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				circle = new DrawCircle2D(arg0.getX(), arg0.getY());
				monitor.addInteractionRegion(circle, devices);
//			 	circle.draw();
			
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
		});
 
	}
	
	private void gui(InteractionMonitor monitor){
		JFrame jframe = new JFrame();
		jframe.setBounds(0,0, 650,490);
		jframe.setResizable(false);
		jframe.setLocation(0,0);
		jframe.add(monitor.getComponent());
		
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public static void main(String[] args){
		new TestCircle2D();
	}
}