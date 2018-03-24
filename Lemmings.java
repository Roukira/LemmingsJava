import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Lemmings{			//Classe des Lemmings (elle sera abstraite)

	
//==================== ATTRIBUTS ========================
	private int id; 				//identifiant du Lemming
	private int posX; 				//position en x tout a droite ou gauche de l'image selon direction
	private int posY;				//en y en bas de l'image
	private int direction; 				//sens de mouvement : -1 si a gauche, +1 si a droite
	private static int nbLemmings = 0; 		//compteur static des lemmings.
	private boolean actionState; 			//si en pleine action
	private int action;				//quelle action : 0 si aucune plus tard classe Action
	private boolean alive; 				//dead or alive
	private boolean inWorld;			//si il est entre dans le terrain
	private boolean outside;			//si il a reussi a sortir
	private BufferedImage imageRight;		//Image du lemmings avancant sur la droite
	private BufferedImage imageRightStep;		//Image du lemmings avancant sur la droite en marchant
	private BufferedImage imageLeft;		//Image du lemmings avancant sur la gauche
	private BufferedImage imageLeftStep;		//Image du lemmings avancant sur la gauche en marchant
	private BufferedImage deathFirst;
	private BufferedImage deathSecond;
	private int iDeath = 0;
	private static int height;		//Taille de l'image d'un Lemming standard
	private static int width;			//Largeur de l'image d'un Lemming standard
	private boolean inAir = false;			//Boolean pour savoir si le lemmingsest en train de tomber
	private int iWalk = 0;				//iteration qui debute lanimation de bouger
	private int iFall = 0;
	private static int maxFall;		//hauteur max avant la mort
	
	

//================== CONSTRUCTEURS ======================

	public Lemmings(int id, int posX, int posY){
		this.id = id;
		nbLemmings++;				//Incrementation de nombre total de lemmings
		this.posX = posX;
		this.posY = posY;
		direction = 1;				//initialement, ils se deplacent a droite
		id = nbLemmings;			//Iniatialise l identifiant a la
		outside = false;			//boolean pour savoir si il s'est refugie initialement faux
		inWorld = false;			//initialement, le lemming nest pas dans le monde jusqu au spawn
		alive = false;				//initialement, il est en vie
		action = 0;				//classe Action
		actionState = false;			
		try{
			imageRight = ImageIO.read(new File("lemmings/lemmings1.png"));				//recupere les images des lemmings a differents etats
			
			imageRightStep = ImageIO.read(new File("lemmings/lemmings1step.png"));
			
			
			imageLeft = ImageIO.read(new File("lemmings/lemmings2.png"));

			imageLeftStep = ImageIO.read(new File("lemmings/lemmings2step.png"));

			deathFirst = ImageIO.read(new File("lemmings/death1.png"));

			deathSecond = ImageIO.read(new File("lemmings/death2.png"));
			
		}catch(Exception e){e.printStackTrace();}
		width = imageRight.getWidth();							//recupere la largeur et hauteur du lemming
		height = imageRight.getHeight();
		maxFall = 7*height;
	}	


//===================== METHODES =========================


	public String toString(){
		return "Lemmings number "+id;
	}
	
	public void draw(Graphics2D g){
	//Dessine le lemming
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
	
	
	public void move(World w){
	//bouge le lemming selon le world
		//plus tard ajout de draw animation
		
		if (!alive) return;
		if (fall(w)) return;
		if (walk(w)) return;							//tente de grimper
		if (climbUp(w)) return;							//tente de descendre 
		if (climbDown(w)) return;
		direction = -direction;
		posX += direction*width;						 //retourne le lemming si faux
	}
	
	public boolean fall(World w){
		if(w.getPos(posX,posY+1)==0  && w.getPos(posX-direction*(width/2),posY+1)==0 && w.getPos(posX-direction*width,posY+1)==0){		//Si pas de sol en dessous de lui pour tout son corps
			posY++;
			inAir = true;
			iWalk = GameWindow.getTps();
			iFall++;
			return true;
		}
		inAir = false;
		if (iFall<maxFall) iFall = 0;
		else kill();
		return false;	
	}
	
	
	public boolean walk(World w){
	//Fonction qui fait marcher le lemmings
		for (int i =0;i<(height);i++){
			if(w.getPos(posX+direction,posY-i)!=0){				//Verifie qur toute la hauteur du corps passe pour marcher
						return false;
			}
		}
		posX+=direction;
		return true;		//avance car aucun obstacle
	}
	
	public boolean climbDown(World w){
	//Fonction qui tente de descendre le lemming
		int i;
		
		for (i=1;i<height/2;i++){												//descend si le leming n'a pas a se baisser trop 
			if(w.getPos(posX+direction,posY+i)==0 && w.getPos(posX+direction+(direction*width),i+posY-height+1)==0){	//et qu'il peut rentrer
				posX+=direction;
				posY+=i;
				return true;
			}
		}
		
		return false;
		
	}
	
	public boolean climbUp(World w){
		int i;
		for (i =(height/2);i<(height);i++){
			if(w.getPos(posX+direction,posY-i)!=0){
				return false;
			}
		}
		for (i =(height)/2+1;i>0;i--){
			if(w.getPos(posX+direction,posY-i)!=0){
				posX+=direction;
				posY-=i+1;
				return true;
			}
		}
		return false;
		
	}
	
	
	public void spawn(World w){
		inWorld = true;
		move(w);
	}
	
	public void kill(){
		alive = false;
		iDeath = 20;
	}
	
	public void setAlive(boolean alive){
		this.alive = alive;
	}
	

}
