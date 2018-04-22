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
	
	private GameScene gs;
	
	public InputGame(Window w, GameScene gs){
		super(w);
		try{
			imageCurseurSelect = ImageIO.read(new File("cursor/cursorSelect.png"));
			imageCurseurInit = ImageIO.read(new File("cursor/cursorInit.png"));
			imageCurseurSelectRed = ImageIO.read(new File("cursor/cursorSelectRed.png"));
			imageCurseurInitRed = ImageIO.read(new File("cursor/cursorInitRed.png"));
			
		}catch(Exception e){e.printStackTrace();}
		
		this.gs = gs;
		
		CurseurInit = tk.createCustomCursor( imageCurseurInit, new Point(imageCurseurInit.getWidth()/2,imageCurseurInit.getHeight()/2), "Pointeur" );
		CurseurSelect = tk.createCustomCursor( imageCurseurSelect, new Point(imageCurseurSelect.getWidth()/2,imageCurseurSelect.getHeight()/2), "Pointeur" );
		CurseurInitRed = tk.createCustomCursor( imageCurseurInitRed, new Point(imageCurseurInitRed.getWidth()/2,imageCurseurInitRed.getHeight()/2), "Pointeur" );
		CurseurSelectRed = tk.createCustomCursor( imageCurseurSelectRed, new Point(imageCurseurSelectRed.getWidth()/2,imageCurseurSelectRed.getHeight()/2), "Pointeur" );
		gs.getCanvas().setCursor(CurseurInit); //test getCanvas
		gs.getCanvas().addMouseListener(this);
		gs.getCanvas().addMouseMotionListener(this);
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
        		if (getCapacityClicSetter()==0) gs.getCanvas().setCursor( CurseurSelect );
        		else gs.getCanvas().setCursor( CurseurSelectRed );
        	}
        	else{
        		if (getCapacityClicSetter()==0) w.getFrame().setCursor( CurseurInit );
        		else gs.getCanvas().setCursor( CurseurInitRed );
        	}
	}
	
	public void draw(Graphics2D g){}
	
	public int getCapacityClicSetter(){
		return gs.getCapacityInput().getCapacityClicSetter();
	}
	
	//===================MOUSE EVENT========================================================
        
        public void updateButtons(){}
        
        public void mouseMoved(MouseEvent e){
        	super.mouseMoved(e);
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
		int posXclic = e.getX();
		int posYclic = e.getY();
		
		World world = w.getCurrentWorld();
		
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
