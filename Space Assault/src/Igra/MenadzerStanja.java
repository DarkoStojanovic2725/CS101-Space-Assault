package Igra;
import java.util.ArrayList;

public class MenadzerStanja {
	private ArrayList<Stanje> gameStates;
	private int trenutnoStanje;
	
	public static final int STANJEMENIJA = 0;
	public static final int LEVEL1STATE = 1;
	public MenadzerStanja(){
		gameStates = new ArrayList<Stanje>();
		
		trenutnoStanje = STANJEMENIJA;
		gameStates.add(new StanjeMenija(this));
		gameStates.add(new Level1State(this));
		
	}
	public void setState(int state){
		trenutnoStanje = state;
		gameStates.get(trenutnoStanje).init();
	}
	
	public void update(){
		gameStates.get(trenutnoStanje).update();
	}
	
	public void draw(java.awt.Graphics2D grafika){
		gameStates.get(trenutnoStanje).draw(grafika);
		
	}
	public void keyPressed(int s){
		gameStates.get(trenutnoStanje).keyPressed(s);
	}
	
	public void keyReleased(int s){
		gameStates.get(trenutnoStanje).keyReleased(s);
	}
	
}
