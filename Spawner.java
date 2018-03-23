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
	private static int iSpawn = 0;
	
	public Spawner(int id, int posX, int posY){
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		spawnList = new ArrayList<Lemmings>();
		try{
			image = ImageIO.read(new File("spawn"+id+".png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void update(){
		if(!spawnList.isEmpty()){
			if(iSpawn == 0){
				Lemmings l = spawnList.get(0);
				l.spawn();
				iSpawn = 400;
				System.out.println("hello");
				spawnList.remove(0);
			}
			else iSpawn--;
		}
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
