import java.util.ArrayList;
import java.util.Scanner;
/*
==================== MAIN =================
*/
public class Game{
	
	public static void main(String[] args){
		
		Scanner keyboard = new Scanner(System.in);
		int worldCounter;
		
		do{
			System.out.println("Choose the world between 1 and 2 : ");
			worldCounter = keyboard.nextInt();
		}while(worldCounter<1 || worldCounter>2);
		World w = new World(worldCounter);		//Creaation d un monde avec la taille de l image
		//fin de la creation de tous les lemmings necessaire au jeu
		
		
		GameWindow UI = new GameWindow("Lemmings v0",w.getWidth(),w.getHeight());	//Creation de la fenetre Interface Utilisateur
		UI.setWorld(w);	
		w.spawnLemmings();	//Remplissage de la fenetre avec le world w
		long time = System.currentTimeMillis();
		while(true){
			UI.update();					//Update les mouvements
			UI.draw();					//dessines les nouveaux mouvements
			UI.iterateTps();				//Itere le compteur
			if(w.getFinished()){
				System.out.println("THE END");
				UI.drawVictory();
				UI.pause(2000);
				UI.dispose();
				break;
			}
			UI.waitForFrame(time);					//temps en milliseconde entre deux iterations	
			time = System.currentTimeMillis();
			
		}
	}

}
