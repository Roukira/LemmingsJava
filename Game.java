import java.util.ArrayList;
/*
==================== MAIN =================
*/
public class Game{
	
	public static void main(String[] args){
		
		Window UI = new Window("Lemmings v1.0",600,400);	//Creation de la fenetre Interface Utilisateur
		Screen currentScreen = UI.getCurrentScreen();
		long lastTime = System.nanoTime();
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(true){
			long now = System.nanoTime();
			delta += (now - lastTime)/currentScreen.ns;
			lastTime = now;
			if(delta>=1){
				UI.update(); // 60 updates/second
				updates++;
				UI.iterateTps();
				delta--;
				UI.draw(); //fix 60FPS - mettre en dehors du if pour + d'image par seconde sans modifier la vitesse de jeu
				frames++; //fix 60FPS - same
			}
			
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("ticks : "+updates+" | FPS : "+frames);
				updates = 0;
				frames = 0;
			}
		}
		//UI.dispose();
	}
}
