import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Outside extends Item{

	private BufferedImage imageFirst;
	private BufferedImage imageSecond;
	private BufferedImage imageThird;
	private BufferedImage imageForth;
	private World w;
	
	public Outside(int posX, int posY, Lemmings[] list, World w){
		super(posX,posY);
		this.w = w;
		fillArray(list);
		try{
			imageFirst = ImageIO.read(new File("world/outside1-1.png"));
			imageSecond = ImageIO.read(new File("world/outside1-2.png"));
			imageThird = ImageIO.read(new File("world/outside1-3.png"));
			imageForth = ImageIO.read(new File("world/outside1-4.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void fillArray(Lemmings[] listLemmings){
		for (Lemmings l:listLemmings){
			list.add(l);
		}
	}
	
	public void update(){
		printList();
		int size = list.size();
		if(list.isEmpty()){
			w.setFinished(true);
		}
		for(int i = 0;i<size;i++){
			if (i<list.size()){
				Lemmings l = list.get(i);
			
				//System.out.println(l.toString()+" | "+l.getJob());
				if(l.getInWorld() && l.getPosX()==posX && l.getPosY()==posY){
					//System.out.println(l.toString()+" | "+l.getJob());
					l.win();
					list.remove(i);
				}
				else if(!l.getAlive()){
					list.remove(i);
				}
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
