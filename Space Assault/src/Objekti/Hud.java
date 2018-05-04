package Objekti;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Hud {
  
	private Igrac igrac;
	private BufferedImage slika;
	private Font font;
	
	public Hud(Igrac ig){
		igrac = ig;
		try {
		
			slika = ImageIO.read(getClass().getResourceAsStream("/ostalo/hud1.png"));
			font = new Font("Rockwell", Font.PLAIN, 12);
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D grafika){
		grafika.drawImage(slika, 0, 10, null);
		grafika.setFont(font);
		grafika.setColor(Color.ORANGE);
		grafika.drawString(igrac.getHealth() + "/" + igrac.getMaxHealth(), 30, 25);
		grafika.drawString(igrac.getShot() / 100 + "/" + igrac.getMaxShot() / 100, 30, 45);
		
	}
}
