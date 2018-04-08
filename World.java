import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.awt.Color;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class World{
	//classe qui represente le monde actuel dans la fenetre principale
	
//==================== ATTRIBUTS ========================	
	
	private int[][] map;									//carte colore du monde a mettre a jour au tout debut
	private int height;									//hauteur de la carte
	private int width;									//largeur
	private int id;	
	private Lemmings[] list;									//identifiant
	private BufferedImage mapImage;							//Image en .png de la carte a charger
	private BufferedImage imageCapacity;
	private BufferedImage imageCapacityBorder;
	private BufferedImage imageCapacitySelectBorder;
	public static final ArrayList<Color> AIR_LIST = new ArrayList<Color>();			//liste des constantes d'air
	public static final int AIR_CST = 0;							//constantes pour mieux lire
	public static final int GROUND_CST = 1;
	public int airIndex;
	public static final int settingsLines = 13;
	private Spawner spawn;
	private Outside end;
	private int spawnX;
	private int spawnY;
	private int outsideX;
	private int outsideY;
	private int posXcapacity1;
	private int posXcapacity2;
	private int posXcapacity3;
	private int posXcapacity4;
	private int posYcapacity;
	private long iFinish = -1;
	private boolean finished = false;
	private boolean victory = false;
	public static final int WALKER = 0;
	public static final int STOPPER = 1;
	public static final int BOMBER = 2;
	public static final int BUILDER = 3;
	public static final int BASHER = 4;
	
//================== CONSTRUCTEURS ======================
	
	public World(int id){
		this.id = id;
		
		
		try{
			mapImage = ImageIO.read(new File("world/world"+id+".png")); //lit l'image de la carte et la stocke en fonction de l'identifiant
		}catch(Exception e){e.printStackTrace();}
		
		this.width = mapImage.getWidth();
		this.height = mapImage.getHeight();
		fillMap();
		initAirCst();
		setSettings();
		
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
	
	public void setSettings(){
		BufferedReader br = null;
		FileReader fr = null;
		int[] settings = new int[settingsLines];
		try{
			fr = new FileReader("world/world"+id+"settings.txt");
			br = new BufferedReader(fr);
			String currentLine;
			for (int i=0;i<settingsLines;i++){
				currentLine = br.readLine();
				settings[i] = Integer.parseInt(currentLine);
			}
			imageCapacityBorder = ImageIO.read(new File("world/capacityBorder.png"));
			imageCapacitySelectBorder = ImageIO.read(new File("world/capacitySelectBorder.png"));
			spawnX = settings[0];
			spawnY = settings[1];
			spawn = new Spawner(settings[4],spawnX,spawnY,settings[5]);
			outsideX = settings[2];
			outsideY = settings[3];
			loadLemmings(settings[6]);
			end = new Outside(settings[4],outsideX,outsideY,list,this);
			posYcapacity = settings[7]; 
			posXcapacity1 = settings[8];
			posXcapacity2 = settings[9];
			posXcapacity3 = settings[10];
			posXcapacity4 = settings[11];
			airIndex = settings[12];
			
			
		}catch (IOException e){e.printStackTrace();}
		finally{

			try{
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			}catch (IOException e2) {e2.printStackTrace();}
		}
	}
	
	public void loadLemmings(int nb){
		Lemmings.w = this;
		list = new Lemmings[nb];
		for (int i=0;i<nb;i++){
			list[i] = new Walker(i,spawnX,spawnY);
		}
		/*for (int j=0;j<nb;j++){
			if ((j%2)==1) {
				int id = list[j].id;
				Lemmings[] tab = new Lemmings[1];
				//end.removeLemmingFromList(id);
				list[j] = list[j].changeJob(STOPPER);
				tab[0] = list[j];
				//end.addLemmings(tab);
			}
		}*/
		
	}
	
	public void initAirCst(){
	//initialise les constantes d'air pour avoir plus de choix (background et tout)
		AIR_LIST.add(new Color(97,172,191));
		AIR_LIST.add(new Color(1,1,1));
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
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
	
	
	public boolean addObjectToWorld(int posX, int posY, BufferedImage image){
	//pas sur encore
		if (posX>=width || posX<0 || posY<0 || posY >=height || posX+image.getWidth()>=width || posY+image.getHeight()>=height) return false;
		for(int i = posX;i<posX+image.getWidth();i++){
			for(int j = posY;j<posY+image.getHeight();j++){
				if (getPos(i,j)==1) return false;
				setMapTypeAtPos(i,j,GROUND_CST);
				setMapPixelColor(i,j,getColor(i-posX,j-posY,image));
			}
		}
		return true;
	}
	
	public void draw(Graphics2D g){
	//Dessine l'image avec l'image .png choisi au debut
		g.drawImage(mapImage,0,0,null);
		drawLemmingsCapacity(g,"bomb",posXcapacity2,posYcapacity);
		drawLemmingsCapacity(g,"stopper",posXcapacity1,posYcapacity);
		drawLemmingsCapacity(g,"builder",posXcapacity3,posYcapacity);
		drawLemmingsCapacity(g,"basher",posXcapacity4,posYcapacity);
	}

	public int getSpawnX(){
		return spawnX;	
	}
	
	public int getSpawnY(){
		return spawnY;	
	}
	public int getOutsideX(){
		return outsideX;	
	}
	public int getOutsideY(){
		return outsideY;	
	}
	
	public int getPosXcapacity1(){
		return posXcapacity1;	
	}
	
	public int getPosXcapacity2(){
		return posXcapacity2;	
	}
	
	public int getPosXcapacity3(){
		return posXcapacity3;	
	}
	
	public int getPosXcapacity4(){
		return posXcapacity4;	
	}
	
	public int getPosYcapacity(){
		return posYcapacity;	
	}
	
	public BufferedImage getImageCapacityBorder(){
		return imageCapacityBorder;
	}
	
	public BufferedImage getImageCapacitySelectBorder(){
		return imageCapacitySelectBorder;
	}
	
	public void spawnLemmings(){
		spawn.addLemmings(list);
	}
	
	public Lemmings[] getLemmingsList(){
		return list;
	}

	public Spawner getSpawner(){
		return spawn;
	}
	
	public Outside getOutside(){
		return end;
	}
	
	public boolean getFinished(){
		return finished;
	}
	
	public boolean getVictory(){
		return victory;
	}
	
	public void setFinished(boolean finished, boolean victory){
		this.finished = finished;
		this.victory = victory;
		iFinish = System.currentTimeMillis(); 
	}
	
	public void drawLemmingsCapacity( Graphics2D g, String nomImage, int posX, int posY){
		try{
			//System.out.println("lemmings/"+nomImage+"Capacity.png");
			imageCapacity = ImageIO.read(new File("lemmings/"+nomImage+"Capacity.png"));
		}catch(Exception e){e.printStackTrace();}
		g.drawImage(imageCapacity,posX,posY,null);
		
	}
	
	public void changeJob(Lemmings l,int state){
		if(l instanceof Affecter){
			System.out.println("reset map");
			((Affecter)l).resetMap();
		}
		Lemmings newLemming = null;
		if(state == WALKER) newLemming = new Walker(l);
		else if(state == STOPPER) newLemming = new Stopper(l);
		else if(state == BUILDER) newLemming = new Builder(l);
		else if(state == BASHER) newLemming = new Basher(l);
		else{
			System.out.println("Erreur : job non crée.");
		}
		int index = -1;
		for(int i=0;i<list.length;i++){
			if (list[i].getId()==l.getId()){
				index = i;
				break;
			}
		}
		if((index == -1) || (newLemming==null)){
			System.out.println("Invalide");
			return;
		}
		list[index] = newLemming;
        	Lemmings[] tab = new Lemmings[1];
		tab[0] = list[index];
		spawn.removeLemmingFromList(l.getId());
		spawn.addLemmings(tab);
		end.removeLemmingFromList(l.getId());
		end.addLemmings(tab);

	}	
	
	public long getiFinish(){
		return iFinish;
	}
	
}	









