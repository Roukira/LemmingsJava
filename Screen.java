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
	
	protected Canvas canvas;
	protected BufferStrategy bs;
	protected Input input;
	
	public static double FPS = 60.0;
	public static double ns = 1000000000/FPS;
	
	public Screen(Window gw){
		this.gw = gw;
		canvas = new Canvas();
		//canvas.createBufferStrategy(3);				//fenetre de dessin des pixels
		bs = canvas.getBufferStrategy();				//assigne a bs la fenetre de dessin
		/*canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);*/
	}
	
	public abstract void draw(Graphics2D g);
	
	public Input getInput(){
		return input;
	}
	
	public Canvas getCanvas(){
		return canvas;
	}
	
	public BufferStrategy getBufferStrategy(){
		return bs;
	}
	
	public void createBufferStrategy(){
		canvas.createBufferStrategy(3);
		bs = canvas.getBufferStrategy();
	}


}
