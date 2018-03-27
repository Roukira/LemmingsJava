import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;



public final class GameWindow extends JFrame implements MouseListener{		
//Sous-classe de la classe de fenetre java JFrame || class final car il n y aura qu une seule fenetre

//==================== ATTRIBUTS ========================

	private BufferStrategy bs;			//fenetre de dessin		
	private World world;				//monde
	private static int tps = 0;			//compteur de temps
	private BufferedImage victory;
	
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
		this.requestFocus();
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
		
		for(int i=0;i<world.getLemmingsList().length;i++){
			
        		world.getLemmingsList()[i].move(world); //met a jour la position des lemmings		
        		
        	}
        	world.getOutside().update();
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
	
	
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.
		//System.out.println("clic a la position : "+e.getX()+","+e.getY());
		int posXclic = e.getX();
		int posYclic = e.getY();
		int posXlem;
		int posYlem;	
		for(int i=0;i<world.getLemmingsList().length;i++){
			Lemmings l = world.getLemmingsList()[i]; //met a jour la position des lemmings	
			posXlem = l.getPosX();
			posYlem = l.getPosY();	
        		if ( posYlem-l.height<posYclic  && posYlem>posYclic && posXlem-l.width<posXclic  && posXlem>posXclic){
        			System.out.println("///////////////\n");
        			System.out.println(l);
        			System.out.println("\n///////////////");
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
	
}
