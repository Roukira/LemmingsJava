import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Excavater extends Digger{
	private static BufferedImage excavaterImage0;
	private static BufferedImage excavaterImage1;
	private static BufferedImage excavaterImage2;
	private static BufferedImage excavaterImage3;        
	private int iExca;
	private static final int iExca_MAX = 40;
	private static final int DIGG_DEEP = 2;
	

//================== CONSTRUCTEURS ======================

	public Excavater(int posX, int posY){
		super(posX,posY);
		height = excavaterImage0.getHeight();
		width = excavaterImage0.getWidth();
	}
	
	public static void loadAssets(){
		try{
			excavaterImage0 = ImageIO.read(new File("lemmings/excavater0.png"));
			excavaterImage1 = ImageIO.read(new File("lemmings/excavater1.png"));
			excavaterImage2 = ImageIO.read(new File("lemmings/excavater2.png"));
			excavaterImage3 = ImageIO.read(new File("lemmings/excavater3.png"));
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
		if(!affectMapBool){
			if (fall()) return;
			affectMapBool = true;
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
	
	public void draw(Graphics2D g){
	//Dessine le lemming
		super.draw(g);
		if (!alive) return;
		if (!inWorld) return;
		if (inAir) return;
		if (affectMapBool) drawExcavater(g);
	}
	
	public void drawExcavater(Graphics2D g){
		if (iExca<(int)(iExca_MAX/4)) g.drawImage(excavaterImage3,posX-(width/2),posY-height,null);
		else if (iExca<(int)(2*iExca_MAX/4)) g.drawImage(excavaterImage2,posX-(width/2),posY-height,null);
		else if (iExca<(int)(3*iExca_MAX/4)) g.drawImage(excavaterImage1,posX-(width/2),posY-height,null);
		else g.drawImage(excavaterImage0,posX-(width/2),posY-height,null);
	}
	
	
	public void affectMap(){
		for (int i=-width/2;i<=width/2;i++){
			for (int j = 0; j<= DIGG_DEEP ;j++){
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
	
}
