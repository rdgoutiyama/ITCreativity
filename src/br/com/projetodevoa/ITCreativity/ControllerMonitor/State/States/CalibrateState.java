package br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States;

import com.sun.org.glassfish.gmbal.Description;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Controllers.ControllerDepth;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.InteractionMonitorState;
/**
 * @author Rodrigo Junior Utiyama
 * 
 * Classe responsável por inciar a calibragem.
 */
public class CalibrateState implements InteractionMonitorState {


	
	@Override
	/**
	 * 
	 * @see br.com.projetodevoa.ITCreativity.ControllerMonitor.State.InteractionMonitorState#stateOperation(br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor)
	 */
	public synchronized boolean stateOperation(InteractionMonitor interactionMonitor) {
		
		if(interactionMonitor instanceof ControllerDepth){
			final ControllerDepth dc = ((ControllerDepth)interactionMonitor);	
			dc.startCalibration();
			while(dc.getCalibrationStatus());
		} 

		return true;
	}

	
//	for(int x = 0; x < 640; x++){
//		for(int y = 0; y < 480; y++){
//			System.out.println(media[x][y]);
//		}
//	}
}
