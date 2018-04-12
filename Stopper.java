import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class Stopper extends Lemmings implements Affecter{


//==================== ATTRIBUTS ========================

	private BufferedImage image0;		//Image du Stopper avancant sur la droite
	private BufferedImage image1;		//Image du Stopper avancant sur la droite en marchant
	private BufferedImage image2;		//Image du Stopper avancant sur la gauche
	private BufferedImage image3;		//Image du Stopper avancant sur la gauche en marchant
	private int iStopBegin = 0;
	private int iStop = 0;
	private boolean enoughPlace;
	private int tPosXLeft;
	private int tPosXRight;
	private int tPosYUpper;
	private int tPosYLower;
	private boolean affectMapBool = false;

//================== CONSTRUCTEURS ======================

	public Stopper(int posX, int posY){
		super(posX,posY);
		try{
			image0 = ImageIO.read(new File("lemmings/stopper0.png"));
			image1 = ImageIO.read(new File("lemmings/stopper1.png"));
			image2 = ImageIO.read(new File("lemmings/stopper2.png"));
			image3 = ImageIO.read(new File("lemmings/stopper3.png"));
			
		}catch(Exception e){e.printStackTrace();}
		height = image0.getHeight();
		width = image0.getWidth();
		this.job = World.STOPPER;
		this.action = true;
		tPosXLeft = posX-direction*(width/2);
		tPosXRight = posX+direction*(width/2);
		tPosYUpper = posY-height;
		tPosYLower = posY;
	}
	
	public Stopper(Lemmings l){
		super(l);
		try{
			image0 = ImageIO.read(new File("lemmings/stopper0.png"));
			image1 = ImageIO.read(new File("lemmings/stopper1.png"));
			image2 = ImageIO.read(new File("lemmings/stopper2.png"));
			image3 = ImageIO.read(new File("lemmings/stopper3.png"));
			
		}catch(Exception e){e.printStackTrace();}
		this.height = image0.getHeight();
		this.width = image0.getWidth();
		this.job = World.STOPPER;
		this.action = true;
		tPosXLeft = posX-width/2;
		tPosXRight = posX+width/2;
		tPosYUpper = posY-height;
		tPosYLower = posY;
	}

//===================== METHODES =========================
	
	public void draw(Graphics2D g){
	//Dessine le lemming
		super.draw(g);
		if (!alive) return;
		if (!inWorld) return;
		if (inAir) return;
		drawStop(g);
	}
	
	public boolean drawStop(Graphics2D g){
		if(!action) return false;
		else if(iStopBegin<20){		
			g.drawImage(image0,posX-width/2,posY-height,null);
			iStopBegin++;
			return true;
		}
		else if((GameWindow.getTps()-iStop)%80 < 20){	
			g.drawImage(image1,posX-width/2,posY-height,null);
			return true;
		}
		else if((GameWindow.getTps()-iStop)%80 < 40){	
			g.drawImage(image3,posX-width/2,posY-height,null);
			return true;
		}
		else if((GameWindow.getTps()-iStop)%80 < 60){	
			g.drawImage(image2,posX-width/2,posY-height,null);
			return true;
		}
		else{
			g.drawImage(image3,posX-width/2,posY-height,null);
			return true;
		}
	}
	
	public void affectMap(){
		if (affectMapBool) return;
		for(int i = 0;i<height;i++) {
						w.setMapTypeAtPos(tPosXLeft,tPosYLower-i,w.STOPPER_WALL_LEFT_CST);
						w.setMapPixelColor(tPosXLeft,tPosYLower-i,Color.red);	
					}
		for(int j = 0;j<height;j++) {
						w.setMapTypeAtPos(tPosXRight,tPosYLower-j,w.STOPPER_WALL_RIGHT_CST);
						w.setMapPixelColor(tPosXRight,tPosYLower-j,Color.red);
					}
		action = true;
	}
	
	public void resetMap(){
		if (!affectMapBool) return;
		for(int i = 0;i<height;i++) {
						w.setMapTypeAtPos(tPosXLeft,tPosYLower-i,w.AIR_CST);
						w.setMapPixelColor(tPosXLeft,tPosYLower-i,Color.blue);	
					}
		for(int j = 0;j<height;j++) {
						w.setMapTypeAtPos(tPosXRight,tPosYLower-j,w.AIR_CST);
						w.setMapPixelColor(tPosXRight,tPosYLower-j,Color.blue);
					}
		action = false;
		affectMapBool = false;
	}
	
	
	public boolean haveEnoughPlace(){
	//Fonction qui tente de descendre le lemming
		int i;
		
		for (i=0;i<height;i++){		//recherche pour la place 
			if(w.getPos(posX+(width/2),posY-i)!=0 || w.getPos(posX-(width/2),posY-i)!=0){	//et qu'il peut rentrer
				//System.out.println("False pas la place");
				enoughPlace = false;
				return false;
			}
		}
		//System.out.println("VVrai y a la place");
		enoughPlace = true;
		return true;
		
	}
	
	public void move(){
	//bouge le lemming selon le world
		//plus tard ajout de draw animation
		if (!inWorld) return;
		if (fall()) return;
		if (!alive) return;
		if(!affectMapBool && haveEnoughPlace()){
					affectMap();
					affectMapBool = true;
					return;
		}
		//System.out.println("False pas la place");
		if (!affectMapBool){
			if (walk()) return;
			direction = -direction;
		}
	}
	
	public boolean walk(){
		boolean res = super.walk();
		if(res){
			resetMap();
			tPosXLeft = posX-direction*(width/2);
			tPosXRight = posX+direction*(width/2);
			tPosYUpper = posY-height;
			tPosYLower = posY;
		}
		return res;
	}
	
	public boolean fall(){
		int tmpWidht = width;
		int tmpHeight = height;
		width = imageRight.getWidth();
		height = imageRight.getHeight();
		boolean res = super.fall();
		if(res) resetMap();
		width = tmpWidht;
		height = tmpHeight;
		tPosYUpper = posY-height;
		tPosYLower = posY;
		return res;
	}
	
}
