import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;


public final class GameWindow extends JFrame implements MouseListener,MouseMotionListener{			
//Sous-classe de la classe de fenetre java JFrame || class final car il n y aura qu une seule fenetre

//==================== ATTRIBUTS ========================

	private BufferStrategy bs;			//fenetre de dessin		
	private World world;				//monde
	private static int tps = 0;			//compteur de temps
	private BufferedImage victory;
	private Toolkit tk = Toolkit.getDefaultToolkit();
	private BufferedImage imageCurseurSelect;
	private BufferedImage imageCurseurInit;
	private static int posXmouse;
	private static int posYmouse;
	
	private Cursor CurseurInit;
	private Cursor CurseurSelect;
	
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
		this.createBufferStrategy(2);				//fenetre de dessin des pixels
		bs = this.getBufferStrategy();				//assigne a bs la fenetre de dessin
		try{
			victory = ImageIO.read(new File("world/victory.png"));
			
		}catch(Exception e){e.printStackTrace();}
		
		addMouseListener(this);
		addMouseMotionListener(this);

		this.requestFocus();
		try{
			imageCurseurSelect = ImageIO.read(new File("cursor/cursorSelect.png"));
			imageCurseurInit = ImageIO.read(new File("cursor/cursorInit.png"));
			
		}catch(Exception e){e.printStackTrace();}
		
		CurseurInit = tk.createCustomCursor( imageCurseurInit, new Point( 10, 10 ), "Pointeur" );
		CurseurSelect = tk.createCustomCursor( imageCurseurSelect, new Point( 10, 10 ), "Pointeur" );
		setCursor( CurseurInit );
		
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
		this.world = w;			
	}
	
	public void update(){
	//met a jour le monde
		world.getSpawner().update();
		Lemmings l;
		boolean cursorOnLemmings = false;
		for(int i=0;i<world.getLemmingsList().length;i++){
			l = world.getLemmingsList()[i];
        		l.move(world); //met a jour la position des lemmings
        		//Le prochain if doit etre le meme que dans mouseClicked (peut etre faire un define...		
        		if ( l.getPosY()-3*l.height<posYmouse  && l.getPosY()+2*l.height>posYmouse && l.getPosX()-3*l.width<posXmouse  && l.getPosX()+2*l.width>posXmouse){
				
				cursorOnLemmings = true;
			}
        	}
        	world.getOutside().update();
        	if (cursorOnLemmings) setCursor( CurseurSelect );
        	else setCursor( CurseurInit );
        	
        	
	}
	
	public void draw(){
	//dessine toute la fenetre
		Graphics2D g = null; //pointeur de l'outil de dessin
		do{
   			try{
        			g = (Graphics2D)bs.getDrawGraphics(); //recupere l'outil de dessin de la fenetre de dessin
        			if(world!=null) world.draw(g); //dessine le monde
        			world.getSpawner().draw(g);
        			world.getOutside().draw(g);
        			for(int i=0;i<world.getLemmingsList().length;i++){
        				world.getLemmingsList()[i].draw(g); //dessine les lemmings
        			}
    			}
    			finally{
           			g.dispose(); //termine l'utilisation de l'outil de dessin
    			}
    			bs.show(); //actualise la fenetre de dessin avec la nouvelle
		} while (bs.contentsLost()); //tant que l'actualisation de la fenetre nest pas complete, recommencer
	}
	
	public void drawVictory(){
		Graphics2D g = null;
		do{
   			try{
        			g = (Graphics2D)bs.getDrawGraphics();
        			g.drawImage(victory,0,0,null);
        			
    			}
    			finally{
           			g.dispose(); //termine l'utilisation de l'outil de dessin
    			}
    			bs.show(); //actualise la fenetre de dessin avec la nouvelle
		} while (bs.contentsLost()); //tant que l'actualisation de la fenetre nest pas complete, recommencer
	}
	
	public static void pause(int ms){
	//Pause le monde
		try{Thread.sleep(ms);}catch(Exception e){};
	}
	
	
	
//===================MOUSE EVENT========================================================
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.
		int posXclic = e.getX();
		int posYclic = e.getY();
		int posXlem;
		int posYlem;	
		Lemmings l;
		for(int i=0;i<world.getLemmingsList().length;i++){
			l = world.getLemmingsList()[i];
			posXlem = l.getPosX();
			posYlem = l.getPosY();	
        		if ( posYlem-3*l.height<posYclic  && posYlem+2*l.height>posYclic && posXlem-3*l.width<posXclic  && posXlem+2*l.width>posXclic){
        			if (World.WALKER == l.getJob() && e.getButton()==1){
        			//si la methode getButton retourne 1 c est le clic gauche 
        				world.getLemmingsList()[i] = l.changeJob(World.STOPPER);
        				Lemmings[] tab = new Lemmings[1];
					tab[0] = world.getLemmingsList()[i];
					world.getOutside().addLemmings(tab);
					world.getOutside().removeLemmingFromList(l.getId());
					return;
        			}
        			else if ( World.STOPPER == l.getJob() && e.getButton()==3){ 
        			//si la methode getButton retourne 3 c est le clic gauche	
        				world.getLemmingsList()[i] = l.changeJob(World.WALKER);
        				Lemmings[] tab = new Lemmings[1];
					tab[0] = world.getLemmingsList()[i];
					world.getOutside().addLemmings(tab);
					world.getOutside().removeLemmingFromList(l.getId());
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
	}

    	public void mouseDragged(MouseEvent e) {}
    	//a chaque mouvement ou un bouton de la souris est enfonce

}

