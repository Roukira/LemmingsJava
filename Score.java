import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Score extends Screen{

	private BufferedImage scoreVictory;
	private BufferedImage scoreDefeat;
	private BufferedImage scoreFG;
	private BufferedImage scoreBG;
	private BufferedImage mainMenu;
	private BufferedImage mainMenuSelect;
	private BufferedImage buttonMainMenu;
	private BufferedImage resetMapButtonDefault;
	private BufferedImage resetMapButtonHover;
	private BufferedImage resetMapButton;
	private boolean resetMapDefault = true;
	private boolean mainDefault = true;
	private boolean victory;
	private int nbLemmings;
	private int nbLemmingsAlive;
	private int nbLemmingsDead;
	private String completion;
	private int victoryCondition;
	
	public Score(Window gw){
		super(gw);
		try{
			scoreBG = ImageIO.read(new File("score/Home.png"));
			mainMenu = ImageIO.read(new File("score/ButtonMainMenu.png"));
			scoreVictory = ImageIO.read(new File("score/victory.png"));
			scoreDefeat = ImageIO.read(new File("score/defeat.png"));
			mainMenuSelect = ImageIO.read(new File("score/ButtonMainMenuSelect.png"));
			resetMapButtonDefault = ImageIO.read(new File("world/resetMapbutton.png"));
			resetMapButtonHover = ImageIO.read(new File("world/resetMapbuttonHover.png"));
			
		}catch(Exception e){e.printStackTrace();}
		buttonMainMenu = mainMenu;
		resetMapButton = resetMapButtonDefault;
	}
	
	public Score(Window gw, int victoryCondition){
		this(gw);
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
	
	public void draw(Graphics2D g){
		super.draw(g);
		g.drawImage(scoreBG,0,0,null);
		g.drawImage(scoreFG,0,0,null);
		g.drawImage(buttonMainMenu,450,300,null);
		g.setColor(Color.white);
		g.setFont(new Font("default", Font.BOLD, 12));
		g.drawString("Number of Lemmings : "+nbLemmings,50,300);
		g.drawString("Deaths : "+nbLemmingsDead,50,330);
		g.drawString("Completion : "+completion+"%",50,360);
		g.drawImage(resetMapButton,560,360,null);
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
