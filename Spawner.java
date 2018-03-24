import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Spawner{

	private int posX;
	private int posY;
	private int id;
	BufferedImage imageFirst;
	BufferedImage imageSecond;
	BufferedImage imageThird;
	ArrayList<Lemmings> spawnList;
	private int iSpawn;
	
	public Spawner(int id, int posX, int posY, int iSpawn){
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.iSpawn = iSpawn;
		spawnList = new ArrayList<Lemmings>();
		try{
			if(id == 1){
				imageFirst = ImageIO.read(new File("world/spawn"+id+"-"+1+".png"));
				imageSecond = ImageIO.read(new File("world/spawn"+id+"-"+2+".png"));
				imageThird = ImageIO.read(new File("world/spawn"+id+".png"));
			}
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void update(){
		if(spawnList.isEmpty()||((GameWindow.getTps()%iSpawn)!=0)) return;
		System.out.println(GameWindow.getTps());
		System.out.println(GameWindow.getTps()%iSpawn);
        	spawnList.get(0).spawn();
        	spawnList.remove(0);
	}
	
	public void draw(Graphics2D g){
	//Dessine l'image avec l'image .png choisi au debut
		if(GameWindow.getTps()<5){
			g.drawImage(imageFirst,posX-(int)(imageFirst.getWidth()/2),posY-(int)(imageFirst.getHeight()/2),null);
			return;
		}
		if(GameWindow.getTps()<10){
			g.drawImage(imageSecond,posX-(int)(imageSecond.getWidth()/2),posY-(int)(imageSecond.getHeight()/2),null);
			return;
		}
		g.drawImage(imageThird,posX-(int)(imageThird.getWidth()/2),posY-(int)(imageThird.getHeight()/2),null);
		
	}
	
	public void addLemmings(Lemmings[] list){
		for(Lemmings l:list){
			spawnList.add(l);
		}
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	

}
