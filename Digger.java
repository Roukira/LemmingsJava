import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Digger extends Lemmings implements Affecter{


//================== CONSTRUCTEURS ======================
	
	public Digger(Lemmings l){
		super(l);
		
	}
	
	public boolean checkForStopperWall(){
		for (int i =0;i<(height);i++){
			if(w.getPos(posX+direction*(imageRight.getWidth()/2),posY-i)== w.STOPPER_WALL_LEFT_CST || w.getPos(posX+direction*(imageRight.getWidth()/2),posY-i)== w.STOPPER_WALL_RIGHT_CST){
					return true;
			}
			
		}
		return false;
	}
	
	public abstract void affectMap();
	
	public void resetMap(){}

}
