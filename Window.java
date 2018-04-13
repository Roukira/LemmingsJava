import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;


public class Window {

	private JFrame frame;
	private Canvas canvas;
	private Canvas canvasCapacity;
	private BufferStrategy bs;
	private BufferStrategy bs2;
	private Input input;
	
	public long preDrawTime;
	
	public static double FPS = 60.0;
	public static double ns = 1000000000/FPS;
	
	private static int tps = 0;			//compteur de temps
	
	private static World world;
	
	private MainMenu mainMenu;
	private Score score;
	
	private String title;
	private int width, height;
	
	public Window(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
		
		createWindow();
	}
	
	private void createWindow(){
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		canvasCapacity = new Canvas();
		//canvas.setPreferredSize(new Dimension(width, 100));
		
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(canvasCapacity, BorderLayout.SOUTH);
		frame.setVisible(true);
		canvas.createBufferStrategy(3);				//fenetre de dessin des pixels
		bs = canvas.getBufferStrategy();				//assigne a bs la fenetre de dessin
		canvasCapacity.createBufferStrategy(3);				//fenetre de dessin des pixels
		bs2 = canvasCapacity.getBufferStrategy();				//assigne a bs la fenetre de dessin
		
		input = new Input(this);
		frame.pack();
		
		mainMenu = new MainMenu(this);
		mainMenu.setOnScreen(true);
		score = new Score(this);
		score.setOnScreen(false);
		
		preDrawTime = System.currentTimeMillis();
		
	}

	public Canvas getCanvas(){
		return canvas;
	}
	
	public Canvas getCanvasCapacity(){
		return canvasCapacity;
	}
	
	public JFrame getFrame(){
		return frame;
	}
	
	public Score getScore(){
		return score;
	}
	
	public MainMenu getMainMenu(){
		return mainMenu;
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
		
		input.setCapacityClicSetter(0);
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

		boolean allDead = true;
		if(world.getLemmingsList().length <= 0) allDead = false;
		
		Lemmings l;
		for(int i=0;i<world.getLemmingsList().length;i++){
			l = world.getLemmingsList()[i];
        		l.update();
        		if (l.getAlive()) allDead = false;
        		
        	}
        	
        	input.update();
        	
        	world.getOutside().update();
        	
        	world.getStats().update();
        	
        	if(allDead){
        		world.setFinished(true);
        		canvasCapacity.setSize(0,0);
        		
        	}
	}
	
	public void draw(){
	//dessine toute la fenetre
		Graphics2D g = null; //pointeur de l'outil de dessin
		Graphics2D g2 = null;
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
							canvasCapacity.setSize(0,0);
						}
					}
					
				}
    			}
    			finally{
           			g.dispose(); //termine l'utilisation de l'outil de dessin
    			}
    			bs.show(); //actualise la fenetre de dessin avec la nouvelle
		} while (bs.contentsLost()); //tant que l'actualisation de la fenetre nest pas complete, recommencer
		
		if(world != null && !world.getFinished()){
			do{
	   			try{
	   				g2 = (Graphics2D)bs2.getDrawGraphics(); //recupere l'outil de dessin de la fenetre de dessin
	   				world.drawCap(g2);
					world.getStats().draw(g2);
					input.drawSelectZone(g2);
	    			}
	    			finally{
	    				g2.dispose();
	    			}
	    			bs2.show();
			} while (bs2.contentsLost()); //tant que l'actualisation de la fenetre nest pas complete, recommencer
		}
		Toolkit.getDefaultToolkit().sync(); //force la fenetre a update meme qd souris en dehors
	}
	
	public void newCurrentWorld(int worldID){
		World w = new World(worldID);
		setWorld(w);
		w.spawnLemmings();
		frame.setSize(w.getWidth(),w.getHeight()+100);
		canvas.setSize(w.getWidth(),w.getHeight());
		canvasCapacity.setSize(w.getWidth(),100);
		frame.setLocationRelativeTo(null);
	}
	
	public void moveToMainMenu(){
		score.setOnScreen(false);
		mainMenu.setOnScreen(true);
		frame.setSize(600,400);
		canvas.setSize(600,400);
		frame.setLocationRelativeTo(null);
	}
	
	public void moveToScoreScreen(){
		score = new Score(this,world.getVictoryCondition());
		score.setOnScreen(true);
		frame.setSize(600,400);
		canvas.setSize(600,400);
		frame.setLocationRelativeTo(null);
	}
	
	public void resetMap(){
		mainMenu.setOnScreen(false);
		score.setOnScreen(false);
		int newID = world.getID();
		world = null;
		newCurrentWorld(newID);
	}
	
	
}
