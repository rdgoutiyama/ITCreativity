package br.com.projetodevoa.ITCreativity.Events;

import br.com.projetodevoa.ITCreativity.Events.model.EventListener;
import br.com.projetodevoa.ITCreativity.Events.model.Events;

public class ChangeTest implements EventListener {

	@Override
	public void fireEvent(Events event) {
		System.out.println("HELLO WORLD!!!");
		event.detect();
	}

}

