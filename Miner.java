import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Miner extends Digger{
	
	private int directionY;
	
	private BufferedImage minerImage1;
	private BufferedImage minerImage2;
	private BufferedImage minerImageUp;
	private BufferedImage minerImageDown;
	
	private BufferedImage minerReversedImage1;
	private BufferedImage minerReversedImage2;
	private BufferedImage minerReversedImageUp;
	private BufferedImage minerReversedImageDown;
	
	private BufferedImage arrowUp;
	private BufferedImage arrowDown;
	private BufferedImage arrowUpHover;
	private BufferedImage arrowDownHover;
	private boolean arrowHovered = false;
	
	private int iMine;
	private int MINE_MAX = 90; //plus tard 90 qd + d'animations
	private int MineCounter = 40;
	
	private int radiusY;
	private int radiusX;
	private int stepHeight = 4;
	
	
	//===================== CONSTRUCTEURS ==========================
	
	public Miner(int posX, int posY){
		super(posX,posY);
		try{
			minerImage1 = ImageIO.read(new File("lemmings/miner1.png"));
			minerImage2 = ImageIO.read(new File("lemmings/miner2.png"));
			minerImageUp = ImageIO.read(new File("lemmings/minerUp.png"));
			minerImageDown = ImageIO.read(new File("lemmings/minerDown.png"));
			
			minerReversedImage1 = ImageIO.read(new File("lemmings/minerReverse1.png"));
			minerReversedImage2 = ImageIO.read(new File("lemmings/minerReverse2.png"));
			minerReversedImageUp = ImageIO.read(new File("lemmings/minerReverseUp.png"));
			minerReversedImageDown = ImageIO.read(new File("lemmings/minerReverseDown.png"));
			
			arrowUp = ImageIO.read(new File("lemmings/arrowUp.png"));
			arrowUpHover = ImageIO.read(new File("lemmings/arrowUpHover.png"));
			arrowDown = ImageIO.read(new File("lemmings/arrowDown.png"));
			arrowDownHover = ImageIO.read(new File("lemmings/arrowDownHover.png"));
			
		}catch(Exception e){e.printStackTrace();}
		//this.job = 2;
		//this.action = true;
		height = minerImage1.getHeight();
		width = minerImage1.getWidth();
		
		directionY = 1;
		//nextDirection ?
		iMine = MINE_MAX;
		radiusX = width/2;
		radiusY = height/4;
		
	}
	
	public Miner(Lemmings l){
		super(l);
		try{
			minerImage1 = ImageIO.read(new File("lemmings/miner1.png"));
			minerImage2 = ImageIO.read(new File("lemmings/miner2.png"));
			minerImageUp = ImageIO.read(new File("lemmings/minerUp.png"));
			minerImageDown = ImageIO.read(new File("lemmings/minerDown.png"));
			
			minerReversedImage1 = ImageIO.read(new File("lemmings/minerReverse1.png"));
			minerReversedImage2 = ImageIO.read(new File("lemmings/minerReverse2.png"));
			minerReversedImageUp = ImageIO.read(new File("lemmings/minerReverseUp.png"));
			minerReversedImageDown = ImageIO.read(new File("lemmings/minerReverseDown.png"));
			
			arrowUp = ImageIO.read(new File("lemmings/arrowUp.png"));
			arrowUpHover = ImageIO.read(new File("lemmings/arrowUpHover.png"));
			arrowDown = ImageIO.read(new File("lemmings/arrowDown.png"));
			arrowDownHover = ImageIO.read(new File("lemmings/arrowDownHover.png"));
			
		}catch(Exception e){e.printStackTrace();}
		//this.job = 2;
		//this.action = true;
		height = minerImage1.getHeight();
		width = minerImage1.getWidth();
		
		directionY = 1;
		//nextDirection ?
		iMine = MINE_MAX;
		radiusX = width/2;
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
				w.changeJob(this,World.WALKER);
				return;
			}
			if (goAhead()){
				w.changeJob(this,World.WALKER);
				return;
			}
			if (iMine == 0){
				affectMap();
				iMine = MINE_MAX;
				MineCounter--;
				int newPosX = posX+direction*radiusX;
				int newPosY = posY-directionY*stepHeight;
				int getPos = w.getPos(posX,posY);
				if (getPos !=-1){
					posX += direction*radiusX;
					posY -= directionY*stepHeight;
				}
				else{
					w.changeJob(this,World.WALKER);
				}
				return;
			}
			iMine--;
			
		}
		
	
	}
	
	public boolean goAhead(){
		boolean res = true;
		int tmpWidht = width;
		int tmpHeight = height;
		width = imageRight.getWidth();
		height = imageRight.getHeight();
		if (!super.walk()){
			if (!super.climbUp()){
				res = super.climbDown();
			}
		}
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
		if (affectMapBool){
			drawMine(g);
			drawArrow(g);
		}
		
	}
	
	public void drawMine(Graphics2D g){
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
	
	public void drawArrow(Graphics2D g){
		if (directionY == 1){
			if(arrowHovered) g.drawImage(arrowDownHover,posX+direction*width/2,posY-height/2,null);
			else g.drawImage(arrowDown,posX+direction*width/2,posY-height/2,null);
		}
		else{
			if(arrowHovered) g.drawImage(arrowUpHover,posX+direction*width/2,posY-height/2,null);
			else g.drawImage(arrowUp,posX+direction*width/2,posY-height/2,null);
		}
	}
	
	
	public void affectMap(){
		if (direction == 1){
			if (directionY == 1){
				for (int i = posX;i<=posX+radiusX;i++){
					for (int j = posY-stepHeight-radiusY;j<=posY-stepHeight;j++){
						if (w.getPos(i,j) != -1){
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
			else{
				for (int i = posX;i<=posX+radiusX;i++){
					for (int j = posY+stepHeight;j<=posY+stepHeight+radiusY;j++){
						if (w.getPos(i,j) != -1){
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
		}
		else{
			if (directionY == 1){
				for (int i = posX;i>=posX-radiusX;i--){
					for (int j = posY-stepHeight-radiusY;j<=posY-stepHeight;j++){
						if (w.getPos(i,j) != -1){
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
			else{
				for (int i = posX;i>=posX-radiusX;i--){
					for (int j = posY+stepHeight;j<=posY+stepHeight+radiusY;j++){
						if (w.getPos(i,j) != -1){
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
	
	public void setArrowHovered(boolean arrowHovered){
		this.arrowHovered = arrowHovered;
	}
	
	public int getArrowPosX(){
		return posX+direction*width/2;
	}
	
	public int getArrowPosY(){
		return posY-height/2;
	}
	
	public int getArrowHeight(){
		return arrowUp.getHeight();
	}
	
	public int getArrowWidth(){
		return arrowUp.getWidth();
	}
	
}
