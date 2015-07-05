package br.com.projetodevoa.ITCreativity.Events;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import com.sun.glass.events.KeyEvent;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionRegion;
import br.com.projetodevoa.ITCreativity.Devices.view.Devices;
import br.com.projetodevoa.ITCreativity.Events.model.Events;

/**
 * <h1>Click Event</h1>
 * <p>
 * 	Implementação de um evento de clique a ser disparado. Essa 
 * <pre>
 * 		public class EventoClick extendsOverride
		{@code @Override}
			public boolean detect() {
				try {
					Robot robot = new Robot();
					robot.mouseMove(region.getXClick(), region.getYClick());
					robot.mousePress(
					InputEvent.BUTTON1_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					robot.mouseMove(1, 1);	
					
					System.out.print("Disparando evento clique na posição[x,y]: "
							+ region.getXClick() + "," + region.getYClick());
				
				} catch (AWTException e) {
					e.printStackTrace();
				}
				return false;
			}
 * 
 * 		}
 * </pre>
 *
 * 
 * @author Rodrigo Junior Utiyama
 * @version 1.0
 * @since 2014-11-30
 */
public class ClickEvent extends Events {

	private InteractionRegion region;
	private Devices device;
	
	public ClickEvent(Devices device, InteractionRegion region){
		this.device = device;
		this.region = region;
	}

	@Override
	public boolean detect() {
		try {
			//Classe Robot que será responsável pelos cliques
			Robot robot = new Robot();
			robot.mouseMove(region.getXClick(), region.getYClick());
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.mouseMove(1, 1);	
			
			System.out.println("Disparando evento clique na posição[x,y]: "
					+ region.getXClick() + "," + region.getYClick());
		
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return false;
	}
}

