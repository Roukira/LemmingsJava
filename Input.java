import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public abstract class Input implements MouseListener,MouseMotionListener,Updatable, Renderable{
	
	protected Window w;
	protected int posXmouse = 0;
	protected int posYmouse = 0;
	protected double mouseRangeX = 2.0;
	protected double mouseRangeY = 1.5;
	
	protected Cursor cursor;
	
	public Input(Window w){
		this.w = w;
		cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	}
	
	public Cursor getCursor(){
		return cursor;
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics2D g); 
	
	public abstract void mouseClicked(MouseEvent e); 
	
	public boolean resetMapPressed(World world, int posXclic, int posYclic){
		if (posXclic >=world.getWidth()-40 && posXclic <=world.getWidth()-10 && posYclic>=world.getHeight()+60 && posYclic<=world.getHeight()+90){
			w.resetMap();
			return true;
		}
		return false;
	}
	
	public abstract void updateButtons();
	
	public void mouseMoved(MouseEvent e) {
        //a chaque mouvement retourne un event
        	posXmouse = e.getX();
        	posYmouse = e.getY();
        	updateButtons();
   	}
	
	public void mousePressed(MouseEvent e) {
	//Invoked when a mouse button has been pressed on a component.
	}


	public void mouseReleased(MouseEvent e) {
	//Invoked when a mouse button has been released on a component.
	}
	
	public void mouseEntered(MouseEvent e) {
	//Invoked when the mouse enters a component.
	}
	
	public void mouseExited(MouseEvent e) {
	//Invoked when the mouse exits a component.
	}

    	public void mouseDragged(MouseEvent e) {}
	
}
