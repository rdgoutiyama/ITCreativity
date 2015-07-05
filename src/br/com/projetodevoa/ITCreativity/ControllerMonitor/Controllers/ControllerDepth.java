package br.com.projetodevoa.ITCreativity.ControllerMonitor.Controllers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.openni.VideoFrameRef;
import org.openni.VideoStream;

import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionMonitor;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.Model.InteractionRegion;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.InteractionMonitorState;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.MonitorOptions;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States.CalibrateState;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States.PauseDeviceState;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States.PauseMonitoringState;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States.StartDeviceState;
import br.com.projetodevoa.ITCreativity.ControllerMonitor.State.States.StartMonitoringState;
import br.com.projetodevoa.ITCreativity.Devices.control.DepthDevice;
import br.com.projetodevoa.ITCreativity.Devices.view.Devices;
import br.com.projetodevoa.ITCreativity.Shapes.Shape2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shape3D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawCircle2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawPolygon2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes2D.DrawRect2D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes3D.DrawCircle3D;
import br.com.projetodevoa.ITCreativity.Shapes.Shapes3D.DrawRect3D;

import com.primesense.nite.HandTracker;
import com.primesense.nite.HandTrackerFrameRef;


public class ControllerDepth extends InteractionMonitor implements  VideoStream.NewFrameListener{//, HandTracker.NewFrameListener{
	    
	   private float mHistogram[];
	   private int[] mImagePixels;
	   private VideoStream mVideoStream;
	   private VideoFrameRef mLastFrame;
	   private BufferedImage mBufferedImage;     
	   private HandTracker mTracker;
	   private HandTrackerFrameRef handFrameRef;
	
	   /* Atributos de controle das mãos*/
//	   private Point p = new Point();
//	   private Float handDepth = 0f;
//	   private Float handPosX = 0f;
//	   private Float handPosY = 0f;
//	   private String fileName;
	    
	   /* Atributos para manipular a imagem de rastreio da mão */
	   private BufferedImage image;
	    
	    /* Atributo que indicará se está monitorando ou não */
	   private boolean isMonitoring = false;
 
	   /* Atributos de resolução da stream */
	   public static int FRAMEWIDTH;
	   public static int FRAMEHEIGHT;
	   
	   /* Atributo para armazenar ao fim de cada frame, a media de profundidade de cada pixel */
	   private Double depthMapMedia[][];
	   private double depthMapSoma [][];
	   
	   private double depthMapSqrt [][];
	   private Double varianciaMap[][];
	   private int nullPixelMap [][];
	   
	   private int contAllFrames = 0;
	   private volatile Boolean  startCalibrate = false;
	   private int contCalibrationFrames;
	   private static final int NOISE_FRAME = 6;
	   private int depth;
	   private final static int FRAME_CALIBRATION_NUMBER = 10;
	   
	   
	   public VideoStream getVideoStream() {
		   return mVideoStream;
	   }
	   
	   public ControllerDepth(Devices device, InteractionMonitorState state) {
		   super.device = device;
		   setStream(((DepthDevice)device).getVideoStream());
		   
		   if(state == null)
			   state = new StartMonitoringState();
		   
		   super.state = state;
	   } 
	    
	   public ControllerDepth(Devices device) {
		   this(device, null);
 
	   } 
	    
	    public synchronized void startCalibration(){
	    	/* Instancia um vetor para mapeamento da profundidade de cada pixel */
	    	depthMapMedia 	= new Double[FRAMEWIDTH][FRAMEHEIGHT];
	    	depthMapSoma  	= new double[FRAMEWIDTH][FRAMEHEIGHT];
	    	depthMapSqrt	= new double[FRAMEWIDTH][FRAMEHEIGHT];
	    	nullPixelMap	= new int[FRAMEWIDTH][FRAMEHEIGHT];
	    	varianciaMap    = new Double[FRAMEWIDTH][FRAMEHEIGHT];
	    	
	    	startCalibrate = true;
	    }

	    public void stopCalibration(){
	    	startCalibrate = false;
	    }
	    
	    public boolean getCalibrationStatus(){
	    	return startCalibrate;
	    }
	    
	    public boolean startMonitoring(){
	 	    isMonitoring = true;
		   
	 	    return isMonitoring;
	    }
	    
	    public boolean stopMonitoring(){
	    	isMonitoring=false;
	    	startCalibrate = false;
	    	contAllFrames = 0;
	    	
	    	return isMonitoring;
	    }
	    
	    public void setStream(VideoStream videoStream) {
	    	
	        if (mLastFrame != null) {
	            mLastFrame.release();
	            mLastFrame = null;
	        }
	        
	        if (mVideoStream != null) {
	            mVideoStream.removeNewFrameListener(this);
	        }
	        
	        mVideoStream = videoStream;
	        
	        if(videoStream != null){
        	   /* Indica  a resolução do Frame */
		        FRAMEWIDTH = mVideoStream.getVideoMode().getResolutionX();
		    	FRAMEHEIGHT = mVideoStream.getVideoMode().getResolutionY();
	        }

	    	if (mVideoStream != null) {
	            mVideoStream.addNewFrameListener(this);
	        }
	    }

 
	    public synchronized void paint(Graphics g) {
	        if (mLastFrame == null) {
	            return;
	        }
	        
	        int width = mLastFrame.getWidth();
	        int height = mLastFrame.getHeight();

	        if (mBufferedImage == null || mBufferedImage.getWidth() != width || mBufferedImage.getHeight() != height) {
	            mBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
	        }
	        
	        mBufferedImage.setRGB(0, 0, width, height, mImagePixels, 0, width);
	        
	        int framePosX = (getWidth() - width) / 2;
	        int framePosY = (getHeight() - height) / 2;
	        g.drawImage(mBufferedImage, framePosX, framePosY, null);
	        
	        /* Implementar solução para novas formas */
	        for(InteractionRegion s : getInteractionRegions()){
	        	if(s instanceof Shape2D)
	        	{ 
	        		Color c = s.getShapeColor();
	            	if(s instanceof DrawRect2D)
	            	{
		        		DrawRect2D rect = (DrawRect2D)s;
		        		g.setColor(c);
		        		g.drawRect((int)rect.getRectangle2D().getX(), (int)rect.getRectangle2D().getY(), (int)rect.getRectangle2D().getWidth(), (int)rect.getRectangle2D().getHeight());
		        	}else if(s instanceof DrawCircle2D)
		        	{
			        		DrawCircle2D circle = (DrawCircle2D)s;
			        		g.setColor(c);
			        		g.drawOval((int)circle.getCircle().getX(), (int)circle.getCircle().getY(), (int)circle.getCircle().getWidth(), (int)circle.getCircle().getHeight());
		        	}else if(s instanceof DrawPolygon2D)
        			{
		        		DrawPolygon2D polygon = (DrawPolygon2D)s;
	        			
	        			for(int x = 0; x < polygon.getXArchs().size(); x++)
	        			{
	        				int archX = polygon.getXArch(x);
	        				int archY = polygon.getYArch(x);
	        							        				
	        				g.drawImage(this.image, archX, archY, this);
	        				
	        				g.setColor(c);
        					g.drawPolygon(polygon.getPolygon());
	        			}
	        		}
	        	}else if(s instanceof Shape3D) //Se é instancia de uma forma em 3D
        		{
	        		if(s instanceof DrawRect3D)
        			{
        				DrawRect3D rect3D = (DrawRect3D)s;
        				g.setColor(rect3D.getShapeColor());
        				g.drawRect(rect3D.getXClick(), rect3D.getYClick(), rect3D.getWidth(), rect3D.getHeight());
        				
        			}else if(s instanceof DrawCircle3D)
        			{
    					DrawCircle3D circle3D = (DrawCircle3D)s;
        				g.setColor(circle3D.getShapeColor());
        				g.drawOval(circle3D.getXClick(), circle3D.getYClick(), circle3D.getWidth(), circle3D.getHeight());
        				
        			}
        		}
	        }
	    }

	    
	    @Override
	    public synchronized void onFrameReady(VideoStream stream) {
	        if (mLastFrame != null) {
	            mLastFrame.release();
	            mLastFrame = null;
	        }
	        
	        mLastFrame = mVideoStream.readFrame();
	        
         
	        ByteBuffer frameData = mLastFrame.getData().order(ByteOrder.LITTLE_ENDIAN);

	        if (mImagePixels == null || mImagePixels.length < mLastFrame.getWidth() * mLastFrame.getHeight()) {
	            mImagePixels = new int[mLastFrame.getWidth() * mLastFrame.getHeight()];
	        }
   
	        switch (mLastFrame.getVideoMode().getPixelFormat())
	        {
	            case DEPTH_1_MM:
	            case DEPTH_100_UM:
	            case SHIFT_9_2:
	            case SHIFT_9_3:
	                
	            	/* CALIBRAÇÃO */
					if (startCalibrate) {
		                int pixelX = 0;
		                int pixelY = 0;
						int pos = 0; 
						isMonitoring = false;
						
						frameData.rewind();
						
						while (frameData.remaining() > 0) {
							int depth = (int) frameData.getShort() & 0xFFFF;
							short pixel = (short) mHistogram[depth];
							mImagePixels[pos] = 0xFF000000 | (pixel << 16) | (pixel << 8);

							depthMapSoma[pixelX][pixelY] = depthMapSoma[pixelX][pixelY] + depth;
							depthMapSqrt[pixelX][pixelY] = depthMapSqrt[pixelX][pixelY] + Math.pow(depth, 2);
		
							if (depth == 0)
								nullPixelMap[pixelX][pixelY] = nullPixelMap[pixelX][pixelY] + 1;
							
							pixelX++;
							if(pixelX == FRAMEWIDTH){
		                 		pixelY++;
		                 		pixelX = 0;
		                 	}
							
							++pos;
						}
						contCalibrationFrames++;
						
						if(contCalibrationFrames == FRAME_CALIBRATION_NUMBER){
							int frames; 
		                	for(int x = 0; x < FRAMEWIDTH; x++){
		                		for(int y = 0; y < FRAMEHEIGHT; y++){
		                			frames 	= (FRAME_CALIBRATION_NUMBER-nullPixelMap[x][y]); 
		                			
		                     		depthMapMedia[x][y] = (depthMapSoma[x][y]/frames);
		                     		varianciaMap[x][y]  = ((depthMapSqrt[x][y]/frames) - Math.pow(depthMapMedia[x][y],2));
		                     		
		                     		if(depthMapMedia[x][y].isNaN()){
		                     			depthMapMedia[x][y] = -1.0;
		                     		}
		                     		
		                     		if(varianciaMap[x][y].isNaN()){
		                     			varianciaMap[x][y] = 0d;
		                     		}
		                     		
			                		depthMapSoma[x][y] = 0;
			                		nullPixelMap[x][y] = 0;
			                		this.stopCalibration();
		                		}
	                		}
						}
					
					/* MONITORAMENTO */
					}else if(isMonitoring){  
						startCalibrate = false;
						int pos = 0;
						int pixelX = 0;
						int pixelY = 0;
						int depth;
						List<InteractionRegion> irTemp = new CopyOnWriteArrayList<InteractionRegion>(getInteractionRegions());
						
						while(frameData.remaining() > 0) {  
		                	depth = (int)frameData.getShort() & 0xFFFF;    
		                	short pixel = (short)mHistogram[depth];
		                 	mImagePixels[pos] = 0xFF000000 | (pixel << 16) | (pixel << 8);
		                 	
		                 	for(InteractionRegion ir : irTemp){
		                 		if(depth > 0 && ir != null && ir.calculateArea() > 0 && ir.containsShape(pixelX, pixelY) && 
		                 				(depth > (depthMapMedia[pixelX][pixelY].intValue()-NOISE_FRAME)-15) && 
		                 				(depth < (depthMapMedia[pixelX][pixelY].intValue()-NOISE_FRAME))){
		                 			ir.setPixelTotalChange(ir.getPixelTotalChange()+1);
		                 		} 
		                 	}
	 	                 
		           
		                 	pixelX++;
		                 	if(pixelX == FRAMEWIDTH){
		                 		pixelY++;
		                 		pixelX = 0;
		                 	}
		                 	++pos;
						}
						
						for(InteractionRegion ir : getInteractionRegions()){
							if(ir.calculateArea() > 0){
								int porcentagem = (ir.getPixelTotalChange()*100) / ir.calculateArea();
								long bet         = System.currentTimeMillis() - ir.getTime();

								if(porcentagem >= 50 && bet > ir.getMinTime()){
									ir.setTime(System.currentTimeMillis());
									ir.notifyAllEventListeners();
								}
							}
							ir.setPixelTotalChange(0);
						}
						
					}else{
						calcHist(frameData);
						frameData.rewind();
						int pos = 0;
						
						while (frameData.remaining() > 0) {
							int depth = (int) frameData.getShort() & 0xFFFF;
							short pixel = (short) mHistogram[depth];
							mImagePixels[pos] = 0xFF000000 | (pixel << 16) | (pixel << 8);
							
							++pos;
						}
					}
					 
					contAllFrames++;
                break;
	                
	            case RGB888:

					frameData.rewind();
	                int pos = 0;
	                while (frameData.remaining() > 0) {
	                    int red = (int)frameData.get() & 0xFF;
	                    int green = (int)frameData.get() & 0xFF;
	                    int blue = (int)frameData.get() & 0xFF;
	                    mImagePixels[pos] = 0xFF000000 | (red << 16) | (green << 8) | blue;
	                    pos++;
	                }
	                break;
	            default:
	                mLastFrame.release();
	                mLastFrame = null;
	        }
	         
	        repaint();
	    }   
	    
	
	    public int getDepth(){ return depth;}
	    public Double[][] getVariancia(){ return varianciaMap; }
	    
	    private void calcHist(ByteBuffer depthBuffer) {
	        if (mHistogram == null || mHistogram.length < mVideoStream.getMaxPixelValue()) {
	            mHistogram = new float[mVideoStream.getMaxPixelValue()];
	        }
	        
	        for (int i = 0; i < mHistogram.length; ++i)
	            mHistogram[i] = 0;

	        int points = 0;
	        while (depthBuffer.remaining() > 0) {
	            int depth = depthBuffer.getShort() & 0xFFFF;
	            if (depth != 0) {
	                mHistogram[depth]++;
	                points++;
	            }
	            
	      }

	        for (int i = 1; i < mHistogram.length; i++) {
	            mHistogram[i] += mHistogram[i - 1];
	        }

	        if (points > 0) {
	            for (int i = 1; i < mHistogram.length; i++) {
	                mHistogram[i] = (int) (256 * (1.0f - (mHistogram[i] / (float) points)));
	            }
	        }
	       
	    }

	    
		@Override
		public Component getComponent() {
			return this;
		}
	
		public Double[][] getDepthMapMedia(){
			return depthMapMedia;
		}
 
		@Override
		public InteractionMonitorState getState(){
			return state;
		}
		
	
		/* Calculo distancia euclidiana */
		public static boolean match(int c1, int c2){
//			int r1 = (c1 >> 16) & 0xFF;
//			int r2 = (c2 >> 16) & 0xFF;
//			
//			int g1 = (c1 >> 8) & 0xFF;
//			int g2 = (c2 >> 8) & 0xFF;
//			
//			int b1 = c1 & 0xFF;	
//			int b2 = c2 & 0xFF;	
//			int alpha = (c1 >> 24) & 0xFF;
			
			
			double deuc=Math.sqrt(Math.pow(((c1&0x00FF0000)>>16) - ((c2&0x00FF0000)>>16),2)+
								  Math.pow(((c1&0x0000FF00)>>8) -  ((c2&0x0000FF00)>>8), 2) +
								  Math.pow((c1&0x000000FF)-(c2&0x000000FF),2));
			
			return deuc<(255*0.1);
		}
		
		
//	    public Float getHandDepth() {
//			return handDepth;
//		}
//
//		public Float getHandPosX() {
//			return handPosX;
//		}
//
//		public Float getHandPosY() {
//			return handPosY;
//		}

//		public void setHandTracker(HandTracker tracker){
//	    	mTracker = tracker;
//	    	mTracker.addNewFrameListener(this);
//	    }
		
//		@Override
//		public void onNewFrame(HandTracker arg0) {
//			try{
//		     	for(HandData hand : handFrameRef.getHands()){
//	        		if(hand.isTracking()){
//	        			Point2D<Float> pos = mTracker.convertHandCoordinatesToDepth(hand.getPosition());
//	        			this.handDepth = hand.getPosition().getZ();
//	        			this.handPosX = pos.getX();
//	        			this.handPosY = pos.getY()-60;
//	        		}
//	        	} 
//	        }catch(Exception ex){}
//	        
//			try{
//				handFrameRef = mTracker.readFrame();
//				
//				for(GestureData gesture : handFrameRef.getGestures()){
//					if(gesture.isComplete()){
//						mTracker.startHandTracking(gesture.getCurrentPosition());
//					}
//				}
//				
//				
//			}catch(Exception ex){}	
//		}
}
 
