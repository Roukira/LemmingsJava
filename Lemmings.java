import java.util.ArrayList;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Lemmings extends Thing{			//Classe des Lemmings (elle sera abstraite)

	
//==================== ATTRIBUTS ========================
	protected int id; 				//identifiant du Lemming
	public static World w;
	protected int direction; 				//sens de mouvement : -1 si a gauche, +1 si a droite
	public static int nbLemmings = 0; 		//compteur static des lemmings.
	protected int job; 			//si en pleine action
	protected boolean action;				//quelle action : 0 si aucune plus tard classe Action
	protected boolean alive; 				//dead or alive
	protected boolean spawned;
	protected boolean inWorld;			//si il est entre dans le terrain
	protected boolean outside;			//si il a reussi a sortir
	protected int iDeath = 0;
	protected boolean bombDeath = false;
	protected int height;		//Taille de l'image d'un Lemming standard
	protected int width;			//Largeur de l'image d'un Lemming standard
	protected boolean inAir = false;			//Boolean pour savoir si le lemmingsest en train de tomber
	protected int iWalk = 0;				//iteration qui debute lanimation de bouger
	protected int iFall = 0;
	protected static int maxFall;		//hauteur max avant la mort
	
	protected static BufferedImage imageRight;		//Image du Walker avancant sur la droite
	protected static BufferedImage imageRightStep;		//Image du Walker avancant sur la droite en marchant
	protected static BufferedImage imageLeft;		//Image du Walker avancant sur la gauche
	protected static BufferedImage imageLeftStep;		//Image du Walker avancant sur la gauche en marchant
	
	protected static BufferedImage imageExplosion;
	
		//Image du Walker avancant sur la gauche en marchant
	
		//Image du Walker avancant sur la gauche en marchant
	
	
	protected static BufferedImage deathFirst;
	protected static BufferedImage deathSecond;	
	protected int bombCountdown=-1;
	protected static BufferedImage boom5;
	protected static BufferedImage boom4;
	protected static BufferedImage boom3;
	protected static BufferedImage boom2;
	protected static BufferedImage boom1;
	public static final int bombRadius = 25;
	

//================== CONSTRUCTEURS ======================

	public Lemmings(int posX, int posY){
		super(posX,posY);
		nbLemmings++;				//Incrementation de nombre total de lemmings
		this.id = nbLemmings;
		direction = 1;				//initialement, ils se deplacent a droite
		id = nbLemmings;			//Iniatialise l identifiant a la
		outside = false;			//boolean pour savoir si il s'est refugie initialement faux
		inWorld = false;			//initialement, le lemming nest pas dans le monde jusqu au spawn
		alive = true;				//initialement, il est en vie
		action = false;				//classe Action
		spawned = false;
		job = World.WALKER;
		
		this.width = imageRight.getWidth();							//recupere la largeur et hauteur du lemming
		this.height = imageRight.getHeight();
		maxFall = 10*height;
		
	}	
	
	public static void loadAssets(){
		try{
			
			imageRight = ImageIO.read(new File("lemmings/lemmings1.png"));				//recupere les images des Walker a differents etats
			imageRightStep = ImageIO.read(new File("lemmings/lemmings1step.png"));
			imageLeft = ImageIO.read(new File("lemmings/lemmings2.png"));
			imageLeftStep = ImageIO.read(new File("lemmings/lemmings2step.png"));
			
			
			imageExplosion = ImageIO.read(new File("lemmings/explosion.png"));
			
			
			deathFirst = ImageIO.read(new File("lemmings/death1.png"));
			deathSecond = ImageIO.read(new File("lemmings/death2.png"));
			
			
		}catch(Exception e){e.printStackTrace();}
	}
	
	public Lemmings(Lemmings l){
		this(l.posX,l.posY);
		this.id = l.id;
		iFall = l.iFall;
		direction = l.direction;
		inWorld = l.inWorld;
		iDeath = l.iDeath;
		inAir = l.inAir;
		iWalk = l.iWalk;
		alive = l.alive;
		spawned = l.spawned;
		bombCountdown = l.bombCountdown;		
	}


//===================== METHODES =========================
	
	public abstract void move();

	public String toString(){
		return "Lemmings number "+id;
	}
	
	public void draw(Graphics2D g){
		if(drawDeath(g)) return;
		if(!inWorld) return;
		if(!alive) return;
		drawBomb(g);
		drawMove(g);
	}
	
	
	public void drawBomb(Graphics2D g){
		if(bombCountdown>0){
			g.setColor(Color.white);
			g.setFont(new Font("default", Font.BOLD, 14));
			if (Window.getTps()-bombCountdown<60) g.drawString(""+5,posX-width/2,posY-2*height);
			else if (Window.getTps()-bombCountdown<120) g.drawString(""+4,posX-width/2,posY-2*height);
			else if (Window.getTps()-bombCountdown<180) g.drawString(""+3,posX-width/2,posY-2*height);
			else if (Window.getTps()-bombCountdown<240) g.drawString(""+2,posX-width/2,posY-2*height);
			else if (Window.getTps()-bombCountdown<300) g.drawString(""+1,posX-width/2,posY-2*height);
		}
	}
	
	public void drawMove(Graphics2D g){
		if(action) return;
		if (direction == 1){
			if((Window.getTps()-iWalk)%10 > 5 && !inAir){		
				g.drawImage(getImageRightStep(),posX-imageRightStep.getWidth()/2,posY-imageRightStep.getHeight(),null);
			}
			else g.drawImage(getImageRight(),posX-imageRight.getWidth()/2,posY-imageRightStep.getHeight(),null);
		}
		else {
			if((Window.getTps()-iWalk)%10 > 5 && !inAir){
				g.drawImage(getImageLeftStep(),posX-imageLeftStep.getWidth()/2,posY-imageRightStep.getHeight(),null);
			}
			else g.drawImage(getImageLeft(),posX-imageLeft.getWidth()/2,posY-imageRightStep.getHeight(),null);
		}
	}
	
	public boolean drawDeath(Graphics2D g){
	
		if (iDeath != 0){
			if (bombDeath){
				g.drawImage(imageExplosion,posX-imageExplosion.getWidth()/2,posY-imageExplosion.getHeight()/2,null);
			}
			else{
				//if(getClass().toString().contains("class Stopper")) System.out.println("death");
				if (iDeath >= 20) g.drawImage(deathFirst,posX-imageRight.getWidth()/2,posY-height,null);
				else g.drawImage(deathSecond,posX-imageRight.getWidth()/2,posY-height,null);
				
			}
			iDeath--;
			return true;
		}
		return false;
	}
	
	public void update(){
		if (!alive) return;
		bomb();
		move();
	}
	
	public boolean fall(){
		
		for (int i=0;i<(imageRight.getWidth()/2);i++){
			if(w.getPos(posX-i,posY+1)!=0 || w.getPos(posX+i,posY+1)!=0){
				if (iFall<maxFall && posY+2<=w.getHeight()) {
					iFall = 0;
				}
				else kill();
				inAir = false;
				return false;
			}
		}
		
		posY++;
		inAir = true;
		iWalk = Window.getTps();
		iFall++;
		action = false;
		return true;	
	}
	
	
	public boolean walk(){
	//Fonction qui fait marcher le lemmings
		for (int i =0;i<(height);i++){
			if(w.getPos(posX+direction*(imageRight.getWidth()/2),posY-i)!=0 
			&& w.getPos(posX+direction*(imageRight.getWidth()/2),posY-i)!=direction+3
			&& w.getPos(posX+direction*(imageRight.getWidth()/2),posY-i)!=direction+4){				//Verifie qur toute la hauteur du corps passe pour marcher
				//direction + 3 car w.WALL_RIGHT_CST = 4; et w.WALL_LEFT_CST = 2;
				return false;
			}
		}
		posX+=direction;
		return true;		//avance car aucun obstacle
	}
	
	public boolean climbDown(){
	//Fonction qui tente de descendre le lemming
		int i;
		for (i=1;i<imageRight.getHeight()/2;i++){			//descend si le leming n'a pas a se baisser trop 
			if(w.getPos(posX+direction*(imageRight.getWidth()/2),posY+i)==0 && w.getPos(posX+direction*(imageRight.getWidth()/2)+(direction*3*(imageRight.getWidth()/2)),i+posY-imageRight.getHeight()+1)==0){	
				for (int j =1;j<i;j++){
					if(w.getPos(posX+direction*(imageRight.getWidth()/2),posY-i)!=0){
					//On verifie que il n y a pas d obstacle trop haut sinon on retourne false
						return false;
					}
				}
			//Le if verifie que il peut descendre et que quand il descend il peut renterr en entier
				posX+=direction;
				posY+=i;
				return true;
			}
		}
		
		return false;
		
	}
	
	public boolean climbUp(){
		int i,j;
		for (i =(imageRight.getHeight()/2);i<imageRight.getHeight();i++){
			if(w.getPos(posX+direction*(imageRight.getWidth()/2),posY-i)!=0 && w.getPos(posX+direction*(imageRight.getWidth()/2),posY-i)!=direction+3){
			//On verifie que il n y a pas d obstacle trop haut sinon on retourne false
				return false;
			}
		}
		for (i =(imageRight.getHeight())/2+1;i>=0;i--){
			if(w.getPos(posX+direction*(imageRight.getWidth()/2),posY-i)!=0 && w.getPos(posX+direction*(imageRight.getWidth()/2),posY-i)!=direction+3){
			//On regarde la taille de la marche et on la climb
				for (j =i+1;j<i+imageRight.getHeight();j++){
					if(w.getPos(posX+direction*(imageRight.getWidth()/2),posY-j)!=0 && w.getPos(posX+direction*(imageRight.getWidth()/2),posY-j)!=direction+3){
					//On verifie que il n y a pas d obstacle trop haut sinon on retourne false
						return false;
					}
				}
				posX+=direction;
				posY-=i+1;
				return true;
			}
		}
		return false;
		
	}
	
	
	public boolean bomb(){
		if(bombCountdown == -1) return false;
		int time = Window.getTps()-bombCountdown;
		if(time>300 && inWorld){
			System.out.println("direction : "+direction);
			for (int i = posX-bombRadius;i<posX+bombRadius;i++){
				for (int j = posY-bombRadius;j<posY+bombRadius;j++){
					if (w.getPos(i,j)>=1 
					&& w.getPos(i,j)!=3 
					&& w.getPos(i,j)!=5
					&& (i-posX)*(i-posX)+(j-posY)*(j-posY)<=bombRadius*bombRadius){
						w.setMapTypeAtPos(i,j,w.AIR_CST);
						w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
					}
				}
			}
			if (this instanceof Affecter) ((Affecter)this).resetMap();
			bombDeath = true;
			kill();
			bombCountdown =-1;
		}
		return true;
	}
	
	public void startBomb(){
		bombCountdown = Window.getTps();
	}
	
	public void spawn(){
		inWorld = true;
		spawned = true;
	}
	
	public void win(){
		if (this instanceof Affecter){
			((Affecter)this).resetMap();
		}
		inWorld = false;
		
	}
	
	public void kill(){
		
		alive = false;
		inWorld = false;
		iDeath = 40;
		if (this instanceof Affecter){
			((Affecter)this).resetMap();
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public void setPosX(int posX){
		this.posX = posX;	
	}
	
	public void setPosY(int posY){
		this.posY = posY;
	}
	
	public boolean getInWorld(){
		return inWorld;
	}
	
	public int getJob(){
		return job;
	}
	
	public int getId(){
		return id;
	}
	
	public boolean getAlive(){
		return alive;
	}
	
	public boolean getSpawned(){
		return spawned;
	}
	
	public long getBombCountdown(){
		return bombCountdown;
	}
	
	public abstract BufferedImage getImageRight();
	public abstract BufferedImage getImageRightStep();
	public abstract BufferedImage getImageLeft();
	public abstract BufferedImage getImageLeftStep();
	
	
}
