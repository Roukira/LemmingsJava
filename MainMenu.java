import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class MainMenu extends Screen{	
	
	private BufferedImage mainMenuBG;
	private BufferedImage world1;
	private BufferedImage world2;
	private BufferedImage world3;
	private BufferedImage world1Select;
	private BufferedImage world2Select;
	private BufferedImage world3Select;
	private BufferedImage world1Button;
	private BufferedImage world2Button;
	private BufferedImage world3Button;
	private boolean w1default = true;
	private boolean w2default = true;
	private boolean w3default = true;
	
	public MainMenu(Window gw,int width,int height){
		super(gw,width,height);
		try{
			mainMenuBG = ImageIO.read(new File("mainmenu/Home.png"));
			world1 = ImageIO.read(new File("mainmenu/ButtonWorld1.png"));
			world2 = ImageIO.read(new File("mainmenu/ButtonWorld2.png"));
			world3 = ImageIO.read(new File("mainmenu/ButtonWorld3.png"));
			world1Select = ImageIO.read(new File("mainmenu/ButtonWorld1Select.png"));
			world2Select = ImageIO.read(new File("mainmenu/ButtonWorld2Select.png"));
			world3Select = ImageIO.read(new File("mainmenu/ButtonWorld3Select.png"));
 		}catch(Exception e){e.printStackTrace();}
		world1Button = world1;
		world2Button = world2;
		world3Button = world3;
		
		input = new InputMainMenu(gw,this);
	}
	
	public void render(){
		screenGraphics.drawImage(mainMenuBG,0,0,null);
		screenGraphics.drawImage(world1Button,250,100,null);
		screenGraphics.drawImage(world2Button,250,160,null);
		screenGraphics.drawImage(world3Button,250,220,null);
	}
	
	public void showSelectButton( int worldNumber){
		if (worldNumber==1 && w1default){
			world1Button = world1Select;
			w1default = false;
		}else if(worldNumber==2 && w2default){
			world2Button = world2Select;	
			w2default = false;	
		}else if(worldNumber==3 && w3default){
			world3Button = world3Select;
			w3default = false;
		}
	}
	
	public void showDefaultButton(int worldNumber){
		if (worldNumber==1 && !w1default){
			world1Button = world1;
			w1default = true;
		}else if(worldNumber==2 && !w2default){
			world2Button = world2;
			w2default = true;		
		}else if(worldNumber==3 && !w3default){
			world3Button = world3;
			w3default = true;
		}
	}

}
