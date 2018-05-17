import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Excavater extends Digger{
	/*this class is a Lemmings sub class, its job is to destoy walls in his way forward*/
	
	
//==================== ATTRIBUTES ========================
	//excavater images
	private static BufferedImage excavaterImage0;
	private static BufferedImage excavaterImage1;
	private static BufferedImage excavaterImage2;
	private static BufferedImage excavaterImage3;   
	
	//basic lemmings images changed to his color
	private static BufferedImage imageRightExcavater;
	private static BufferedImage imageRightStepExcavater;
	private static BufferedImage imageLeftExcavater;
	private static BufferedImage imageLeftStepExcavater;
	     
	private int iExca;					//counter for each ground digging animation, used to know when to change picture, and when to affect the map.
	private static final int iExca_MAX = 40;		//constant to know which state of the animation we are in, the lower the faster the animation will be.
	private static final int DIGG_DEEP = 2;			//digging height per iteration
	

//================== CONSTRUCTORS ======================

	public static void loadAssets(){
		//loading excavater assets
		try{
			excavaterImage0 = ImageIO.read(new File("lemmings/excavater0.png"));
			excavaterImage1 = ImageIO.read(new File("lemmings/excavater1.png"));
			excavaterImage2 = ImageIO.read(new File("lemmings/excavater2.png"));
			excavaterImage3 = ImageIO.read(new File("lemmings/excavater3.png"));
			
			imageRightExcavater = ImageIO.read(new File("lemmings/lemmings1Excavater.png"));
			imageRightStepExcavater = ImageIO.read(new File("lemmings/lemmings1stepExcavater.png"));
			imageLeftExcavater = ImageIO.read(new File("lemmings/lemmings2Excavater.png"));
			imageLeftStepExcavater = ImageIO.read(new File("lemmings/lemmings2stepExcavater.png"));
		}catch(Exception e){e.printStackTrace();}
	}
	
	public Excavater(Lemmings l){
		super(l);
		height = excavaterImage0.getHeight();
		width = excavaterImage0.getWidth();
	}


//=================== METHODS ==========================

	public void move(){
		//move method, describing the way the Excavater moves
		if (!inWorld) return;
		if(!action){							//excavater is not excavating yet
			if (fall()) return;					//and he is not falling
			this.job = World.EXCAVATER;				//then he starts his job
			this.action = true;
		}else{								//if he is digging the ground
			if (fall()){						//and he is falling
				w.changeJob(this,World.WALKER);			//then he stops his job
				return;
			}
			if (iExca == 0){					//during his job, he digs the ground when his animation is finished
				affectMap();
				iExca = iExca_MAX;				//and his animation restarts
			}
			else if( iExca == (int)(iExca_MAX/4)) affectMap(); 	//he also digs at 3/4 of the animation
			iExca--;						//updates his animation counter
			
		}
		
	
	}
	
	public void drawAction(Graphics2D g){
		//drawAction method describes the way the Excavater is drawn during his job
		if (iExca<(int)(iExca_MAX/4)) g.drawImage(excavaterImage3,posX-(width/2),posY-height,null);			//stage 4
		else if (iExca<(int)(2*iExca_MAX/4)) g.drawImage(excavaterImage2,posX-(width/2),posY-height,null);		//stage 3
		else if (iExca<(int)(3*iExca_MAX/4)) g.drawImage(excavaterImage1,posX-(width/2),posY-height,null);		//stage 2
		else g.drawImage(excavaterImage0,posX-(width/2),posY-height,null);						//stage 1
	}
	
	
	public void affectMap(){
		//affectMap method digs the ground.
		for (int i=-width/2;i<=width/2;i++){								//digging with his body shape
			for (int j = 1; j<= DIGG_DEEP ;j++){							//at DIGG_DEEP height
				if (w.getPos(posX+i,posY+j)==World.INVALID_POS_CST 				//if position is out of world
					|| w.getPos(posX+i,posY+j)==World.STOPPER_WALL_LEFT_CST			//or excavater is digging a stopper wall
					|| w.getPos(posX+i,posY+j)==World.STOPPER_WALL_RIGHT_CST){
					w.changeJob(this,World.WALKER);						//he stops his job
					return;
				}
				w.setMapTypeAtPos(posX+i,posY+j,w.AIR_CST);					//else he digs below him
				w.setMapPixelColor(posX+i,posY+j,w.AIR_LIST.get(w.airIndex));
			}
		}
		posY = posY+DIGG_DEEP;										//and falls DIGG_DEEP height
	}
	
	public void resetMap(){}										//Excavater can't recover his actions

	public BufferedImage getImageRight(){
		return imageRightExcavater;
	}
	public BufferedImage getImageRightStep(){
		return imageRightStepExcavater;
	}
	public BufferedImage getImageLeft(){
		return imageLeftExcavater;
	}
	public BufferedImage getImageLeftStep(){
		return imageLeftStepExcavater;
	}
	
}
