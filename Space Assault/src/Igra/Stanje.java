package Igra;

public abstract class Stanje {
	protected MenadzerStanja mng;
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D grafika);
	public abstract void keyPressed(int s);
	public abstract void keyReleased(int s);
}
