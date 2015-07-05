/*
 * 
 */
package br.com.projetodevoa.ITCreativity.ControllerMonitor.Model;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import br.com.projetodevoa.ITCreativity.Devices.control.DepthDevice;
import br.com.projetodevoa.ITCreativity.Devices.view.Devices;
import br.com.projetodevoa.ITCreativity.Events.model.EventListener;
import br.com.projetodevoa.ITCreativity.Events.model.Events;
/**
 * <h1> Interaction Region </h1>
 * <p>
 * A Classe InteractionRegion é responsável por cada área de interação. Portanto,
 * cada área desenhada em um componente será uma InteractionRegion. Esta classe
 * herda de Component.
 * </p>
 * 
 * @author Rodrigo Junior Utiyama
 * @version 1.0
 * @since 2014-11-30
 * 
 */
public abstract class InteractionRegion extends Component{

	/** Posição de clique na posição X da tela */
	protected int pivoX;

	/** Posição de clique na posição Y da tela */
	protected int pivoY;

	/** Profundidade de uma área */
	protected int pivoZ;
	
	/** Total de pixels mudados nesta área */
	protected int pixelTotalChange;
	
	/** Tempo mínimo de cada clique */
	protected long minTime = 1500;
	
	/** Tempo que cada clique foi feito em milesegundos */
	protected long time;

	/** Tamanho mínimo da área na posição X */
	private double minX;

	/** Tamanho máximo da área na posição X */
	private double maxX;

	/** Tamanho mínimo da área na posição Y */
	private double minY;
	
	/** Tamanho máximo da área na posição Y */
	private double maxY;
	
	/** Cor padrão da área */
	protected Color color = Color.RED;

	/** Atributo do tipo 'Devices' que receberá a referência do device instanciado 
	 * na classe InteractionMonitor.addInteractionRegion(..);
	 */
	private Devices device;
	
	/** Lista de EventListener(ouvidores) desta área */
	private List<EventListener> eventListenerList;
	
	/** Lista de eventos desta área */
	private List<Events> eventList;

	
	
	/**
	 * Retorna o tamanho mínimo do desenho na posição X do componente. 
	 * @return  minX
	 * 
	*/
	public abstract double getMinX();
	
	/**
	 * Retorna o tamanho mínimo do desenho na posição Y do componente. 
	 * @return  minY
	 * 
	*/
	public abstract double getMinY();
	
	/**
	 * Retorna o tamanho mínimo do desenho na posição X do componente. 
	 * @return  maxX
	 * 
	*/
	public abstract double getMaxX();
	
	/**
	 * Retorna o tamanho mínimo do desenho na posição X do componente. 
	 * @return  maxY
	 * 
	*/
	public abstract double getMaxY();
	
	/**
	 * Calcula o total de pixel desta área.
	 * @return getWidth()*getHeight()
	 */
	public int calculateArea(){
		return getWidth()*getHeight();
	}
	
	/**
	 * Adiciona a posição X de clique ao evento. Ou seja, se o evento for clicar em uma posição
	 * na tela, esta é a coordenada X
	 * @param pivoX  Coordenada X que será o clique
	 */
	public void addXClick(int pivoX) {
		this.pivoX = pivoX;
	}
 
	/**
	 * Adiciona a posição Y de clique ao evento. Ou seja, se o evento for clicar em uma posição
	 * na tela, esta é a coordenada Y
	 * @param pivoY  Coordenada Y que será o clique
	 */
	public void addYClick(int pivoY) {
		this.pivoY = pivoY;
	}
	
	/**
	 * Adiciona as posições de clique ao evento. Ou seja, se o evento for clicar em uma posição
	 * na tela, estas serão as coordenadas
	 * 
	 * @param pivoX  Coordenada X que será o clique
	 * @param pivoY  Coordenada Y que será o clique
	 * 
	 */
	public void addXYClick(int pivoX, int pivoY){
		this.pivoX = pivoX;
		this.pivoY = pivoY;
	}
	 
	/**
	 * Retorna a posição X do clique
	 * @return pivoX - Posição X do clique
	 */
	public int getXClick(){
		return this.pivoX;
	}
	
	/**
	 * Retorna a posição Y do clique
	 * @return pivoY - Posição Y do clique
	 */
	public int getYClick(){
		return this.pivoY;
	}
	
	/**
	 * Retorna a posição X em que a área está desenhada
	 * @return x - Posição X da área no componente
	 */
	public abstract int getX();
	
	/**
	 * Retorna a posição Y em que a área está desenhada
	 * @return y - Posição Y da área no componente
	 */
	public abstract int getY();
	
	
	/**
	 * Retorna a profundidade da área desenhada (somente para Shapes3D).
	 * Se a InteractionRegion não implementar Shapes3D, o retorno será 0.
	 * @return depth - Profundidade de uma área cadastrada. Caso for Shapes 2D, retorno irá ser 0.
	 */
	public int getPivoZ(){
		if(device instanceof DepthDevice){
			return  pivoZ;
		}
		return 0;
	}
	
	/**
	 * Retorna a cor da borda do desenho.
	 * @return color
	 */
	public Color getShapeColor(){
		return color;
	}
	
	/**
	 * Especifica a cor da borda da área desenhada.
	 * @param color  Cor da borda da área.
	 */
	public void setShapeColor(Color color){
		this.color = color;
	}
 
	/**
	 * Retorna a quantidade de pixels alterado quando esta área for interagida.
	 * @return pixelTotalChange - Total de pixels alterado
	 */
	public int getPixelTotalChange() {
		return pixelTotalChange;
	}

	/**
	 * Retorna o tempo mínimo para cada interação ocorrer.
	 * @return minTime
	 */
	
	public long getMinTime() {
		return minTime;
	}
	

	/**
	 * Especifica o tempo, em milisegundos, da interação realiza.
	 * @param time  Tempo atual da interação ocorrida.
	 */
	
	public void setTime(long time){
		this.time = time;
	}
	
	/**
	 * Retorna, em milisegundos, o tempo da última interação.
	 * @return time - Retorna o tempo em milisegundos cada interação
	 */
	
	public long getTime(){
		return time;
	}
	
	/**
	 * Especifica o total de pixels mudados.
	 * @param pixelTotalChange  Total de pixels alterados.
	 */
	
	public void setPixelTotalChange(int pixelTotalChange) {
		this.pixelTotalChange = pixelTotalChange;
	}

	/**
	 *  Define o atributo device indicando qual o tipo de dispositivo (DEPTH ou WEBCAM) 
	 */
	protected void setDevice(Devices device){
		this.device = device;
	}
	
	/** 
	 * Retorna o dispositivo instanciado. 
	 * 
	 * */
	protected Devices getDevice(){
		return device;
	}

	
	/*
	 * Método que verifica se o atributo 'eventListenerList' (ArrayList) está nulo ou vazio 
	 * */ 
	private boolean listsNotInitialized(){
		if((eventListenerList == null) || (eventListenerList.size() < 0) 
									   || eventList == null || eventList.size() < 0){
			return true;
		}
		return false;
	}

	/**
	 * Retorna todas as listas de eventos desta área.
	 * @return eventList - Lista de eventos desta área.
	 */
	public List getEvents(){ return eventList; }
	
	/**
	 * Adiciona um evento para esta área. Poderá ser cadastrado mais que um evento.
	 * @param eventlistener Tipo do Evento, é uma categoria para cada Events que irá disparar os eventos.
	 * @param event		 Um evento a ser cadastrado. Os eventos poderão ser desenvolvidos e extendidos.
	 * @exception NullPointerException - Caso algum dos parâmetros forem nulos.
	 */
	public void addEventListener(EventListener eventlistener, Events event) {
		
		if((eventlistener == null) || event == null) 
			throw new NullPointerException("Parâmetros nulos");
		
		
		if(listsNotInitialized()){
			eventListenerList = new ArrayList<EventListener>();
			eventList = new ArrayList<Events>();
		}
	
		try{
			eventList.add(event);
			eventListenerList.add(eventlistener);
		}catch(IndexOutOfBoundsException ex){
			System.out.println(ex);
		}catch(NullPointerException ex){
			System.out.println(ex);
		}
	}

	/**
	 * Remove um listener de um evento. 
	 * @param observer  EventListener a ser removido
	 */
	public void removeEventListener(EventListener observer) {
		if(!listsNotInitialized()){
			if(eventListenerList.contains(observer)){
				eventListenerList.remove(observer);
			}		
		}
	}

	/**
	 * Responsável por notificar todos os EventListener que esta área foi 'invadida'.
	 * Quando invadida, notifica todos os Listener dos eventos, que esta dispara seus eventos
	 * cadastrados.
	 */
	public void notifyAllEventListeners() {
		if(!listsNotInitialized()){
			for(EventListener eventListener : eventListenerList){	
				for(Events event : eventList){
					eventListener.fireEvent(event);
				}
			}
		}
	}
	
	/**
	 * Verifica se esta área está na posição especificada.
	 * @param point - Objeto do tipo Point que será a localização da área.
	 * @return true se esta área está localizada na posição especificada pelo objeto Point, 
	 * ou não caso esta área não está localizada na posição especificada pelo objeto Point.
	 */
	public abstract boolean containsShape(Point point);
	
	/**
	 * Verifica se esta área está na posição especificada.
	 * @param x  posição X a ser verificada.
	 * @param y  posição Y a ser verificada.
	 * @return true se esta área está localizada na posição especificada por X e Y, 
	 * ou não caso esta área não está localizada na posição especificada por X e Y.
	 */
	public abstract boolean containsShape(int x, int y);
	
	/**
	 * Retorna uma descrição da área
	 * @return retorna a descrição desta área
	 */
	public abstract String  getShapeDescription();
	
	/**
	 * Indica a cor da borda desta área.
	 * @param color  Cor da área a ser alterada.
	 */
	public abstract void    setColor(Color color);
}
