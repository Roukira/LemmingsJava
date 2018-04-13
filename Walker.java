import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Walker extends Lemmings{			//Classe des Walker (elle sera abstraite)

//================== CONSTRUCTEURS ======================

	public Walker(int posX, int posY){
		super(posX,posY);
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
		if (climbUp()) return;		
		if (climbDown()) return;
		direction = -direction;
	}
	
}
