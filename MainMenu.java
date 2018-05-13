import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class MainMenu extends Screen{	
	
	private static BufferedImage mainMenuBG;
	private static BufferedImage world1;
	private static BufferedImage world2;
	private static BufferedImage world3;
	private static BufferedImage world4;
	private static BufferedImage world5;
	private static BufferedImage world6;
	
	private static BufferedImage world1Select;
	private static BufferedImage world2Select;
	private static BufferedImage world3Select;
	private static BufferedImage world4Select;
	private static BufferedImage world5Select;
	private static BufferedImage world6Select;
	
	private static BufferedImage world1Button;
	private static BufferedImage world2Button;
	private static BufferedImage world3Button;
	private static BufferedImage world4Button;
	private static BufferedImage world5Button;
	private static BufferedImage world6Button;
	
	private boolean w1default = true;
	private boolean w2default = true;
	private boolean w3default = true;
	private boolean w4default = true;
	private boolean w5default = true;
	private boolean w6default = true;
	
	public static final int RES_WIDTH = 600;
	public static final int RES_HEIGHT = 400;
	
	public MainMenu(Window gw,int width,int height){
		super(gw,width,height);
		loadAssets();
		world1Button = world1;
		world2Button = world2;
		world3Button = world3;
		world4Button = world4;
		world5Button = world5;
		world6Button = world6;
		
		input = new InputMainMenu(gw,this);
	}
	
	public static void loadAssets(){
		try{
			mainMenuBG = ImageIO.read(new File("mainmenu/Home.png"));
			world1 = ImageIO.read(new File("mainmenu/ButtonWorld1.png"));
			world2 = ImageIO.read(new File("mainmenu/ButtonWorld2.png"));
			world3 = ImageIO.read(new File("mainmenu/ButtonWorld3.png"));
			world4 = ImageIO.read(new File("mainmenu/ButtonWorld4.png"));
			world5 = ImageIO.read(new File("mainmenu/ButtonWorld5.png"));
			world6 = ImageIO.read(new File("mainmenu/ButtonWorld6.png"));
			world1Select = ImageIO.read(new File("mainmenu/ButtonWorld1Select.png"));
			world2Select = ImageIO.read(new File("mainmenu/ButtonWorld2Select.png"));
			world3Select = ImageIO.read(new File("mainmenu/ButtonWorld3Select.png"));
			world4Select = ImageIO.read(new File("mainmenu/ButtonWorld4Select.png"));
			world5Select = ImageIO.read(new File("mainmenu/ButtonWorld5Select.png"));
			world6Select = ImageIO.read(new File("mainmenu/ButtonWorld6Select.png"));
 		}catch(Exception e){e.printStackTrace();}
	}
	
	public void render(){
		screenGraphics.drawImage(mainMenuBG,0,0,null);
		screenGraphics.drawImage(world1Button,100,100,null);
		screenGraphics.drawImage(world2Button,100,160,null);
		screenGraphics.drawImage(world3Button,100,220,null);
		screenGraphics.drawImage(world4Button,100,280,null);
		screenGraphics.drawImage(world5Button,300,100,null);
		screenGraphics.drawImage(world6Button,300,160,null);
	}
	
	public void showSelectButton( int worldNumber){
		if (worldNumber==1 && w1default){
			world1Button = world1Select;
			w1default = false;
		}
		else if(worldNumber==2 && w2default){
			world2Button = world2Select;	
			w2default = false;	
		}
		else if(worldNumber==3 && w3default){
			world3Button = world3Select;
			w3default = false;
		}
		else if(worldNumber==4 && w4default){
			world4Button = world4Select;
			w4default = false;
		}
		else if(worldNumber==5 && w5default){
			world5Button = world5Select;
			w5default = false;
		}
		else if(worldNumber==6 && w6default){
			world6Button = world6Select;
			w6default = false;
		}
	}
	
	public void showDefaultButton(int worldNumber){
		if (worldNumber==1 && !w1default){
			world1Button = world1;
			w1default = true;
		}
		else if(worldNumber==2 && !w2default){
			world2Button = world2;
			w2default = true;		
		}
		else if(worldNumber==3 && !w3default){
			world3Button = world3;
			w3default = true;
		}
		else if(worldNumber==4 && !w4default){
			world4Button = world4;
			w4default = true;
		}
		else if(worldNumber==5 && !w5default){
			world5Button = world5;
			w5default = true;
		}
		else if(worldNumber==6 && !w6default){
			world6Button = world6;
			w6default = true;
		}
	}

}
