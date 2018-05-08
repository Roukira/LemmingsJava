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
	
	private SkillBar skillbar;
	
	public static final int UIheight = 100;
	
	public GameScene(Window gw, int width, int height){
		super(gw, width, height);
		gameImage = new BufferedImage(width,height-UIheight,BufferedImage.TYPE_INT_ARGB);
		UIimage = new BufferedImage(width,UIheight,BufferedImage.TYPE_INT_ARGB);
		gamegraphics = gameImage.createGraphics();
		UIgraphics = UIimage.createGraphics();
		input = new InputGame(gw,this);
		skillbar = new SkillBar(this);
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
	
	public static void loadAssets(){
		SkillBar.loadAssets();
	}
	
	public void draw(Graphics2D g){
		render();
		JFrame frame = gw.getFrame();
		screenGraphics.drawImage(gameImage,0,0,null);
		screenGraphics.drawImage(UIimage,0,height-UIheight,null);
		g.drawImage(screenImage,0,0,frame.getContentPane().getWidth(),frame.getContentPane().getHeight(),0,0,width,height,null);
	}
	
	
	public void renderUI(World world){
   		world.getStats().draw(UIgraphics);
   		skillbar.draw(UIgraphics);
		input.draw(UIgraphics);
	}
	
	public SkillBar getSkillBar(){
		return skillbar;
	}

}
