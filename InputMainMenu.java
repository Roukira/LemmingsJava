import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class InputMainMenu extends Input{

	private MainMenu m;
	
	public InputMainMenu(Window w, MainMenu m){
		super(w);
		this.m = m; 
		/*m.getCanvas().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		m.getCanvas().addMouseListener(this);
		m.getCanvas().addMouseMotionListener(this);*/
	}
	
	public void update(){}
	
	public void draw(Graphics2D g){}
	
	//===================MOUSE EVENT========================================================
        
        public void updateButtons(){
        	changeWorldButton();
        }
        
        public void mouseMoved(MouseEvent e){
        	posXmouse = (int)(e.getX()*((1.0*m.getWidth())/(1.0*w.getFrame().getContentPane().getWidth())));
        	posYmouse = (int)(e.getY()*((1.0*m.getHeight())/(1.0*w.getFrame().getContentPane().getHeight())));
        	updateButtons();
        }
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.
		int posXclic = (int)(e.getX()*((1.0*m.getWidth())/(1.0*w.getFrame().getContentPane().getWidth())));
		int posYclic = (int)(e.getY()*((1.0*m.getHeight())/(1.0*w.getFrame().getContentPane().getHeight())));
		worldSelection();
	}
    	
	public void changeWorldButton(){
        	if(posXmouse >= 250 && posXmouse <=370 && posYmouse>=100 && posYmouse <=150){
        		m.showSelectButton(1);
        	}
        	else{
            		m.showDefaultButton(1);
        	}
		if (posXmouse >= 250 && posXmouse <=370 && posYmouse>=160 && posYmouse <=210){
            		m.showSelectButton(2);
        	}
        	else{
            		m.showDefaultButton(2);
        	}
        	if (posXmouse >= 250 && posXmouse <=370 && posYmouse>=220 && posYmouse <=270){
            		m.showSelectButton(3);
       		} 
       		else{
            		m.showDefaultButton(3);
        	}
        }

	public void worldSelection(){
       		if(posXmouse >= 250 && posXmouse <=370 && posYmouse>=100 && posYmouse <=150){
            		w.newCurrentWorld(1);
		}
		else if (posXmouse >= 250 && posXmouse <=370 && posYmouse>=160 && posYmouse <=210){
			w.newCurrentWorld(2);
		}
		else if (posXmouse >= 250 && posXmouse <=370 && posYmouse>=220 && posYmouse <=270){
			w.newCurrentWorld(3);
		}
        }

}
