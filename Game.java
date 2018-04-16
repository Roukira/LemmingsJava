import java.util.ArrayList;
/*
==================== MAIN =================
*/
public class Game{
	
	public static void main(String[] args){
		
		Window UI = new Window("Lemmings v0",600,400);	//Creation de la fenetre Interface Utilisateur
		long lastTime = System.nanoTime();
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(true){
			long now = System.nanoTime();
			delta += (now - lastTime)/UI.ns;
			lastTime = now;
			if(delta>=1){
				UI.update(); // 60 updates/second
				updates++;
				UI.iterateTps();
				delta--;
				UI.draw(); //fix 60FPS - mettre en dehors du if pour + de FPS
				frames++; //fix 60FPS - same
			}
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				updates = 0;
				frames = 0;
			}
		}
		//UI.dispose();
	}
}
