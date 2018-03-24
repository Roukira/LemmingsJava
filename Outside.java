import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Outside extends Item{

	private int id;
	Lemmings[] list;
	BufferedImage imageFirst;
	BufferedImage imageSecond;
	BufferedImage imageThird;
	BufferedImage imageForth;
	
	public Outside(int id, int posX, int posY, Lemmings[] list){
		super(posX,posY);
		this.id = id;
		this.list = list;
		try{
			
			if(id == 1){
				imageFirst = ImageIO.read(new File("world/outside"+id+"-"+1+".png"));
				imageSecond = ImageIO.read(new File("world/outside"+id+"-"+2+".png"));
				imageThird = ImageIO.read(new File("world/outside"+id+"-"+3+".png"));
				imageForth = ImageIO.read(new File("world/outside"+id+"-"+4+".png"));
			}
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void update(){
		for(Lemmings l:list){
			if(l.getPosX()==posX && l.getPosY()==posY){
				l.win();
			}
		}
		
	}
	
	public void draw(Graphics2D g){
	//Dessine l'image avec l'image .png choisi au debut
		if(GameWindow.getTps()%40<10) g.drawImage(imageFirst,posX-(int)(imageFirst.getWidth()/2),posY+1-(int)(imageFirst.getHeight()),null);
		if(GameWindow.getTps()%40<20) g.drawImage(imageSecond,posX-(int)(imageSecond.getWidth()/2),posY+1-(int)(imageSecond.getHeight()),null);
		if(GameWindow.getTps()%40<30) g.drawImage(imageThird,posX-(int)(imageThird.getWidth()/2),posY+1-(int)(imageThird.getHeight()),null);
		else g.drawImage(imageForth,posX-(int)(imageForth.getWidth()/2),posY+1-(int)(imageForth.getHeight()),null);
	}
	

}
