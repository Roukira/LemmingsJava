import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Renderer{
	
	private Window w;
	private int width;
	private int height;
	
	public Renderer(Window w){
		this.w = w;
		width = w.getFrame().getWidth();
		height = w.getFrame().getHeight();
	}
	
	public void drawImage(Graphics2D g, BufferedImage image, int posX, int posY){
		int newWidth = w.getFrame().getWidth();
		int newHeight = w.getFrame().getHeight();
		BufferedImage imageResized;
		if(width != newHeight || height != newHeight){
			imageResized = resize(image,image.getWidth()*newWidth/width,image.getHeight()*newHeight/height);
			width = newWidth;
			height = newHeight;
		}
		else imageResized = image;
		g.drawImage(imageResized,posX,posY,null);
	}
	
	
	public BufferedImage resize(BufferedImage img, int newW, int newH){ 
    		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

    		Graphics2D g2d = dimg.createGraphics();
    		g2d.drawImage(tmp, 0, 0, null);
   		g2d.dispose();

  		return dimg;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
}
