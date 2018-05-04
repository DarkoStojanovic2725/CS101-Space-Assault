package Objekti;


import Mapa.*;


import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Igrac extends ObjektiMape {
	
	// osobine igraca
	private int health;
	private int maxHealth;
	private int shot;
	private int maxShot;
	private boolean dead;
	private boolean fl;
	private long flt;
	
	// pucanje
	private boolean puca;
	private int shotCost;
	private int shotDamage;
	private ArrayList<Shot> shots;
	
	// lowdmg
	private boolean lowDamage; //scratching;
	private int lowDamageDMG; //scratchDamage;
	private int lowDamageRange; //scratchRange;
	
	// letenje
	private boolean  letenje;
	
	// animacije
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		2, 8, 1, 2, 4, 2, 5
	};
	
	// animacije
	private static final int IDLE = 0;
	private static final int HOD = 1;
	private static final int SKOK = 2;
	private static final int PAD = 3;
	private static final int LETENJE = 4;
	private static final int METAK = 5;
	private static final int LOWDAMAGE = 6;
	
	public Igrac(MapaIgre tm) {
		
		super(tm);
		
		width = 30;
		height = 30;
		kwidth = 20;
		kheight = 20;
		
		brzinaKretanja = 0.3;
		maxBrzina =  2.0;
		brzinaZaustavljanja = 0.4;
		brzinaPada = 0.15;
		maxBrzinaPada = 4.0;
		skokMax = -4.8;
		brzinaPrizemljenja = 0.3;
		
		okretDesno = true;
		
		health = maxHealth = 5;
		shot = maxShot = 2500;
		
		shotCost = 200;
		shotDamage = 5;
		shots = new ArrayList<Shot>();
		
		lowDamageDMG = 8;
		lowDamageRange = 40;
		
		// load 

		try {
			
			BufferedImage igrac = ImageIO.read(
				getClass().getResourceAsStream(
					"/igrac/kenimod1.png"
				)
			);
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 7; i++) {
				
				BufferedImage[] bi =
					new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
					
					if(i != 6) {
						bi[j] = igrac.getSubimage(
								j * width,
								i * height,
								width,
								height
						);
					}
					else {
						bi[j] = igrac.getSubimage(
								j * width * 2,
								i * height,
								width * 2,
								height
						);
					}
					
				}
				
				sprites.add(bi);
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animacija = new Animacije();
		trenutnaAnimacija = IDLE;
		animacija.setFrejm(sprites.get(IDLE));
		animacija.setKasnjenje(400);
		
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public int getShot() { return shot; }
	public int getMaxShot() { return maxShot; }
	
	public void setFiring() { 
		puca = true;
	}
	public void setLowDamage() {
		lowDamage = true;
	}
	public void setLetenje(boolean b) { 
		letenje = b;
	}
	
	public void checkAttack(ArrayList<Neprijatelji> neprijatelji){
		
		
		//loop kroz neprijatelje
		
		
		for(int i = 0; i < neprijatelji.size(); i++){
			
			Neprijatelji n = neprijatelji.get(i);
			//lowdamage attack
			if(lowDamage){
				if(okretDesno){
					if(n.getX() > x && n.getX() < x + lowDamageRange && 
							n.getY() > y - height / 2 && 
							n.getY() < y + height / 2 )
					{
						n.pogodak(lowDamageDMG);
					}
				}
				else {
					if(n.getX() < x && n.getX() > x - lowDamageRange 
							&& n.getY() > y - height /2 
							&& n.getY() < y + height / 2){
						n.pogodak(lowDamageDMG);
					}	
				}
				
			}
		//shots
			for(int j = 0; j < shots.size(); j++){
				if(shots.get(j).intersects(n)){
					n.pogodak(shotDamage);
					shots.get(j).setPogodak();
					break;
					
					
				}
			}
			
			//neprijatelj moze da ubije igraca
			
			if(intersects(n)){
				hit(n.getDamage());
				
			}
			
		}
	}
	
	public void hit(int damage){  //za napad neprijatelja na igraca
		if(fl)return;
		health -= damage;
		if (health < 0) health = 0;
		if(health == 0) dead = true;
		if(health == 0) {
			JOptionPane.showMessageDialog(null, "Pokusajte ponovo");
			health = maxHealth = 5;

		
		}
		fl = true;
		flt = System.nanoTime();
		
	}
	
	
	private void getSlPoz() {
		
		// movement
		
		
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
		else {
			if(dx > 0) {
				dx -= brzinaZaustavljanja;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += brzinaZaustavljanja;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
	
		
		// nema kretanja dok napada
		if(
		(trenutnaAnimacija == LOWDAMAGE || trenutnaAnimacija == METAK) &&
		!(skok || pad)) {
			dx = 0;
		}
		
		// skok
		if(skok && !pad) {
			dy = skokMax;
			pad = true;	
		}
		
		// pad
		if(pad) {
			
			if(dy > 0 && letenje) dy += brzinaPada * 0.1;
			else dy += brzinaPada;
			
			if(dy > 0) skok = false;
			if(dy < 0 && !skok) dy += brzinaPrizemljenja;
			
			if(dy > maxBrzinaPada) dy = maxBrzinaPada;
			
		}
		
	}
	
	public void update() {
		//System.out.println(xdestinacija); 
		
		
		// update position
		getSlPoz();
		checkCollision();
		setPozicija(xTem, yTem);
		
		
		//ako se napad zavrsio
		
		if(trenutnaAnimacija == LOWDAMAGE){
			if(animacija.biloPusteno()) lowDamage = false;
		}
		if(trenutnaAnimacija == METAK){
			if(animacija.biloPusteno()) puca = false;
			
		}
		
		//crtanje shot attack
		shot +=1;
		if(shot > maxShot){
			shot = maxShot;
			
		}
		if(puca && trenutnaAnimacija != METAK){
			if(shot > shotCost){
				shot -= shotCost;
				Shot sh = new Shot(TileMapa, okretDesno);
				sh.setPozicija(x, y);
				shots.add(sh);
				
			}
		}
		//shot update
		for(int i = 0; i < shots.size(); i++){
			shots.get(i).update();
			if(shots.get(i).trebaUkloniti()){
				shots.remove(i);
				i--;
			}
			
		}
		//provera kada neprijatelj ne napada vise
		
		if(fl){
			
			long elapsed = (System.nanoTime() - flt) / 1000000;
			if(elapsed > 1000){
				fl = false;
			}
		}
		
		
		
		
		// podesi animaciju
		if(lowDamage) {
			if(trenutnaAnimacija != LOWDAMAGE) {
				trenutnaAnimacija = LOWDAMAGE;
				animacija.setFrejm(sprites.get(LOWDAMAGE));
				animacija.setKasnjenje(50);
				width = 60;
			}
		}
		else if(puca) {
			if(trenutnaAnimacija != METAK) {
				trenutnaAnimacija = METAK;
				animacija.setFrejm(sprites.get(METAK));
				animacija.setKasnjenje(100);
				width = 30;
			}
		}
		else if(dy > 0) {
			if(letenje) {
				if(trenutnaAnimacija != LETENJE) {
					trenutnaAnimacija = LETENJE;
					animacija.setFrejm(sprites.get(LETENJE));
					animacija.setKasnjenje(100);
					width = 30;
				}
			}
			else if(trenutnaAnimacija != PAD) {
				trenutnaAnimacija = PAD;
				animacija.setFrejm(sprites.get(PAD));
				animacija.setKasnjenje(100);
				width = 30;
			}
		}
		else if(dy < 0) {
			if(trenutnaAnimacija != SKOK) {
				trenutnaAnimacija = SKOK;
				animacija.setFrejm(sprites.get(SKOK));
				animacija.setKasnjenje(-1);
				width = 30;
			}
		}
		else if(levo || desno) {
			if(trenutnaAnimacija != HOD) {
				trenutnaAnimacija = HOD;
				animacija.setFrejm(sprites.get(HOD));
				animacija.setKasnjenje(40);
				width = 30;
			}
		}
		else {
			if(trenutnaAnimacija != IDLE) {
				trenutnaAnimacija = IDLE;
				animacija.setFrejm(sprites.get(IDLE));
				animacija.setKasnjenje(400);
				width = 30;
			}
		}
		
		animacija.update();
		
		// set direction
		if(trenutnaAnimacija != LOWDAMAGE && trenutnaAnimacija != METAK) {
			if(desno) okretDesno = true;
			if(levo) okretDesno = false;
		}
		
	}
	
	public void draw(Graphics2D grafika) {
		
		setMapPosition();
		
		//crtanje shot napada
		for(int i = 0; i < shots.size(); i++){
			
			shots.get(i).draw(grafika);
		}
		
		
		// crtanje igraca
		if(fl) {
			long elapsed =
				(System.nanoTime() - flt) / 1000000;
			if(elapsed / 100 % 2 == 0) {
				return;
			}
		}
		
		super.draw(grafika);
		
		
	}
	
}

