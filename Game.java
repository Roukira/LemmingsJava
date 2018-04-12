import java.util.ArrayList;
import java.util.Scanner;
/*
==================== MAIN =================
*/
public class Game{
	
	public static void main(String[] args){
		
		Scanner keyboard = new Scanner(System.in);
		GameWindow UI = new GameWindow("Lemmings v0",600,400);	//Creation de la fenetre Interface Utilisateur
		//UI.setWorld(w);	
		//w.spawnLemmings();	//Remplissage de la fenetre avec le world w
		long time = System.currentTimeMillis();
		while(true){
			UI.update();					//Update les mouvements
			UI.draw();					//dessines les nouveaux mouvements
			UI.iterateTps();				//Itere le compteur
			UI.waitForFrame(time);				//60FPS
			time = System.currentTimeMillis();
		}
		//UI.dispose();
	}

}
