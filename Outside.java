import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Outside extends Item{

	private int id;
	private BufferedImage imageFirst;
	private BufferedImage imageSecond;
	private BufferedImage imageThird;
	private BufferedImage imageForth;
	private World w;
	
	public Outside(int id, int posX, int posY, Lemmings[] list, World w){
		super(posX,posY);
		this.id = id;
		this.w = w;
		fillArray(list);
		try{
			
			if(id == 1){
				imageFirst = ImageIO.read(new File("world/outside"+id+"-"+1+".png"));
				imageSecond = ImageIO.read(new File("world/outside"+id+"-"+2+".png"));
				imageThird = ImageIO.read(new File("world/outside"+id+"-"+3+".png"));
				imageForth = ImageIO.read(new File("world/outside"+id+"-"+4+".png"));
			}
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void fillArray(Lemmings[] listLemmings){
		for (Lemmings l:listLemmings){
			list.add(l);
		}
	}
	
	public void update(){
		if(list.isEmpty()){
			w.setFinished(true);
		}
		for(int i = 0;i<list.size();i++){
			Lemmings l = list.get(i);
			
			if(l.getInWorld() && l.getPosX()==posX && l.getPosY()==posY){
				l.win();
				list.remove(i);
			}
			else if(!l.getAlive()){
				list.remove(i);
			}
		}
		
	}
	
	public void draw(Graphics2D g){
	//Dessine l'image avec l'image .png choisi au debut
		if(Window.getTps()%40<10) g.drawImage(imageFirst,posX-(int)(imageFirst.getWidth()/2),posY+1-(int)(imageFirst.getHeight()),null);
		if(Window.getTps()%40<20) g.drawImage(imageSecond,posX-(int)(imageSecond.getWidth()/2),posY+1-(int)(imageSecond.getHeight()),null);
		if(Window.getTps()%40<30) g.drawImage(imageThird,posX-(int)(imageThird.getWidth()/2),posY+1-(int)(imageThird.getHeight()),null);
		else g.drawImage(imageForth,posX-(int)(imageForth.getWidth()/2),posY+1-(int)(imageForth.getHeight()),null);
	}
	

}
