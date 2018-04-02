import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class Stopper extends Lemmings{


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
	private boolean affectMapBool = true;

//================== CONSTRUCTEURS ======================

	public Stopper(int id, int posX, int posY){
		super(id,posX,posY);
		try{
			image0 = ImageIO.read(new File("lemmings/stopper0.png"));
			image1 = ImageIO.read(new File("lemmings/stopper1.png"));
			image2 = ImageIO.read(new File("lemmings/stopper2.png"));
			image3 = ImageIO.read(new File("lemmings/stopper3.png"));
			
		}catch(Exception e){e.printStackTrace();}
		height = image0.getHeight();
		width = image0.getWidth();
		this.actionState = 1;
		this.action = true;
		tPosXLeft = posX-width;
		tPosXRight = posX;
		if (!inAir) affectMap();
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
		this.actionState = 1;
		this.action = true;
		tPosXLeft = posX-width;
		tPosXRight = posX;
		if (!inAir) affectMap();
	}

//===================== METHODES =========================
	
	public void draw(Graphics2D g){
	//Dessine le lemming
		super.draw(g);
		if (!alive) return;
		if (!inWorld) return;
		drawStop(g);
	}
	
	public boolean drawStop(Graphics2D g){
		if(!enoughPlace) return false;
		else if(iStopBegin<20){		
			g.drawImage(image0,posX-width,posY-height,null);
			iStopBegin++;
			return true;
		}
		else if((GameWindow.getTps()-iStop)%80 < 20){	
			g.drawImage(image1,posX-width,posY-height,null);
			return true;
		}
		else if((GameWindow.getTps()-iStop)%80 < 40){	
			g.drawImage(image3,posX-width,posY-height,null);
			return true;
		}
		else if((GameWindow.getTps()-iStop)%80 < 60){	
			g.drawImage(image2,posX-width,posY-height,null);
			return true;
		}
		else{
			g.drawImage(image3,posX-width,posY-height,null);
			return true;
		}
	}
	
	public void affectMap(){
		World w = GameWindow.getCurrentWorld();
		int lPosX;
		for (Lemmings l:w.getLemmingsList()){
			if(l.getId()!=id){
				lPosX = l.getPosX();
				if(lPosX>=tPosXLeft && lPosX <= tPosXRight){
					l.setPosX(tPosXRight+1);
				}
			}
		}
		for(int i = 0;i<height;i++) {
						w.setMapTypeAtPos(tPosXLeft,posY-i,w.GROUND_CST); //truc bizarre, taille 1 suffit(sans for, juste posY une fois)
						w.setMapPixelColor(tPosXLeft,posY-i,Color.red);	
					}
		for(int j = 0;j<height;j++) {
						w.setMapTypeAtPos(tPosXRight,posY-j,w.GROUND_CST);
						w.setMapPixelColor(tPosXRight,posY-j,Color.red);
					}
	}
	
	public void resetMap(){
		World w = GameWindow.getCurrentWorld();
		for(int i = 0;i<height;i++) {
						w.setMapTypeAtPos(tPosXLeft,posY-i,w.AIR_CST); //truc bizarre, taille 1 suffit(sans for, juste posY une fois)
						w.setMapPixelColor(tPosXLeft,posY-i,Color.blue);	
					}
		for(int j = 0;j<height;j++) {
						w.setMapTypeAtPos(tPosXRight,posY-j,w.AIR_CST);
						w.setMapPixelColor(tPosXRight,posY-j,Color.blue);
					}
	}
	
	public Lemmings changeJob(int state){
		resetMap();
		return super.changeJob(state);
	}
	
	public void kill(){
		resetMap();
		super.kill();
	}
	
	public boolean haveEnoughPlace(World w){
	//Fonction qui tente de descendre le lemming
		int i;
		
		for (i=1;i<height+1;i++){		//recherche pour la place 
			if(w.getPos(posX+direction,posY-i)!=0 || w.getPos(posX-direction*width,posY-i)!=0){	//et qu'il peut rentrer
				//System.out.println("False pas la place");
				enoughPlace = false;
				return false;
			}
		}
		//System.out.println("VVrai y a la place");
		enoughPlace = true;
		return true;
		
	}
	
	public void move(World w){
	//bouge le lemming selon le world
		//plus tard ajout de draw animation
		if (!inWorld) return;
		if (fall(w)) return;
		if(affectMapBool){
					affectMap();
					affectMapBool = false;
		}
		if (haveEnoughPlace(w)) return;
		System.out.println("False pas la place");
		if (walk(w)) return;
		direction = -direction;
		posX += direction*width;
		tPosXLeft = posX-width;
		tPosXRight = posX;
		
	}	
	
}
