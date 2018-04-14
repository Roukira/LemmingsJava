import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class InputGameUI extends Input{

	private BufferedImage border;
	private BufferedImage whiteBorder;
	private BufferedImage redBorder;
	
	public static final int REGULArBORDER = 0;
	public static final int SELECtBORDER = 1;
	
	private int capacityClicSetter = 0;
	
	public InputGameUI(Window w){
		super(w);
		try{
			whiteBorder = ImageIO.read(new File("world/capacityBorder.png"));
			redBorder = ImageIO.read(new File("world/capacitySelectBorder.png"));
			
		}catch(Exception e){e.printStackTrace();}
		
		w.getCanvasCapacity().addMouseListener(this);
		w.getCanvasCapacity().addMouseMotionListener(this);
	}
	
	
	
	public void update(){
		
	}
	
	public void draw(Graphics2D g){
		drawSelectZone(g);
	}
	
	public void drawSelectZone(Graphics2D g){
	//=======partie select blanche======
		World world = w.getCurrentWorld();
		if ( posXmouse > world.getPosXcapacity1() && posXmouse < world.getPosXcapacity1()+60
		&& posYmouse > world.getPosYcapacity() && posYmouse < world.getPosYcapacity()+60){
		//remplacer 60 par un truc propre
			drawCapacityBorder(g,REGULArBORDER, world.getPosXcapacity1()-1, world.getPosYcapacity()-1);
		}
		else if ( posXmouse > world.getPosXcapacity2() && posXmouse < world.getPosXcapacity2()+60
		&& posYmouse > world.getPosYcapacity() && posYmouse < world.getPosYcapacity()+60){
			drawCapacityBorder(g,REGULArBORDER, world.getPosXcapacity2()-1, world.getPosYcapacity()-1);
		}
		else if ( posXmouse > world.getPosXcapacity3() && posXmouse < world.getPosXcapacity3()+60
		&& posYmouse > world.getPosYcapacity() && posYmouse < world.getPosYcapacity()+60){
			drawCapacityBorder(g,REGULArBORDER, world.getPosXcapacity3()-1, world.getPosYcapacity()-1);
		}
		else if ( posXmouse > world.getPosXcapacity4() && posXmouse < world.getPosXcapacity4()+60
		&& posYmouse > world.getPosYcapacity() && posYmouse < world.getPosYcapacity()+60){
			drawCapacityBorder(g,REGULArBORDER, world.getPosXcapacity4()-1, world.getPosYcapacity()-1);
		}
		
	//=======partie select rouge======	
		
		if (capacityClicSetter == 1){
        		drawCapacityBorder(g,SELECtBORDER, world.getPosXcapacity1()-1, world.getPosYcapacity()-1);
    		}else if (capacityClicSetter == 2){
        		drawCapacityBorder(g,SELECtBORDER, world.getPosXcapacity2()-1, world.getPosYcapacity()-1);
    		
    		}else if (capacityClicSetter == 3){
        		drawCapacityBorder(g,SELECtBORDER, world.getPosXcapacity3()-1, world.getPosYcapacity()-1);
    		}else if (capacityClicSetter == 4){
        		drawCapacityBorder(g,SELECtBORDER, world.getPosXcapacity4()-1, world.getPosYcapacity()-1);
    		}
	}
	
	public void drawCapacityBorder(Graphics2D g,int borderType, int posX, int posY){
		if ( borderType == REGULArBORDER ){
			border = whiteBorder;
		}else{
			border = redBorder;
		}
		g.drawImage(border,posX,10,null);
	}
	
	
	public boolean fastForwardPressed(World world, int posXclic, int posYclic){
		if (posXclic >=world.getWidth()-40 && posXclic <=world.getWidth()-10 && posYclic>=20 && posYclic<=50){
			if (w.FPS == 60){
				w.FPS = 120;
				w.ns = 1000000000/w.FPS;
			}
			else{
				w.FPS = 60;
				w.ns = 1000000000/w.FPS;
			}
			System.out.println("Changed speed to " + w.FPS);
			return true;
		}
		return false;
	}
	
	// Setters
	
	public void setCapacityClicSetter(int clicNumber){
		capacityClicSetter = clicNumber;
	}
	
	public int getCapacityClicSetter(){
		return capacityClicSetter;
	}
	
	//===================MOUSE EVENT========================================================
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.

		int posXclic = e.getX();
		int posYclic = e.getY();
		
		World world = w.getCurrentWorld();
		
		if(world == null) return;
		
		if (resetMapPressed(world, posXclic, posYclic)) return;
		
		if (fastForwardPressed(world, posXclic, posYclic)) return;
		
		if ( posXclic > world.getPosXcapacity1() && posXclic < world.getPosXcapacity1()+60
		&& posYclic > world.getPosYcapacity() && posYclic < world.getPosYcapacity()+60){
		//remplacer 60 par un truc propre			
			capacityClicSetter = 1;
			return;
		}
		
		if ( posXclic > world.getPosXcapacity2() && posXclic < world.getPosXcapacity2()+60
		&& posYclic > world.getPosYcapacity() && posYclic < world.getPosYcapacity()+60){
			capacityClicSetter = 2;
			return;
		}
		
		if ( posXclic > world.getPosXcapacity3() && posXclic < world.getPosXcapacity3()+60
		&& posYclic > world.getPosYcapacity() && posYclic < world.getPosYcapacity()+60){
			capacityClicSetter = 3;
			return;
		}
		if ( posXclic > world.getPosXcapacity4() && posXclic < world.getPosXcapacity4()+60
		&& posYclic > world.getPosYcapacity() && posYclic < world.getPosYcapacity()+60){
			capacityClicSetter = 4;
			return;
		}
	}
	

}
