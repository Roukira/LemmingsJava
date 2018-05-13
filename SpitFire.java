import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class SpitFire extends Item{

	private static BufferedImage imageSpitFire;
	
	private static BufferedImage imageFire1;
	private static BufferedImage imageFire2;
	private static BufferedImage imageFire3;
	private static BufferedImage imageFire4;
	
	private static final int SPEEDFIRE = 30; //plus tard mettre dans le constructeur
	
	private int fireRangeX;
	private int fireRangeY;
	private int iFire = 0;
	private int posXfire;
	private int posYfire;
	private World world;
	
	private int spitFireRhythm;
	
	private static int height;
	private static int width; 
	
	public static void loadAssets(){
		System.out.println("##############################################");
		try{
			imageSpitFire = ImageIO.read(new File("world/spitFire0.png"));;
			imageFire1 = ImageIO.read(new File("world/spitFire1.png"));
			imageFire2 = ImageIO.read(new File("world/spitFire2.png"));
			imageFire3 = ImageIO.read(new File("world/spitFire3.png"));
			imageFire4 = ImageIO.read(new File("world/spitFire4.png"));
			
		}catch(Exception e){e.printStackTrace();}
		//fixInMap();
	}
	
	public SpitFire(int posX, int posY, World world, int rhythm){
		super(posX,posY);
		this.world = world;
		spitFireRhythm = rhythm;
	}
	
	public SpitFire(int posX, int posY, World world){
		this(posX,posY,world,3);
	}
	
	public void startItem(){
		
		width = imageSpitFire.getWidth();
		height = imageSpitFire.getHeight();
		
		world.addObjectToWorld(posX-width/2, posY-height, world.GROUND_CST, imageSpitFire, 1);
		fireRangeX = imageFire1.getWidth();
		fireRangeY = imageFire1.getHeight();
		posXfire = posX-(fireRangeX/2)-(width/2);
		posYfire = posY-(height/2)+(fireRangeY/2);
	}
	
	public void fixInMap(){
		//rendre dur pour les lemmings
		
		
		
		/*for(int i=0; i<imageSpitFire.getWidth()/2;i++){
			for(int j=posY; j<posY-imageSpitFire.getHeight();j--){
				w.setMapTypeAtPos(posX-i,j,w.GROUND_CST);
				w.setMapTypeAtPos(posX+i,j,w.GROUND_CST);
			}
		}*/
	}
	
	public void update(){
		//specifier la taille de la range + kill ceux dedans
		if (iFire<=0) iFire = spitFireRhythm*SPEEDFIRE;
		if (iFire<SPEEDFIRE) burning();
        	iFire--;      
        	//System.out.println("iFire vaut "+iFire);  	
	}
	
	public void burning(){
		//kill ceux dans la range du fire
		Lemmings[] listLemmings;
		Lemmings l;
		listLemmings = world.getLemmingsList();
        	for (int i=0;i<listLemmings.length;i++){
        		l=listLemmings[i];
			if (l.getPosX() >=  posXfire-fireRangeX/2
				&& l.getPosX() <= posXfire+fireRangeX/2
				&& l.getPosY() >= posYfire-fireRangeY
				&& l.getPosY() <= posYfire){
				if (l.alive) l.kill();
				//ameliorer l efficite
				//creer un killFire plus tard...
			}
		}
	}

	public void draw(Graphics2D g){
		//g.drawImage(imageSpitFire,posX-(width/2),posY-height/2,null);
		if (iFire<(int)(SPEEDFIRE/4)) g.drawImage(imageFire4, posXfire-(fireRangeX/2), posYfire-(fireRangeY),null);
		else if (iFire<(int)(2*SPEEDFIRE/4)) g.drawImage(imageFire3, posXfire-(fireRangeX/2), posYfire-(fireRangeY),null);
		else if (iFire<(int)(3*SPEEDFIRE/4)) g.drawImage(imageFire2, posXfire-(fireRangeX/2), posYfire-(fireRangeY),null);
		else if (iFire<SPEEDFIRE) g.drawImage(imageFire1, posXfire-(fireRangeX/2), posYfire-(fireRangeY),null);                  
	}

	

}
