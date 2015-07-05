package br.com.projetodevoa.ITCreativity.ControllerMonitor.State;

public enum MonitorOptions {
	
	//Cada constante terá seu próprio indice:
	START_MONITORING(1), 
	PAUSE_MONITORING(2), 
	STOP_DEVICE_CAPTURE(3), 
	START_DEVICE_CAPTURE(4), 
	CALIBRATE(5);
	
	private int option;
	
	MonitorOptions(int option){
		this.option = option;
	}
	
	public int getOption(){ return this.option; }
 
}


