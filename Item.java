import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public abstract class Item{

	protected int posX;
	protected int posY;
	protected ArrayList<Lemmings> list;
	
	public Item(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
		this.list = new ArrayList<Lemmings>();
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics2D g);
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public void removeLemmingFromList(int id){
		System.out.println("debug8");
		for (int i =0;i<list.size();i++){
			System.out.println("debug9, i : "+i);
			if(list.get(i).getId() == id){
				System.out.println("debug10");
				list.remove(i);
				return;
			}
		}
	}

	public void addLemmings(Lemmings[] list){
		for(Lemmings l:list){
			this.list.add(l);
		}
	}

}
