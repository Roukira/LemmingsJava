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
	
	
	private static final int iAnimateMAX = 500;
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
	private static BufferedImage imageBasherCapacity1;
	private static BufferedImage imageBasherCapacity2;
	private static BufferedImage imageBasherCapacity3;
	private int iBash = -1;
	
	private static BufferedImage imageBuilderCapacity;
	private static BufferedImage imageBuilderCapacity1;
	private static BufferedImage imageBuilderCapacity2;
	private static BufferedImage imageBuilderCapacity3;
	private static BufferedImage imageBuilderCapacity4;
	private int iBuild = -1;
	
	private static BufferedImage imageBombCapacity;
	private static BufferedImage imageBombCapacity2;
	private static BufferedImage imageBombCapacity3;
	private static BufferedImage imageBombCapacity4;
	private static BufferedImage imageBombCapacity5;
	private static BufferedImage imageBombCapacity6;
	private static BufferedImage imageBombCapacity7;
	private static BufferedImage imageBombCapacity8;
	private int iBomb = -1;
	
	private static BufferedImage imageStopperCapacity;
	private static BufferedImage imageStopperCapacity1;
	private static BufferedImage imageStopperCapacity2;
	private static BufferedImage imageStopperCapacity3;
	private static BufferedImage imageStopperCapacity4;
	private int iStop = -1;
	
	
	private static BufferedImage imageMinerCapacity;
	private static BufferedImage imageMinerCapacity1;
	private static BufferedImage imageMinerCapacity2;
	private static BufferedImage imageMinerCapacity3;
	private static BufferedImage imageMinerCapacity4;
	private static BufferedImage imageMinerCapacity5;
	private static BufferedImage imageMinerCapacity6;
	private static BufferedImage imageMinerCapacity7;
	private static BufferedImage imageMinerCapacity8;
	private int iMine = -1;
	private int minerDirection = 1;
	
	private static BufferedImage imageExcavaterCapacity;
	private static BufferedImage imageExcavaterCapacity1;
	private static BufferedImage imageExcavaterCapacity2;
	private static BufferedImage imageExcavaterCapacity3;
	private static BufferedImage imageExcavaterCapacity4;
	private int iExcavate = -1;
	
	
	private static BufferedImage arrowUp;
	private static BufferedImage arrowDown;
	private static BufferedImage arrowUpHover;
	private static BufferedImage arrowDownHover;
	private boolean arrowHovered = false;
	
	private static BufferedImage border;
	private static BufferedImage whiteBorder;
	private static BufferedImage redBorder;
	
	public static final int REGULArBORDER = 0;
	public static final int SELECtBORDER = 1;
	
	public static final int nbJobs = 6;
	
	private int[] posXcapacityTab;
	private int posYcapacity = 20;
	
	private static final int spacing = 8;
	private static final int leftPadding = 5;
	
	private int capacityClicSetter = -1;
	
	public SkillBar(GameScene gs){
		this.gs = gs;
		
		resetMapButton = resetMapButtonDefault;
		fastForwardButton = fastForwardButtonDefault;
		
		generatePosXTab();
		
		
	}
	
	public static void loadAssets(){
		try{	
			whiteBorder = ImageIO.read(new File("skillbar/capacityBorder.png"));
			redBorder = ImageIO.read(new File("skillbar/capacitySelectBorder.png"));
		
			lemmingsPanelImage = ImageIO.read(new File("skillbar/lemmingspanel.png"));
			
			imageCapacityBorder = ImageIO.read(new File("skillbar/capacityBorder.png"));
			imageCapacitySelectBorder = ImageIO.read(new File("skillbar/capacitySelectBorder.png"));
			
			imageBasherCapacity = ImageIO.read(new File("skillbar/basherCapacity.png"));
			imageBasherCapacity1 = imageBasherCapacity;
			imageBasherCapacity2 = ImageIO.read(new File("skillbar/basherCapacity2.png"));
			imageBasherCapacity3 = ImageIO.read(new File("skillbar/basherCapacity3.png"));
			
			imageBuilderCapacity = ImageIO.read(new File("skillbar/builderCapacity.png"));
			imageBuilderCapacity1 = imageBuilderCapacity;
			imageBuilderCapacity2 = ImageIO.read(new File("skillbar/builderCapacity2.png"));
			imageBuilderCapacity3 = ImageIO.read(new File("skillbar/builderCapacity3.png"));
			imageBuilderCapacity4 = ImageIO.read(new File("skillbar/builderCapacity4.png"));
			
			imageBombCapacity = ImageIO.read(new File("skillbar/bombCapacity.png"));
			imageBombCapacity2 = ImageIO.read(new File("skillbar/bombCapacity2.png"));
			imageBombCapacity3 = ImageIO.read(new File("skillbar/bombCapacity3.png"));
			imageBombCapacity4 = ImageIO.read(new File("skillbar/bombCapacity4.png"));
			imageBombCapacity5 = ImageIO.read(new File("skillbar/bombCapacity5.png"));
			imageBombCapacity6 = ImageIO.read(new File("skillbar/bombCapacity6.png"));
			imageBombCapacity7 = ImageIO.read(new File("skillbar/bombCapacity7.png"));
			imageBombCapacity8 = ImageIO.read(new File("skillbar/bombCapacity8.png"));
			
			
			imageStopperCapacity  = ImageIO.read(new File("skillbar/stopperCapacity.png"));
			imageStopperCapacity1 = imageStopperCapacity;
			imageStopperCapacity2 = ImageIO.read(new File("skillbar/stopperCapacity2.png"));
			imageStopperCapacity3 = ImageIO.read(new File("skillbar/stopperCapacity3.png"));
			imageStopperCapacity4 = imageStopperCapacity2;
			
			imageMinerCapacity = ImageIO.read(new File("skillbar/minerCapacity.png"));
			imageMinerCapacity1 = imageMinerCapacity;
			imageMinerCapacity2 = ImageIO.read(new File("skillbar/minerCapacity2.png"));
			imageMinerCapacity3 = ImageIO.read(new File("skillbar/minerCapacity3.png"));
			imageMinerCapacity4 = imageMinerCapacity2;
			imageMinerCapacity5 = imageMinerCapacity1;
			imageMinerCapacity6 = imageMinerCapacity2;
			imageMinerCapacity7 = ImageIO.read(new File("skillbar/minerCapacity7.png"));
			imageMinerCapacity8 = imageMinerCapacity2;
			
			imageExcavaterCapacity = ImageIO.read(new File("skillbar/excavaterCapacity.png"));
			imageExcavaterCapacity1 = ImageIO.read(new File("skillbar/excavaterCapacity1.png"));
			imageExcavaterCapacity2 = ImageIO.read(new File("skillbar/excavaterCapacity2.png"));
			imageExcavaterCapacity3 = imageExcavaterCapacity;
			imageExcavaterCapacity4 = ImageIO.read(new File("skillbar/excavaterCapacity4.png"));			
			
			resetMapButtonDefault = ImageIO.read(new File("world/resetMapbutton.png"));
			resetMapButtonHover = ImageIO.read(new File("world/resetMapbuttonHover.png"));
			
			fastForwardButtonDefault = ImageIO.read(new File("world/fastforwardbutton.png"));
			fastForwardButtonHover = ImageIO.read(new File("world/fastforwardbuttonHover.png"));
			
			arrowUp = ImageIO.read(new File("lemmings/arrowUp.png"));
			arrowUpHover = ImageIO.read(new File("lemmings/arrowUpHover.png"));
			arrowDown = ImageIO.read(new File("lemmings/arrowDown.png"));
			arrowDownHover = ImageIO.read(new File("lemmings/arrowDownHover.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void generatePosXTab(){
		
		posXcapacityTab = new int[nbJobs];		
		World w = gs.getWindow().getCurrentWorld();
		int newPosX = leftPadding;
		for (int i = 0;i<nbJobs;i++){
			if (w.getLemmingsLimit(i+1) >0){
				posXcapacityTab[i] = newPosX;
				newPosX += getCapacityWidth()+spacing;
			}
			else posXcapacityTab[i] = -1;
		}
	}
	
	public void draw(Graphics2D g){
		World world = gs.getWindow().getCurrentWorld();
		if (world == null) return;
		g.drawImage(lemmingsPanelImage,0,0,null);
		
		g.setColor(Color.DARK_GRAY);
		//au lieu de mettre 450 mettre la taaille de  limage pour power
		g.fillRect(420,0,world.getWidth(),100);
		world.getStats().draw(g);
		
		for (int i = 0;i<nbJobs;i++){
			if (getPosXCapacity(i)>=0) g.drawImage(getImageCapacity(i+1),getPosXCapacity(i),posYcapacity,null);
		}
		
		g.setColor(Color.white);
		
		for (int i = 0;i<nbJobs;i++){
			if (getPosXCapacity(i)>=0){
				g.drawString(""+world.getLemmingsJob(i+1),getPosXCapacity(i),posYcapacity+getCapacityWidth()+15);
				g.drawString(world.getLemmingsLimit(i+1)+" left",getPosXCapacity(i),posYcapacity+getCapacityWidth()+30);
			}
		}
		
		g.drawImage(fastForwardButton,world.getWidth()-40,20,null);
		
		g.drawImage(resetMapButton,world.getWidth()-40,60,null);
		drawArrow(g);
	}
	
	public void drawArrow(Graphics2D g){
		if (getPosXCapacity(World.MINER)<0) return;
		int x = getArrowPosX();
		int y = getArrowPosY();
		if (minerDirection == 1){
			if (arrowHovered) g.drawImage(arrowUpHover,x,y,null);
			else g.drawImage(arrowUp,x,y,null);
		}
		else {
			if (arrowHovered) g.drawImage(arrowDownHover,x,y,null);
			else g.drawImage(arrowDown,x,y,null);
		}
	}
	
	public void drawSelectZone(Graphics2D g, int posXmouse, int posYmouse){
	//=======partie select ======
		World world = gs.getWindow().getCurrentWorld();
		
		for (int i = 0;i<nbJobs;i++){
			if (getPosXCapacity(i)>=0){
				if (posXmouse > getPosXCapacity(i) && posXmouse < getPosXCapacity(i)+getCapacityWidth()
				&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+getCapacityWidth()){
					drawCapacityBorder(g,REGULArBORDER, getPosXCapacity(i)-1,posYcapacity-1);
				}
			}
		}
		
		for (int i = 0; i<nbJobs;i++){
			if (getPosXCapacity(i)>=0){
				if (i+1 == capacityClicSetter){
					drawCapacityBorder(g, SELECtBORDER, getPosXCapacity(i)-1,posYcapacity-1);
				}
			}
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
		setAnimateCapacity(-1);
		capacityClicSetter = clicNumber;
		setAnimateCapacity(0);
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
  	
  	public BufferedImage getImageCapacity(int i){
  		if (i == World.BOMBER){
  			if(iBomb>=0){
  			
  				if(iBomb == iAnimateMAX) iBomb = 0;
  				else iBomb++;
  				
  				if (iBomb<=iAnimateMAX/(10*1.0)) return imageBombCapacity;
  				else if (iBomb<=2*iAnimateMAX/(10*1.0)) return imageBombCapacity2;
  				else if (iBomb<=3*iAnimateMAX/(10*1.0)) return imageBombCapacity3;
  				else if (iBomb<=4*iAnimateMAX/(10*1.0)) return imageBombCapacity4;
  				else if (iBomb<=5*iAnimateMAX/(10*1.0)) return imageBombCapacity5;
  				else if (iBomb<=6*iAnimateMAX/(10*1.0)) return imageBombCapacity6;
  				else if (iBomb<=7*iAnimateMAX/(10*1.0)) return imageBombCapacity7;
  				else return imageBombCapacity8;
  			}
  			else return imageBombCapacity;
  		}
  		else if (i == World.STOPPER){
  			if (iStop>=0){
  				
  				if (iStop == iAnimateMAX) iStop = 0;
  				else iStop++;
  				
  				if (iStop<=2.5*iAnimateMAX/(10*1.0)) return imageStopperCapacity1;
  				else if (iStop<=5*iAnimateMAX/(10*1.0)) return imageStopperCapacity2;
  				else if (iStop<=7.5*iAnimateMAX/(10*1.0)) return imageStopperCapacity3;
  				else return imageStopperCapacity4;
  			}
  			else return imageStopperCapacity;
  		}
  		else if (i == World.MINER){
  			if (iMine>=0){
  				
  				if (iMine == iAnimateMAX) iMine = 0;
  				else iMine++;
  				
  				if (iMine<=iAnimateMAX/(8*1.0)) return imageMinerCapacity1;
  				else if (iMine<=2*iAnimateMAX/(8*1.0)) return imageMinerCapacity2;
  				else if (iMine<=3*iAnimateMAX/(8*1.0)) return imageMinerCapacity3;
  				else if (iMine<=4*iAnimateMAX/(8*1.0)) return imageMinerCapacity4;
  				else if (iMine<=5*iAnimateMAX/(8*1.0)) return imageMinerCapacity5;
  				else if (iMine<=6*iAnimateMAX/(8*1.0)) return imageMinerCapacity6;
  				else if (iMine<=7*iAnimateMAX/(8*1.0)) return imageMinerCapacity7;
  				else return imageMinerCapacity8;
  			}
  			else return imageMinerCapacity;
  		}
  		else if (i == World.EXCAVATER){
  			if (iExcavate>=0){
  				
  				if (iExcavate == iAnimateMAX) iExcavate = 0;
  				else iExcavate++;
  				
  				if (iExcavate<=iAnimateMAX/(5*1.0)) return imageExcavaterCapacity1;
  				else if (iExcavate<=2*iAnimateMAX/(5*1.0)) return imageExcavaterCapacity2;
  				else if (iExcavate<=3*iAnimateMAX/(5*1.0)) return imageExcavaterCapacity3;
  				else if (iExcavate<=4*iAnimateMAX/(5*1.0)) return imageExcavaterCapacity4;
  				else return imageExcavaterCapacity1;
  			}
  			else return imageExcavaterCapacity;
  		}
  		else if (i == World.BUILDER){
  			if (iBuild>=0){
  				
  				if (iBuild == iAnimateMAX) iBuild = 0;
  				else iBuild++;
  				
  				if (iBuild<=iAnimateMAX/(5*1.0)) return imageBuilderCapacity1;
  				else if (iBuild<=2*iAnimateMAX/(5*1.0)) return imageBuilderCapacity2;
  				else if (iBuild<=3*iAnimateMAX/(5*1.0)) return imageBuilderCapacity3;
  				else return imageBuilderCapacity4;	
  			}
  			else return imageBuilderCapacity;
  		}
  		else if (i == World.BASHER){
  			if (iBash>=0){
  				
  				if (iBash == iAnimateMAX) iBash = 0;
  				else iBash++;
  				
  				if (iBash<=iAnimateMAX/(4*1.0)) return imageBasherCapacity1;
  				else if (iBash<=2*iAnimateMAX/(4*1.0)) return imageBasherCapacity2;
  				else if (iBash<=3*iAnimateMAX/(4*1.0)) return imageBasherCapacity3;
  				else return imageBasherCapacity;
  			}
  			else return imageBasherCapacity;
  		}
  		else{
  			System.out.println("job not existing");
  			return imageCapacity;
  		}
  	}
  	
  	public int getPosXCapacity(int i){
  		return posXcapacityTab[i];
  	}
  	
  	public int getPosYCapacity(){
  		return posYcapacity;
  	}
  	
  	public int getCapacityWidth(){
  		return imageStopperCapacity.getWidth();
  	}
  	
  	public int getCapacityHeight(){
  		return imageStopperCapacity.getHeight();
  	}
  	
  	public int getMinerDirection(){
  		return minerDirection;
  	}
  	
  	public void changeMinerDirection(){
  		minerDirection = -minerDirection;
  	}
  	
  	public void setAnimateCapacity(int value){
  		if (capacityClicSetter == World.BOMBER) iBomb = value;
  		else if (capacityClicSetter == World.STOPPER) iStop = value;
  		else if (capacityClicSetter == World.MINER) iMine = value;
  		else if (capacityClicSetter == World.EXCAVATER) iExcavate = value;
  		else if (capacityClicSetter == World.BUILDER) iBuild = value;
  		else if (capacityClicSetter == World.BASHER) iBash = value;
  	}
  	
  	public void setArrowHovered(boolean hovered){
  		arrowHovered = hovered;
  	}
  	
  	public int getArrowWidth(){
  		return arrowUp.getWidth(); 
  	}
  	
  	public int getArrowHeight(){
  		return arrowUp.getHeight();
  	}
  	
  	public int getArrowPosX(){
  		return getPosXCapacity(World.MINER-1)+getCapacityWidth()-getArrowWidth();
  	}
  	
  	public int getArrowPosY(){
  		return getPosYCapacity()+getCapacityHeight()-getArrowHeight();
  	}
  	
  	
  	
}
