package Mapa;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

import MainPackage.GamePanel;

public class Pozadina {

	private BufferedImage slika;
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double pomeriUvecanje;
	
	public Pozadina (String h, double pu){
		try {
			slika = ImageIO.read(
					getClass().getResourceAsStream(h));
			pomeriUvecanje = pu;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public void setPosition(double x, double y){
		this.x = (x * pomeriUvecanje) % GamePanel.WIDTH;
		this.y = (y * pomeriUvecanje) % GamePanel.HEIGHT;
	}
	
	public void setVektor(double dx, double dy){    //za automatsko pomeranje pozadine
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update(){
		x +=dx;
		y +=dy;
	}
	
	public void draw(Graphics2D grafika){
		
		
		grafika.drawImage(slika, (int)x, (int)y, null);
		
		if (x < 0){
			grafika.drawImage(slika, (int)x + GamePanel.HEIGHT, (int)y, null);
			
		}
		if (x > 0){
			grafika.drawImage(slika, (int)x - GamePanel.WIDTH, (int)y, null);
			
		}
	}
	
}
