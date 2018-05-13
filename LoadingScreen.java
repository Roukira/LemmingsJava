import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class LoadingScreen extends Screen implements Runnable{

	private BufferedImage loadingImage;	
	private String loadingText;
	
	public static final int RES_WIDTH = 600;
	public static final int RES_HEIGHT = 400;
	
	public LoadingScreen(Window gw, int width, int height){
		super(gw,width,height);
		try{
			loadingImage = ImageIO.read(new File("loadingscreen/loading.png"));
		}catch(Exception e){e.printStackTrace();}
		input = new InputLoading(gw,this);
		loadingText = "Loading assets...";
	}
	
	public void loadAssets(){
		loadingText = "Loading thing assets...";
		Thing.loadAssets();
		
		loadingText = "Loading lemmings assets...";
		loadLemmingsAssets();
		
		loadingText = "Loading items assets...";
		loadItemsAssets();
		
		loadingText = "Loading input assets...";
		loadInputsAssets();
		
		loadingText = "Loading game scene assets...";
		GameScene.loadAssets();
		
	}
	
	public void loadLemmingsAssets(){
		Lemmings.loadAssets();
		
		Walker.loadAssets();	
		Stopper.loadAssets();
		Digger.loadAssets();
		Builder.loadAssets();
		
		Basher.loadAssets();
		Excavater.loadAssets();
		Miner.loadAssets();
	}
	
	public void loadItemsAssets(){
		Item.loadAssets();
		Outside.loadAssets();
		Spawner.loadAssets();
		SpitFire.loadAssets();
	}
	
	public void loadInputsAssets(){
		Input.loadAssets();
		
		InputGame.loadAssets();
		InputMainMenu.loadAssets();
		InputMainMenu.loadAssets();
		InputScore.loadAssets();
	}
	
	public void run(){
		System.out.println("running");
		loadAssets();
		loadingText = "loading world...";
		while(gw.getCurrentWorld() == null){
			System.out.println("waiting for world");
			try{
          	  		Thread.sleep(1000);
        		}catch(InterruptedException e){e.printStackTrace();}
		}
		gw.moveToGameScene();
	}
	
	/*public static void main(String[] args){
		
	}*/
	
	public void render(){
		screenGraphics.drawImage(loadingImage,0,0,null);
		screenGraphics.setColor(Color.white);
		screenGraphics.setFont(new Font("default", Font.BOLD, 12));
		screenGraphics.drawString(loadingText,40,360);
	}

}
