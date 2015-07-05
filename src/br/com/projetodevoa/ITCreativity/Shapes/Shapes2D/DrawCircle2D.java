package br.com.projetodevoa.ITCreativity.Shapes.Shapes2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import br.com.projetodevoa.ITCreativity.Shapes.Shape2D;

public class DrawCircle2D extends Shape2D {

	//Atributo que receberá o valor da largura do retângulo. Default: 1px
	private int width = 1;

	//Atributo que receberá o valor da altura do retângulo. Default: 1px
	private int height = 1;
	 
	private int pivoX;
	private int pivoY;
	private Ellipse2D circle;

	public DrawCircle2D(Ellipse2D circle){
		this.circle = circle;
		this.width  = (int) circle.getWidth();
		this.height = (int) circle.getHeight();
		this.pivoX = (int) circle.getX();
		this.pivoY = (int) circle.getY();
	}
	
	public DrawCircle2D(int x, int y, int width, int height){
		this.width = width;
		this.height = height;
		this.pivoX = x;
		this.pivoY = y;
	 
		circle = new Ellipse2D.Double(pivoX, pivoY, this.width, this.height);
	}
	
	public DrawCircle2D(int pivoX, int pivoY){
		this(pivoX, pivoY, 1,1);
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
	}
	
	public void setWidth(int width){
		this.width = width;
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
	
	public Ellipse2D getCircle() {
		return circle;
	}

	public void setCircle(Ellipse2D circle) {
		this.circle = circle;
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
 
