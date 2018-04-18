import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public abstract class Item extends Thing{

	protected ArrayList<Lemmings> list;
	
	public Item(int posX, int posY){
		super(posX,posY);
		this.list = new ArrayList<Lemmings>();
	}
	
	public void removeLemmingFromList(int id){
		for (int i =0;i<list.size();i++){
			if(list.get(i).getId() == id){
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
	
	public void printList(){
		for (Lemmings l:list){
			System.out.println(l.toString()+" | "+ l.getJob());
		}
		System.out.println("");
	}

}
