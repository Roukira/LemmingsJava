import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class InputGame extends Input{

	private Toolkit tk = Toolkit.getDefaultToolkit();
	private static BufferedImage imageCurseurSelect;
	private static BufferedImage imageCurseurInit;
	private static BufferedImage imageCurseurSelectRed;
	private static BufferedImage imageCurseurInitRed;
	
	private int frameWidth;
	private int frameHeight;
	
	private Cursor cursorGame;
	
	private Cursor CurseurInit;
	private Cursor CurseurSelect;
	private Cursor CurseurInitRed;
	private Cursor CurseurSelectRed;
	
	private static BufferedImage border;
	private static BufferedImage whiteBorder;
	private static BufferedImage redBorder;
	
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
	
	
	public static final int posXcapacity1 = 0;
	public static final int posXcapacity2 = 70;
	public static final int posXcapacity3 = 140;
	public static final int posXcapacity4 = 210;
	public static final int posXcapacity5 = 280;
	public static final int posXcapacity6 = 350;
	public static final int posYcapacity = 20;
	
	
	public static final int REGULArBORDER = 0;
	public static final int SELECtBORDER = 1;
	
	private int capacityClicSetter = 0;
	
	private double ratioX = 1.0;
	private double ratioY = 1.0;
	
	private GameScene gs;
	
	public InputGame(Window w, GameScene gs){
		super(w);
		
		this.gs = gs;
		
		frameWidth = w.getCanvas().getWidth();
		System.out.println("frameWidth init : "+frameWidth);
		frameHeight = w.getCanvas().getHeight();
		System.out.println("frameHeight init : "+frameHeight);
		resetMapButton = resetMapButtonDefault;
		fastForwardButton = fastForwardButtonDefault;
		
		CurseurInit = tk.createCustomCursor( imageCurseurInit, new Point(imageCurseurInit.getWidth()/2,imageCurseurInit.getHeight()/2), "Pointeur" );
		CurseurSelect = tk.createCustomCursor( imageCurseurSelect, new Point(imageCurseurSelect.getWidth()/2,imageCurseurSelect.getHeight()/2), "Pointeur" );
		CurseurInitRed = tk.createCustomCursor( imageCurseurInitRed, new Point(imageCurseurInitRed.getWidth()/2,imageCurseurInitRed.getHeight()/2), "Pointeur" );
		CurseurSelectRed = tk.createCustomCursor( imageCurseurSelectRed, new Point(imageCurseurSelectRed.getWidth()/2,imageCurseurSelectRed.getHeight()/2), "Pointeur" );
		cursorGame = CurseurInit;
	}
	
	public static void loadAssets(){
		try{
			imageCurseurSelect = ImageIO.read(new File("cursor/cursorSelect.png"));
			imageCurseurInit = ImageIO.read(new File("cursor/cursorInit.png"));
			imageCurseurSelectRed = ImageIO.read(new File("cursor/cursorSelectRed.png"));
			imageCurseurInitRed = ImageIO.read(new File("cursor/cursorInitRed.png"));
			
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
	
	public void setCursor(Cursor cursor){
		w.getCanvas().setCursor(cursor);
	}
	
	public Cursor getCursorGame(){
		return cursorGame;
	}
	
	public boolean lemmingsInRange(Lemmings l){
		int lemmingsLimitYUpper = (int)(l.getPosY()-1.15*1.5*l.getHeight());
		int lemmingsLimitYLower = (int)(l.getPosY()+1.15*0.5*l.getHeight());
		int lemmingsLimitXUpper = (int)(l.getPosX()-1.15*l.getWidth());
		int lemmingsLimitXLower = (int)(l.getPosX()+1.15*l.getWidth());
		return (l.getInWorld() && lemmingsLimitYUpper<posYmouse  && lemmingsLimitYLower>posYmouse && lemmingsLimitXUpper<posXmouse  && lemmingsLimitXLower>posXmouse);
	}
	
	public void update(){
		boolean cursorOnLemmings = false;
		Lemmings l;
		for(int i=0;i<w.getCurrentWorld().getLemmingsList().length;i++){
			l = w.getCurrentWorld().getLemmingsList()[i];
			if (lemmingsInRange(l)){
				cursorOnLemmings = true;
			}
		}
		if (cursorOnLemmings){
        		if (capacityClicSetter==0) setCursor( CurseurSelect );
        		else setCursor( CurseurSelectRed );
        	}
        	else{
        		if (capacityClicSetter==0) setCursor( CurseurInit );
        		else setCursor( CurseurInitRed );
        	}
	}
	
	public void draw(Graphics2D g){
		World world = w.getCurrentWorld();
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
		
		//System.out.println("taille content pane X : "+w.getFrame().getContentPane().getWidth());
		//System.out.println("taille screen X : "+frameWidth);
		//System.out.println("taille content pane Y : "+w.getFrame().getContentPane().getHeight());
		//System.out.println("taille screen Y : "+frameHeight);
		//if (null)
		if (w.getCanvas().getWidth()!=frameWidth || w.getCanvas().getHeight()!=frameHeight){
			
			
			frameWidth = w.getCanvas().getWidth();
			frameHeight = w.getCanvas().getHeight();
			
			ratioX = (frameWidth*1.0)/gs.getWidth();
			ratioY = (frameHeight*1.0)/gs.getHeight(); //pb61
			//update les images en les ecrasant
			
			
			if(gs.getWidth() != frameWidth || gs.getHeight() != frameHeight){
				BufferedImage imageCurseurInitTemp = Window.resize(imageCurseurInit, (int)(imageCurseurInit.getWidth()*ratioX),(int)(imageCurseurInit.getHeight()*ratioY));
				BufferedImage imageCurseurSelectTemp = Window.resize(imageCurseurSelect, (int)(imageCurseurSelect.getWidth()*ratioX),(int)(imageCurseurSelect.getHeight()*ratioY));
				BufferedImage imageCurseurInitRedTemp = Window.resize(imageCurseurInitRed, (int)(imageCurseurInitRed.getWidth()*ratioX),(int)(imageCurseurInitRed.getHeight()*ratioY));
				BufferedImage imageCurseurSelectRedTemp = Window.resize(imageCurseurSelectRed, (int)(imageCurseurSelectRed.getWidth()*ratioX),(int)(imageCurseurSelectRed.getHeight()*ratioY));
				CurseurInit = tk.createCustomCursor( imageCurseurInitTemp, new Point(imageCurseurInitTemp.getWidth()/2,imageCurseurInitTemp.getHeight()/2), "Pointeur" );
				CurseurSelect = tk.createCustomCursor( imageCurseurSelectTemp, new Point(imageCurseurSelectTemp.getWidth()/2,imageCurseurSelectTemp.getHeight()/2), "Pointeur" );
				CurseurInitRed = tk.createCustomCursor( imageCurseurInitRedTemp, new Point(imageCurseurInitRedTemp.getWidth()/2,imageCurseurInitRedTemp.getHeight()/2), "Pointeur" );
				CurseurSelectRed = tk.createCustomCursor( imageCurseurSelectRedTemp, new Point(imageCurseurSelectRedTemp.getWidth()/2,imageCurseurSelectRedTemp.getHeight()/2), "Pointeur" );
				}
			else{
				CurseurInit = tk.createCustomCursor( imageCurseurInit, new Point(imageCurseurInit.getWidth()/2,imageCurseurInit.getHeight()/2), "Pointeur" );
				CurseurSelect = tk.createCustomCursor( imageCurseurSelect, new Point(imageCurseurSelect.getWidth()/2,imageCurseurSelect.getHeight()/2), "Pointeur" );
				CurseurInitRed = tk.createCustomCursor( imageCurseurInitRed, new Point(imageCurseurInitRed.getWidth()/2,imageCurseurInitRed.getHeight()/2), "Pointeur" );
				CurseurSelectRed = tk.createCustomCursor( imageCurseurSelectRed, new Point(imageCurseurSelectRed.getWidth()/2,imageCurseurSelectRed.getHeight()/2), "Pointeur" );
				}
			cursorGame = CurseurInit;
		}
		drawSelectZone(g);
		
	}
	
	public void drawSelectZone(Graphics2D g){
	//=======partie select blanche======
		World world = w.getCurrentWorld();
		if ( posXmouse > posXcapacity1 && posXmouse < posXcapacity1+60
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+60){
		//remplacer 60 par un truc propre
			drawCapacityBorder(g,REGULArBORDER, posXcapacity1-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity2 && posXmouse < posXcapacity2+60
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+60){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity2-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity3 && posXmouse < posXcapacity3+60
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+60){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity3-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity4 && posXmouse < posXcapacity4+60
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+60){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity4-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity5 && posXmouse < posXcapacity5+60
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+60){
			drawCapacityBorder(g,REGULArBORDER, posXcapacity5-1, posYcapacity-1);
		}
		else if ( posXmouse > posXcapacity6 && posXmouse < posXcapacity6+60
		&& posYmouse > world.getHeight()+posYcapacity && posYmouse < world.getHeight()+posYcapacity+60){
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
	
	public boolean fastForwardPressed(World world, int posXclic, int posYclic){
		if (posXclic >=world.getWidth()-40 && posXclic <=world.getWidth()-10 && posYclic>=world.getHeight()+20 && posYclic<=world.getHeight()+50){
			if (gs.FPS == 60) w.changeGameSpeed(2);
			else if(gs.FPS == 120) w.changeGameSpeed(1);
			return true;
		}
		return false;
	}
	
	//===================MOUSE EVENT========================================================
        
        public void updateButtons(){
        	changeResetMapButton();
        	changeFastForwardButton();
        }
        
        public void changeResetMapButton(){
        	World world = w.getCurrentWorld();
        	if(posXmouse >= world.getWidth()-40 && posXmouse <=world.getWidth()-10 && posYmouse>=world.getHeight()+60 && posYmouse <=world.getHeight()+90){
        		resetMapButton = resetMapButtonHover;
        	}
        	else{
            		resetMapButton = resetMapButtonDefault;
        	}
        }
        
        public void changeFastForwardButton(){
        	World world = w.getCurrentWorld();
        	if(posXmouse >= world.getWidth()-40 && posXmouse <=world.getWidth()-10 && posYmouse>=world.getHeight()+20 && posYmouse <=world.getHeight()+50){
        		fastForwardButton = fastForwardButtonHover;
        	}
        	else{
            		fastForwardButton = fastForwardButtonDefault;
        	}
        }
        
        public void mouseMoved(MouseEvent e){
        	posXmouse = (int)(e.getX()/ratioX);
        	posYmouse = (int)(e.getY()/ratioY);
        	updateButtons();
        	World world = w.getCurrentWorld();
        	Lemmings l;
		if(world == null) return;
        	for(int i=0;i<world.getLemmingsList().length;i++){
			l = world.getLemmingsList()[i];
        		if (lemmingsInRange(l)){
        			if (l instanceof Miner){
        				Miner m = (Miner)l;
        				if (posXmouse >= m.getArrowPosX() &&
        				posXmouse <= m.getArrowPosX()+m.getArrowWidth() &&
        				posYmouse >= m.getArrowPosY() && 
        				posYmouse <= m.getArrowPosY()+m.getArrowHeight()){
        					
        					m.setArrowHovered(true);
        				}
        				else{
        					m.setArrowHovered(false);
        				}
        			}
        		}
        	}
        }
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.
		System.out.println("aaaa");
		int posXclic = (int)(e.getX()/ratioX);
		int posYclic = (int)(e.getY()/ratioY);
		
		World world = w.getCurrentWorld();
		
		if(world == null) return;
		if (resetMapPressed(world, posXclic, posYclic)) return;
		if (fastForwardPressed(world, posXclic, posYclic)) return;
		if ( posXclic > posXcapacity1 && posXclic < posXcapacity1+60
		&& posYclic > world.getHeight()+posYcapacity && posYclic < world.getHeight()+posYcapacity+60){
		//remplacer 60 par un truc propre			
			capacityClicSetter = 1;
			return;
		}
		
		if ( posXclic > posXcapacity2 && posXclic < posXcapacity2+60
		&& posYclic > world.getHeight()+posYcapacity && posYclic < world.getHeight()+posYcapacity+60){
			capacityClicSetter = 2;
			return;
		}
		
		if ( posXclic > posXcapacity3 && posXclic < posXcapacity3+60
		&& posYclic > world.getHeight()+posYcapacity && posYclic < world.getHeight()+posYcapacity+60){
			capacityClicSetter = 3;
			return;
		}
		if ( posXclic > posXcapacity4 && posXclic < posXcapacity4+60
		&& posYclic > world.getHeight()+posYcapacity && posYclic < world.getHeight()+posYcapacity+60){
			capacityClicSetter = 4;
			return;
		}
		if ( posXclic > posXcapacity5 && posXclic < posXcapacity5+60
		&& posYclic > world.getHeight()+posYcapacity && posYclic < world.getHeight()+posYcapacity+60){
			capacityClicSetter = 5;
			return;
		}
		if ( posXclic > posXcapacity6 && posXclic < posXcapacity6+60
		&& posYclic > world.getHeight()+posYcapacity && posYclic < world.getHeight()+posYcapacity+60){
			capacityClicSetter = 6;
			return;
		}
		
		int posXlem;
		int posYlem;	
		Lemmings l;
		for(int i=0;i<world.getLemmingsList().length;i++){
			l = world.getLemmingsList()[i];
			posXlem = l.getPosX();
			posYlem = l.getPosY();	
			//ce if doit etre le meme que celui qui dit si le curseur est sur un lemmings
        		if (lemmingsInRange(l)){
        			if (l instanceof Miner){
        				Miner m = (Miner)l;
        				if (posXclic >= m.getArrowPosX() && 
        				posXclic <= m.getArrowPosX()+m.getArrowWidth() && 
        				posYclic >= m.getArrowPosY() && 
        				posYclic <= m.getArrowPosY()+m.getArrowHeight()){
        					m.changeDirectionY();
        				}
        				
        			}
        			if (World.STOPPER != l.getJob() && getCapacityClicSetter() == 1 && l.getInWorld() && e.getButton()==1){
        			//si la methode getButton retourne 1 c est le clic gauche 
        				world.changeJob(l,World.STOPPER);
					System.out.println("turn into STOPPER");
					return;
        			}
        			else if ( World.WALKER != l.getJob() && e.getButton()==3 && l.getInWorld()){ 
        			//si la methode getButton retourne 3 c est le clic gauche	
        				world.changeJob(l,World.WALKER);
					System.out.println("turn into WALKER");
        				return;
        			}
        			else if ( getCapacityClicSetter() == 2 && l.getBombCountdown()==-1 && l.getInWorld()){
        				System.out.println("turn into BOMBER");
        				world.changeJob(l,World.BOMBER);
        				return; 
        				
        			}
        			else if (getCapacityClicSetter() == 3 && l.getInWorld() && e.getButton()==1){
        				world.changeJob(l,World.BUILDER);
					System.out.println("turn into Builder");
					return;
				}
				else if (World.BASHER != l.getJob() && getCapacityClicSetter() == 4 && l.getInWorld() && e.getButton()==1){
        				world.changeJob(l,World.BASHER);
					System.out.println("turn into BASHER");
					return;
				}
				else if (World.MINER != l.getJob() && getCapacityClicSetter() == 5 && l.getInWorld() && e.getButton()==1){
        				world.changeJob(l,World.MINER);
					System.out.println("turn into MINER");
					return;
				}else if (World.EXCAVATER != l.getJob() && getCapacityClicSetter() == 6 && l.getInWorld() && e.getButton()==1){
        				world.changeJob(l,World.EXCAVATER);
					System.out.println("turn into EXCAVATER");
					return;
				}
        			
        		}
        	}
	}
}
