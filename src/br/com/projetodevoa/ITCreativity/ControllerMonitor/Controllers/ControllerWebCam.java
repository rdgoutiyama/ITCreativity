package br.com.projetodevoa.ITCreativity.ControllerMonitor.Controllers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionRegion;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.InteractionMonitorState;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.MonitorOptions;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States.CalibrateState;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States.PauseDeviceState;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States.PauseMonitoringState;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States.StartDeviceState;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States.StartMonitoringState;
import br.com.projetodevoa.ITCreativity.Devices.control.WebcamDevice;
import br.com.projetodevoa.ITCreativity.Devices.view.Devices;
import br.com.projetodevoa.ITCreativity.Shapes.Shape2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shape3D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawCircle2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawPolygon2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawRect2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes3D.DrawCircle3D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes3D.DrawRect3D;

public class ControllerWebCam extends InteractionMonitor{

	public Image myimg;
	public Color ref;
	private BufferedImage img, imgAnt, imgRes;
	private boolean gravando = false;
	private boolean view = false;
	private JPanel painel;
	private WebcamDevice device;
	
	/* Controla o estado atual da camera */
	private List<InteractionRegion> allInteractionRegions;

	public boolean stop(){
		device.player.stop();
		return true;
	}

	public boolean start(){
		device.player.start();
		return true;
	}
	public boolean pauseMonitoring(){
		return (gravando = false);
	}
	
	public boolean startMonitoring(){
		return (gravando = true);
	}
	public ControllerWebCam(Devices device, InteractionMonitorState state) {
		this.device = (WebcamDevice) device;
		this.device.setController(this);

		if (device != null) {
			allInteractionRegions = getInteractionRegions();
		}
		
		if(state == null)
			state = new StartMonitoringState();
	
		super.state = state;
		setBounds(0, 0, this.device.frameHeight(), this.device.frameWidth());
	}
	
	public ControllerWebCam(Devices device) {
		this(device, null);
	}

	public synchronized void setImage(Image img) {
		this.myimg = img;
		repaint();
	}
 
 	public void paintComponent(Graphics g) {
		if (myimg != null) {

			if (img == null) {
				img = new BufferedImage(myimg.getWidth(this),
						myimg.getHeight(this), BufferedImage.TYPE_INT_RGB);
				imgAnt = new BufferedImage(myimg.getWidth(this),
						myimg.getHeight(this), BufferedImage.TYPE_INT_RGB);
				imgRes = new BufferedImage(myimg.getWidth(this),
						myimg.getHeight(this), BufferedImage.TYPE_INT_RGB);
			}

			Graphics2D gg = img.createGraphics();
			Graphics2D ggAnt = imgAnt.createGraphics();
			Graphics2D ggRes = imgRes.createGraphics();
			Graphics2D ggPanel = (Graphics2D) g;

			ggAnt.drawImage(img, 0, 0, this);
			gg.drawImage(myimg, 0, 0, this);
			if (gravando) {
				ggRes.setPaint(Color.BLACK);

				ggRes.fill(new Rectangle2D.Double(0, 0, myimg.getWidth(this),
						myimg.getHeight(this)));

				for (InteractionRegion interactionRegion : allInteractionRegions) {
					int count = 0;
					int x0 = (int) interactionRegion.getX();
					int xf = (int) interactionRegion.getWidth() + x0;
					int y0 = (int) interactionRegion.getY();
					int yf = (int) interactionRegion.getHeight() + y0;

					for (int y = y0; y < yf; y++) {
						for (int x = x0; x < xf; x++) {
							if (!match(img.getRGB(x, y), imgAnt.getRGB(x, y))) {
								count++;
							} 
						}
					}

					long bet = System.currentTimeMillis() - interactionRegion.getTime();

					if (bet > interactionRegion.getMinTime() && count > interactionRegion.calculateArea()/2) {
						interactionRegion.notifyAllEventListeners();
						interactionRegion.setTime(System.currentTimeMillis());
					}

				}// fim for
			}// fim if

			// Desenha Imagem de Visão
			if (view) {
				ggPanel.drawImage(imgRes, 0, 0, this);
			} else {
				ggPanel.drawImage(img, 0, 0, this);
			}

			// Desenha retangulos de seleção
			ggPanel.setPaint(Color.GREEN);
		}
		
		for (InteractionRegion s : allInteractionRegions) {
			if (s instanceof Shape2D) {
				Color c = s.getShapeColor();
				
				if (s instanceof DrawRect2D) {
					DrawRect2D rect = (DrawRect2D) s;
					g.setColor(c);
					g.drawRect((int) rect.getRectangle2D().getX(),
							(int) rect.getRectangle2D().getY(), (int) rect
									.getRectangle2D().getWidth(),
							(int) rect.getRectangle2D().getHeight());
					
				} else if (s instanceof DrawCircle2D) {
					DrawCircle2D circle = (DrawCircle2D) s;
					g.setColor(c);
					g.drawOval((int) circle.getCircle().getX(),
							(int) circle.getCircle().getY(), (int) circle
									.getCircle().getWidth(), (int) circle
									.getCircle().getHeight());
				} else if (s instanceof DrawPolygon2D) {
					DrawPolygon2D polygon = (DrawPolygon2D) s;

					for (int x = 0; x < polygon.getXArchs().size(); x++) {
						int archX = polygon.getXArch(x);
						int archY = polygon.getYArch(x);

						g.setColor(c);
						g.drawPolygon(polygon.getPolygon());
					}
				}
			} else if (s instanceof Shape3D) // Se é instancia de uma forma
												// em 3D
			{

				if (s instanceof DrawRect3D) {
					DrawRect3D rect3D = (DrawRect3D) s;
					g.setColor(rect3D.getShapeColor());
					g.drawRect(rect3D.getXClick(), rect3D.getYClick(),
							rect3D.getWidth(), rect3D.getHeight());

				} else if (s instanceof DrawCircle3D) {
					DrawCircle3D circle3D = (DrawCircle3D) s;
					g.setColor(circle3D.getShapeColor());
					g.drawOval(circle3D.getXClick(), circle3D.getYClick(),
							circle3D.getWidth(), circle3D.getHeight());

				}
			}
		}
	}

	public static boolean match(int c1, int c2) {

		double deuc = Math.sqrt(Math.pow(((c1 & 0x00FF0000) >> 16) - ((c2 & 0x00FF0000) >> 16), 2) +
				 	  Math.pow(((c1 & 0x0000FF00) >> 8) - ((c2 & 0x0000FF00) >> 8), 2) + 
				 	  Math.pow((c1 & 0x000000FF) - (c2 & 0x000000FF), 2));

		return deuc < (255 * 0.30);
	}

	public static int gray(int c1) {
		int red = (c1 & 0x00FF0000) >> 16;
		int green = (c1 & 0x0000FF00) >> 8;
		int blue = (c1 & 0x000000FF);
		return (int) (red * 0.3 + green * .59 + blue * 0.11);

	}


	@Override
	public Component getComponent() {
		
		return this;
	}

}
