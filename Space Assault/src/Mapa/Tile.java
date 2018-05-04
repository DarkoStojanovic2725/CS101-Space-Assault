package Mapa;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage image;
	private int tip;
	
	
	//tip tilesa
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public Tile(BufferedImage image,int tip){
		
		this.image = image;
		this.tip = tip;
		
	}
	
	public BufferedImage getImage() {return image;}
	public int getTip(){return tip;}
	
	
	
}
