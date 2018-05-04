package Igra;


import Mapa.*;
import javafx.scene.layout.Background;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Enemies.Alien;
import MainPackage.GamePanel;
import Objekti.*;
public class Level1State extends Stanje{
	
	
private MapaIgre mapa;
private Pozadina pozadina;

private Igrac igrac;

private ArrayList<Neprijatelji> neprijatelj;


private Hud hud;

  public Level1State(MenadzerStanja mng){
	  this.mng = mng;
	  init();
  }
  public void init() {
	  mapa = new MapaIgre(30);
	  mapa.loadTiles("/Mapa/mapa.png");
	  mapa.loadMap("/ostalo/level1-1.map");
	  mapa.setPosition(0, 0);
	  pozadina = new Pozadina("/ostalo/bckgrmain.jpg",0);
	  
	  igrac = new Igrac (mapa);
	  igrac.setPozicija(100,100);
	  
	  dodajNeprijatelje();
	  
	 // neprijatelj = new ArrayList<Neprijatelji>();
	 // Alien a;
	//  Alien b;
	//  Alien c;
	//  Alien d;
	//  Alien e;   //pojedinacno dadavanje
	 // Alien f;
	  
	//  f = new Alien(mapa);
	  //f.setPozicija(3062.60, 150.00);
	  //neprijatelj.add(f);
	  
	 // e = new Alien(mapa);
	  //e.setPozicija(2799.70, 150.00);
	  //neprijatelj.add(e);
	  
	 // d = new Alien(mapa);
	  //d.setPozicija(1654.50, 150.00);
	  //neprijatelj.add(d);
	  
	  //c = new Alien(mapa);
	  //c.setPozicija(1020.80, 150.00);
	  //neprijatelj.add(c);
	  
	  //b = new Alien(mapa);
	  //b.setPozicija(825.90, 150.00);
	  //neprijatelj.add(b);
	  
	 // a = new Alien(mapa);
	  //a.setPozicija(100, 100);
	  //neprijatelj.add(a);
	  
	 
	  
	  
	  hud = new Hud(igrac);
	  
  }
  
  private void dodajNeprijatelje(){
	  neprijatelj = new ArrayList<Neprijatelji>();
	  Alien a;
	  
	  Point[] point = new Point [] {     //sluzi kao lista neprijatelj,dodaje ih na mapu umesto pojedinacno
		  new Point(150,150),
		  new Point(826,150),
		  new Point(1021, 150),
		  new Point(1655, 150),
		  new Point(2800, 150),
		  new Point(3063, 150)
		  
		  
	  };
	  
	for (int i = 0; i < point.length; i++){
		a = new Alien(mapa);
		a.setPozicija(point[i].x,point[i].y);
		neprijatelj.add(a);
		
		
	}
	  
	  
	  
  }
  
  public void update () {
	  
	  igrac.update();
	  mapa.setPosition(GamePanel.WIDTH / 2 - igrac.getX(),GamePanel.HEIGHT / 2 - igrac.getY());
	  
	  pozadina.setPosition(mapa.getx(), mapa.gety());
	  
	  //napadanje neprijatelja
	  igrac.checkAttack(neprijatelj);
	  
	  
	  //update neprijatelja
	  for(int s = 0; s < neprijatelj.size(); s++){
		  Neprijatelji f = neprijatelj.get(s);
		  //neprijatelj.get(s).update();
		  f.update();
		  if(f.isDead()){
			  neprijatelj.remove(s);
			  s--;
			  
			  if( neprijatelj.size() == 0){
					JOptionPane.showMessageDialog(null, "Cestitamo, neprijatelji su pobedjeni!");
					System.exit(0);
				}
			  
			  
		     }
		  }
		 
	  
	  
	  }
  
  public void draw (Graphics2D grafika) {
	  //crtaj pozadinu
	  
	  pozadina.draw(grafika);
	 
	  //crtaj tilemap
	  mapa.draw(grafika);
	  
	  //crtaj igraca
	  igrac.draw(grafika);
	  
	  //crtaj neprijatelja
	  for (int i = 0; i < neprijatelj.size(); i++){
		  neprijatelj.get(i).draw(grafika);
		  	  
	  }
	  
	  
	  //crtaj hud
	  hud.draw(grafika);
	  
  }
  public void keyPressed(int s) {
	  if (s == KeyEvent.VK_LEFT) igrac.setLevo(true);
	  if (s == KeyEvent.VK_RIGHT) igrac.setDesno(true);
	  if (s == KeyEvent.VK_UP) igrac.setGore(true);
	  if (s == KeyEvent.VK_DOWN) igrac.setDole(true);
	  if (s == KeyEvent.VK_SPACE) igrac.setSkok(true);
	  if (s == KeyEvent.VK_SHIFT) igrac.setLetenje(true);
	  if (s == KeyEvent.VK_E) igrac.setLowDamage();
	  if (s == KeyEvent.VK_R) igrac.setFiring();
	  
  }
  public void keyReleased (int s) {
	  if (s == KeyEvent.VK_LEFT) igrac.setLevo(false);
	  if (s == KeyEvent.VK_RIGHT) igrac.setDesno(false);
	  if (s == KeyEvent.VK_UP) igrac.setGore(false);
	  if (s == KeyEvent.VK_DOWN) igrac.setDole(false);
	  if (s == KeyEvent.VK_SPACE) igrac.setSkok(false);
	  if (s == KeyEvent.VK_SHIFT) igrac.setLetenje(false);
  }
  
  
}
