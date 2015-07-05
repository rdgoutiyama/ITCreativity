package br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Controllers.ControllerWebCam;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Controllers.ControllerDepth;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.InteractionMonitorState;

public class PauseDeviceState implements InteractionMonitorState{

	@Override
	public boolean stateOperation(InteractionMonitor interactionMonitor) {
		if(interactionMonitor instanceof ControllerDepth){
			((ControllerDepth) interactionMonitor).getVideoStream().stop();
			return true;
		}else if(interactionMonitor instanceof ControllerWebCam){
			((ControllerWebCam) interactionMonitor).stop();
			return true;
		}
		
		
		return false;
		
		
	}

}
