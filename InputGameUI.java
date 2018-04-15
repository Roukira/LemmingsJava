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
	
	private BufferedImage lemmingsPanelImage;
	
	private BufferedImage fastForwardButton;
	private BufferedImage fastForwardButtonDefault;
	private BufferedImage fastForwardButtonHover;
	
	private BufferedImage resetMapButtonDefault;
	private BufferedImage resetMapButtonHover;
	private BufferedImage resetMapButton;
	
	private BufferedImage imageCapacity;
	private BufferedImage imageCapacityBorder;
	private BufferedImage imageCapacitySelectBorder;
	private BufferedImage imageBasherCapacity;
	private BufferedImage imageBuilderCapacity;
	private BufferedImage imageBombCapacity;
	private BufferedImage imageStopperCapacity;
	
	public static final int posXcapacity1 = 0;
	public static final int posXcapacity2 = 70;
	public static final int posXcapacity3 = 140;
	public static final int posXcapacity4 = 210;
	public static final int posYcapacity = 10;
	
	public static final int REGULArBORDER = 0;
	public static final int SELECtBORDER = 1;
	
	private int capacityClicSetter = 0;
	
	public InputGameUI(Window w){
		super(w);
		try{
			whiteBorder = ImageIO.read(new File("world/capacityBorder.png"));
			redBorder = ImageIO.read(new File("world/capacitySelectBorder.png"));
			
			lemmingsPanelImage = ImageIO.read(new File("world/lemmingspanel.png"));
			
			imageCapacityBorder = ImageIO.read(new File("world/capacityBorder.png"));
			imageCapacitySelectBorder = ImageIO.read(new File("world/capacitySelectBorder.png"));
			imageBasherCapacity = ImageIO.read(new File("lemmings/basherCapacity.png"));
			imageBuilderCapacity = ImageIO.read(new File("lemmings/builderCapacity.png"));
			imageBombCapacity = ImageIO.read(new File("lemmings/bombCapacity.png"));
			imageStopperCapacity  = ImageIO.read(new File("lemmings/stopperCapacity.png"));
			
			resetMapButtonDefault = ImageIO.read(new File("world/resetMapbutton.png"));
			resetMapButtonHover = ImageIO.read(new File("world/resetMapbuttonHover.png"));
			
			fastForwardButtonDefault = ImageIO.read(new File("world/fastforwardbutton.png"));
			fastForwardButtonHover = ImageIO.read(new File("world/fastforwardbuttonHover.png"));
			
		}catch(Exception e){e.printStackTrace();}
		
		resetMapButton = resetMapButtonDefault;
		fastForwardButton = fastForwardButtonDefault;
		
		w.getCanvasCapacity().addMouseListener(this);
		w.getCanvasCapacity().addMouseMotionListener(this);
		w.getCanvasCapacity().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public void update(){}
	
	public void draw(Graphics2D g){
		World world = w.getCurrentWorld();
		
		g.drawImage(lemmingsPanelImage,0,0,null);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(400,0,world.getWidth(),100);
		
		g.drawImage(imageBombCapacity,posXcapacity2,posYcapacity,null);
		g.drawImage(imageStopperCapacity,posXcapacity1,posYcapacity,null);
		g.drawImage(imageBuilderCapacity,posXcapacity3,posYcapacity,null);
		g.drawImage(imageBasherCapacity,posXcapacity4,posYcapacity,null);
		
		g.setColor(Color.white);
		g.drawString(""+world.getLemmingsLimit(world.STOPPER),posXcapacity1,posYcapacity+60);
		g.drawString(""+world.getLemmingsLimit(world.BOMBER),posXcapacity2,posYcapacity+60);
		g.drawString(""+world.getLemmingsLimit(world.BUILDER),posXcapacity3,posYcapacity+60);
		g.drawString(""+world.getLemmingsLimit(world.BASHER),posXcapacity4,posYcapacity+60);
		
		g.drawImage(fastForwardButton,world.getWidth()-40,20,null);
		
		g.drawImage(resetMapButton,world.getWidth()-40,60,null);
		
		drawSelectZone(g);
	}
	
	public void drawSelectZone(Graphics2D g){
	//=======partie select blanche======
		World world = w.getCurrentWorld();
		if ( posXmouse > posXcapacity1 && posXmouse < posXcapacity1+60
		&& posYmouse > posYcapacity && posYmouse < posYcapacity+60){
		//remplacer 60 par un truc propre
			drawCapacityBorder(g,REGULArBORDER, posXcapacity1-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity2 && posXmouse < posXcapacity2+60
		&& posYmouse > posYcapacity && posYmouse < posYcapacity+60){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity2-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity3 && posXmouse < posXcapacity3+60
		&& posYmouse > posYcapacity && posYmouse < posYcapacity+60){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity3-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity4 && posXmouse < posXcapacity4+60
		&& posYmouse > posYcapacity && posYmouse < posYcapacity+60){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity4-1, posYcapacity-1);
		}
		
	//=======partie select rouge======	
		
		if (capacityClicSetter == 1){
        		drawCapacityBorder(g,SELECtBORDER, posXcapacity1-1, posYcapacity-1);
    		}else if (capacityClicSetter == 2){
        		drawCapacityBorder(g,SELECtBORDER, posXcapacity2-1, posYcapacity-1);
    		
    		}else if (capacityClicSetter == 3){
        		drawCapacityBorder(g,SELECtBORDER, posXcapacity3-1, posYcapacity-1);
    		}else if (capacityClicSetter == 4){
        		drawCapacityBorder(g,SELECtBORDER, posXcapacity4-1, posYcapacity-1);
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
			if (w.FPS == 60) w.changeGameSpeed(2);
			else if(w.FPS == 120) w.changeGameSpeed(1);
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
        
        public void updateButtons(){
        	changeResetMapButton();
        	changeFastForwardButton();
        }
        
        public void changeResetMapButton(){
        	World world = w.getCurrentWorld();
        	if(posXmouse >= world.getWidth()-40 && posXmouse <=world.getWidth()-10 && posYmouse>=60 && posYmouse <=90){
        		resetMapButton = resetMapButtonHover;
        	}
        	else{
            		resetMapButton = resetMapButtonDefault;
        	}
        }
        
        public void changeFastForwardButton(){
        	World world = w.getCurrentWorld();
        	if(posXmouse >= world.getWidth()-40 && posXmouse <=world.getWidth()-10 && posYmouse>=20 && posYmouse <=50){
        		fastForwardButton = fastForwardButtonHover;
        	}
        	else{
            		fastForwardButton = fastForwardButtonDefault;
        	}
        }
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.

		int posXclic = e.getX();
		int posYclic = e.getY();
		
		World world = w.getCurrentWorld();
		
		if(world == null) return;
		
		if (resetMapPressed(world, posXclic, posYclic)) return;
		
		if (fastForwardPressed(world, posXclic, posYclic)) return;
		
		if ( posXclic > posXcapacity1 && posXclic < posXcapacity1+60
		&& posYclic > posYcapacity && posYclic < posYcapacity+60){
		//remplacer 60 par un truc propre			
			capacityClicSetter = 1;
			return;
		}
		
		if ( posXclic > posXcapacity2 && posXclic < posXcapacity2+60
		&& posYclic > posYcapacity && posYclic < posYcapacity+60){
			capacityClicSetter = 2;
			return;
		}
		
		if ( posXclic > posXcapacity3 && posXclic < posXcapacity3+60
		&& posYclic > posYcapacity && posYclic < posYcapacity+60){
			capacityClicSetter = 3;
			return;
		}
		if ( posXclic > posXcapacity4 && posXclic < posXcapacity4+60
		&& posYclic > posYcapacity && posYclic < posYcapacity+60){
			capacityClicSetter = 4;
			return;
		}
	}
	

}
