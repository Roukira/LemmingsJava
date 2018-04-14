import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Spawner extends Item{

	private BufferedImage imageFirst;
	private BufferedImage imageSecond;
	private BufferedImage imageThird;
	private BufferedImage imageForth;
	private BufferedImage imageFifth;
	private int iSpawn;
	private boolean close = false;
	private int iClose = 0;
	
	public Spawner(int posX, int posY, int iSpawn){
		super(posX,posY);
		this.iSpawn = iSpawn;
		try{
			imageFirst = ImageIO.read(new File("world/spawn1-1.png"));
			imageSecond = ImageIO.read(new File("world/spawn1-2.png"));
			imageThird = ImageIO.read(new File("world/spawn1-3.png"));
			imageForth = ImageIO.read(new File("world/spawn1-4.png"));
			imageFifth = ImageIO.read(new File("world/spawn1-5.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void update(){
		if(list.isEmpty()){
			if (close) return;
			close = true;
			iClose = Window.getTps();
			return;
		}
		if(((Window.getTps()%iSpawn)!=0)) return;
        	list.get(0).spawn();
        	list.remove(0);
        	
	}
	
	public void draw(Graphics2D g){
	//Dessine l'image avec l'image .png choisi au debut
		if (!close){
			if(Window.getTps()<10){
				g.drawImage(imageFirst,posX-(int)(imageFirst.getWidth()/2),posY-(int)(imageFirst.getHeight()/2),null);
				return;
			}
			if(Window.getTps()<20){
				g.drawImage(imageSecond,posX-(int)(imageSecond.getWidth()/2),posY-(int)(imageSecond.getHeight()/2),null);
				return;
			}
			if(Window.getTps()<30){
				g.drawImage(imageThird,posX-(int)(imageThird.getWidth()/2),posY-(int)(imageThird.getHeight()/2),null);
				return;
			}
			if(Window.getTps()<40){
				g.drawImage(imageForth,posX-(int)(imageForth.getWidth()/2),posY-(int)(imageForth.getHeight()/2),null);
				return;
			}
			g.drawImage(imageFifth,posX-(int)(imageFifth.getWidth()/2),posY-(int)(imageFifth.getHeight()/2),null);
			
		}
		else{
			if((Window.getTps()-iClose)<10){
				g.drawImage(imageFifth,posX-(int)(imageFifth.getWidth()/2),posY-(int)(imageFifth.getHeight()/2),null);
				return;
			}
			if((Window.getTps()-iClose)<20){
				g.drawImage(imageForth,posX-(int)(imageForth.getWidth()/2),posY-(int)(imageForth.getHeight()/2),null);
				return;
			}
			if((Window.getTps()-iClose)<30){
				g.drawImage(imageThird,posX-(int)(imageThird.getWidth()/2),posY-(int)(imageThird.getHeight()/2),null);
				return;
			}
			if((Window.getTps()-iClose)<40){
				g.drawImage(imageSecond,posX-(int)(imageSecond.getWidth()/2),posY-(int)(imageSecond.getHeight()/2),null);
				return;
			}
			g.drawImage(imageFirst,posX-(int)(imageFirst.getWidth()/2),posY-(int)(imageFirst.getHeight()/2),null);
			
		}
		
	}
	
	

}
