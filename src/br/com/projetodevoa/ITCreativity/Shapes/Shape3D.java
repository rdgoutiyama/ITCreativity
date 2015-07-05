package br.com.projetodevoa.ITCreativity.Shapes;
 
import java.awt.Color;
import java.awt.Point;

public abstract class Shape3D extends Shapes {

	/* Além do Pivo Z de cada forma, os shapes3D terão outra posição Z que será
	 * a profundidade DA FORMA e não a média da área (PivoZ)
	 */
	private int shapeZ;
	protected Color color = Color.RED;
	
	 
	
	public int getShapeDepth(){
		return shapeZ;
	}
	
	public void setShapeDepth(int shapeZ){
		this.shapeZ = shapeZ;
	}
	
	public abstract boolean containsShape(Point p);
	
//	public void draw() {
//		//Super = Shapes
//		Devices device = super.getDevice();
//		if(device instanceof DepthDevice){
//			DepthDevice d = (DepthDevice)device;
//
//			if(!(d.getDepthController().getShapes().contains(this))){
//				d.getDepthController().getShapes().add(this);				
//			}
//			 
//			//System.out.println(d.getDepthController().getShapes().size());
//		}else
//			System.out.println("IS CAMERA DEVICE");
//	}
 

}
