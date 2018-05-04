package MainPackage;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

import Igra.MenadzerStanja;



public class GamePanel extends JPanel 
	implements Runnable, KeyListener{
	
	// dimenzije
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int UVECANJE = 2;
	
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// slika
	private BufferedImage image;
	private Graphics2D g;
	
	// menadzer stanja
	private MenadzerStanja mng;
	
	public GamePanel() {
		super();
		setPreferredSize(
			new Dimension(WIDTH * UVECANJE, HEIGHT * UVECANJE));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();   //execution
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		
		image = new BufferedImage(
					WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB
				);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		mng = new MenadzerStanja();
		
	}
	
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// game loop
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void update() {
		mng.update();
	}
	private void draw() {
		mng.draw(g);
	}
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,
				WIDTH * UVECANJE, HEIGHT * UVECANJE,
				null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		mng.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key) {
		mng.keyReleased(key.getKeyCode());
	}

}

