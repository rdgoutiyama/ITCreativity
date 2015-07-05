package br.com.projetodevoa.ITCreativity.Shapes.Shapes3D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import br.com.projetodevoa.ITCreativity.Shapes.Shape3D;

public class DrawPolygon3D extends Shape3D {
	private List<Integer> xArchs = new ArrayList<Integer>();
	private List<Integer> yArchs = new ArrayList<Integer>();
	private Polygon polygon; 
	private int[][] polygonPixels;
	
	public DrawPolygon3D(List<Integer> xArchs, List<Integer> yArchs){

		/* Primeira verificação: Se o foi passado apenas as coordenadas */
		if(xArchs == null && yArchs == null){
			throw new NullPointerException("Parâmetros nulos!");
		}
		
		/* Atribui as referencias, pois as coordenadas não são nulas */
		this.xArchs = xArchs;
		this.yArchs = yArchs;
		
		/* Cria um array temporário para armazenar as coordenas, 
		 * pois o objeto polygon apenas aceita arrays*/
		int[] xtemp = new int[this.xArchs.size()];
		int[] ytemp = new int[this.yArchs.size()];
			
		/* Faz a atribuição dos valores para o array */
		for(int x = 0; x < xArchs.size(); x++){
			xtemp[x] = xArchs.get(x);
			ytemp[x] = yArchs.get(x);
		}
		
		/* Cria um novo objeto do tipo Polygon com as informações */
		this.polygon = new Polygon(xtemp,ytemp,xtemp.length);		
	}
	 
	public DrawPolygon3D(int x, int y){
		this(new ArrayList<Integer>(Arrays.asList(x)), new ArrayList<Integer>(Arrays.asList(y)));
	}
 
	/* get archs X Vector */
	public List<Integer> getXArchs() {
		return xArchs;
	}

	/* Set archs X Vector */
	public void setXArchs(Vector<Integer> xArchs) {
		this.xArchs = xArchs;
	}

	/* get archs Y Vector */
	public List<Integer> getYArchs() {
		return yArchs;
	}

	/* set archs Y Vector */
	public void setYArchs(Vector<Integer> yArchs) {
		this.yArchs = yArchs;
	}


	/* get arch X in position "pos"*/
	public Integer getXArch(int pos){
		return xArchs.get(pos);
	}
	 
	/* set element arch in position "pos" and */
	public void addXArch(int arch, int pos){
		xArchs.set(pos, arch);
	}
	

	/* get arch Y in position pos */
	public Integer getYArch(int pos){
		return yArchs.get(pos);
	}
	

	/* Set arch Y element in position pos */
	public void addYArch(int arch, int pos){
		xArchs.set(pos, arch);
	}
	

	public void addXYArch(int archX, int archY){
		xArchs.add(archX);
		yArchs.add(archY);
		
		polygon.addPoint(archX, archY);
	}
	
	public Polygon getPolygon() {
		return polygon;
	}

	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}

	@Override
	public boolean containsShape(Point p) {
		if(polygon.contains(p)) return true;	
		return false;
	}
	
	@Override
	public boolean containsShape(int x, int y) {
		if(polygon.contains(x,y)) return true;
		return false;
	}

	@Override
	public String getShapeDescription() {
		return polygon.toString();
	}
	
	@Override
	public double getMinX() {
		return polygon.getBounds2D().getMinX();
	}

	@Override
	public double getMinY() {
		return polygon.getBounds2D().getMinY();
	}

	@Override
	public double getMaxX() {
		// TODO Auto-generated method stub
		return polygon.getBounds2D().getMaxX();
	}

	@Override
	public double getMaxY() {
		// TODO Auto-generated method stub
		return polygon.getBounds2D().getMaxY();
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setColor(Color color) {
		super.color = color;		
	}
}
