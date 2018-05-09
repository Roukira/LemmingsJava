//import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Basher extends Digger{
	private static BufferedImage basherImage0;
	private static BufferedImage basherImage1;
	private static BufferedImage basherImage2;
	private static BufferedImage basherImage0reverse;
	private static BufferedImage basherImage1reverse;
	private static BufferedImage basherImage2reverse;
	
	private static BufferedImage imageRightBasher;		//Image du Walker avancant sur la droite
	private static BufferedImage imageRightStepBasher;		//Image du Walker avancant sur la droite en marchant
	private static BufferedImage imageLeftBasher;		//Image du Walker avancant sur la gauche
	private static BufferedImage imageLeftStepBasher;
	
	private int iBash;
	private static final int iBASH_MAX = 40;
	

//================== CONSTRUCTEURS ======================

	public Basher(int posX, int posY){
		super(posX,posY);
		height = basherImage0.getHeight();
		width = basherImage0.getWidth();
	}
	
	public static void loadAssets(){
		try{
			basherImage0 = ImageIO.read(new File("lemmings/basher0.png"));
			basherImage1 = ImageIO.read(new File("lemmings/basher1.png"));
			basherImage2 = ImageIO.read(new File("lemmings/basher2.png"));
			basherImage0reverse = ImageIO.read(new File("lemmings/basher0reverse.png"));
			basherImage1reverse = ImageIO.read(new File("lemmings/basher1reverse.png"));
			basherImage2reverse = ImageIO.read(new File("lemmings/basher2reverse.png"));
			
			imageRightBasher = ImageIO.read(new File("lemmings/lemmings1Basher.png"));				//recupere les images des Walker a differents etats
			imageRightStepBasher = ImageIO.read(new File("lemmings/lemmings1stepBasher.png"));
			imageLeftBasher = ImageIO.read(new File("lemmings/lemmings2Basher.png"));
			imageLeftStepBasher = ImageIO.read(new File("lemmings/lemmings2stepBasher.png"));
			
		}catch(Exception e){e.printStackTrace();}
		//this.job = 2;
		//this.action = true;
		
	}
	
	public Basher(Lemmings l){
		super(l);
		height = basherImage0.getHeight();
		width = basherImage0.getWidth();
		System.out.println("la direction de ce lemmings est "+direction);
	}


//=================== METHODES ==========================

	public void move(){
		if (!inWorld) return;
		if(!affectMapBool){
			if (fall()) return;
			if (goAhead()) return;
			affectMapBool = true;
			this.job = World.BASHER;
			this.action = true;
			move();
		}else{
			if (fall()){
				System.out.println("can fall so changeJob");
				w.changeJob(this,World.WALKER);
				return;
			}
			if (goAhead()){
				System.out.println("goAhead transforming Basher into Walker at stopper wall.");
				w.changeJob(this,World.WALKER);
				return;
			}
			if (iBash<1){
				affectMap();
				iBash = iBASH_MAX;
			}else if( iBash == (int)(2*iBASH_MAX/3)){
				affectMap();
			}else if( iBash == (int)(iBASH_MAX/3)){
				affectMap(); 
				posX+=5*direction;
			}
			iBash--;
			
		}
		
	
	}
	
	public boolean goAhead(){
		if (!super.walk()){
			if (!super.climbUp()){
				if(!super.climbDown()){
					if (super.checkForStopperWall()){
						direction = -direction;
					}
					else{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void draw(Graphics2D g){
	//Dessine le lemmingjordan
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
				if (!w.canDestructPixel(posX+direction*i,posY-j)){
					w.changeJob(this,World.WALKER);
					return;
				}
				w.setMapTypeAtPos(posX+direction*i,posY-j,w.AIR_CST);
				w.setMapPixelColor(posX+direction*i,posY-j,w.AIR_LIST.get(w.airIndex));
			}
		}
	}
	
	public void resetMap(){}
	
	public BufferedImage getImageRight(){
		return imageRightBasher;
	}
	public BufferedImage getImageRightStep(){
		return imageRightStepBasher;
	}
	public BufferedImage getImageLeft(){
		return imageLeftBasher;
	}
	public BufferedImage getImageLeftStep(){
		return imageLeftStepBasher;
	}
	
}
