	package br.com.projetodevoa.ITCreativity.Events;

import br.com.projetodevoa.ITCreativity.Events.model.EventListener;
import br.com.projetodevoa.ITCreativity.Events.model.Events;

public class ChangeSlide implements EventListener{

	/* Classe que poderá ser modificada ou criada uma nova pelo desenvolvedor */
	@Override
	public void fireEvent(Events event) {
//		System.out.print("HELLO WORLD!!! ");
		event.detect();
	}
}
