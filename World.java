import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import java.awt.Color;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class World implements Renderable{
	//classe qui represente le monde actuel dans la fenetre principale
	
//==================== ATTRIBUTS ========================	
	
	private int[][] map;									//carte colore du monde a mettre a jour au tout debut
	private int height;									//hauteur de la carte
	private int width;									//largeur
	private int id;	
	private Lemmings[] list;									//identifiant
	private BufferedImage mapImage;							//Image en .png de la carte a charger
	
	public static final ArrayList<Color> AIR_LIST = new ArrayList<Color>();			//liste des constantes d'air
	public static final int AIR_CST = 0;							//constantes pour mieux lire
	public static final int GROUND_CST = 1;
	public static final int WALL_RIGHT_CST = 4;							//constantes pour mieux lire
	public static final int WALL_LEFT_CST = 2;
	public static final int STOPPER_WALL_RIGHT_CST = 5;							//constantes pour mieux lire
	public static final int STOPPER_WALL_LEFT_CST = 3;
	public int airIndex;
	public static final int settingsLines = 14;
	private Spawner spawn;
	private Outside end;
	private int spawnX;
	private int spawnY;
	private int outsideX;
	private int outsideY;
	
	private int stopperLimit;
	private int bomberLimit;
	private int builderLimit;
	private int basherLimit;
	private int minerLimit;
	private int excavaterLimit;
	
	private int minerDirection = 1;
	
	private boolean finished = false;
	private boolean started = false;
	private int victoryCondition;
	
	public static final int WALKER = 0;
	public static final int STOPPER = 5;
	public static final int BOMBER = 1;
	public static final int BUILDER = 4;
	public static final int BASHER = 2;
	public static final int MINER = 3;
	public static final int EXCAVATER = 6;
	
	Stats stats;
	
//================== CONSTRUCTEURS ======================
	
	public World(int id){
		this.id = id;
		loadWorld();
	}
	
//===================== METHODES =========================
	
	public void loadWorld(){
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
		stats = new Stats(this);
	}
	
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
			spawnX = settings[0];
			spawnY = settings[1];
			spawn = new Spawner(spawnX,spawnY,settings[4]);
			outsideX = settings[2];
			outsideY = settings[3];
			loadLemmings(settings[5]);
			end = new Outside(outsideX,outsideY,list,this);
			airIndex = settings[6];
			victoryCondition = settings[7];
			stopperLimit = settings[8];
			bomberLimit = settings[9];
			builderLimit = settings[10];
			basherLimit = settings[11];
			minerLimit = settings[12];
			excavaterLimit = settings[13];
			
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
			addLemmings(i,new Walker(spawnX,spawnY));
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
		AIR_LIST.add(new Color(0,0,0));
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getID(){
		return id;
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
	
	
	public boolean addObjectToWorld(int posX, int posY, int type_CST, BufferedImage image, int direction){
	//pas sur encore
		if (posX>=width || posX<0 || posY<0 || posY >=height || posX+image.getWidth()>=width || posY+image.getHeight()>=height) return false;
		if (direction == 1){
			for(int i = posX;i<posX+image.getWidth();i++){
				for(int j = posY;j<posY+image.getHeight();j++){
					if (getPos(i,j)==1) return false;
					else setMapTypeAtPos(i,j,type_CST);
					setMapPixelColor(i,j,getColor(i-posX,j-posY,image));
				}
			}
		}
		else{
			for(int i = posX+image.getWidth()-1;i>=posX;i--){
				for(int j = posY+image.getHeight()-1;j>=posY;j--){
					if (getPos(i,j)==1) return false;
					else setMapTypeAtPos(i,j,type_CST);
					setMapPixelColor(i,j,getColor(i-posX,j-posY,image));
				}
			}
		}
		return true;
		
	}
	
	public void draw(Graphics2D g){
	//Dessine l'image avec l'image .png choisi au debut
		g.drawImage(mapImage,0,0,null);
		
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
	
	public boolean getStarted(){
		return started;
	}
	
	public void startWorld(){
		started = true;
	}
	
	public void setMinerDirection(int directionY){
		minerDirection = directionY;
	}
	
	public int getVictoryCondition(){
		return victoryCondition;
	}
	public void setFinished(boolean finished){
		this.finished = finished;
	}
	
	public Stats getStats(){
		return stats;
	}
	
	public String getLemmingsJob(int state){
		if (state == BOMBER) return "Bomber";
		else if (state == BUILDER) return "Builder";
		else if (state == BASHER) return "Basher";
		else if (state == STOPPER) return "Stopper";
		else if (state == MINER) return "Miner";
		else if (state == EXCAVATER) return "Excavater";
		else return "Walker";
	}
	
	public int getLemmingsLimit(int state){
		if (state == BOMBER) return bomberLimit;
		else if (state == BUILDER) return builderLimit;
		else if (state == BASHER) return basherLimit;
		else if (state == STOPPER) return stopperLimit;
		else if (state == MINER) return minerLimit;
		else if (state == EXCAVATER) return excavaterLimit;
		else return -20;
	}
	
	public boolean canDestructPixel(int posX, int posY){
		if (!onBounds(posX,posY)) return false;
		if (getPos(posX,posY)==-1 || getPos(posX,posY)==3 || getPos(posX,posY)==5) return false;
		return true;
	}
	
	public boolean onBounds(int posX, int posY){
		if (posX<width && posX>=0 && posY<height && posY >=0) return true;
		return false;
	}
	
	//=========================PRIORITY==========================
	
	public void addLemmings(int i,Lemmings l){
		list[i] = l;
		sortLemmings();
	}
	
	public void sortLemmings(){
		if (list[list.length-1] == null) return;
		int index = 0;
		for (int i=0;i<list.length;i++){
			Lemmings l = list[i];
			if (list[index].getJob()>l.getJob()){
				reverseLemmings(i,index);
				System.out.println("reverse "+i+" et "+index);
				index = i;
			}
		}
	}
	
	public void reverseLemmings(int i, int j){
		Lemmings lTemp = list[j];
		list[j] = list[i];
		list[i] = lTemp;
	}
	
	public void replaceLemmings(Lemmings l, Lemmings l2){
		for (int i=0;i<list.length;i++){
			if(l.getId()==list[i].getId()){
				addLemmings(i,l2);
				return;
			}		
		}
	}
	
	public void printList(){
		for (int i=0;i<list.length;i++){
			if(list[i]!=null){
				System.out.println("i : "+i+" | "+list[i].toString());
			}
		}
	}
//======================= END PRIORITY =========================
	
	
	public void changeJob(Lemmings l,int state){
		Lemmings newLemming = null;
		if(state == WALKER){
			newLemming = new Walker(l);
			System.out.println("changeJob to WALKER");
		}
		
		else if(state == STOPPER){
			if(stopperLimit>0){
				stopperLimit--;
				newLemming = new Stopper(l);
				System.out.println("changeJob to STOPPER");
			}
			
		}
		else if(state == BOMBER){
			if(bomberLimit>0){
				bomberLimit--;
				l.startBomb();
			}
			return;
			
		}
		else if(state == BUILDER){
			if(builderLimit>0){
				builderLimit--;
				newLemming = new Builder(l);
				System.out.println("changeJob to BUILDER");
			}
		}
		else if(state == BASHER){ 
			if(basherLimit>0){
				basherLimit--;
				newLemming = new Basher(l);
				System.out.println("changeJob to BASHER");
			}
		}
		else if(state == MINER){ 
			if(minerLimit>0){
				minerLimit--;
				newLemming = new Miner(l,minerDirection);
				System.out.println("changeJob to MINER");
			}
		}
		else if(state == EXCAVATER){ 
			if(excavaterLimit>0){
				excavaterLimit--;
				newLemming = new Excavater(l);
				System.out.println("changeJob to EXCAVATER");
			}
		}
		else{
			System.out.println("Erreur : job non crée.");
		}
		if(l instanceof Affecter){
			System.out.println(l.toString()+" | reset la map");
			((Affecter)l).resetMap();
		}
		int index = -1;
		for(int i=0;i<list.length;i++){
			if (list[i].getId()==l.getId()){
				index = i;
				break;
			}
		}
		if((index == -1) || (newLemming==null)){
			System.out.println("Out of lemmings of this type");
			return;
		}
		replaceLemmings(list[index],newLemming);
        	Lemmings[] tab = new Lemmings[1];
		tab[0] = newLemming;
		spawn.removeLemmingFromList(l.getId());
		spawn.addLemmings(tab);
		end.removeLemmingFromList(l.getId());
		end.addLemmings(tab);
	}
	
}	









