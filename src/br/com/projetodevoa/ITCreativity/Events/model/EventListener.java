package br.com.projetodevoa.ITCreativity.Events.model;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor;

/**
 * <h1> Event Listener </h1>
 * <p>
 * EventListener é uma interface responsável pela assinatura do método responsável
 * por disparar um determinado evento. Ela é um listener que será chamado quando
 * o observador {@link InteractionMonitor} disparar um evento.
 * </p>
 * Exemplo de notificação:
 * 	<pre>	
 * 		public void fireEvent(Events event){
 * 			if(event.detect()){
 * 				System.out.println("Evento disparado");
 * 			}
 * 		}
 * </pre>
 * 
 * @author Rodrigo Junior Utiyama
 * @version 1.0
 * @since 2014-11-30
 */
public interface EventListener {

	/**
	 * 
	 * @param event Evento que será disparado quando notoficado.
	 * 
	 */
	public abstract void fireEvent(Events event);

}
