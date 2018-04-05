import java.awt.Graphics2D;

public abstract class Thing{

	protected int posX;
	protected int posY;
	
	public Thing(int posX, int posY){
		this.posX = posX;
		this.posY = posY;
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics2D g);
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
}
