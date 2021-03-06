import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Score extends Screen{

	private static BufferedImage scoreVictory;
	private static BufferedImage scoreDefeat;
	private static BufferedImage scoreFG;
	private static BufferedImage scoreBG;
	private static BufferedImage mainMenu;
	private static BufferedImage mainMenuSelect;
	private static BufferedImage buttonMainMenu;
	private static BufferedImage resetMapButtonDefault;
	private static BufferedImage resetMapButtonHover;
	private static BufferedImage resetMapButton;
	private boolean resetMapDefault = true;
	private boolean mainDefault = true;
	private boolean victory;
	private int nbLemmings;
	private int nbLemmingsAlive;
	private int nbLemmingsDead;
	private String completion;
	private int victoryCondition;
	
	public static final int RES_WIDTH = 600;
	public static final int RES_HEIGHT = 400;
	
	public Score(Window gw, int width, int height){
		super(gw,width,height);
		loadAssets();
		buttonMainMenu = mainMenu;
		resetMapButton = resetMapButtonDefault;
		input = new InputScore(gw,this);
	}
	
	public static void loadAssets(){
		try{
			scoreBG = ImageIO.read(new File("score/Home.png"));
			mainMenu = ImageIO.read(new File("score/ButtonMainMenu.png"));
			scoreVictory = ImageIO.read(new File("score/victory.png"));
			scoreDefeat = ImageIO.read(new File("score/defeat.png"));
			mainMenuSelect = ImageIO.read(new File("score/ButtonMainMenuSelect.png"));
			resetMapButtonDefault = ImageIO.read(new File("world/resetMapbutton.png"));
			resetMapButtonHover = ImageIO.read(new File("world/resetMapbuttonHover.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public Score(Window gw, int victoryCondition, int width, int height){
		this(gw, width,height);
		this.victoryCondition = victoryCondition;
		calculateScore();
	}
	
	public void calculateScore(){
		Lemmings[] list = gw.getCurrentWorld().getLemmingsList();
		nbLemmings = 0;
		nbLemmingsAlive = 0;
		nbLemmingsDead = 0;
		completion = "";
		for(int i=0;i<list.length;i++){
			nbLemmings++;
			if(list[i].getAlive()) nbLemmingsAlive++;
			else nbLemmingsDead++;
		}
		double completionCount = 100*(nbLemmingsAlive*1.0)/nbLemmings;
		if(completionCount>=victoryCondition) scoreFG = scoreVictory;
		else scoreFG = scoreDefeat;
		completion = String.format("%.1f",completionCount);
	}
	
	public void render(){
		screenGraphics.drawImage(scoreBG,0,0,null);
		screenGraphics.drawImage(scoreFG,0,0,null);
		screenGraphics.drawImage(buttonMainMenu,450,300,null);
		screenGraphics.setColor(Color.white);
		screenGraphics.setFont(new Font("default", Font.BOLD, 12));
		screenGraphics.drawString("Number of Lemmings : "+nbLemmings,50,300);
		screenGraphics.drawString("Deaths : "+nbLemmingsDead,50,330);
		screenGraphics.drawString("Completion : "+completion+"%",50,360);
		screenGraphics.drawImage(resetMapButton,560,360,null);
	}
	
	public void showSelectButton(){
		if (mainDefault){
			buttonMainMenu = mainMenuSelect;
			mainDefault = false;
		}
	}
	
	public void showDefaultButton(){
		if (!mainDefault){
			buttonMainMenu = mainMenu;
			mainDefault = true;
		}
	}
	
	public void showResetMapSelectButton(){
		if (resetMapDefault){
			resetMapButton = resetMapButtonHover;
			resetMapDefault = false;
		}
	}
	
	public void showResetMapDefaultButton(){
		if (!resetMapDefault){
			resetMapButton = resetMapButtonDefault;
			resetMapDefault = true;
		}
	}

}
