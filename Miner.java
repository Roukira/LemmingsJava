import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Miner extends Digger{
	
	private int directionY;
	
	private static BufferedImage minerImage1;
	private static BufferedImage minerImage2;
	private static BufferedImage minerImageUp;
	private static BufferedImage minerImageDown;
	
	private static BufferedImage minerReversedImage1;
	private static BufferedImage minerReversedImage2;
	private static BufferedImage minerReversedImageUp;
	private static BufferedImage minerReversedImageDown;
	
	protected static BufferedImage imageRightMiner;		//Image du Walker avancant sur la droite
	protected static BufferedImage imageRightStepMiner;		//Image du Walker avancant sur la droite en marchant
	protected static BufferedImage imageLeftMiner;		//Image du Walker avancant sur la gauche
	protected static BufferedImage imageLeftStepMiner;	
	
	private int iMine;
	private int MINE_MAX = 30;
	private int MineCounter = 40;
	
	private int radiusY;
	private int radiusX;
	private int stepHeight = 2;
	
	
	//===================== CONSTRUCTEURS ==========================
		
	public static void loadAssets(){
		try{
			minerImage1 = ImageIO.read(new File("lemmings/miner1.png"));
			minerImage2 = ImageIO.read(new File("lemmings/miner2.png"));
			minerImageUp = ImageIO.read(new File("lemmings/minerUp.png"));
			minerImageDown = ImageIO.read(new File("lemmings/minerDown.png"));
			
			minerReversedImage1 = ImageIO.read(new File("lemmings/minerReverse1.png"));
			minerReversedImage2 = ImageIO.read(new File("lemmings/minerReverse2.png"));
			minerReversedImageUp = ImageIO.read(new File("lemmings/minerReverseUp.png"));
			minerReversedImageDown = ImageIO.read(new File("lemmings/minerReverseDown.png"));
			
			imageRightMiner = ImageIO.read(new File("lemmings/lemmings1Miner.png"));
			imageRightStepMiner = ImageIO.read(new File("lemmings/lemmings1stepMiner.png"));
			imageLeftMiner = ImageIO.read(new File("lemmings/lemmings2Miner.png"));
			imageLeftStepMiner = ImageIO.read(new File("lemmings/lemmings2stepMiner.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public Miner(Lemmings l, int directionY){
		super(l);
		
		height = minerImage1.getHeight();
		width = minerImage1.getWidth();
		
		this.directionY = directionY;
		iMine = MINE_MAX;
		radiusX = width/4;
		radiusY = height;
	}
	
	//=================== METHODES ==========================

	public void move(){
		if (!inWorld) return;
		if(!affectMapBool){
			if (fall()) return;
			if (goAhead()) return;
			affectMapBool = true;
			this.job = World.MINER;
			this.action = true;
			move();
		}else{
			if (fall()){
				System.out.println("turn into walker from fall");
				w.changeJob(this,World.WALKER);
				return;
			}
			if (iMine == 0){
				affectMap();
				iMine = MINE_MAX;
				MineCounter--;
				if (MineCounter == 0){
					System.out.println("turn into walker from out of mine");
					w.changeJob(this,World.WALKER);
				}
				int newPosX = posX+direction*(radiusX+width/4);
				int newPosY = posY-directionY*stepHeight;
				if (!w.onBounds(newPosX,newPosY)){
					System.out.println("turn into walker from imine");
					w.changeJob(this,World.WALKER);
				}
				posX += direction*(radiusX+width/4);
				posY -= directionY*stepHeight;
				System.out.println("Avancement");
				return;
			}
			iMine--;
			
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
	
	public void drawAction(Graphics2D g){
		if (direction == 1){
			if (iMine<(int)(1+MINE_MAX/3)){
				if (directionY == 1) g.drawImage(minerImageUp,posX-(width/2),posY-height,null);
				else g.drawImage(minerImageDown,posX-(width/2),posY-height,null);
			}
			else if (iMine<(int)(1+2*MINE_MAX/3)) g.drawImage(minerImage2,posX-(width/2),posY-height,null);
			else g.drawImage(minerImage1,posX-(width/2),posY-height,null);
		}else{
			if (iMine<(int)(1+MINE_MAX/3)){
				if (directionY == 1) g.drawImage(minerReversedImageUp,posX-(width/2),posY-height,null);
				else g.drawImage(minerReversedImageDown,posX-(width/2),posY-height,null);
			}
			else if (iMine<(int)(1+2*MINE_MAX/3)) g.drawImage(minerReversedImage2,posX-(width/2),posY-height,null);
			else g.drawImage(minerReversedImage1,posX-(width/2),posY-height,null);
		}
	}
	
	public void affectMap(){
		if (direction == 1){
			if (directionY == 1){
				for (int i = posX;i<=posX+width/2+radiusX;i++){
					for (int j = posY-stepHeight-radiusY;j<=posY-stepHeight;j++){
						if (w.canDestructPixel(i,j)){
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
			else{
				for (int i = posX;i<=posX+width/2+radiusX;i++){
					for (int j = posY+stepHeight-radiusY;j<=posY+stepHeight;j++){
						if (w.canDestructPixel(i,j)){
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
		}
		else{
			if (directionY == 1){
				for (int i = posX;i>=posX-width/2-radiusX;i--){
					for (int j = posY-stepHeight-radiusY;j<=posY-stepHeight;j++){
						if (w.canDestructPixel(i,j)){
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
			else{
				for (int i = posX;i>=posX-width/2-radiusX;i--){
					for (int j = posY+stepHeight-radiusY;j<=posY+stepHeight;j++){
						if (w.canDestructPixel(i,j)){
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
		}
	}
	
	public void changeDirectionY(){
		directionY = -directionY;
	}
	
	
	public BufferedImage getImageRight(){
		return imageRightMiner;
	}
	public BufferedImage getImageRightStep(){
		return imageRightStepMiner;
	}
	public BufferedImage getImageLeft(){
		return imageLeftMiner;
	}
	public BufferedImage getImageLeftStep(){
		return imageLeftStepMiner;
	}
	
}
