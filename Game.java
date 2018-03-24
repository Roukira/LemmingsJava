import java.util.ArrayList;
/*
==================== MAIN =================
*/
public class Game{
	
	public static void main(String[] args){
		Lemmings[] lemTab = new Lemmings[30]; 	//plus tard array list?
		World w = new World(1,600,400);		//Creaation d un monde avec la taille de l image
		for (int i = 0;i<lemTab.length;i++){
			int posX = w.getSpawnX();
			int posY = w.getSpawnY();
			System.out.println(posX);
			System.out.println(posY);
			Lemmings l = new Lemmings(i,posX,posY);
			lemTab[i] = l;
		}
		//fin de la creation de tous les lemmings necessaire au jeu
		
		
		GameWindow UI = new GameWindow("Lemmings v0",lemTab);	//Creation de la fenetre Interface Utilisateur
		UI.setWorld(w);	
		w.spawnLemmings(lemTab);	//Remplissage de la fenetre avec le world w
		
		while(true){
			UI.update();					//Update les mouvements
			UI.draw();					//dessines les nouveaux mouvements
			UI.pause(19);					//temps en milliseconde entre deux iterations
			UI.iterateTps();				//Itere le compteur
		}
	}

}
