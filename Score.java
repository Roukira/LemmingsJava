import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Score extends Screen{

	private BufferedImage scoreBG;
	private BufferedImage scoreFG;
	private BufferedImage mainMenu;
	private BufferedImage mainMenuSelect;
	private BufferedImage buttonMainMenu;
	private boolean mainDefault = true;
	private boolean victory;
	private int nbLemmings;
	private int nbLemmingsAlive;
	private int nbLemmingsDead;
	private String completion;
	
	public Score(GameWindow gw){
		super(gw);
		try{
			scoreBG = ImageIO.read(new File("score/Home.png"));
			mainMenu = ImageIO.read(new File("score/ButtonMainMenu.png"));
			mainMenuSelect = ImageIO.read(new File("score/ButtonMainMenuSelect.png"));
			
		}catch(Exception e){e.printStackTrace();}
		buttonMainMenu = mainMenu;
	}
	
	public Score(GameWindow gw, boolean victory){
		this(gw);
		try{
			if(victory) scoreFG = ImageIO.read(new File("score/victory.png"));
			else scoreFG = ImageIO.read(new File("score/defeat.png"));
			
		}catch(Exception e){e.printStackTrace();}
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
		if (nbLemmings == nbLemmingsAlive) completion = "100";
		else completion = String.format("%.1f",100*(nbLemmingsAlive*1.0)/nbLemmings);
	}
	
	public void draw(Graphics g){
		super.draw(g);
		g.drawImage(scoreBG,0,0,null);
		g.drawImage(scoreFG,0,0,null);
		g.drawImage(buttonMainMenu,450,300,null);
		g.setColor(Color.white);
		g.setFont(new Font("default", Font.BOLD, 12));
		g.drawString("Number of Lemmings : "+nbLemmings,50,300);
		g.drawString("Deaths : "+nbLemmingsDead,50,330);
		g.drawString("Completion : "+completion+"%",50,360);
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

}
