package Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Mapa.MapaIgre;
import Objekti.Animacije;
import Objekti.Neprijatelji;

public class Alien extends Neprijatelji{

	private BufferedImage[] en;
	
	public Alien(MapaIgre tm){
		super(tm);
		
		brzinaKretanja = 0.5;
		maxBrzina = 0.5;
		brzinaPada = 0.3;
		maxBrzinaPada = 10.0;
		
		width = 30;
		height = 30;
		kwidth = 20;
		kheight = 20;
		health = maxHealth = 2; 
		damage = 1; 
		
		//dodavanje slike neprijatelja
		
		try {
			BufferedImage igrac = ImageIO.read(getClass().getResourceAsStream("/ostalo/enemy1.png"));
			
			en = new BufferedImage[3];
			for (int i = 0; i < en.length; i++){
				en[i] = igrac.getSubimage(i * width, 0, width, height);
				
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		
		animacija = new Animacije();
		animacija.setFrejm(en);
		animacija.setKasnjenje(300);
		
		desno = true; //kada se spawnuje krece se desno
		okretDesno = true;
		
		
	}
	private void getSlPoz(){
		
		if(levo) {
			dx -= brzinaKretanja;
			if(dx < -maxBrzina) {
				dx = -maxBrzina;
			}
		}
		else if(desno) {
			dx += brzinaKretanja;
			if(dx > maxBrzina) {
				dx = maxBrzina;
			}
		}
		if(pad) {
			dy += brzinaPada;
			
			
			
		}
		
		
	}
	public void update(){
		//update pozicije
		getSlPoz();
		checkCollision();
		setPozicija(xTem, yTem);
		
		//provera napada
		
		if (fl){
			
			long elapsed = (System.nanoTime() - flt) / 1000000;
			if(elapsed > 400){
				fl = false;
				
			}
			
		}
		//odbijanje od zid
		
		if (desno && dx == 0){
			desno = false;
			levo = true;
			okretDesno = false;
			
		}
		else if (levo && dx == 0){
			desno = true;
			levo = false;
			okretDesno = true;
		}
		//update animacije
		animacija.update();
		
		
	}
	public void draw(Graphics2D grafika){
		setMapPosition();
		
		
		super.draw(grafika);
		
		
	}
	
	
}
