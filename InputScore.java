import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class InputScore extends Input{

	private Score s;
	
	public InputScore(Window w, Score s){
		super(w);
		this.s = s; 
		/*s.getCanvas().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		s.getCanvas().addMouseListener(this);
		s.getCanvas().addMouseMotionListener(this);*/
	}
	
	public void update(){}
	
	public void draw(Graphics2D g){}
	
	//===================MOUSE EVENT========================================================
        
        public void updateButtons(){
        	changeMainMenueButton();
        }
        
        public void mouseMoved(MouseEvent e){
        	posXmouse = (int)(e.getX()*((1.0*s.getWidth())/(1.0*w.getFrame().getContentPane().getWidth())));
        	posYmouse = (int)(e.getY()*((1.0*s.getHeight())/(1.0*w.getFrame().getContentPane().getHeight())));
        	updateButtons();
        }
        
        public void mouseClicked(MouseEvent e) {
	//Invoked when the mouse has been clicked on a component.
		int posXclic = (int)(e.getX()*((1.0*s.getWidth())/(1.0*w.getFrame().getContentPane().getWidth())));
		int posYclic = (int)(e.getY()*((1.0*s.getHeight())/(1.0*w.getFrame().getContentPane().getHeight())));
		
		//System.out.println(""+posXclic+","+posYclic);
		
		if (posXclic >=560 && posXclic <=590 && posYclic>=360 && posYclic<=390) w.resetMap();
		else if(posXclic >= 450 && posXclic <=570 && posYclic>=300 && posYclic <=350){
			w.moveToMainMenu();
		}
		if (resetMapPressed(w.getCurrentWorld(), posXclic, posYclic)) return;
		
	}
	
	public void changeMainMenueButton(){
    		if(posXmouse >= 450 && posXmouse <=570 && posYmouse>=300 && posYmouse <=350){
        		s.showSelectButton();
        	}
        	else{
            		s.showDefaultButton();
        	}
        	if(posXmouse >= 560 && posXmouse <=590 && posYmouse>=360 && posYmouse <=390){
        		s.showResetMapSelectButton();
        	}
        	else{
            		s.showResetMapDefaultButton();
        	}
    	}

}
