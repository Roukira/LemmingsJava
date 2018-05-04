import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class GameScene extends Screen{
	
	private BufferedImage gameImage;
	private BufferedImage UIimage;
	private Graphics2D gamegraphics;
	private Graphics2D UIgraphics;
	
	public GameScene(Window gw, int width, int height){
		super(gw, width, height);
		gameImage = new BufferedImage(width,height-100,BufferedImage.TYPE_INT_ARGB);
		UIimage = new BufferedImage(width,100,BufferedImage.TYPE_INT_ARGB);
		gamegraphics = gameImage.createGraphics();
		UIgraphics = UIimage.createGraphics();
		input = new InputGame(gw,this);
	}
	
	public void render(){
		World world = gw.getCurrentWorld();
   		if(world!=null){
			world.draw(gamegraphics); //dessine le monde
			world.getSpawner().draw(gamegraphics);
			world.getOutside().draw(gamegraphics);
			for(int i=0;i<world.getLemmingsList().length;i++){
				world.getLemmingsList()[i].draw(gamegraphics); //dessine les lemmings
			}
		
			if(world.getFinished()) gw.moveToScoreScreen();	
			else renderUI(world);
		}
	}
	
	public void draw(Graphics2D g){
		render();
		JFrame frame = gw.getFrame();
		screenGraphics.drawImage(gameImage,0,0,null);
		screenGraphics.drawImage(UIimage,0,height-100,null);
		g.drawImage(screenImage,0,0,frame.getContentPane().getWidth(),frame.getContentPane().getHeight(),0,0,width,height,null);
	}
	
	
	public void renderUI(World world){
   		world.getStats().draw(UIgraphics);
		input.draw(UIgraphics);
	}

}
