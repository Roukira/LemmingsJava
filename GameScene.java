import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class GameScene extends Screen{
	
	private Canvas canvasCapacity;
	private BufferStrategy bs2;
	private InputGameUI inputCapacity;
	
	public GameScene(Window gw){
		super(gw);
		canvasCapacity = new Canvas();
		//canvasCapacity.createBufferStrategy(3);
		bs2 = canvasCapacity.getBufferStrategy();
		
		input = new InputGame(gw,this);
		inputCapacity = new InputGameUI(gw,this);
	}
	
	public void draw(Graphics2D g){
		World world = gw.getCurrentWorld();
   		if(world!=null){
			world.draw(g); //dessine le monde
			world.getSpawner().draw(g);
			world.getOutside().draw(g);
			for(int i=0;i<world.getLemmingsList().length;i++){
				world.getLemmingsList()[i].draw(g); //dessine les lemmings
			}
		
			if(world.getFinished()) gw.moveToScoreScreen();	
			else drawCapacity(world);
		}
	}
	
	
	public void drawCapacity(World world){
		Graphics2D g2 = null;
		do{
   			try{
   				g2 = (Graphics2D)bs2.getDrawGraphics(); //recupere l'outil de dessin de la fenetre de dessin
   				world.getStats().draw(g2);
				inputCapacity.draw(g2);
    			}
    			finally{
           			g2.dispose(); //termine l'utilisation de l'outil de dessin
    			}
    			bs2.show(); //actualise la fenetre de dessin avec la nouvelle
		} while (bs2.contentsLost()); //tant que l'actualisation de la fenetre nest pas complete, recommencer
		
	}
	public Canvas getCanvasCapacity(){
		return canvasCapacity;
	}
	
	public InputGameUI getCapacityInput(){
		return inputCapacity;
	}
	
	public void createCapacityBufferStrategy(){
		canvasCapacity.createBufferStrategy(3);
		bs2 = canvasCapacity.getBufferStrategy();
	}
	
	public void createBufferStrategy(){
		super.createBufferStrategy();
		createCapacityBufferStrategy();
	}

}
