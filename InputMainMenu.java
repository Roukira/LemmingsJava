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
        	if (posXmouse >= 100 && posXmouse <=220 && posYmouse>=100 && posYmouse <=150){
        		m.showSelectButton(1);
        	}
        	else{
            		m.showDefaultButton(1);
        	}
			if (posXmouse >= 100 && posXmouse <=220 && posYmouse>=160 && posYmouse <=210){
            		m.showSelectButton(2);
        	}
        	else{
            		m.showDefaultButton(2);
        	}
        	if (posXmouse >= 100 && posXmouse <=220 && posYmouse>=220 && posYmouse <=270){
            		m.showSelectButton(3);
       		} 
       		else{
            		m.showDefaultButton(3);
        	}
        	if (posXmouse >= 100 && posXmouse <=220 && posYmouse>=280 && posYmouse <=330){
            		m.showSelectButton(4);
       		} 
       		else{
            		m.showDefaultButton(4);
        	}
        	if (posXmouse >= 300 && posXmouse <=420 && posYmouse>=100 && posYmouse <=150){
            		m.showSelectButton(5);
       		} 
       		else{
            		m.showDefaultButton(5);
        	}
        	if (posXmouse >= 300 && posXmouse <=420 && posYmouse>=160 && posYmouse <=210){
            		m.showSelectButton(6);
       		} 
       		else{
            		m.showDefaultButton(6);
        	}
        }

	public void worldSelection(){
			if (posXmouse >= 100 && posXmouse <=220 && posYmouse>=100 && posYmouse <=150) w.newCurrentWorld(1);
			else if (posXmouse >= 100 && posXmouse <=220 && posYmouse>=160 && posYmouse <=210) w.newCurrentWorld(2);
        	else if (posXmouse >= 100 && posXmouse <=220 && posYmouse>=220 && posYmouse <=270) w.newCurrentWorld(3);
            else if (posXmouse >= 100 && posXmouse <=220 && posYmouse>=280 && posYmouse <=330) w.newCurrentWorld(4);
            else if (posXmouse >= 300 && posXmouse <=420 && posYmouse>=100 && posYmouse <=150) w.newCurrentWorld(5);
  	    else if (posXmouse >= 300 && posXmouse <=420 && posYmouse>=160 && posYmouse <=210) w.newCurrentWorld(6);
  	}
  	
}
