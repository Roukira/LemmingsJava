import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class MainMenu{

	private GameWindow gw;
	private boolean onMainMenu = false;
	private BufferedImage mainMenuBG;
	private BufferedImage world1;
	private BufferedImage world2;
	private BufferedImage world3;
	
	public MainMenu(GameWindow gw){
		this.gw = gw;
		try{
			mainMenuBG = ImageIO.read(new File("mainmenu/Home.png"));
			world1 = ImageIO.read(new File("mainmenu/ButtonWorld1.png"));
			world2 = ImageIO.read(new File("mainmenu/ButtonWorld2.png"));
			world3 = ImageIO.read(new File("mainmenu/ButtonWorld3.png"));
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void draw(Graphics2D g){
		if(!onMainMenu) return;
		g.drawImage(mainMenuBG,0,0,null);
		g.drawImage(world1,250,100,null);
		g.drawImage(world2,250,160,null);
		g.drawImage(world3,250,220,null);
	}
	
	
	public boolean getOnMainMenu(){
		return onMainMenu;
	}
	
	public void setOnMainMenu(boolean onMainMenu){
		this.onMainMenu = onMainMenu;
	}
	

}
