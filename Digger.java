import java.awt.Graphics2D;


public class Digger extends Lemmings{

	protected boolean affectMapBool = false;
	

//================== CONSTRUCTEURS ======================

	public Digger(int posX, int posY){
		super(posX,posY);
		
	}
	
	public Digger(Lemmings l){
		super(l);
		
	}
	

//=================== METHODES ==========================
	
	public void move(){}
	
	public void draw( Graphics2D g){
		super.draw(g);
	}

}
