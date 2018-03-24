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
	BufferedImage image;
	ArrayList<Lemmings> spawnList;
	private int iSpawn;
	
	public Spawner(int id, int posX, int posY, int iSpawn){
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.iSpawn = iSpawn;
		spawnList = new ArrayList<Lemmings>();
		try{
			image = ImageIO.read(new File("world/spawn"+id+".png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void update(){
		if(spawnList.isEmpty()||((GameWindow.getTps()%iSpawn)!=0)) return;
		System.out.println(GameWindow.getTps());
		System.out.println(GameWindow.getTps()%iSpawn);
        	spawnList.get(0).spawn();
        	System.out.println("hello"+iSpawn);
        	spawnList.remove(0);
	}
	
	public void draw(Graphics2D g){
	//Dessine l'image avec l'image .png choisi au debut
		g.drawImage(image,posX-(int)(image.getWidth()/2),posY-(int)(image.getHeight()/2),null);
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
