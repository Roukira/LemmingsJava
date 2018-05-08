import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class SkillBar implements Renderable{

	private GameScene gs;
	
	private static BufferedImage lemmingsPanelImage;
	
	private static BufferedImage fastForwardButton;
	private static BufferedImage fastForwardButtonDefault;
	private static BufferedImage fastForwardButtonHover;
	
	private static BufferedImage resetMapButtonDefault;
	private static BufferedImage resetMapButtonHover;
	private static BufferedImage resetMapButton;
	
	private static BufferedImage imageCapacity;
	private static BufferedImage imageCapacityBorder;
	private static BufferedImage imageCapacitySelectBorder;
	private static BufferedImage imageBasherCapacity;
	private static BufferedImage imageBuilderCapacity;
	private static BufferedImage imageBombCapacity;
	private static BufferedImage imageStopperCapacity;
	private static BufferedImage imageMinerCapacity;
	private static BufferedImage imageExcavaterCapacity;
	
	private static BufferedImage border;
	private static BufferedImage whiteBorder;
	private static BufferedImage redBorder;
	
	public static final int REGULArBORDER = 0;
	public static final int SELECtBORDER = 1;
	
	
	private int posXcapacity1;
	private int posXcapacity2;
	private int posXcapacity3;
	private int posXcapacity4;
	private int posXcapacity5;
	private int posXcapacity6;
	private int posYcapacity = 20;
	
	private static final int spacing = 10; //8 plus tard
	private static final int leftPadding = 5;
	
	private int capacityClicSetter = 0;
	
	public SkillBar(GameScene gs){
		this.gs = gs;
		
		resetMapButton = resetMapButtonDefault;
		fastForwardButton = fastForwardButtonDefault;
		
		posXcapacity1 = leftPadding;
		posXcapacity2 = leftPadding + getCapacityWidth()+spacing;
		posXcapacity3 = leftPadding + (getCapacityWidth()+spacing)*2;
		posXcapacity4 = leftPadding + (getCapacityWidth()+spacing)*3;
		posXcapacity5 = leftPadding + (getCapacityWidth()+spacing)*4;
		posXcapacity6 = leftPadding + (getCapacityWidth()+spacing)*5;
		
		
	}
	
	public static void loadAssets(){
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
			imageMinerCapacity = ImageIO.read(new File("lemmings/minerCapacity.png"));
			imageExcavaterCapacity = ImageIO.read(new File("lemmings/excavaterCapacity.png")); 			
			
			resetMapButtonDefault = ImageIO.read(new File("world/resetMapbutton.png"));
			resetMapButtonHover = ImageIO.read(new File("world/resetMapbuttonHover.png"));
			
			fastForwardButtonDefault = ImageIO.read(new File("world/fastforwardbutton.png"));
			fastForwardButtonHover = ImageIO.read(new File("world/fastforwardbuttonHover.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void draw(Graphics2D g){
		World world = gs.getWindow().getCurrentWorld();
		if (world == null) return;
		g.drawImage(lemmingsPanelImage,0,0,null);
		
		g.setColor(Color.DARK_GRAY);
		//au lieu de mettre 450 mettre la taaille de  limage pour power
		g.fillRect(420,0,world.getWidth(),100);
		world.getStats().draw(g);
		
		g.drawImage(imageBombCapacity,posXcapacity2,posYcapacity,null);
		g.drawImage(imageStopperCapacity,posXcapacity1,posYcapacity,null);
		g.drawImage(imageBuilderCapacity,posXcapacity3,posYcapacity,null);
		g.drawImage(imageBasherCapacity,posXcapacity4,posYcapacity,null);
		g.drawImage(imageMinerCapacity,posXcapacity5,posYcapacity,null);
		g.drawImage(imageExcavaterCapacity,posXcapacity6,posYcapacity,null);
		
		g.setColor(Color.white);
		g.drawString(""+world.getLemmingsLimit(world.STOPPER),posXcapacity1,posYcapacity+60);
		g.drawString(""+world.getLemmingsLimit(world.BOMBER),posXcapacity2,posYcapacity+60);
		g.drawString(""+world.getLemmingsLimit(world.BUILDER),posXcapacity3,posYcapacity+60);
		g.drawString(""+world.getLemmingsLimit(world.BASHER),posXcapacity4,posYcapacity+60);
		g.drawString(""+world.getLemmingsLimit(world.MINER),posXcapacity5,posYcapacity+60);
		g.drawString(""+world.getLemmingsLimit(world.EXCAVATER),posXcapacity6,posYcapacity+60);
		
		g.drawImage(fastForwardButton,world.getWidth()-40,20,null);
		
		g.drawImage(resetMapButton,world.getWidth()-40,60,null);
	}
	
			public void drawSelectZone(Graphics2D g, int posXmouse, int posYmouse){
	//=======partie select blanche======
		World world = gs.getWindow().getCurrentWorld();
		if ( posXmouse > posXcapacity1 && posXmouse < posXcapacity1+getCapacityWidth()
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+getCapacityWidth()){
		//remplacer getCapacityWidth() par un truc propre
			drawCapacityBorder(g,REGULArBORDER, posXcapacity1-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity2 && posXmouse < posXcapacity2+getCapacityWidth()
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+getCapacityWidth()){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity2-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity3 && posXmouse < posXcapacity3+getCapacityWidth()
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+getCapacityWidth()){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity3-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity4 && posXmouse < posXcapacity4+getCapacityWidth()
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+getCapacityWidth()){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity4-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity5 && posXmouse < posXcapacity5+getCapacityWidth()
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+getCapacityWidth()){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity5-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity6 && posXmouse < posXcapacity6+getCapacityWidth()
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+getCapacityWidth()){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity6-1, posYcapacity-1);
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
    		}else if (capacityClicSetter == 5){
        		drawCapacityBorder(g,SELECtBORDER, posXcapacity5-1, posYcapacity-1);
    		}else if (capacityClicSetter == 6){
        		drawCapacityBorder(g,SELECtBORDER, posXcapacity6-1, posYcapacity-1);
    		}
	}
	
	public void drawCapacityBorder(Graphics2D g,int borderType, int posX, int posY){
		if ( borderType == REGULArBORDER ){
			border = whiteBorder;
		}else{
			border = redBorder;
		}
		g.drawImage(border,posX,posY,null);
	}
	
	public void setCapacityClicSetter(int clicNumber){
		capacityClicSetter = clicNumber;
	}
	
	public int getCapacityClicSetter(){
		return capacityClicSetter;
	}
	
	public void setResetMapButtonHovered(boolean hovered){
		if (hovered) resetMapButton = resetMapButtonHover;
		else resetMapButton = resetMapButtonDefault;
	}
	
	public void setFastForwardButtonHovered(boolean hovered){
		if (hovered) fastForwardButton = fastForwardButtonHover;
        else fastForwardButton = fastForwardButtonDefault;
  	}
  	
  	public int getPosXCapacity(int i){
  		if (i == 1) return posXcapacity1;
  		else if (i == 2) return posXcapacity2;
  		else if (i == 3) return posXcapacity3;
  		else if (i == 4) return posXcapacity4;
  		else if (i == 5) return posXcapacity5;
  		else return posXcapacity6;
  	}
  	
  	public int getPosYCapacity(){
  		return posYcapacity;
  	}
  	
  	public int getCapacityWidth(){
  		return imageStopperCapacity.getWidth();
  	}
  	
  	
}
