import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Outside extends Item{

	private static BufferedImage imageFirst;
	private static BufferedImage imageSecond;
	private static BufferedImage imageThird;
	private static BufferedImage imageForth;
	
	private static BufferedImage imageFirst1;
	private static BufferedImage imageSecond1;
	private static BufferedImage imageThird1;
	private static BufferedImage imageForth1;
	
	private static BufferedImage imageFirst6;
	private static BufferedImage imageSecond6;
	private static BufferedImage imageThird6;
	private static BufferedImage imageForth6;
	
	private World w;
	private int typeOutside;
	
	public Outside(int posX, int posY, Lemmings[] list, World w){
		this(posX,posY,list,w,1);
	}
	
	public Outside(int posX, int posY, Lemmings[] list, World w, int typeOutside){
		super(posX,posY);
		this.w = w;
		fillArray(list);
		this.typeOutside = typeOutside;
	}
	
	public void startItem(){
		setOutsideType();
	}
	
	public static void loadAssets(){
		try{
			imageFirst1 = ImageIO.read(new File("world/outside1-1.png"));
			imageSecond1 = ImageIO.read(new File("world/outside1-2.png"));
			imageThird1 = ImageIO.read(new File("world/outside1-3.png"));
			imageForth1 = ImageIO.read(new File("world/outside1-4.png"));
			
			imageFirst6 = ImageIO.read(new File("world/outside6-1.png"));
			imageSecond6 = ImageIO.read(new File("world/outside6-2.png"));
			imageThird6 = ImageIO.read(new File("world/outside6-3.png"));
			imageForth6 = ImageIO.read(new File("world/outside6-4.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void fillArray(Lemmings[] listLemmings){
		for (Lemmings l:listLemmings){
			list.add(l);
		}
	}
	
	public void update(){
		//printList();
		int size = list.size();
		if(list.isEmpty()){
			w.setFinished(true);
		}
		for(int i = 0;i<size;i++){
			if (i<list.size()){
				Lemmings l = list.get(i);
			
				//System.out.println(l.toString()+" | "+l.getJob());
				if(l.getInWorld() && l.getPosX()>=posX-5 && l.getPosX()<=posX+5 && l.getPosY()<=posY && l.getPosY()>=posY-3){
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
		if(Window.getTps()%40<10) g.drawImage(imageFirst,posX-(int)(imageFirst.getWidth()/2),posY-(int)(imageFirst.getHeight()),null);
		if(Window.getTps()%40<20) g.drawImage(imageSecond,posX-(int)(imageSecond.getWidth()/2),posY-(int)(imageSecond.getHeight()),null);
		if(Window.getTps()%40<30) g.drawImage(imageThird,posX-(int)(imageThird.getWidth()/2),posY-(int)(imageThird.getHeight()),null);
		else g.drawImage(imageForth,posX-(int)(imageForth.getWidth()/2),posY-(int)(imageForth.getHeight()),null);
	}
	
	public void setOutsideType(){
		System.out.println("typeOutside : "+typeOutside);
		if (typeOutside == 1){
			imageFirst = imageFirst1;
			imageSecond = imageSecond1;
			imageThird = imageThird1;
			imageForth = imageForth1;
		}else if (typeOutside == 6){
			imageFirst = imageFirst6;
			imageSecond = imageSecond6;
			imageThird = imageThird6;
			imageForth = imageForth6;
		}
	}
	

}
