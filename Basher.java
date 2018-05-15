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
	private static final int iBASH_MAX = 40;	//constant to know which state of the animation we are in, the lower the faster the animation will be.
	
	private static final int bashWidth = 6;
	

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
		
		if (!inWorld) return;
		if(!action){
			if (fall()) return;
			if (goAhead()) return;
			this.job = World.BASHER;
			this.action = true;
			iBash = iBASH_MAX;
		}
		else{
			if (fall()){
				System.out.println(toString()+" is falling.");
				w.changeJob(this,World.WALKER);
				return;
			}
			if (iBash == 0){
				posX+=bashWidth*direction-1;
				iBash = iBASH_MAX;
				if (goAhead()){
					w.changeJob(this,World.WALKER);
					return;
				}
			}
			else affectMap();
			iBash--;
			
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
		return false;
	}
	
	public void drawAction(Graphics2D g){
		//drawAction method describes the way the Basher is drawn during his job
	
		if (direction == 1){
			if (iBash<=(int)(iBASH_MAX/(4*1.0))) g.drawImage(basherImage2,posX-(width/2),posY-height,null);
			else if (iBash<=(int)(2*iBASH_MAX/(4*1.0))) g.drawImage(basherImage1,posX-(width/2),posY-height,null);
			else if (iBash<=(int)(3*iBASH_MAX/(4*1.0))) g.drawImage(basherImage0,posX-(width/2),posY-height,null);
			else g.drawImage(basherImage3,posX-(width/2),posY-height,null);
		}else{
			if (iBash<=(int)(iBASH_MAX/(4*1.0))) g.drawImage(basherImage2reverse,posX-(width/2),posY-height,null);
			else if (iBash<=(int)(2*iBASH_MAX/(4*1.0))) g.drawImage(basherImage1reverse,posX-(width/2),posY-height,null);
			else if (iBash<=(int)(3*iBASH_MAX/(4*1.0))) g.drawImage(basherImage0reverse,posX-(width/2),posY-height,null);
			else g.drawImage(basherImage3reverse,posX-(width/2),posY-height,null);
		}
	}
	
	
	public void affectMap(){
		
		int diggYend = height;
		int diggYstart = 0;
		int diggX = bashWidth;
		
		if (iBash == (int)(iBASH_MAX/(4*1.0))){
			diggYend = (int)(1+height/3);
		}
		else if (iBash == (int)(2*iBASH_MAX/(4))){ 
			diggYstart = (int)(1+height/3);  
			diggYend = (int)(2*height/3);
			diggX = (3*bashWidth)/2;
		}
		else if (iBash == (int)(3*iBASH_MAX/(4))){
			diggYstart = (int)(2*height/3);
		}
		else return;	
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
