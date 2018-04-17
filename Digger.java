import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Digger extends Lemmings implements Affecter{

	protected boolean affectMapBool = false;
	

//================== CONSTRUCTEURS ======================

	public Digger(int posX, int posY){
		super(posX,posY);
		
	}
	
	public Digger(Lemmings l){
		super(l);
		
	}
	
	public abstract void affectMap();
	
	public void resetMap(){}

}
