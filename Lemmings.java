import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Lemmings{			//Classe des Lemmings (elle sera abstraite)

	
//==================== ATTRIBUTS ========================
	protected int id; 				//identifiant du Lemming
	protected int posX; 				//position en x tout a droite ou gauche de l'image selon direction
	protected int posY;				//en y en bas de l'image
	protected int direction; 				//sens de mouvement : -1 si a gauche, +1 si a droite
	public static int nbLemmings = 0; 		//compteur static des lemmings.
	protected int actionState; 			//si en pleine action
	protected boolean action;				//quelle action : 0 si aucune plus tard classe Action
	protected boolean alive; 				//dead or alive
	protected boolean inWorld;			//si il est entre dans le terrain
	protected boolean outside;			//si il a reussi a sortir
	protected int iDeath = 0;
	protected int height;		//Taille de l'image d'un Lemming standard
	protected int width;			//Largeur de l'image d'un Lemming standard
	protected boolean inAir = false;			//Boolean pour savoir si le lemmingsest en train de tomber
	protected int iWalk = 0;				//iteration qui debute lanimation de bouger
	protected int iFall = 0;
	protected static int maxFall;		//hauteur max avant la mort
	protected BufferedImage imageRight;		//Image du Walker avancant sur la droite
	protected BufferedImage imageRightStep;		//Image du Walker avancant sur la droite en marchant
	protected BufferedImage imageLeft;		//Image du Walker avancant sur la gauche
	protected BufferedImage imageLeftStep;		//Image du Walker avancant sur la gauche en marchant
	protected BufferedImage deathFirst;
	protected BufferedImage deathSecond;	
	protected long bombCountdown=-1;
	protected BufferedImage boom5;
	protected BufferedImage boom4;
	protected BufferedImage boom3;
	protected BufferedImage boom2;
	protected BufferedImage boom1;
	protected static int widthCountdown;
	

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
		alive = true;				//initialement, il est en vie
		action = false;				//classe Action
		actionState = 0;
		try{
			
			imageRight = ImageIO.read(new File("lemmings/lemmings1.png"));				//recupere les images des Walker a differents etats
			imageRightStep = ImageIO.read(new File("lemmings/lemmings1step.png"));
			imageLeft = ImageIO.read(new File("lemmings/lemmings2.png"));
			imageLeftStep = ImageIO.read(new File("lemmings/lemmings2step.png"));
			deathFirst = ImageIO.read(new File("lemmings/death1.png"));
			deathSecond = ImageIO.read(new File("lemmings/death2.png"));
			boom5 = ImageIO.read(new File("lemmings/boom5.png"));
			boom4 = ImageIO.read(new File("lemmings/boom4.png"));
			boom3 = ImageIO.read(new File("lemmings/boom3.png"));
			boom2 = ImageIO.read(new File("lemmings/boom2.png"));
			boom1 = ImageIO.read(new File("lemmings/boom1.png"));
			
			
		}catch(Exception e){e.printStackTrace();}
		this.width = imageRight.getWidth();							//recupere la largeur et hauteur du lemming
		this.height = imageRight.getHeight();
		maxFall = 10*height;
		this.widthCountdown = boom1.getHeight();
		
	}	
	
	public Lemmings(Lemmings l){
		this(l.id,l.posX,l.posY);
		iFall = l.iFall;
		direction = l.direction;
		inWorld = l.inWorld;
		iDeath = l.iDeath;
		inAir = l.inAir;
		iWalk = l.iWalk;
		alive = l.alive;
		bombCountdown = l.bombCountdown;
		
	}


//===================== METHODES =========================


	public String toString(){
		return "Lemmings number "+id;
	}
	
	public void draw(Graphics2D g){
	//Dessine le lemming
		//if(getClass().toString().contains("class Stopper")) System.out.println(getClass());
		if(drawDeath(g)) return;
		if(!inWorld) return;
		if(!alive) return;
		drawBomb(g);
		drawMove(g);
	}
	
	
	public void drawBomb(Graphics2D g){
		//System.out.println("startDrawBomb");
		if(bombCountdown>0){
			int posXbomb;
			if (direction == 1){
				posXbomb = posX-(width/2)-(widthCountdown/2);
			}else posXbomb = posX-(width/2)+(widthCountdown/2);
			
			if (System.currentTimeMillis()-bombCountdown<1000) g.drawImage(boom5,posXbomb,posY-2*height,null);
			else if (System.currentTimeMillis()-bombCountdown<2000) g.drawImage(boom4,posXbomb,posY-2*height,null);
			else if (System.currentTimeMillis()-bombCountdown<3000) g.drawImage(boom3,posXbomb,posY-2*height,null);
			else if (System.currentTimeMillis()-bombCountdown<4000) g.drawImage(boom2,posXbomb,posY-2*height,null);
			else if (System.currentTimeMillis()-bombCountdown<5000) g.drawImage(boom1,posXbomb,posY-2*height,null);	
			//refaire passer le contdown a -1 ????
		}
	}
	
	public void drawMove(Graphics2D g){
		if(action && actionState>=1) return;
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
	
	public boolean drawDeath(Graphics2D g){
	
		if (iDeath != 0){
			//if(getClass().toString().contains("class Stopper")) System.out.println("death");
			if (iDeath >= 10) g.drawImage(deathFirst,posX-width,posY-height,null);
			else g.drawImage(deathSecond,posX-width,posY-height,null);
			iDeath--;
			return true;
		}
		return false;
	}
	
	public void update(World w){
		if (!alive) return;
		bomb();
		move(w);
	}
	
	public abstract void move(World w);
	
	public boolean fall(World w){
		if(w.getPos(posX,posY+1)==0  && w.getPos(posX-direction*(width/2),posY+1)==0 && w.getPos(posX-direction*width,posY+1)==0){		//Si pas de sol en dessous de lui pour tout son corps
			posY++;
			inAir = true;
			iWalk = GameWindow.getTps();
			iFall++;
			action = false;
			return true;
		}
		inAir = false;
		if (iFall<maxFall) {
					iFall = 0;
					action = true;
					}
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
	
	
	public boolean bomb(){
		if(bombCountdown == -1) return false;
		//System.out.println("hello2");
		long time = System.currentTimeMillis()-bombCountdown;
		if(time>5000){
			World w = GameWindow.getCurrentWorld();
			System.out.println("boom?");
			int r = 2*width;
			for (int i = posX-r;i<posX+r;i++){
				System.out.println("boom??");
				for (int j = posY-r;j<posY+r;j++){
					System.out.println("boom ?? ?? ??");
					System.out.println(((i-posX)*(i-posX)+(j-posY)*(j-posY)));
					if (w.getPos(i,j)>=1 && (i-posX)*(i-posX)+(j-posY)*(j-posY)<=r*r){
						System.out.println("boom at X = "+i+" Y = "+j);
						w.setMapTypeAtPos(i,j,w.AIR_CST);
						w.setMapPixelColor(i,j,w.AIR_LIST.get(w.airIndex));
					}
				}
			}
			kill();
			bombCountdown =-1;
		}
		return true;
	}
	
	public void startBomb(){
		bombCountdown = System.currentTimeMillis();
		System.out.println(bombCountdown);
	}
	
	public void spawn(){
		inWorld = true;
	}
	
	public void win(){
		inWorld = false;
	}
	
	public void kill(){
		alive = false;
		iDeath = 20;
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
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
	
	public Lemmings changeJob(int state){
		this.actionState = state;
		if (state == 1) return new Stopper(this);
		return new Walker(this);
	}	
	
	public int getJob(){
		return actionState;
	}
	
	public int getId(){
		return id;
	}
	
	public boolean getAlive(){
		return alive;
	}
	
	public long getBombCountdown(){
		return bombCountdown;
	}
	
}
