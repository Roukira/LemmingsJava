import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Basher extends Digger{
	/*this class is a Lemmings sub class, its job is to destoy walls in his way forward*/
	
	//basher images
	private static BufferedImage basherImage0;
	private static BufferedImage basherImage1;
	private static BufferedImage basherImage2;
	private static BufferedImage basherImage3;
	
	private static BufferedImage basherImage0reverse;
	private static BufferedImage basherImage1reverse;
	private static BufferedImage basherImage2reverse;
	private static BufferedImage basherImage3reverse;
	
	
	//basic lemmings images changed to his color
	private static BufferedImage imageRightBasher;
	private static BufferedImage imageRightStepBasher;
	private static BufferedImage imageLeftBasher;
	private static BufferedImage imageLeftStepBasher;
	
	private int iBash;							//counter for each wall bashing animation, used to know when to change picture, and when to affect the map.
	private static final int iBASH_MAX = 15;	//constant to know which state of the animation we are in, the lower the faster the animation will be.
	
	private static final int bashWidth = 3;
	private static final int rangeBash = 4;
	

//================== CONSTRUCTORS ======================
	
	public static void loadAssets(){
		//loading Basher assets
		try{
			basherImage0 = ImageIO.read(new File("lemmings/basher0.png"));
			basherImage1 = ImageIO.read(new File("lemmings/basher1.png"));
			basherImage2 = ImageIO.read(new File("lemmings/basher2.png"));
			basherImage3 = ImageIO.read(new File("lemmings/basher3.png"));
			
			basherImage0reverse = ImageIO.read(new File("lemmings/basher0reverse.png"));
			basherImage1reverse = ImageIO.read(new File("lemmings/basher1reverse.png"));
			basherImage2reverse = ImageIO.read(new File("lemmings/basher2reverse.png"));
			basherImage3reverse = ImageIO.read(new File("lemmings/basher3reverse.png"));
			
			imageRightBasher = ImageIO.read(new File("lemmings/lemmings1Basher.png"));
			imageRightStepBasher = ImageIO.read(new File("lemmings/lemmings1stepBasher.png"));
			imageLeftBasher = ImageIO.read(new File("lemmings/lemmings2Basher.png"));
			imageLeftStepBasher = ImageIO.read(new File("lemmings/lemmings2stepBasher.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public Basher(Lemmings l){
		//Basher constructor, copied from the former lemming
		super(l);																//copies former lemming attributes
		height = basherImage0.getHeight();										//updates height
		width = basherImage0.getWidth();										//updates width
	}


//=================== METHODS ==========================

	public void move(){
		//move method, describing the way the Basher moves
		
		if (!inWorld) return;										//if Basher is not bashing wall yet
		if(!action){												//check if he can fall, or move forward
			if (fall()) return;										
			if (goAhead()) return;
			this.job = World.BASHER;
			this.action = true;										//if he can start bashing wall, start his job.
			iBash = iBASH_MAX;
		}
		else{
			if (fall()){											//if he is bashing wall, check if he can fall
				System.out.println(toString()+" is falling.");
				w.changeJob(this,World.WALKER);						//if he can, change to Walker
				return;
			}
			if (iBash == 0){										//else if he reached end of his animation, move him bashWidth pixels on his direction
				posX+=bashWidth*direction;							//and start a new animation
				iBash = iBASH_MAX;
				if (goAhead()){										//unless he can walk, which means his job is done
					w.changeJob(this,World.WALKER);
					return;
				}
			}
			else affectMap();										//during animation, affectMap is called to bash the walls
			iBash--;												//update the animation counter
			
		}
		
	
	}
	
	public boolean goAhead(){
		//goAhead method says if the Basher can move forward (if he can, it means his job is done), and if he can, he will.
		
		if (super.walk()){																	//if he can move forward
			if (action) System.out.println(toString()+" is walking.");
			return true;
		}
		else if (super.climbUp()){															//or he can climb up
			if (action) System.out.println(toString()+" is climbing up.");
			return true;
		}
		else if (super.climbDown()){														//or he can climb down
			if (action) System.out.println(toString()+" is climbing down.");
			return true;
		}
		else if (super.checkForStopperWall()){												//or the obstacle is a Stopper wall (which he shouldn't consider an obstacle)
			direction = -direction;															//then he can moves forward (or changes his direction if it's a Stopper wall)
			if (action) System.out.println(toString()+" is changing direction due to stopper wall");
			return true;
		}
		else if (!w.onBounds(posX+direction*(imageRight.getWidth()/2),posY)){				//or he reached world limits
			direction = -direction;
			System.out.println(toString()+" is changing direction due to map limits");
			return true;
		}
		return false;
	}
	
	public void drawAction(Graphics2D g){
		//drawAction method describes the way the Basher is drawn during his job
	
		if (direction == 1){
			if (iBash<=(int)(iBASH_MAX/(4*1.0))) g.drawImage(basherImage2,posX-(width/2),posY-height,null);					//stage 3
			else if (iBash<=(int)(2*iBASH_MAX/(4*1.0))) g.drawImage(basherImage1,posX-(width/2),posY-height,null);			//stage 2
			else if (iBash<=(int)(3*iBASH_MAX/(4*1.0))) g.drawImage(basherImage0,posX-(width/2),posY-height,null);			//stage 1
			else g.drawImage(basherImage3,posX-(width/2),posY-height,null);													//stage 4
		}else{
			if (iBash<=(int)(iBASH_MAX/(4*1.0))) g.drawImage(basherImage2reverse,posX-(width/2),posY-height,null);
			else if (iBash<=(int)(2*iBASH_MAX/(4*1.0))) g.drawImage(basherImage1reverse,posX-(width/2),posY-height,null);
			else if (iBash<=(int)(3*iBASH_MAX/(4*1.0))) g.drawImage(basherImage0reverse,posX-(width/2),posY-height,null);
			else g.drawImage(basherImage3reverse,posX-(width/2),posY-height,null);
		}
	}
	
	
	public void affectMap(){
		//affectMap method digs the ground
		int diggYend = height;																		//dig Y position's end
		int diggYstart = 0;																			//dig Y position's beginning
		int diggX = bashWidth;																		//dig X width
		
		if (iBash == (int)(iBASH_MAX/(4*1.0))){														//if stage 3
			diggYend = (int)(1+height/3);
		}
		else if (iBash == (int)(2*iBASH_MAX/(4*1.0))){ 													//if stage 2
			diggYstart = (int)(1+height/3);  
			diggYend = (int)(2*height/3);
			diggX = (3*bashWidth)/2;
		}
		else if (iBash == (int)(3*iBASH_MAX/(4*1.0))){													//if stage 1
			diggYstart = (int)(2*height/3);
		}
		else return;																				//stage 4 does nothing
		for (int i=rangeBash;i<diggX+rangeBash;i++){												//applying world modifications
			for (int j = diggYstart; j<=diggYend;j++){
				w.setMapTypeAtPos(posX+direction*i,posY-j,w.AIR_CST);
				w.setMapPixelColor(posX+direction*i,posY-j,w.AIR_LIST.get(w.airIndex));
			}
		}
	}
	
	public void resetMap(){} 			//Basher can't remove the modifications he applied
	
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
