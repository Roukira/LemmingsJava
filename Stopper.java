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
	
	private static BufferedImage imageRightStopper;		//Image du Walker avancant sur la droite
	private static BufferedImage imageRightStepStopper;		//Image du Walker avancant sur la droite en marchant
	private static BufferedImage imageLeftStopper;		//Image du Walker avancant sur la gauche
	private static BufferedImage imageLeftStepStopper;
	
	private boolean affectMapBool = false;
	private int iStopBegin = 0;
	private int iStop = 0;
	

//================== CONSTRUCTEURS ======================

	public static void loadAssets(){
		try{
			image0 = ImageIO.read(new File("lemmings/stopper0.png"));
			image1 = ImageIO.read(new File("lemmings/stopper1.png"));
			image2 = ImageIO.read(new File("lemmings/stopper2.png"));
			image3 = ImageIO.read(new File("lemmings/stopper3.png"));
			
			imageRightStopper = ImageIO.read(new File("lemmings/lemmings1Stopper.png"));
			imageRightStepStopper = ImageIO.read(new File("lemmings/lemmings1stepStopper.png"));
			imageLeftStopper = ImageIO.read(new File("lemmings/lemmings2Stopper.png"));
			imageLeftStepStopper = ImageIO.read(new File("lemmings/lemmings2stepStopper.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public Stopper(Lemmings l){
		super(l);
		
		this.height = image0.getHeight();
		this.width = image0.getWidth();
	}

//===================== METHODES =========================
	
	public void drawAction(Graphics2D g){
		if(!action) return;
		else if(iStopBegin<20){		
			g.drawImage(image0,posX-width/2,posY-height,null);
			iStopBegin++;
		}
		else if((Window.getTps()-iStop)%80 < 20) g.drawImage(image1,posX-width/2,posY-height,null);
		else if((Window.getTps()-iStop)%80 < 40) g.drawImage(image3,posX-width/2,posY-height,null);
		else if((Window.getTps()-iStop)%80 < 60) g.drawImage(image2,posX-width/2,posY-height,null);
		else g.drawImage(image3,posX-width/2,posY-height,null);
	}
	
	public void affectMap(){
		int xLeft = posX-(width/2);
		int xRight = posX+(width/2);
		int y = posY-height;
		boolean wallRightCanGo = true;
		boolean wallLeftCanGo = false;
		while(wallRightCanGo || wallLeftCanGo) {
			if (w.getPos(xLeft,y) == World.AIR_CST){
				w.setMapTypeAtPos(xLeft,y,w.STOPPER_WALL_LEFT_CST);
				w.setMapPixelColor(xLeft,y,Color.red);
				wallLeftCanGo = true;
			}
			else wallLeftCanGo = false;
			
			
			if (w.getPos(xRight,y) == World.AIR_CST){
				w.setMapTypeAtPos(xRight,y,w.STOPPER_WALL_RIGHT_CST);
				w.setMapPixelColor(xRight,y,Color.red);
				wallRightCanGo = true;
			}
			else wallRightCanGo = false;
			
			y++;
		}
	}
	
	public void resetMap(){
		int xLeft = posX-(width/2);
		int xRight = posX+(width/2);
		int y = posY-height;
		boolean wallRightCanGo = true;
		boolean wallLeftCanGo = false;
		while(wallRightCanGo || wallLeftCanGo) {
			if (w.getPos(xLeft,y) == World.STOPPER_WALL_LEFT_CST){
				w.setMapTypeAtPos(xLeft,y,w.AIR_CST);
				wallLeftCanGo = true;
			}
			else wallLeftCanGo = false;
			w.setMapPixelColor(xLeft,y,Color.blue);
			
			if (w.getPos(xRight,y) == World.STOPPER_WALL_RIGHT_CST){
				w.setMapTypeAtPos(xRight,y,w.AIR_CST);
				wallRightCanGo = true;
			}
			else wallRightCanGo = false;
			w.setMapPixelColor(xRight,y,Color.blue);
			
			y++;
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
	
	public BufferedImage getImageRight(){
		return imageRightStopper;
	}
	public BufferedImage getImageRightStep(){
		return imageRightStepStopper;
	}
	public BufferedImage getImageLeft(){
		return imageLeftStopper;
	}
	public BufferedImage getImageLeftStep(){
		return imageLeftStepStopper;
	}
}
