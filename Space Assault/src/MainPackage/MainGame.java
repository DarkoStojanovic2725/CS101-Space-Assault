package MainPackage;

import javax.swing.JFrame;

import MainPackage.GamePanel;

public class MainGame {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Space Assault");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		
	}
	
}