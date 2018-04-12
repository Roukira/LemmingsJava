import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public abstract class Screen{

	protected GameWindow gw;
	protected boolean onScreen = false;
	
	public Screen(GameWindow gw){
		this.gw = gw;
	}
	
	public void draw(Graphics g){
		if(!onScreen) return;
	}
	
	
	public boolean getOnScreen(){
		return onScreen;
	}
	
	public void setOnScreen(boolean onScreen){
		this.onScreen = onScreen;
	}


}
