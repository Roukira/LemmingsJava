import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Outside{

	private int posX;
	private int posY;
	private int id;
	BufferedImage image;
	
	public Outside(int id, int posX, int posY){
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		try{
			image = ImageIO.read(new File("world/outside"+id+".png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void update(Lemmings[] list){
		for(Lemmings l:list){
			if(l.getPosX()==posX && l.getPosY()==posY){
				l.win();
			}
		}
		
	}
	
	public void draw(Graphics2D g){
	//Dessine l'image avec l'image .png choisi au debut
		g.drawImage(image,posX-(int)(image.getWidth()/2),posY-(int)(image.getHeight()/2),null);
	}
	
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	

}
