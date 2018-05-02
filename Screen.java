import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public abstract class Screen implements Renderable{

	protected Window gw;
	
	protected BufferedImage screenImage;
	protected Graphics2D screenGraphics;
	protected Input input;
	
	protected int width;
	protected int height;
	
	protected int fillColor = Color.BLACK.getRGB();
	
	public static double FPS = 60.0;
	public static double ns = 1000000000/FPS;
	
	public Screen(Window gw, int width, int height){ /*, int canvasWidth, int canvasHeight*/
		this.gw = gw;
		this.width = width;
		this.height = height;
		screenImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		screenGraphics = screenImage.createGraphics();
	}
	
	public Input getInput(){
		return input;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public abstract void render();
	
	public void draw(Graphics2D g){
		//refreshImage();
		render();
		JFrame frame = gw.getFrame();
		g.drawImage(screenImage,0,0,frame.getContentPane().getWidth(),frame.getContentPane().getHeight(),0,0,width,height,null);
	}
}
