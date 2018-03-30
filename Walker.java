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
		
	public void move(World w){
	//bouge le lemming selon le world
		//plus tard ajout de draw animation
		if (!inWorld) return;
		if (fall(w)) return;
		if (walk(w)) return;							//tente de grimper
		if (climbUp(w)) return;							//tente de descendre 
		if (climbDown(w)) return;
		direction = -direction;
		posX += direction*width;						 //retourne le lemming si faux
	}
	
}
