import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;


public class GameWindow extends JFrame implements MouseListener,MouseMotionListener{			

//==================== ATTRIBUTS ========================

	private BufferStrategy bs;			//fenetre de dessin		
	private static World world;				//monde
	private static int tps = 0;			//compteur de temps
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private BufferedImage imageCurseurSelect;
	private BufferedImage imageCurseurInit;
	private BufferedImage imageCurseurSelectRed;
	private BufferedImage imageCurseurInitRed;
	private BufferedImage border;
	private BufferedImage whiteBorder;
	private BufferedImage redBorder;
	private static int posXmouse;
	private static int posYmouse;
	private int capacityClicSetter = 0;
	public static final int REGULArBORDER = 0;
	public static final int SELECtBORDER = 1;
	
	private Cursor CurseurInit;
	private Cursor CurseurSelect;
	private Cursor CurseurInitRed;
	private Cursor CurseurSelectRed;
	
	private MainMenu mainMenu;
	private Score score;
	
//================== CONSTRUCTEURS ======================
	
	public GameWindow(String name){
		this(name,600,400);
	}
	
	public GameWindow(String name,int width,int height){
		this.setTitle(name); 					//titre de fenetre
		this.setSize(width,height); 				//change la taille
		this.setLocationRelativeTo(null); 			//place au centre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	//quitte avec la croix
		this.setResizable(false); 				//empeche resize
		this.setVisible(true); 					//rend visible
		this.createBufferStrategy(3);				//fenetre de dessin des pixels
		bs = this.getBufferStrategy();				//assigne a bs la fenetre de dessin
		addMouseListener(this);
		addMouseMotionListener(this);

		this.requestFocus();
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
		setCursor( CurseurInit );
		
		mainMenu = new MainMenu(this);
		mainMenu.setOnScreen(true);
		score = new Score(this);
		score.setOnScreen(false);
		
	}
	
	
	
//===================== METHODES =========================

	public static int getTps(){
	//retourne l iteration de notre temps
		return tps;			
	}
	
	public static void iterateTps(){
	// itere notre temps
		tps++;				
	}
	public void setWorld(World w){
	//Modifie le monde actuel
		
		capacityClicSetter = 0;
		this.world = w;			
	}
	
	public static World getCurrentWorld(){
		return world;
	}
	
	public void update(){
	//met a jour le monde
		if(score.getOnScreen()) return;
		if(mainMenu.getOnScreen()) return;
		if(world == null) return;
		if(world.getFinished()) return;
		world.getSpawner().update();
		Lemmings l;
		boolean cursorOnLemmings = false;
		boolean allDead = true;
		if(world.getLemmingsList().length <= 0) allDead = false;
		for(int i=0;i<world.getLemmingsList().length;i++){
			l = world.getLemmingsList()[i];
			if (l.getAlive()) allDead = false;
        		l.update(); //met a jour la position des lemmings
        		//Le prochain if doit etre le meme que dans mouseClicked (peut etre faire un define...	)	
        		if (l.getInWorld() && l.getPosY()-1.5*l.getHeight()<posYmouse  && l.getPosY()+(0.5*l.getHeight())>posYmouse && l.getPosX()-1*l.getWidth()<posXmouse  && l.getPosX()+1*l.getWidth()>posXmouse){
				
				cursorOnLemmings = true;
			}
        	}
        	world.getOutside().update();
        	if (cursorOnLemmings){
        		if (capacityClicSetter==0) setCursor( CurseurSelect );
        		else setCursor( CurseurSelectRed );
        	}else{
        		if (capacityClicSetter==0) setCursor( CurseurInit );
        		else setCursor( CurseurInitRed );
        	}
        	
        	if(allDead){
        		world.setFinished(true,false);
        	}
        	
        	
        	
	}
	
	public void draw(){
	//dessine toute la fenetre
		Graphics2D g = null; //pointeur de l'outil de dessin
		do{
   			try{
   				g = (Graphics2D)bs.getDrawGraphics(); //recupere l'outil de dessin de la fenetre de dessin
   				if(score.getOnScreen()) score.draw(g);
   				else if(mainMenu.getOnScreen()) mainMenu.draw(g);
        			else{
					if(world!=null){
						world.draw(g); //dessine le monde
						world.getSpawner().draw(g);
						world.getOutside().draw(g);
						for(int i=0;i<world.getLemmingsList().length;i++){
							world.getLemmingsList()[i].draw(g); //dessine les lemmings
						}
					
						if(world.getFinished()){
							moveToScoreScreen();	
						}
						drawSelectZone(g);
					}
					
				}
    			}
    			finally{
           			g.dispose(); //termine l'utilisation de l'outil de dessin
    			}
    			bs.show(); //actualise la fenetre de dessin avec la nouvelle
		} while (bs.contentsLost()); //tant que l'actualisation de la fenetre nest pas complete, recommencer
	}
	
	public static void waitForFrame(long preTime){
		long delta = System.currentTimeMillis()-preTime;
		if (delta<17) {
			delta = (long)17 - delta;
			pause((int)delta);
		}
	}
	
	public static void pause(int ms){
	//Pause le monde
		try{Thread.sleep(ms);}catch(Exception e){};
	}
	
	public void drawSelectZone(Graphics2D g){
		if ( posXmouse > world.getPosXcapacity1() && posXmouse < world.getPosXcapacity1()+60
		&& posYmouse > world.getPosYcapacity() && posYmouse < world.getPosYcapacity()+60){
		//remplacer 60 par un truc propre
			drawCapacityBorder(REGULArBORDER, world.getPosXcapacity1()-1, world.getPosYcapacity()-1);
		}
		else if ( posXmouse > world.getPosXcapacity2() && posXmouse < world.getPosXcapacity2()+60
		&& posYmouse > world.getPosYcapacity() && posYmouse < world.getPosYcapacity()+60){
			drawCapacityBorder(REGULArBORDER, world.getPosXcapacity2()-1, world.getPosYcapacity()-1);
		}
		else if ( posXmouse > world.getPosXcapacity3() && posXmouse < world.getPosXcapacity3()+60
		&& posYmouse > world.getPosYcapacity() && posYmouse < world.getPosYcapacity()+60){
			drawCapacityBorder(REGULArBORDER, world.getPosXcapacity3()-1, world.getPosYcapacity()-1);
		}
		else if ( posXmouse > world.getPosXcapacity4() && posXmouse < world.getPosXcapacity4()+60
		&& posYmouse > world.getPosYcapacity() && posYmouse < world.getPosYcapacity()+60){
			drawCapacityBorder(REGULArBORDER, world.getPosXcapacity4()-1, world.getPosYcapacity()-1);
		}
		
		
	//=======partie select rouge======	
		
		if (capacityClicSetter == 1){
        		drawCapacityBorder(SELECtBORDER, world.getPosXcapacity1()-1, world.getPosYcapacity()-1);
    		}else if (capacityClicSetter == 2){
        		drawCapacityBorder(SELECtBORDER, world.getPosXcapacity2()-1, world.getPosYcapacity()-1);
    		
    		}else if (capacityClicSetter == 3){
        		drawCapacityBorder(SELECtBORDER, world.getPosXcapacity3()-1, world.getPosYcapacity()-1);
    		}else if (capacityClicSetter == 4){
        		drawCapacityBorder(SELECtBORDER, world.getPosXcapacity4()-1, world.getPosYcapacity()-1);
    		}
	}
	
	public void drawCapacityBorder(int borderType, int posX, int posY){
		Graphics2D g = null;
		
		if ( borderType == REGULArBORDER ){
			border = whiteBorder;
		}else{
			border = redBorder;
		}
		
		
		do{
   			try{
        			g = (Graphics2D)bs.getDrawGraphics();
        			g.drawImage(border,posX,posY,null);       			
    			}
    			finally{
           			g.dispose(); //termine l'utilisation de l'outil de dessin
    			}
    			bs.show(); //actualise la fenetre de dessin avec la nouvelle
		} while (bs.contentsLost()); //tant que l'actualisation de la fenetre nest pas complete, recommencer
	}
	
	public void newCurrentWorld(int worldID){
		World w = new World(worldID);
		setWorld(w);
		w.spawnLemmings();
		this.setSize(w.getWidth(),w.getHeight());
	}
	
	public void moveToMainMenu(){
		score.setOnScreen(false);
		mainMenu.setOnScreen(true);
		this.setSize(600,400);
	}
	
	public void moveToScoreScreen(){
		score = new Score(this,world.getVictory());
		score.setOnScreen(true);
		this.setSize(600,400);
	}
	
//===================MOUSE EVENT========================================================
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.
		int posXclic = e.getX();
		int posYclic = e.getY();
		
		if (score.getOnScreen()){
			if(posXclic >= 450 && posXclic <=570 && posYclic>=300 && posYclic <=350){
				moveToMainMenu();
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
        		if (l.getInWorld() && l.getPosY()-1.5*l.getHeight()<posYmouse  && l.getPosY()+(0.5*l.getHeight())>posYmouse && l.getPosX()-1*l.getWidth()<posXmouse  && l.getPosX()+1*l.getWidth()>posXmouse){
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
        			else if (World.BUILDER != l.getJob() && capacityClicSetter == 3 && l.getInWorld() && e.getButton()==1){
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
    		if(posXmouse >= 450 && posXmouse <=570 && posYmouse>=300 && posYmouse <=350){
        		score.showSelectButton();
        	}
        	else{
            		score.showDefaultButton();
        	}
    	}
    	
	public void changeWorldButton(){
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
            if (mainMenu.getOnScreen()){
            	if(posXmouse >= 250 && posXmouse <=370 && posYmouse>=100 && posYmouse <=150){
            		newCurrentWorld(1);
		}
		else if (posXmouse >= 250 && posXmouse <=370 && posYmouse>=160 && posYmouse <=210){
			newCurrentWorld(2);
		}
		else if (posXmouse >= 250 && posXmouse <=370 && posYmouse>=220 && posYmouse <=270){
			newCurrentWorld(3);
		}
		mainMenu.setOnScreen(false);
		return true;
	}
        return false;
        }
	
}


