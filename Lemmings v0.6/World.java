import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.awt.Color;
import java.io.IOException;

public class World{
	//classe qui represente le monde actuel dans la fenetre principale
	
//==================== ATTRIBUTS ========================	
	
	private int[][] map;									//carte colore du monde a mettre a jour au tout debut
	private int height;									//hauteur de la carte
	private int width;									//largeur
	private int id;										//identifiant
	private BufferedImage mapImage;								//Image en .png de la carte a charger
	public static final ArrayList<Color> AIR_LIST = new ArrayList<Color>();			//liste des constantes d'air
	public static final int AIR_CST = 0;							//constantes pour mieux lire
	public static final int GROUND_CST = 1;
	
//================== CONSTRUCTEURS ======================
	
	public World(int id, int width, int height){
		this.id = id;
		this.width = width;
		this.height = height;
		fillMap();
		initAirCst();
		try{
			mapImage = ImageIO.read(new File("world"+id+".png")); //lit l'image de la carte et la stocke en fonction de l'identifiant
		}catch(Exception e){e.printStackTrace();}
		for (int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				Color mapXY = getColor(i,j,mapImage);
  				if(AIR_LIST.contains(mapXY)){
  					map[i][j] = AIR_CST;
  				}
  				else {
  					map[i][j] = GROUND_CST;
  				}
			}
		}
	}
	
//===================== METHODES =========================
	
	public void initAirCst(){
	//initialise les constantes d'air pour avoir plus de choix (background et tout)
		AIR_LIST.add(new Color(97,172,191));
	}
	
	public void fillMap(){
	// remplit la map d'air
		map = new int[width][height];
		for (int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				map[i][j] = 0;
			}
		}
	}
	
	public int getPos(int posX, int posY){
	//Fonction qui en fonction de x et y renvoie 1 si la couleur sur la case x,y est considéré comme de l'aire
	//le truc des couleur va etre dans la construction du monde pour l'initialisation de map[][] (pour ne pas recalculer a chaque fois)
		if (posX >=0 && posX <width && posY>=0 && posY< height) return map[posX][posY];
		return -1;
  		
	}
	
	public void setMapTypeAtPos(int posX, int posY, int TYPE_CST){
	//cette fonction sert plus bas
		map[posX][posY] = TYPE_CST;
	}
	
	public void setMapPixelColor(int posX, int posY, Color c){
	//cette fonction servira pour modifier un pixel - ex replaceGroundWithBackground
		mapImage.setRGB(posX,posY,c.getRGB());
	}
	
	public void replaceGroundWithBackground(int posX, int posY){
	//cette fonction servira pour la destruction de terrains
	//il faudra select un AIR random ? pas sur que ca marche encore
		setMapPixelColor(posX,posY,AIR_LIST.get((int)(Math.random()*(AIR_LIST.size()+1))));
	}
	
	public Color getColor(int posX, int posY, BufferedImage image){
	//retourne la couleur de la map en posX, posY
		int color;					
		int red;
		int green;
		int blue;
		color =  image.getRGB(posX,posY);
		red   = (color & 0x00ff0000) >> 16;
		green = (color & 0x0000ff00) >> 8;
		blue  =  color & 0x000000ff;
		return new Color(red,green,blue);
	}
	
	
	public void addObjectToWorld(int posX, int posY, BufferedImage image){
	//pas sur encore
		for(int i = posX;i<posX+image.getWidth();i++){
			for(int j = posY;j<posY+image.getHeight();j++){
			
				setMapTypeAtPos(i,j,GROUND_CST);
				setMapPixelColor(i,j,getColor(i-posX,j-posY,image));
			}
		}
	}
	
	public void draw(Graphics2D g){
	//Dessine l'image avec l'image .png choisi au debut
		g.drawImage(mapImage,0,0,null);
	}





}