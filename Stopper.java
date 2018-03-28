import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;


public class Stopper extends Lemmings{


//==================== ATTRIBUTS ========================

	private BufferedImage image0;		//Image du Stopper avancant sur la droite
	private BufferedImage image1;		//Image du Stopper avancant sur la droite en marchant
	private BufferedImage image2;		//Image du Stopper avancant sur la gauche
	private BufferedImage image3;		//Image du Stopper avancant sur la gauche en marchant
	private int iStopBegin = 0;
	private int iStop = 0;
	private int sWidth;
	private int sHeight;
	private boolean enoughPlace;

//================== CONSTRUCTEURS ======================

	public Stopper(int id, int posX, int posY){
		super(id,posX,posY);
		try{
			image0 = ImageIO.read(new File("lemmings/stopper0.png"));
			image1 = ImageIO.read(new File("lemmings/stopper1.png"));
			image2 = ImageIO.read(new File("lemmings/stopper2.png"));
			image3 = ImageIO.read(new File("lemmings/stopper3.png"));
			
		}catch(Exception e){e.printStackTrace();}
		sHeight = image0.getHeight();
		sWidth = image0.getWidth();
		this.actionState = 1;
		this.action = true;
	}
	
	public Stopper(Lemmings l){
		super(l);
		try{
			image0 = ImageIO.read(new File("lemmings/stopper0.png"));
			image1 = ImageIO.read(new File("lemmings/stopper1.png"));
			image2 = ImageIO.read(new File("lemmings/stopper2.png"));
			image3 = ImageIO.read(new File("lemmings/stopper3.png"));
			
		}catch(Exception e){e.printStackTrace();}
		sHeight = image0.getHeight();
		sWidth = image0.getWidth();
		this.actionState = 1;
		this.action = true;
	}

//===================== METHODES =========================
	
	public void draw(Graphics2D g){
	//Dessine le lemming
		if(inWorld){
			if(alive){
				if(!inAir && enoughPlace){
					if(iStopBegin<20){		
						g.drawImage(image0,posX-sWidth,posY-height,null);
						iStopBegin++;
						return;
					}
					else if((GameWindow.getTps()-iStop)%60 < 20){	
						g.drawImage(image1,posX-sWidth,posY-height,null);
						return;
					}
					else if((GameWindow.getTps()-iStop)%60 < 40){	
						g.drawImage(image2,posX-sWidth,posY-height,null);
						return;
					}
					else{
						g.drawImage(image3,posX-sWidth,posY-height,null);
						return;
					}
				}
				else{
					
					iStop = GameWindow.getTps();
					super.draw(g);
				}
			}
		}
	}
	
	public void affectMap(){
		
	}
	
	public boolean haveEnoughPlace(World w){
	//Fonction qui tente de descendre le lemming
		int i;
		
		for (i=1;i<height+1;i++){		//recherche pour la place 
			if(w.getPos(posX+direction,posY-i)!=0 || w.getPos(posX-direction*sWidth,posY-i)!=0){	//et qu'il peut rentrer
				//System.out.println("False pas la place");
				enoughPlace = false;
				return false;
			}
		}
		//System.out.println("VVrai y a la place");
		enoughPlace = true;
		return true;
		
	}
	
	public void move(World w){
	//bouge le lemming selon le world
		//plus tard ajout de draw animation
		if (!inWorld) return;
		if (!alive) return;
		if (fall(w)) return;
		if (haveEnoughPlace(w)) return;
		System.out.println("False pas la place");
		if (walk(w)) return;
		direction = -direction;
		posX += direction*sWidth;
		
	}
	
	
}
