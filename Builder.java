import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Builder extends Lemmings implements Affecter{

	private BufferedImage builderImage0;
	private BufferedImage builderImage1;
	private BufferedImage builderImage2;
	private BufferedImage builderImage3;
	private BufferedImage buildStep;
	private boolean affectMapBool = false;
	private int nbSteps = 20;
	private int iBuild = 80;

//================== CONSTRUCTEURS ======================

	public Builder(int id, int posX, int posY){
		super(id,posX,posY);
		try{
			builderImage0 = ImageIO.read(new File("lemmings/builder0.png"));
			builderImage1 = ImageIO.read(new File("lemmings/builder1.png"));
			builderImage2 = ImageIO.read(new File("lemmings/builder2.png"));
			builderImage3 = ImageIO.read(new File("lemmings/builder3.png"));
			buildStep = ImageIO.read(new File("lemmings/buildstep.png"));
			
		}catch(Exception e){e.printStackTrace();}
		this.job = 2;
		this.action = true;
		height = builderImage0.getHeight();
		width = builderImage0.getWidth();
	}
	
	public Builder(Lemmings l){
		super(l);
		try{
			builderImage0 = ImageIO.read(new File("lemmings/builder0.png"));
			builderImage1 = ImageIO.read(new File("lemmings/builder1.png"));
			builderImage2 = ImageIO.read(new File("lemmings/builder2.png"));
			builderImage3 = ImageIO.read(new File("lemmings/builder3.png"));
			buildStep = ImageIO.read(new File("lemmings/buildstep.png"));
			
		}catch(Exception e){e.printStackTrace();}
		this.job = 2;
		this.action = true;
		height = builderImage0.getHeight();
		width = builderImage0.getWidth();
		System.out.println("new builder...");
	}

//===================== METHODES =========================
	
	public void draw(Graphics2D g){
	//Dessine le lemming
		if (!alive) return;
		if (!inWorld) return;
		if (inAir) return;
		drawBuild(g);
	}
	
	public void drawBuild(Graphics2D g){
		if (iBuild<20) g.drawImage(builderImage3,posX-(width/2),posY-height,null);
		else if (iBuild<40) g.drawImage(builderImage2,posX-(width/2),posY-height,null);
		else if (iBuild<60) g.drawImage(builderImage1,posX-(width/2),posY-height,null);
		else g.drawImage(builderImage0,posX-(width/2),posY-height,null);
	}
	
	public void affectMap(){
		if (iBuild>0) {
			affectMapBool = false;
			return;
		}
		
		/*for(int i = 0;i<buildStep.getWidth();i++){
			for(int j = 0;j<buildStep.getHeight();j++){
				w.setMapTypeAtPos(posX+1+j,posY+i,w.GROUND_CST);
			}
		}*/
		w.addObjectToWorld(posX+direction,posY-buildStep.getHeight(),buildStep);
		affectMapBool = true;
		return;
	}
	
	public void resetMap(){}
	
	public void move(){
		if (!inWorld) return;
		if (fall()) return;
		affectMap();
		if(affectMapBool){
			nbSteps--;
			posX+=5*direction;
			posY-=buildStep.getHeight();
			iBuild = 80;
		}
		else iBuild--;
		
	}
	
}
