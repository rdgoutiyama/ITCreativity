package br.com.projetodevoa.ITCreativity.Shapes.Shapes3D;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import br.com.projetodevoa.ITCreativity.Shapes.Shape3D;

public class DrawCircle3D extends Shape3D {

	//Atributo que receberá o valor da largura do retângulo. Default: 1px
	private int width = 1;

	//Atributo que receberá o valor da altura do retângulo. Default: 1px
	private int height = 1;
	 
	private Ellipse2D circle;

	private int pivoX;
	private int pivoY;
	
	public DrawCircle3D(int x, int y, int width, int height, int depth){
		this.width = width;
		this.height = height;
		this.pivoX = x;
		this.pivoY = y;
		super.setShapeDepth(depth);
		circle = new Ellipse2D.Double(pivoX, pivoY, this.width, this.height);
	}
	
	public DrawCircle3D(int pivoX, int pivoY, int depth){
		this(pivoX, pivoY, 1,1, depth);
	}

	
	public Ellipse2D getCircle() {
		return circle;
	}

	public void setCircle(Ellipse2D circle) {
		this.circle = circle;
	}
	
	@Override
	public int getWidth() {
		return (int)circle.getWidth();
	}

	@Override
	public int getHeight() {
		return (int)circle.getHeight();
	}

	public void setHeight(int height){
		this.height = height;
		circle.setFrame(pivoX, pivoX, this.height, width);
	}
	
	public void setWidth(int width){
		this.width = width;
		circle.setFrame(pivoX, pivoX, height, this.width);
	}
	
	public void setFrame(int pivoX, int pivoY, int height, int width){
		this.width = width;
		this.height = height;
		this.pivoX = pivoX;
		this.pivoY = pivoY;
		circle.setFrame(pivoX, pivoX, height, width);
	}
	public void setFrame(int height, int width){
		this.width = width;
		circle.setFrame(pivoX, pivoX, height, width);
	}

	@Override
	public boolean containsShape(Point p) {
		if(circle.contains(p)) return true;
		return false;
	}
	
	@Override
	public boolean containsShape(int x, int y) {
		if(circle.contains(x,y)) return true;
		return false;
	}

	@Override
	public String getShapeDescription() {
		return circle.toString();
	}

	@Override
	public double getMinX() {
		return circle.getMinX();
	}

	@Override
	public double getMinY() {
		return circle.getMinY();
	}

	@Override
	public double getMaxX() {
		return circle.getMaxX();
	}

	@Override
	public double getMaxY() {
		return circle.getMaxY();
	}

	@Override
	public int getX() {
		return this.pivoX;
	}

	@Override
	public int getY() {
		return this.pivoY;
	}

	@Override
	public void setColor(Color color) {
		super.color = color;		
	}
}
