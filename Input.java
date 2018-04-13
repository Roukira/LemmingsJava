import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Input implements MouseListener,MouseMotionListener{

	private Window w;
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private BufferedImage imageCurseurSelect;
	private BufferedImage imageCurseurInit;
	private BufferedImage imageCurseurSelectRed;
	private BufferedImage imageCurseurInitRed;
	private BufferedImage border;
	private BufferedImage whiteBorder;
	private BufferedImage redBorder;
	private static int posXmouse = 0;
	private static int posYmouse = 0;
	private int capacityClicSetter = 0;
	public static final int REGULArBORDER = 0;
	public static final int SELECtBORDER = 1;
	
	private double mouseRangeX = 2.0;
	private double mouseRangeY = 1.5;
	
	private Cursor CurseurInit;
	private Cursor CurseurSelect;
	private Cursor CurseurInitRed;
	private Cursor CurseurSelectRed;
	
	public Input(Window w){
		this.w = w;
		try{
			imageCurseurSelect = ImageIO.read(new File("cursor/cursorSelect.png"));
			imageCurseurInit = ImageIO.read(new File("cursor/cursorInit.png"));
			imageCurseurSelectRed = ImageIO.read(new File("cursor/cursorSelectRed.png"));
			imageCurseurInitRed = ImageIO.read(new File("cursor/cursorInitRed.png"));
			whiteBorder = ImageIO.read(new File("world/capacityBorder.png"));
			redBorder = ImageIO.read(new File("world/capacitySelectBorder.png"));
			
		}catch(Exception e){e.printStackTrace();}
		
		CurseurInit = tk.createCustomCursor( imageCurseurInit, new Point( 10, 10 ), "Pointeur" );
		CurseurSelect = tk.createCustomCursor( imageCurseurSelect, new Point( 10, 10 ), "Pointeur" );
		CurseurInitRed = tk.createCustomCursor( imageCurseurInitRed, new Point( 10, 10 ), "Pointeur" );
		CurseurSelectRed = tk.createCustomCursor( imageCurseurSelectRed, new Point( 10, 10 ), "Pointeur" );
		w.getFrame().setCursor(CurseurInit);
		w.getCanvas().addMouseListener(this);
		w.getCanvas().addMouseMotionListener(this);
		w.getCanvasCapacity().addMouseListener(this);
		w.getCanvasCapacity().addMouseMotionListener(this);
	}
	
	public boolean lemmingsInRange(Lemmings l){
		return (l.getInWorld() && l.getPosY()-mouseRangeY*l.getHeight()<posYmouse  && l.getPosY()+((mouseRangeY-1)*l.getHeight())>posYmouse && l.getPosX()-mouseRangeX*l.getWidth()<posXmouse  && l.getPosX()+mouseRangeX*l.getWidth()>posXmouse);
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
        		if (capacityClicSetter==0) w.getFrame().setCursor( CurseurSelect );
        		else w.getFrame().setCursor( CurseurSelectRed );
        	}
        	else{
        		if (capacityClicSetter==0) w.getFrame().setCursor( CurseurInit );
        		else w.getFrame().setCursor( CurseurInitRed );
        	}
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
	
	
	// Setters
	
	public void setCapacityClicSetter(int clicNumber){
		capacityClicSetter = clicNumber;
	}
	
	//===================MOUSE EVENT========================================================
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.
		int posXclic = e.getX();
		int posYclic = e.getY();
		System.out.println(""+posXclic+" "+posYclic);
		
		World world = w.getCurrentWorld();
		Score score = w.getScore();
		MainMenu mainMenu = w.getMainMenu();
		
		if (score.getOnScreen()){
			if(posXclic >= 450 && posXclic <=570 && posYclic>=300 && posYclic <=350){
				w.moveToMainMenu();
			}
			return;
		}
		
		
		if(worldSelection()) return;
		if(world == null) return;
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
		
		int posXlem;
		int posYlem;	
		Lemmings l;
		for(int i=0;i<world.getLemmingsList().length;i++){
			l = world.getLemmingsList()[i];
			posXlem = l.getPosX();
			posYlem = l.getPosY();	
			//ce if doit etre le meme que celui qui dit si le curseur est sur un lemmings
        		if (lemmingsInRange(l)){
        			if (World.STOPPER != l.getJob() && capacityClicSetter == 1 && l.getInWorld() && e.getButton()==1){
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
        			else if ( capacityClicSetter == 2 && l.getBombCountdown()==-1 && l.getInWorld()){
        				System.out.println("turn into BOMBER");
        				l.startBomb();
        				return; 
        				
        			}
        			else if (capacityClicSetter == 3 && l.getInWorld() && e.getButton()==1){
        				world.changeJob(l,World.BUILDER);
					System.out.println("turn into Builder");
					return;
				}
				else if (World.BASHER != l.getJob() && capacityClicSetter == 4 && l.getInWorld() && e.getButton()==1){
        				world.changeJob(l,World.BASHER);
					System.out.println("turn into BASHER");
					return;
				}
        			
        		}
        	}
	}
	
	public void mousePressed(MouseEvent e) {
	//Invoked when a mouse button has been pressed on a component.
	}


	public void mouseReleased(MouseEvent e) {
	//Invoked when a mouse button has been released on a component.
	}
	
	public void mouseEntered(MouseEvent e) {
	//Invoked when the mouse enters a component.
	}
	
	public void mouseExited(MouseEvent e) {
	//Invoked when the mouse exits a component.
	}
	
	
    	public void mouseMoved(MouseEvent e) {
        //a chaque mouvement retourne un event
        	posXmouse = e.getX();
        	posYmouse = e.getY();
        	changeWorldButton();
        	changeMainMenueButton();
    }

    	public void mouseDragged(MouseEvent e) {}
    	//a chaque mouvement ou un bouton de la souris est enfonce
    	
    	public void changeMainMenueButton(){
		Score score = w.getScore();
    		if(posXmouse >= 450 && posXmouse <=570 && posYmouse>=300 && posYmouse <=350){
        		score.showSelectButton();
        	}
        	else{
            		score.showDefaultButton();
        	}
    	}
    	
	public void changeWorldButton(){
		MainMenu mainMenu = w.getMainMenu();
        	if(posXmouse >= 250 && posXmouse <=370 && posYmouse>=100 && posYmouse <=150){
        		mainMenu.showSelectButton(1);
        	}
        	else{
            		mainMenu.showDefaultButton(1);
        	}
		if (posXmouse >= 250 && posXmouse <=370 && posYmouse>=160 && posYmouse <=210){
            		mainMenu.showSelectButton(2);
        	}
        	else{
            		mainMenu.showDefaultButton(2);
        	}
        	if (posXmouse >= 250 && posXmouse <=370 && posYmouse>=220 && posYmouse <=270){
            		mainMenu.showSelectButton(3);
       		} 
       		else{
            		mainMenu.showDefaultButton(3);
        	}
        }

	public boolean worldSelection(){
		MainMenu mainMenu = w.getMainMenu();
       		if (mainMenu.getOnScreen()){
            		if(posXmouse >= 250 && posXmouse <=370 && posYmouse>=100 && posYmouse <=150){
            			w.newCurrentWorld(1);
			}
			else if (posXmouse >= 250 && posXmouse <=370 && posYmouse>=160 && posYmouse <=210){
				w.newCurrentWorld(2);
			}
			else if (posXmouse >= 250 && posXmouse <=370 && posYmouse>=220 && posYmouse <=270){
				w.newCurrentWorld(3);
			}
			else{
				return false;
			}
			mainMenu.setOnScreen(false);
			return true;
		}
        	return false;
        }

}
