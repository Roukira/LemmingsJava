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
	private BufferedImage imageCurseurSelect;
	private BufferedImage imageCurseurInit;
	private BufferedImage imageCurseurSelectRed;
	private BufferedImage imageCurseurInitRed;
	
	private Cursor CurseurInit;
	private Cursor CurseurSelect;
	private Cursor CurseurInitRed;
	private Cursor CurseurSelectRed;
	
	public InputGame(Window w){
		super(w);
		try{
			imageCurseurSelect = ImageIO.read(new File("cursor/cursorSelect.png"));
			imageCurseurInit = ImageIO.read(new File("cursor/cursorInit.png"));
			imageCurseurSelectRed = ImageIO.read(new File("cursor/cursorSelectRed.png"));
			imageCurseurInitRed = ImageIO.read(new File("cursor/cursorInitRed.png"));
			
		}catch(Exception e){e.printStackTrace();}
		
		CurseurInit = tk.createCustomCursor( imageCurseurInit, new Point( 10, 10 ), "Pointeur" );
		CurseurSelect = tk.createCustomCursor( imageCurseurSelect, new Point( 10, 10 ), "Pointeur" );
		CurseurInitRed = tk.createCustomCursor( imageCurseurInitRed, new Point( 10, 10 ), "Pointeur" );
		CurseurSelectRed = tk.createCustomCursor( imageCurseurSelectRed, new Point( 10, 10 ), "Pointeur" );
		w.getFrame().setCursor(CurseurInit); //test getCanvas
		w.getCanvas().addMouseListener(this);
		w.getCanvas().addMouseMotionListener(this);
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
        		if (getCapacityClicSetter()==0) w.getFrame().setCursor( CurseurSelect );
        		else w.getFrame().setCursor( CurseurSelectRed );
        	}
        	else{
        		if (getCapacityClicSetter()==0) w.getFrame().setCursor( CurseurInit );
        		else w.getFrame().setCursor( CurseurInitRed );
        	}
	}
	
	public int getCapacityClicSetter(){
		return w.getCanvasCapacityInput().getCapacityClicSetter();
	}
	
	public void draw(Graphics2D g){
		
	}
	
	//===================MOUSE EVENT========================================================
        
        public void mouseMoved(MouseEvent e) {
        //a chaque mouvement retourne un event
        	super.mouseMoved(e);
        	changeWorldButton();
        	changeMainMenueButton();
    	}
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.
		int posXclic = e.getX();
		int posYclic = e.getY();
		
		//System.out.println(""+posXclic+","+posYclic);
		
		World world = w.getCurrentWorld();
		Score score = w.getScore();
		MainMenu mainMenu = w.getMainMenu();
		
		if (score.getOnScreen()){
			if (posXclic >=560 && posXclic <=590 && posYclic>=360 && posYclic<=390) w.resetMap();
			else if(posXclic >= 450 && posXclic <=570 && posYclic>=300 && posYclic <=350){
				w.moveToMainMenu();
			}
			return;
		}
		
		
		if(worldSelection()) return;
		if(world == null) return;
		
		if (resetMapPressed(world, posXclic, posYclic)) return;
		
		int posXlem;
		int posYlem;	
		Lemmings l;
		for(int i=0;i<world.getLemmingsList().length;i++){
			l = world.getLemmingsList()[i];
			posXlem = l.getPosX();
			posYlem = l.getPosY();	
			//ce if doit etre le meme que celui qui dit si le curseur est sur un lemmings
        		if (lemmingsInRange(l)){
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
        			
        		}
        	}
	}
	
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
