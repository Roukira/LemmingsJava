import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Stats implements Renderable,Updatable{
	
	private World w;
	
	private int nbLemmings;
	private int nbLemmingsAlive;
	private int nbLemmingsWin;
	private int nbLemmingsDead;
	private String completion;
		
	public Stats(World w){
		if (w != null){
			this.w = w;
			nbLemmings = w.getLemmingsList().length;
			nbLemmingsAlive = nbLemmings;
			nbLemmingsWin = 0;
			nbLemmingsDead = 0;
			completion = "0";
		}
	}
	
	public void update(){
		if(w.getFinished()){
			if (nbLemmings == nbLemmingsWin) completion = "100";
			else completion = String.format("%.1f",100*(nbLemmingsWin*1.0)/nbLemmings);
			w = null;
		}
		
		if (w == null) return;
		nbLemmingsAlive = 0;
		nbLemmingsDead = 0;
		nbLemmingsWin = 0;
		Lemmings[] list = w.getLemmingsList();
		for(int i=0;i<list.length;i++){
			if(list[i].getAlive() && list[i].getInWorld()) nbLemmingsAlive++;
			else if(list[i].getAlive() && !list[i].getInWorld() && list[i].getSpawned()) nbLemmingsWin++;
			else if(list[i].getSpawned()) nbLemmingsDead++;
		}
		if (nbLemmings == nbLemmingsWin){
			completion = "100";
		}
		else{
			completion = String.format("%.1f",100*(nbLemmingsWin*1.0)/nbLemmings);
		}
		
		
	}
	
	public void draw(Graphics2D g){
		if (w == null) return;
		g.setColor(Color.white);
		g.setFont(new Font("default", Font.BOLD, 12));
		g.drawString("Number of Lemmings : "+nbLemmings,420,20);
		g.drawString("Deaths : "+nbLemmingsDead,420,40);
		g.drawString("Completion : "+completion+"%",420,60);
	}
	
	public int getNbLemmings(){
		return nbLemmings;
	}
	
	public int getNbLemmingsAlive(){
		return nbLemmingsAlive;
	}
	
	public int getNbLemmingsDead(){
		return nbLemmingsDead;
	}
	
	public String getCompletion(){
		return completion;
	}
	
	public void setWorld(World w){
		this.w = w;
		nbLemmings = w.getLemmingsList().length;
	}
	
}
