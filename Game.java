import java.util.ArrayList;
/*
==================== MAIN =================
*/
public class Game{
	
	public static void main(String[] args){
		World w = new World(1,600,400);		//Creaation d un monde avec la taille de l image
		//fin de la creation de tous les lemmings necessaire au jeu
		
		
		GameWindow UI = new GameWindow("Lemmings v0");	//Creation de la fenetre Interface Utilisateur
		UI.setWorld(w);	
		w.spawnLemmings();	//Remplissage de la fenetre avec le world w
		
		while(true){
			UI.update();					//Update les mouvements
			UI.draw();					//dessines les nouveaux mouvements
			UI.pause(19);					//temps en milliseconde entre deux iterations
			UI.iterateTps();				//Itere le compteur
			if(w.getFinished()){
				System.out.println("THE END");
				UI.drawVictory();
				UI.pause(2000);
				UI.dispose();
				break;
			}
		}
	}

}
