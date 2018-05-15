import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Excavater extends Digger{
	/*this class is a Lemmings sub class, its job is to destoy walls in his way forward*/
	
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
	     
	private int iExca;										//counter for each ground digging animation, used to know when to change picture, and when to affect the map.
	private static final int iExca_MAX = 40;				//constant to know which state of the animation we are in, the lower the faster the animation will be.
	private static final int DIGG_DEEP = 2;					//digging height per iteration
	

//================== CONSTRUCTEURS ======================

	public static void loadAssets(){
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


//=================== METHODES ==========================

	public void move(){
		if (!inWorld) return;
		if(!action){
			if (fall()) return;
			this.job = World.EXCAVATER;
			this.action = true;
			move();
		}else{
			if (fall()){
				w.changeJob(this,World.WALKER);
				return;
			}
			if (iExca<1){
				affectMap();
				iExca = iExca_MAX;
			}else if( iExca == (int)(iExca_MAX/4)){
				affectMap();
			}
			iExca--;
			
		}
		
	
	}
	
	public void drawAction(Graphics2D g){
		if (iExca<(int)(iExca_MAX/4)) g.drawImage(excavaterImage3,posX-(width/2),posY-height,null);
		else if (iExca<(int)(2*iExca_MAX/4)) g.drawImage(excavaterImage2,posX-(width/2),posY-height,null);
		else if (iExca<(int)(3*iExca_MAX/4)) g.drawImage(excavaterImage1,posX-(width/2),posY-height,null);
		else g.drawImage(excavaterImage0,posX-(width/2),posY-height,null);
	}
	
	
	public void affectMap(){
		for (int i=-width/2;i<=width/2;i++){
			for (int j = 1; j<= DIGG_DEEP ;j++){
				if (w.getPos(posX+i,posY+j)==-1 
					|| w.getPos(posX+i,posY+j)==3
					|| w.getPos(posX+i,posY+j)==5){
					w.changeJob(this,World.WALKER);
					return;
				}
				w.setMapTypeAtPos(posX+i,posY+j,w.AIR_CST);
				w.setMapPixelColor(posX+i,posY+j,w.AIR_LIST.get(w.airIndex));
			}
		}
		posY = posY+DIGG_DEEP;
	}
	
	public void resetMap(){}	

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
