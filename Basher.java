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
	

//================== CONSTRUCTEURS ======================

	public Basher(int id, int posX, int posY){
		super(id,posX,posY);
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
	}


//=================== METHODES ==========================

	public void move(){
		if (!inWorld) return;
		if(!affectMapBool){
			if (fall()) return;
			if (walk()) return;
			affectMapBool = true;
			this.job = 2;
			this.action = true;
			move();
		}else{
			if (fall()){
				w.changeJob(this,World.WALKER);
				return;
			}
			if (iBash<1){
				posX+=5*direction;
				affectMap();
				iBash = 61;
			}else if( iBash == 39){
				affectMap();
			}else if( iBash == 19){
				
				affectMap();
			}
			iBash--;
			
		}
		
	
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
			if (iBash<20) g.drawImage(basherImage2,posX-(width/2),posY-height,null);
			else if (iBash<40) g.drawImage(basherImage1,posX-(width/2),posY-height,null);
			else g.drawImage(basherImage0,posX-(width/2),posY-height,null);
		}else{
			if (iBash<20) g.drawImage(basherImage2reverse,posX-(width/2),posY-height,null);
			else if (iBash<40) g.drawImage(basherImage1reverse,posX-(width/2),posY-height,null);
			else g.drawImage(basherImage0reverse,posX-(width/2),posY-height,null);
		}
	}
	
	
	public void affectMap(){
		
		int diggYend = height;
		int diggYstart = 0;
		int diggX = 5;
		
		if (iBash<1){
			diggYstart = (int)(2*height/3);
		}
		else if( iBash == 39){ 
			diggYstart = (int)(1+height/3);  
			diggYend = (int)(2*height/3);
			diggX = 7;
		}
		else if( iBash == 19){
			diggYend = (int)(1+height/3);
		}	
		for (int i=0;i<=diggX;i++){
			for (int j = diggYstart; j<=diggYend;j++){
				w.setMapTypeAtPos(posX+direction*i,posY-j,w.AIR_CST);
				w.setMapPixelColor(posX+direction*i,posY-j,w.AIR_LIST.get(w.airIndex));
			}
		}
	}
	
	public void resetMap(){}
	
}
