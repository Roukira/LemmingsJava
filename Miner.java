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
	private int MINE_MAX = 30;
	private int MineCounter = 40;
	
	private int radiusY;
	private int radiusX;
	private int stepHeight = 2;
	
	
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
		iMine = 0;
		radiusX = width/2-1;
		radiusY = height;
		
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
		iMine = 0;
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
				int newPosX = posX+direction*radiusX;
				int newPosY = posY-directionY*stepHeight;
				if (w.onBounds(newPosX,newPosY)){
					//posX += direction*(5*radiusX/4);
					//posY -= directionY*stepHeight;
				}
				else{
					System.out.println("turn into walker from imine");
					w.changeJob(this,World.WALKER);
				}
				posX += direction*(radiusX+width/4);
				posY -= directionY*stepHeight;
				System.out.println("Avancement");
				return;
			}/*if (iMine == MINE_MAX/2){
				posX += direction*(radiusX+width/4)/2;
				posY -= directionY*stepHeight/2;
			}
			/*if (super.goAhead()){
				System.out.println("turn into walker from goAhead");
				w.changeJob(this,World.WALKER);
				return;
			}*/
			iMine--;
			
		}
		
	
	}
	
	public boolean goAhead(){
		boolean res = true;
		//int tmpWidht = width;
		//int tmpHeight = height;
		//width = imageRight.getWidth();
		//height = imageRight.getHeight();
		if (!super.walk()){
			if (!super.climbUp()){
				res = super.climbDown();
			}
		}
		//width = tmpWidht;
		//height = tmpHeight;
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
		int x;
		int y = posY-height/2;
		if (direction == 1){
			x = posX+width/2;
			if (!w.onBounds(x,y)) x = posX-width/2-arrowDownHover.getWidth();
			if (directionY == 1){
				if(arrowHovered) g.drawImage(arrowDownHover,x,y,null);
				else g.drawImage(arrowDown,x,y,null);
		}
			else{
				if(arrowHovered) g.drawImage(arrowUpHover,x,y,null);
				else g.drawImage(arrowUp,x,y,null);
			}
		}
		else{
			x = posX-width/2-arrowDownHover.getWidth();
			if (!w.onBounds(x,y)) x = posX+width/2;
			if (directionY == 1){
				if(arrowHovered) g.drawImage(arrowDownHover,x,y,null);
				else g.drawImage(arrowDown,x,y,null);
			}
			else{
				if(arrowHovered) g.drawImage(arrowUpHover,x,y,null);
				else g.drawImage(arrowUp,x,y,null);
			}
		}
		
	}
	
	
	public void affectMap(){
		if (direction == 1){
			if (directionY == 1){
				for (int i = posX;i<=posX+width/2+radiusX;i++){
					for (int j = posY-stepHeight-radiusY;j<=posY-stepHeight;j++){
						if (w.onBounds(i,j)){
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
			else{
				for (int i = posX;i<=posX+width/2+radiusX;i++){
					for (int j = posY+stepHeight-radiusY;j<=posY+stepHeight;j++){
						if (w.onBounds(i,j)){
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
						if (w.onBounds(i,j)){
							w.setMapTypeAtPos(i,j,w.AIR_CST);
							w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
						}
					}
				}
			}
			else{
				for (int i = posX;i>=posX-width/2-radiusX;i--){
					for (int j = posY+stepHeight-radiusY;j<=posY+stepHeight;j++){
						if (w.onBounds(i,j)){
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
		if (direction == 1) return posX+width/2;
		else return posX-width/2-arrowDownHover.getWidth();
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
