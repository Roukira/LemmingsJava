import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Miner extends Digger{

//==================== ATTRIBUTES ========================

	private int directionY;
	
	//image for miner actions
	private static BufferedImage minerImage1;
	private static BufferedImage minerImage2;
	private static BufferedImage minerImageUp;
	private static BufferedImage minerImageDown;
	
	private static BufferedImage minerReversedImage1;
	private static BufferedImage minerReversedImage2;
	private static BufferedImage minerReversedImageUp;
	private static BufferedImage minerReversedImageDown;
	
	//colored lemmings walker like the miner
	protected static BufferedImage imageRightMiner;
	protected static BufferedImage imageRightStepMiner;
	protected static BufferedImage imageLeftMiner;
	protected static BufferedImage imageLeftStepMiner;	
	
	private int iMine;		//counter for each wall mining animation, used to know when to change picture, and when to affect the map.
	private int MINE_MAX = 30;
	private int MineCounter = 40;	//counter to limit the time the miner mine
	
	private int radiusY;		//the range of the mining on the y axis
	private int radiusX;		//the range of the mining on the x axis
	private int stepHeight = 2;	//Miner step Height when moving forward
	
	
	
	//===================== CONSTRUCTORS ==========================
		
	public static void loadAssets(){
		//loading Basher assets
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
	
	
	//=================== METHODS ==========================

	public void move(){
	//move method, describing the way the Miner moves
		if (!inWorld) return;			//check if he is in world
		if(!action){				//then if he hasn't begin the action
			if (fall()) return;		//if falling keep falling
			if (goAhead()) return;		//if no obstacle to mine keep going ahead
			this.job = World.MINER;		//else start mining
			this.action = true;
			move();
		}else{
			if (fall()){
				//if start to fall after the action begin stop the action
				w.changeJob(this,World.WALKER);
				return;
			}
			if (iMine == 0){
				affectMap();
				iMine = MINE_MAX;				//to start a new animation
				MineCounter--;					//update MineCounter
				if (MineCounter == 0){
					//change his job after a period of time define with MineCounter
					w.changeJob(this,World.WALKER);
				}
				int newPosX = posX+direction*(radiusX+width/4);
				int newPosY = posY-directionY*stepHeight;
				if (!w.onBounds(newPosX,newPosY)){
					//if try to mine out of the map change his job
					w.changeJob(this,World.WALKER);
				}
				posX += direction*(radiusX+width/4);
				posY -= directionY*stepHeight;
				//change his postion after mining
				return;
			}
			iMine--;						//update the animation counter
			
		}
		
	
	}
	
	public boolean goAhead(){
		if (!super.walk()){
			//if cannot walk check if can climbUp
			if (!super.climbUp()){
				//if cannot ClimUP check if can climbDown
				if(!super.climbDown()){
					//if cannot climbDown check if it is because of a stopper wall
					if (super.checkForStopperWall()){
						//if yes goback to find another wall
						direction = -direction;
					}
					else{
						//if all this false then miner can't go ahead
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void drawAction(Graphics2D g){
	//draw action method using iMine to adapt the animation
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
	//method affecting the world's map
		if (direction == 1){
			if (directionY == 1){
				for (int i = posX;i<=posX+width/2+radiusX;i++){
					for (int j = posY-stepHeight-radiusY;j<=posY-stepHeight;j++){
						if (w.canDestructPixel(i,j)){
							//change the mined place so lemmings can go throught
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							//and change his color
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
			else{
				for (int i = posX;i<=posX+width/2+radiusX;i++){
					for (int j = posY+stepHeight-radiusY;j<=posY+stepHeight;j++){
						if (w.canDestructPixel(i,j)){
							//change the mined place so lemmings can go throught
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							//and change his color
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
							//change the mined place so lemmings can go throught
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							//and change his color
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
			else{
				for (int i = posX;i>=posX-width/2-radiusX;i--){
					for (int j = posY+stepHeight-radiusY;j<=posY+stepHeight;j++){
						if (w.canDestructPixel(i,j)){
							//change the mined place so lemmings can go throught
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							//and change his color
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
	
	
	//==================method tochange the walker Lemmings color=====================
	
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
