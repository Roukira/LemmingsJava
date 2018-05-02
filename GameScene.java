import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class GameScene extends Screen{
	
	
	private BufferedImage UIimage;
	private Graphics2D UIgraphics;
	
	public GameScene(Window gw, int width, int height){
		super(gw, width, height);
		UIimage = new BufferedImage(width,100,BufferedImage.TYPE_INT_ARGB);
		UIgraphics = UIimage.createGraphics();
		input = new InputGame(gw,this);
	}
	
	public void render(){
		World world = gw.getCurrentWorld();
   		if(world!=null){
			world.draw(screenGraphics); //dessine le monde
			world.getSpawner().draw(screenGraphics);
			world.getOutside().draw(screenGraphics);
			for(int i=0;i<world.getLemmingsList().length;i++){
				world.getLemmingsList()[i].draw(screenGraphics); //dessine les lemmings
			}
		
			if(world.getFinished()) gw.moveToScoreScreen();	
			else renderUI(world);
		}
	}
	
	public void draw(Graphics2D g){
		render();
		JFrame frame = gw.getFrame();
		g.drawImage(screenImage,0,0,frame.getContentPane().getWidth(),frame.getContentPane().getHeight()-100,0,0,width,height,null);
		//g.drawImage(UIimage,0,frame.getContentPane().getHeight()-100,frame.getContentPane().getWidth(),frame.getContentPane().getHeight(),0,frame.getContentPane().getHeight()-100,width,100,null);
		g.drawImage(UIimage,0,frame.getContentPane().getHeight()-100,frame.getContentPane().getWidth(),frame.getContentPane().getHeight(),0,0,width,100,null);
	}
	
	
	public void renderUI(World world){
   		world.getStats().draw(UIgraphics);
		input.draw(UIgraphics);
	}

}
