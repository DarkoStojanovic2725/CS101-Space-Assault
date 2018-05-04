package Objekti;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Mapa.MapaIgre;

public class Shot extends ObjektiMape{

	private boolean pogodak;
	private boolean ukloni;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	
	public Shot(MapaIgre tm, boolean desno){
		super(tm);
		
		okretDesno = desno;
		
		brzinaKretanja = 3.8;
		if(desno) dx = brzinaKretanja;
		else dx = -brzinaKretanja;
		
		width = 30;
		height = 30;
		kwidth = 14;
		kheight = 14;
		
		
		//load crteza
		
		try{
			
			BufferedImage igrac = ImageIO.read(getClass().getResourceAsStream("/ostalo/fire.png"));
			sprites = new BufferedImage[4];
			for(int i =0; i < sprites.length; i++){
				
				sprites[i] = igrac.getSubimage(i * width,0, width, height);
			}
			hitSprites = new BufferedImage[3];
			for(int i = 0; i < hitSprites.length; i++){
				hitSprites[i] = igrac.getSubimage(i * width, height, width, height);
				
			}
			animacija = new Animacije();
			animacija.setFrejm(sprites);
			animacija.setKasnjenje(70);
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setPogodak(){
		if(pogodak) return;
		pogodak = true;
		animacija.setFrejm(hitSprites);
		animacija.setKasnjenje(70);
		dx = 0;
		
	}
	public boolean trebaUkloniti(){return ukloni;}
	
	public void update(){
		checkCollision();
		setPozicija(xTem, yTem);
		
		if(dx == 0 && !pogodak){
			setPogodak();
		}
		
		animacija.update();
		if(pogodak && animacija.biloPusteno()){
			ukloni = true;
		}
		
	}
	public void draw(Graphics2D grafika){
		
		setMapPosition();
		
		super.draw(grafika);
		
		
	}
}
