package Objekti;

import Mapa.MapaIgre;

public class Neprijatelji extends ObjektiMape{

	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	protected boolean fl;
	protected long flt;
	
	public Neprijatelji(MapaIgre tm){
		super(tm);
		
	}
	
	
	public boolean isDead(){return dead;}
	public int getDamage(){return damage;}
	public void pogodak(int damage){
		if(dead || fl) return;
		health -= damage;
		if (health < 0) health = 0;
		if (health == 0) dead = true;
		fl = true;
		flt = System.nanoTime();
		
	}
	
	public void update(){}
	
}
