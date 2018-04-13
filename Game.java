import java.util.ArrayList;
/*
==================== MAIN =================
*/
public class Game{
	
	public static void main(String[] args){
		
		Window UI = new Window("Lemmings v0",600,400);	//Creation de la fenetre Interface Utilisateur
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
