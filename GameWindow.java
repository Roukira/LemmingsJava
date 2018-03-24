import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;

public final class GameWindow extends JFrame{		//Sous-classe de la classe de fenetre java JFrame || class final car il n y aura qu une seule fenetre

//==================== ATTRIBUTS ========================

	private BufferStrategy bs;			//fenetre de dessin		
	private World world;				//monde
	private Lemmings[] LemmingsList;		//liste des lemmings
	private static int tps = 0;			//compteur de temps
	
//================== CONSTRUCTEURS ======================
	
	public GameWindow(String name, Lemmings[] LemmingsList){
		this(name,LemmingsList,600,400);
	}
	
	public GameWindow(String name, Lemmings[] LemmingsList,int width,int height){
		this.setTitle(name); 					//titre de fenetre
		this.setSize(width,height); 				//change la taille
		this.setLocationRelativeTo(null); 			//place au centre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	//quitte avec la croix
		this.setResizable(false); 				//empeche resize
		this.setVisible(true); 					//rend visible
		this.createBufferStrategy(2);				//fenetre de dessin des pixels
		bs = this.getBufferStrategy();				//assigne a bs la fenetre de dessin
		this.LemmingsList = LemmingsList;			//reference vers la liste des lemmings
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
		for(int i=0;i<LemmingsList.length;i++){
        		LemmingsList[i].move(world); //met a jour la position des lemmings		
        	}
	}
	
	public void draw(){
	//dessine toute la fenetre
		Graphics2D g = null; //pointeur de l'outil de dessin
		do{
   			try{
        			g = (Graphics2D)bs.getDrawGraphics(); //recupere l'outil de dessin de la fenetre de dessin
        			if(world!=null) world.draw(g); //dessine le monde
        			Spawner s = world.getSpawner();
        			s.draw(g);
        			s.spawnLemmings(world);
        			for(int i=0;i<LemmingsList.length;i++){
        				LemmingsList[i].draw(g); //met a jour la position des lemmings		
        			}
        			
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


}
