import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Spawner extends Item{

	private int id;
	private BufferedImage imageFirst;
	private BufferedImage imageSecond;
	private BufferedImage imageThird;
	private BufferedImage imageForth;
	private BufferedImage imageFifth;
	private ArrayList<Lemmings> spawnList;
	private int iSpawn;
	private boolean close = false;
	private int iClose = 0;
	
	public Spawner(int id, int posX, int posY, int iSpawn){
		super(posX,posY);
		this.id = id;
		this.iSpawn = iSpawn;
		spawnList = new ArrayList<Lemmings>();
		try{
			if(id == 1){
				imageFirst = ImageIO.read(new File("world/spawn"+id+"-"+1+".png"));
				imageSecond = ImageIO.read(new File("world/spawn"+id+"-"+2+".png"));
				imageThird = ImageIO.read(new File("world/spawn"+id+"-"+3+".png"));
				imageForth = ImageIO.read(new File("world/spawn"+id+"-"+4+".png"));
				imageFifth = ImageIO.read(new File("world/spawn"+id+"-"+5+".png"));
			}
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void update(){
		if(spawnList.isEmpty()){
			if (close) return;
			close = true;
			iClose = GameWindow.getTps();
			return;
		}
		if(((GameWindow.getTps()%iSpawn)!=0)) return;
        	spawnList.get(0).spawn();
        	spawnList.remove(0);
        	
	}
	
	public void draw(Graphics2D g){
	//Dessine l'image avec l'image .png choisi au debut
		if (!close){
			if(GameWindow.getTps()<10){
				g.drawImage(imageFirst,posX-(int)(imageFirst.getWidth()/2),posY-(int)(imageFirst.getHeight()/2),null);
				return;
			}
			if(GameWindow.getTps()<20){
				g.drawImage(imageSecond,posX-(int)(imageSecond.getWidth()/2),posY-(int)(imageSecond.getHeight()/2),null);
				return;
			}
			if(GameWindow.getTps()<30){
				g.drawImage(imageThird,posX-(int)(imageThird.getWidth()/2),posY-(int)(imageThird.getHeight()/2),null);
				return;
			}
			if(GameWindow.getTps()<40){
				g.drawImage(imageForth,posX-(int)(imageForth.getWidth()/2),posY-(int)(imageForth.getHeight()/2),null);
				return;
			}
			g.drawImage(imageFifth,posX-(int)(imageFifth.getWidth()/2),posY-(int)(imageFifth.getHeight()/2),null);
			
		}
		else{
			if((GameWindow.getTps()-iClose)<10){
				g.drawImage(imageFifth,posX-(int)(imageFifth.getWidth()/2),posY-(int)(imageFifth.getHeight()/2),null);
				return;
			}
			if((GameWindow.getTps()-iClose)<20){
				g.drawImage(imageForth,posX-(int)(imageForth.getWidth()/2),posY-(int)(imageForth.getHeight()/2),null);
				return;
			}
			if((GameWindow.getTps()-iClose)<30){
				g.drawImage(imageThird,posX-(int)(imageThird.getWidth()/2),posY-(int)(imageThird.getHeight()/2),null);
				return;
			}
			if((GameWindow.getTps()-iClose)<40){
				g.drawImage(imageSecond,posX-(int)(imageSecond.getWidth()/2),posY-(int)(imageSecond.getHeight()/2),null);
				return;
			}
			g.drawImage(imageFirst,posX-(int)(imageFirst.getWidth()/2),posY-(int)(imageFirst.getHeight()/2),null);
			
		}
		
	}
	
	
	public void addLemmings(Lemmings[] list){
		for(Lemmings l:list){
			spawnList.add(l);
		}
	}
	

}
