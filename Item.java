import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class Item{

	protected int posX;
	protected int posY;
	
	public Item(int posX, int posY){
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
