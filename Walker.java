import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Walker extends Lemmings{			//Classe des Walker (elle sera abstraite)

//================== CONSTRUCTEURS ======================

	public Walker(int id, int posX, int posY){
		super(id,posX,posY);
	}

	public Walker(Lemmings l){
		super(l);
	}
	
//===================== METHODES =========================
		
	public void move(){
	//bouge le lemming selon le world
		//plus tard ajout de draw animation
		if (!inWorld) return;
		if (fall()) return;
		if (walk()) return;							//tente de grimper
		System.out.println("Can't walk");
		if (climbUp()) return;		
		System.out.println("Can't climb");					//tente de descendre 
		if (climbDown()) return;
		System.out.println("Can't climdown");
		direction = -direction;
	}
	
}
