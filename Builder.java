import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Builder extends Lemmings implements Affecter{

	private static BufferedImage builderImage0;
	private static BufferedImage builderImage1;
	private static BufferedImage builderImage2;
	private static BufferedImage builderImage3;
	private static BufferedImage builderImageReverse0;
	private static BufferedImage builderImageReverse1;
	private static BufferedImage builderImageReverse2;
	private static BufferedImage builderImageReverse3;
	
	private static BufferedImage builderWait0;
	private static BufferedImage builderWait1;
	private static BufferedImage builderWait2;
	private static BufferedImage builderWait3;
	
	private static BufferedImage buildStep;
	private boolean outOfBounds = false;
	private boolean changeJobBool = false;
	private int nbSteps = 20;
	
	private int iBuild = 80;
	
	private int iWait = 150;
	private String nbStepsString = ""+nbSteps;

//================== CONSTRUCTEURS ======================
	
	public static void loadAssets(){
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
		this.job = World.BUILDER;
		height = builderImage0.getHeight();
		width = builderImage0.getWidth();
		System.out.println("new builder...");
	}

//===================== METHODES =========================
	
	public void drawAction(Graphics2D g){
		if (nbSteps==0){
			if(iWait>140) g.drawImage(builderWait0,posX-(width/2),posY-height,null);
			else if (iWait>130) g.drawImage(builderWait1,posX-(width/2),posY-height,null);
			else if (iWait>120) g.drawImage(builderWait2,posX-(width/2),posY-height,null);
			else if (iWait>100) g.drawImage(builderWait3,posX-(width/2),posY-height,null);
			else if (iWait>90) g.drawImage(builderWait2,posX-(width/2),posY-height,null);
			else if (iWait>80) g.drawImage(builderWait1,posX-(width/2),posY-height,null);
			else if(iWait>70) g.drawImage(builderWait0,posX-(width/2),posY-height,null);
			else if (iWait>60) g.drawImage(builderWait1,posX-(width/2),posY-height,null);
			else if (iWait>50) g.drawImage(builderWait2,posX-(width/2),posY-height,null);
			else if (iWait>30) g.drawImage(builderWait3,posX-(width/2),posY-height,null);
			else if (iWait>20) g.drawImage(builderWait2,posX-(width/2),posY-height,null);
			else if (iWait>10) g.drawImage(builderWait1,posX-(width/2),posY-height,null);
			else g.drawImage(builderWait0,posX-(width/2),posY-height,null);
			return;
		}
		if (direction == 1){
			if (iBuild<20) g.drawImage(builderImage3,posX-(width/2),posY-height,null);
			else if (iBuild<40) g.drawImage(builderImage2,posX-(width/2),posY-height,null);
			else if (iBuild<60) g.drawImage(builderImage1,posX-(width/2),posY-height,null);
			else g.drawImage(builderImage0,posX-(width/2),posY-height,null);
		}
		else{
			if (iBuild<20) g.drawImage(builderImageReverse3,posX-(width/2),posY-height,null);
			else if (iBuild<40) g.drawImage(builderImageReverse2,posX-(width/2),posY-height,null);
			else if (iBuild<60) g.drawImage(builderImageReverse1,posX-(width/2),posY-height,null);
			else g.drawImage(builderImageReverse0,posX-(width/2),posY-height,null);
		}
		g.setColor(Color.white);
		g.setFont(new Font("default", Font.BOLD, 12));
		if (direction == 1) g.drawString(nbStepsString,posX,posY-height);
		else g.drawString(nbStepsString,posX-width/2,posY-height);
		
		
	}
	
public void affectMap(){
		if (iBuild>0) {
			return;
		}
		int type_CST;
		if (direction==1) type_CST = w.WALL_LEFT_CST;
		else type_CST = w.WALL_RIGHT_CST;
		
		if (w.getPos(posX+direction*buildStep.getWidth(),posY-buildStep.getHeight())!= 0
			&& w.getPos(posX+direction*buildStep.getWidth(),posY-buildStep.getHeight())!= type_CST){
			type_CST = w.GROUND_CST;
		}
		int startX;
		if (direction == 1) startX = posX+buildStep.getWidth()/2;
		else startX = posX-3*buildStep.getWidth()/2;
		if (!w.addObjectToWorld(startX,posY-buildStep.getHeight(), type_CST, buildStep, direction)){ 
			System.out.println("Out of bounds");
			outOfBounds = true;
			int newPosX = checkLastValidPosX();
			if (newPosX !=-1) posX = newPosX -direction*buildStep.getWidth();
			else changeJobBool = true;
			
		}
		return;
	}
	
	public int checkLastValidPosX(){
		if (direction == 1){
			for (int i = posX+buildStep.getWidth();i>=posX;i--){
				if(w.getPos(i,posY)==0){
					return i;
				}
			}
		}
		else{
			for (int i = posX-buildStep.getWidth();i<=posX;i++){
				if(w.getPos(i,posY)==0){
					return i;
				}
			}
		}
		return -1;
	}
	
	public void resetMap(){}
	
	public boolean haveEnoughPlaceAbove(){
		int i = posX;
		for (int j = posY-height;j>posY-height-buildStep.getHeight();j--){
			//w.setMapPixelColor(i,j,Color.red);
			if (w.getPos(i,j) == -1 || w.getPos(i,j) == 1) return false;
		}
		return true;
	}
	
	public boolean fall(){
		boolean res;
		int tmpWidht = width;
		int tmpHeight = height;
		width = imageRight.getWidth();
		height = imageRight.getHeight();
		res = super.fall();
		width = tmpWidht;
		height = tmpHeight;
		return res;
	}
	
	public void move(){
		if (!inWorld) return;
		if (fall()) return;
		//if (!haveEnoughPlace()){
		action=true;
		
		
		if(nbSteps==0){
			if(iWait>0) iWait--;
			else w.changeJob(this,w.WALKER);
			return;
		}
		
		if (!haveEnoughPlaceAbove()){
			System.out.println("Changement de builder a Walker babe");
			w.changeJob(this,w.WALKER);
			return;
		}
		if(iBuild<0){
			affectMap();
			if (changeJobBool){
				System.out.println("Walker because not enough place");
				w.changeJob(this,w.WALKER);
				return;
			}
			nbSteps--;
			nbStepsString = ""+nbSteps;
			posX+=direction*buildStep.getWidth();
			posY-=buildStep.getHeight();
			iBuild = 80;
		}
		if(outOfBounds){
			//w.changeJob(this,w.WALKER);
			direction = -direction;
			outOfBounds = false;
		}
		else iBuild--;
		
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
