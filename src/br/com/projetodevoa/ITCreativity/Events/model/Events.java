package br.com.projetodevoa.ITCreativity.Events.model;


/**
 * <h1> Events</h1>
 * <p>
 * A classe abstrata Events, é responsável por fornecer a assinatura do método que será 
 * implementado por todos os eventos de uma aplicação. Esses eventos poderão ser implementados
 * por desenvolvedores, sendo extensíveis. Um exemplo de classe que implementa um determinado evento:
 * 
 * <pre>
 * 		public class EventoClick extendsOverride
		{@code @Override}
			public boolean detect() {
				try {
					Robot robot = new Robot();
					robot.mouseMove(region.getXClick(), region.getYClick());
					robot.mousePress(InputEvent.BUTTON1_MASK);
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
public abstract class Events {
	
	/**
	 * Responsável pela implementação das ações do evento a ser disparado.
	 * @return true se o evento foi disparado ou false se o evento não foi disparado.
	 */
	public abstract boolean detect();
}
