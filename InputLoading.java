import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class InputLoading extends Input{

	private LoadingScreen l;

	public InputLoading(Window w, LoadingScreen l){
		super(w);
		this.l = l;
	}
	
	public void update(){}
	
	public void draw(Graphics2D g){}
	
	//===================MOUSE EVENT========================================================
        
        public void updateButtons(){
        }
        
        public void mouseMoved(MouseEvent e){
        	posXmouse = (int)(e.getX()*((1.0*l.getWidth())/(1.0*w.getFrame().getContentPane().getWidth())));
        	posYmouse = (int)(e.getY()*((1.0*l.getHeight())/(1.0*w.getFrame().getContentPane().getHeight())));
        	updateButtons();
        }
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.
		int posXclic = (int)(e.getX()*((1.0*l.getWidth())/(1.0*w.getFrame().getContentPane().getWidth())));
		int posYclic = (int)(e.getY()*((1.0*l.getHeight())/(1.0*w.getFrame().getContentPane().getHeight())));
	}

}
