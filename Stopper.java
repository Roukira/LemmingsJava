import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class Stopper extends Lemmings implements Affecter{


//==================== ATTRIBUTS ========================

	private static BufferedImage image0;		//Image du Stopper avancant sur la droite
	private static BufferedImage image1;		//Image du Stopper avancant sur la droite en marchant
	private static BufferedImage image2;		//Image du Stopper avancant sur la gauche
	private static BufferedImage image3;		//Image du Stopper avancant sur la gauche en marchant
	private boolean affectMapBool = false;
	private int iStopBegin = 0;
	private int iStop = 0;
	

//================== CONSTRUCTEURS ======================

	public Stopper(int posX, int posY){
		super(posX,posY);
		
		this.height = image0.getHeight();
		this.width = image0.getWidth();
	}
	
	public static void loadAssets(){
		try{
			image0 = ImageIO.read(new File("lemmings/stopper0.png"));
			image1 = ImageIO.read(new File("lemmings/stopper1.png"));
			image2 = ImageIO.read(new File("lemmings/stopper2.png"));
			image3 = ImageIO.read(new File("lemmings/stopper3.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public Stopper(Lemmings l){
		super(l);
		
		this.height = image0.getHeight();
		this.width = image0.getWidth();
	}

//===================== METHODES =========================
	
	public void draw(Graphics2D g){
	//Dessine le lemming
		super.draw(g);
		if (!alive) return;
		if (!inWorld) return;
		if (inAir) return;
		if (affectMapBool) drawStop(g);
		
	}
	
	public boolean drawStop(Graphics2D g){
		if(!action) return false;
		else if(iStopBegin<20){		
			g.drawImage(image0,posX-width/2,posY-height,null);
			iStopBegin++;
			return true;
		}
		else if((Window.getTps()-iStop)%80 < 20){	
			g.drawImage(image1,posX-width/2,posY-height,null);
			return true;
		}
		else if((Window.getTps()-iStop)%80 < 40){	
			g.drawImage(image3,posX-width/2,posY-height,null);
			return true;
		}
		else if((Window.getTps()-iStop)%80 < 60){	
			g.drawImage(image2,posX-width/2,posY-height,null);
			return true;
		}
		else{
			g.drawImage(image3,posX-width/2,posY-height,null);
			return true;
		}
	}
	
	public void affectMap(){
		for(int i = 0;i<height;i++) {
			w.setMapTypeAtPos(posX-(width/2),posY-i,w.STOPPER_WALL_LEFT_CST);
			w.setMapPixelColor(posX-(width/2),posY-i,Color.red);
			
			w.setMapTypeAtPos(posX+(width/2),posY-i,w.STOPPER_WALL_RIGHT_CST);
			w.setMapPixelColor(posX+(width/2),posY-i,Color.red);
		}
	}
	
	public void resetMap(){
		for(int i = 0;i<height;i++){
			w.setMapTypeAtPos(posX-(width/2),posY-i,w.AIR_CST);
			w.setMapPixelColor(posX-(width/2),posY-i,Color.blue);
			
			w.setMapTypeAtPos(posX+(width/2),posY-i,w.AIR_CST);
			w.setMapPixelColor(posX+(width/2),posY-i,Color.blue);
		}
	}
	
	public boolean haveEnoughPlace(){
		for (int i=posX-(width/2);i<=posX+(width/2);i++){
			for (int j=posY-height;j<=posY;j++){
				if(w.getPos(i,j) != World.AIR_CST) return false;
			}
		}
		return true;
	}
	
	public void move(){
	//bouge le lemming selon le world
		//plus tard ajout de draw animation
		if (!inWorld) return;
		if (!alive) return;
		if(!affectMapBool){
			if (fall()) return;
			if (haveEnoughPlace()){
				affectMapBool = true;
				this.job = World.STOPPER;
				this.action = true;
				affectMap();
			}
			else{
				if (walk()) return;
				if (climbUp()) return;
				if (climbDown()) return;
				direction = -direction;
			}
			
		}else{
			if (fall()){
				System.out.println("turn into walker from fall");
				posY--;
				resetMap();
				posY++;
				w.changeJob(this,World.WALKER);
				return;
			}
		}
		
	}
}
