import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Walker extends Lemmings{			//Classe des Walker (elle sera abstraite)

	
//==================== ATTRIBUTS ========================

	private BufferedImage imageRight;		//Image du Walker avancant sur la droite
	private BufferedImage imageRightStep;		//Image du Walker avancant sur la droite en marchant
	private BufferedImage imageLeft;		//Image du Walker avancant sur la gauche
	private BufferedImage imageLeftStep;		//Image du Walker avancant sur la gauche en marchant
	private BufferedImage deathFirst;
	private BufferedImage deathSecond;	

//================== CONSTRUCTEURS ======================

	public Walker(int id, int posX, int posY){
		super(id,posX,posY);
		try{
			imageRight = ImageIO.read(new File("lemmings/lemmings1.png"));				//recupere les images des Walker a differents etats
			imageRightStep = ImageIO.read(new File("lemmings/lemmings1step.png"));
			imageLeft = ImageIO.read(new File("lemmings/lemmings2.png"));
			imageLeftStep = ImageIO.read(new File("lemmings/lemmings2step.png"));
			deathFirst = ImageIO.read(new File("lemmings/death1.png"));
			deathSecond = ImageIO.read(new File("lemmings/death2.png"));
			
		}catch(Exception e){e.printStackTrace();}
		super.width = imageRight.getWidth();							//recupere la largeur et hauteur du lemming
		super.height = imageRight.getHeight();
	}	


//===================== METHODES =========================
	
	public void draw(Graphics2D g){
	//Dessine le lemming
		if(inWorld){
			if(alive){
				if (direction == 1){
		
					if((GameWindow.getTps()-iWalk)%10 > 5 && !inAir){		
						g.drawImage(imageRightStep,posX-width,posY-height,null);
					}
					else g.drawImage(imageRight,posX-width,posY-height,null);
				}
				else {
					if((GameWindow.getTps()-iWalk)%10 > 5 && !inAir){
						g.drawImage(imageLeftStep,posX,posY-height,null);
					}
					else g.drawImage(imageLeft,posX,posY-height,null);
				}
			}
			else if (iDeath != 0){
				if (iDeath >= 10) g.drawImage(deathFirst,posX-width,posY-height,null);
				else g.drawImage(deathSecond,posX-width,posY-height,null);
				iDeath--;
			}
		}
	}
	
	
	public void move(World w){
	//bouge le lemming selon le world
		//plus tard ajout de draw animation
		if (!inWorld) return;
		if (!alive) return;
		if (fall(w)) return;
		if (walk(w)) return;							//tente de grimper
		if (climbUp(w)) return;							//tente de descendre 
		if (climbDown(w)) return;
		direction = -direction;
		posX += direction*width;						 //retourne le lemming si faux
	}
	
}
