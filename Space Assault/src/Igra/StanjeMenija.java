package Igra;
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import Mapa.Pozadina;

public class StanjeMenija extends Stanje{
	
	private Pozadina meni;
	
	private int trenutniIzbor = 0;
	private String[] options = {
			"Igraj",
			"Informacije",
			"Izlaz"
	};
	
	private Color glBoja;
	private Font glFont;
	private Font font;
	
	public StanjeMenija(MenadzerStanja mng){
		this.mng = mng;
		
		try{
			
			meni = new Pozadina("/ostalo/bckgrmenu.jpg", 1);  //dodaje pozadinu iz res foldera
			meni.setVektor(0, 0);
			
			glBoja = new Color(128,0,0);
			glFont = new Font("Century Gothic", Font.PLAIN, 28);
			font = new Font("Arial",Font.PLAIN,12);
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
	}
	public void init(){}
	public void update(){
		meni.update();
	}
	public void draw(Graphics2D grafika){
		//prikazi pozadinu
		
		meni.draw(grafika);
		
		//dodaj naslov
		grafika.setColor(glBoja);
		grafika.setFont(glFont);
		grafika.drawString("Space Assault", 80, 70);
		
		//dodaj meni opcije
		
		grafika.setFont(font);
		for (int i = 0; i < options.length; i++){
			if (i == trenutniIzbor){
				grafika.setColor(Color.GREEN);
			}
			else {
				grafika.setColor(Color.RED);
			}
			grafika.drawString(options[i], 145, 140 + i * 15 );
		}
		
	}
	
	private void select(){
		if(trenutniIzbor == 0){
			//start
			mng.setState(MenadzerStanja.LEVEL1STATE);
			
		}
		if (trenutniIzbor == 1){
			//info
			JOptionPane.showMessageDialog(null, "Za kretalje levo i desno koristi strelice,"
					+ "\n Za pucanje E i R,"
					+ "\n Za skok koristi SPACE,"
					+ "\n Za letenje LSHIFT.");
		}
		if (trenutniIzbor == 2){
			//quit
			System.exit(0);
		}
	}
	
	public void keyPressed(int s){
		if (s == KeyEvent.VK_ENTER){
			select();
		}
		if(s == KeyEvent.VK_UP){
			trenutniIzbor--;
			if (trenutniIzbor == -1){
				trenutniIzbor = options.length - 1;
			}
		}
		if (s == KeyEvent.VK_DOWN){
			trenutniIzbor++;
			if (trenutniIzbor == options.length){
				trenutniIzbor = 0;
			}
		}
	}
	public void keyReleased(int s){
		
	}
	
	

}
