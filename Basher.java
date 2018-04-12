import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Basher extends Digger implements Affecter{
	private BufferedImage basherImage0;
	private BufferedImage basherImage1;
	private BufferedImage basherImage2;
	private BufferedImage basherImage0reverse;
	private BufferedImage basherImage1reverse;
	private BufferedImage basherImage2reverse;
	private int iBash;
	private static final int iBASH_MAX = 40;
	

//================== CONSTRUCTEURS ======================

	public Basher(int posX, int posY){
		super(posX,posY);
		try{
			basherImage0 = ImageIO.read(new File("lemmings/basher0.png"));
			basherImage1 = ImageIO.read(new File("lemmings/basher1.png"));
			basherImage2 = ImageIO.read(new File("lemmings/basher2.png"));
			basherImage0reverse = ImageIO.read(new File("lemmings/basher0reverse.png"));
			basherImage1reverse = ImageIO.read(new File("lemmings/basher1reverse.png"));
			basherImage2reverse = ImageIO.read(new File("lemmings/basher2reverse.png"));
			
		}catch(Exception e){e.printStackTrace();}
		//this.job = 2;
		//this.action = true;
		height = basherImage0.getHeight();
		width = basherImage0.getWidth();
	}
	
	public Basher(Lemmings l){
		super(l);
		try{
			basherImage0 = ImageIO.read(new File("lemmings/basher0.png"));
			basherImage1 = ImageIO.read(new File("lemmings/basher1.png"));
			basherImage2 = ImageIO.read(new File("lemmings/basher2.png"));
			basherImage0reverse = ImageIO.read(new File("lemmings/basher0reverse.png"));
			basherImage1reverse = ImageIO.read(new File("lemmings/basher1reverse.png"));
			basherImage2reverse = ImageIO.read(new File("lemmings/basher2reverse.png"));
			
		}catch(Exception e){e.printStackTrace();}
		//this.job = 3;
		//this.action = true;
		height = basherImage0.getHeight();
		width = basherImage0.getWidth();
		System.out.println("la direction de ce lemmings est "+direction);
	}


//=================== METHODES ==========================

	public void move(){
		if (!inWorld) return;
		if(!affectMapBool){
			if (fall()) return;
			if (walk()) return;
			affectMapBool = true;
			this.job = World.BASHER;
			this.action = true;
			move();
		}else{
			if (fall()){
				System.out.println("comment peut il tomber???");
				w.changeJob(this,World.WALKER);
				return;
			}
			if (walk()){
				System.out.println("comment peut il tomber???");
				w.changeJob(this,World.WALKER);
				return;
			}
			if (iBash<1){
				System.out.println("order 1");
				
				affectMap();
				iBash = iBASH_MAX;
			}else if( iBash == (int)(2*iBASH_MAX/3)){
				System.out.println("order 12");
				affectMap();
			}else if( iBash == (int)(iBASH_MAX/3)){
				
				affectMap();System.out.println("order 13");
				posX+=5*direction;
			}
			iBash--;
			
		}
		
	
	}
	
	public boolean walk(){
		int tmpWidht = width;
		int tmpHeight = height;
		width = imageRight.getWidth();
		height = imageRight.getHeight();
		boolean res = super.walk();
		width = tmpWidht;
		height = tmpHeight;
		return res;
	}
	
	public void draw(Graphics2D g){
	//Dessine le lemming
		super.draw(g);
		if (!alive) return;
		if (!inWorld) return;
		if (inAir) return;
		if (affectMapBool) drawBash(g);
	}
	
	public void drawBash(Graphics2D g){
		if (direction == 1){
			if (iBash<(int)(1+2*iBASH_MAX/3)) g.drawImage(basherImage2,posX-(width/2),posY-height,null);
			else if (iBash<(int)(1+iBASH_MAX/3)) g.drawImage(basherImage1,posX-(width/2),posY-height,null);
			else g.drawImage(basherImage0,posX-(width/2),posY-height,null);
		}else{
			if (iBash<(int)(1+iBASH_MAX/3)) g.drawImage(basherImage2reverse,posX-(width/2),posY-height,null);
			else if (iBash<(int)(1+2*iBASH_MAX/3)) g.drawImage(basherImage1reverse,posX-(width/2),posY-height,null);
			else g.drawImage(basherImage0reverse,posX-(width/2),posY-height,null);
		}
	}
	
	
	public void affectMap(){
		
		int diggYend = height;
		int diggYstart = 0;
		int diggX = 8;
		
		if (iBash<1){
			diggYstart = (int)(2*height/3);
		}
		else if( iBash == (int)(2*iBASH_MAX/3)){ 
			diggYstart = (int)(1+height/3);  
			diggYend = (int)(2*height/3);
			diggX = 9;
		}
		else if( iBash == (int)(iBASH_MAX/3)){
			diggYend = (int)(1+height/3);
		}	
		for (int i=-width/2;i<=diggX;i++){
			for (int j = diggYstart; j<=diggYend;j++){
				if (w.getPos(posX+direction*i,posY-j)==-1){
					w.changeJob(this,World.WALKER);
					return;
				}
				w.setMapTypeAtPos(posX+direction*i,posY-j,w.AIR_CST);
				w.setMapPixelColor(posX+direction*i,posY-j,w.AIR_LIST.get(w.airIndex));
			}
		}
	}
	
	public void resetMap(){}
	
}
