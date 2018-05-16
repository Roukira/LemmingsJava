import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Builder extends Lemmings implements Affecter{
	/*this class is a Lemmings sub class, its job is to build stairs in his way forward up to nbSteps stairs*/
	
	//builder images
	private static BufferedImage builderImage0;
	private static BufferedImage builderImage1;
	private static BufferedImage builderImage2;
	private static BufferedImage builderImage3;
	private static BufferedImage builderImageReverse0;
	private static BufferedImage builderImageReverse1;
	private static BufferedImage builderImageReverse2;
	private static BufferedImage builderImageReverse3;
	
	//builder idle waiting images
	private static BufferedImage builderWait0;
	private static BufferedImage builderWait1;
	private static BufferedImage builderWait2;
	private static BufferedImage builderWait3;
	
	private static BufferedImage buildStep;		//stair image
	private boolean changeJobBool = false;		//boolean to know if he should change to Walker
	private boolean changedDirection = false;	//boolean to know if he should change the direction he builds in
	
	private int nbSteps = 20;					//counter for number of stairs he builds
	
	private static final int BUILD_MAX = 40;	//constant to know which state of the animation we are in, the lower the faster the animation will be.
	private int iBuild;							//animation state
	
	private static final int WAIT_MAX = 150;	//same for idle waiting animation
	private int iWait;							//same
	private String nbStepsString = ""+nbSteps;	//number of stairs remaining String displayed on top of builder

//================== CONSTRUCTORS ======================
	
	public static void loadAssets(){
		//loading Builder assets
		try{
			builderImage0 = ImageIO.read(new File("lemmings/builder0.png"));
			builderImage1 = ImageIO.read(new File("lemmings/builder1.png"));
			builderImage2 = ImageIO.read(new File("lemmings/builder2.png"));
			builderImage3 = ImageIO.read(new File("lemmings/builder3.png"));
			
			builderImageReverse0 = ImageIO.read(new File("lemmings/builderReverse0.png"));
			builderImageReverse1 = ImageIO.read(new File("lemmings/builderReverse1.png"));
			builderImageReverse2 = ImageIO.read(new File("lemmings/builderReverse2.png"));
			builderImageReverse3 = ImageIO.read(new File("lemmings/builderReverse3.png"));
			
			builderWait0 = ImageIO.read(new File("lemmings/builderWait0.png"));
			builderWait1 = ImageIO.read(new File("lemmings/builderWait1.png"));
			builderWait2 = ImageIO.read(new File("lemmings/builderWait2.png"));
			builderWait3 = ImageIO.read(new File("lemmings/builderWait3.png"));
			
			buildStep = ImageIO.read(new File("lemmings/buildstep2.png"));
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public Builder(Lemmings l){
		super(l);
		height = builderImage0.getHeight();
		width = builderImage0.getWidth();
	}

//===================== METHODS =========================
	
	public void move(){
		//move method, describing the way the Builder moves
		if (!action){																	//if Builder has not started his job yet
			if (!inWorld) return;														//and he is on the ground
			if (fall()) return;
			this.action = true;															//then he can start his job
			this.job = World.BUILDER;
			iBuild = BUILD_MAX;
			iWait = WAIT_MAX;
		}
		else{																			//if he is building stairs
			if (fall()){																//and he can fall then he stops his job
				System.out.println("Builder changing to Walker due to falling");
				w.changeJob(this,w.WALKER);
				return;
			}
			if(nbSteps==0){																//if he has reached nbSteps stairs he stops his job as well
				if(iWait>0) iWait--;													//after his idle waiting animation is done
				else w.changeJob(this,w.WALKER);
				return;
			}
			
			if (!haveEnoughPlaceAbove()){												//if he is under a ceiling, he stops his job to avoid getting stuck.		
				System.out.println("Changement de builder a Walker");
				w.changeJob(this,w.WALKER);
				return;
			}
			if(iBuild == 0){															//if his building animation is done,
				affectMap();															//he puts the stairs
				if (changeJobBool){														//and stops his job if he can climb up.
					System.out.println("Walker because not enough place");
					w.changeJob(this,w.WALKER);
					return;
				}
				nbSteps--;																//number of stairs left updated
				nbStepsString = ""+nbSteps;												//string updated
				if (!changedDirection){													//if changing direction is necessary due to an obstacle, he does it
					posX+=direction*buildStep.getWidth();								//by replacing himself in the stairs under the ones he just placed
					posY-=buildStep.getHeight();
				}
				else changedDirection = false;
				
				iBuild = BUILD_MAX;														//restart animation when done
			}
			else iBuild--;																//updates animation
		}
		
	}
	
	public void drawAction(Graphics2D g){
		//drawAction method describes the way the Builder is drawn during his job
		
		//drawing idle waiting animation at end of job
		if (nbSteps==0){
			if(iWait>12*WAIT_MAX/13) g.drawImage(builderWait0,posX-(width/2),posY-height,null);
			else if (iWait>11*WAIT_MAX/13) g.drawImage(builderWait1,posX-(width/2),posY-height,null);
			else if (iWait>10*WAIT_MAX/13) g.drawImage(builderWait2,posX-(width/2),posY-height,null);
			else if (iWait>9*WAIT_MAX/13) g.drawImage(builderWait3,posX-(width/2),posY-height,null);
			else if (iWait>8*WAIT_MAX/13) g.drawImage(builderWait2,posX-(width/2),posY-height,null);
			else if (iWait>7*WAIT_MAX/13) g.drawImage(builderWait1,posX-(width/2),posY-height,null);
			else if (iWait>6*WAIT_MAX/13) g.drawImage(builderWait0,posX-(width/2),posY-height,null);
			else if (iWait>5*WAIT_MAX/13) g.drawImage(builderWait1,posX-(width/2),posY-height,null);
			else if (iWait>4*WAIT_MAX/13) g.drawImage(builderWait2,posX-(width/2),posY-height,null);
			else if (iWait>3*WAIT_MAX/13) g.drawImage(builderWait3,posX-(width/2),posY-height,null);
			else if (iWait>2*WAIT_MAX/13) g.drawImage(builderWait2,posX-(width/2),posY-height,null);
			else if (iWait>WAIT_MAX/13) g.drawImage(builderWait1,posX-(width/2),posY-height,null);
			else g.drawImage(builderWait0,posX-(width/2),posY-height,null);
			return;
		}
		//drawing building animation
		if (direction == 1){
			if (iBuild<BUILD_MAX/4) g.drawImage(builderImage3,posX-(width/2),posY-height,null);
			else if (iBuild<2*BUILD_MAX/4) g.drawImage(builderImage2,posX-(width/2),posY-height,null);
			else if (iBuild<3*BUILD_MAX/4) g.drawImage(builderImage1,posX-(width/2),posY-height,null);
			else g.drawImage(builderImage0,posX-(width/2),posY-height,null);
		}
		else{
			if (iBuild<BUILD_MAX/4) g.drawImage(builderImageReverse3,posX-(width/2),posY-height,null);
			else if (iBuild<2*BUILD_MAX/4) g.drawImage(builderImageReverse2,posX-(width/2),posY-height,null);
			else if (iBuild<3*BUILD_MAX/4) g.drawImage(builderImageReverse1,posX-(width/2),posY-height,null);
			else g.drawImage(builderImageReverse0,posX-(width/2),posY-height,null);
		}
		//drawing number of stairs left
		g.setColor(Color.white);
		g.setFont(new Font("default", Font.BOLD, 12));
		if (direction == 1) g.drawString(nbStepsString,posX,posY-height);
		else g.drawString(nbStepsString,posX-width/2,posY-height);
		
		
	}
	
public void affectMap(){
		//affectMap method for building the stairs
		int type_CST;																					//select the type of stairs 
		if (direction==1) type_CST = w.WALL_LEFT_CST;													//left or right depending on direction
		else type_CST = w.WALL_RIGHT_CST;
		
		if (w.getPos(posX+direction*buildStep.getWidth(),posY-buildStep.getHeight())!= World.AIR_CST	//if he is building on opposite stairs
			&& w.getPos(posX+direction*buildStep.getWidth(),posY-buildStep.getHeight())!= type_CST){	//then the stairs at this location is GROUND
			type_CST = w.GROUND_CST;
		}
		int startX;																						//starting location for drawing the stairs
		if (direction == 1) startX = posX+buildStep.getWidth()/2;										//depending on direction
		else startX = posX-3*buildStep.getWidth()/2;
		if (!w.addObjectToWorld(startX,posY-buildStep.getHeight(), type_CST, buildStep, direction)){ 	//if the stairs were obstructed by a wall
			int newPosX = checkLastValidPosX();															//get the closest position to that wall
			if (newPosX !=World.INVALID_POS_CST){														//and check if it is a valid position
				int temPosX = posX;																		//to move him there
				int temPosY = posY;
				posX = newPosX;
				posY -= buildStep.getHeight();
				//w.setMapPixelColor(posX,posY,Color.yellow);
				if (super.climbUp()){																	//and try to climb up	
					changeJobBool = true;																//which would result in changing job
					return;
				}
				else{																					//if he can't climb it up,
					posX = temPosX;																		//return to previous stairs position
					posY = temPosY;
					direction = -direction;																//and change direction
					changedDirection = true;
				}
			}
			else changeJobBool = true;
			
		}
		return;
	}
	
	public int checkLastValidPosX(){
		//checkLastValidPosX method searches for closest position to a wall in the direction Builder faces
		if (direction == 1){
			for (int i = posX;i<=posX+2*buildStep.getWidth();i++){			//up to 2x the width of stairs
				if(w.getPos(i,posY) == World.GROUND_CST){
					return i-1;												//i-1 to get the previous position
				}
			}
		}
		else{
			for (int i = posX;i>=posX-2*buildStep.getWidth();i--){
				if(w.getPos(i,posY) == World.GROUND_CST){
					return i-1;
				}
			}
		}
		return World.INVALID_POS_CST;										//if no reasonable closest position to the wall
	}
	
	public void resetMap(){}												//Builder can't remove modifications he applied
	
	public boolean haveEnoughPlaceAbove(){
		//haveEnoughPlaceAbove method checks if the Builder has no ceiling obstructing his way while building
		int i = posX;
		for (int j = posY-height;j>posY-height-buildStep.getHeight();j--){
			//w.setMapPixelColor(i,j,Color.red);
			if (w.getPos(i,j) == World.INVALID_POS_CST || w.getPos(i,j) == World.GROUND_CST) return false;	//checks if out of world, or if it is a wall
		}
		return true;
	}
	
	public BufferedImage getImageRight(){
		return imageRight;
	}
	public BufferedImage getImageRightStep(){
		return imageRightStep;
	}
	public BufferedImage getImageLeft(){
		return imageLeft;
	}
	public BufferedImage getImageLeftStep(){
		return imageLeftStep;
	}
	
}
