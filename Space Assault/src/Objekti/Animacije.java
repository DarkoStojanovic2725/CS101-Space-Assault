package Objekti;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Animacije {
  private BufferedImage[] frejm;
  private int currFrejm;
  
  private long startTime;
  private long kasnjenje;
  
  private boolean vecPusteno;
  
  public Animacije(){
	  vecPusteno = false;
	  
  }
  
  public void setFrejm(BufferedImage[] frejm){
	  this.frejm = frejm;
	  currFrejm = 0;
	  startTime = System.nanoTime();
	  vecPusteno = false;
	  
  }
  public void setKasnjenje(long ks){kasnjenje = ks;}
  public void setFrejm(int i){currFrejm = i;}
  
  public void update(){
	  if(kasnjenje == -1) return;
	  
	  long elapsed = (System.nanoTime() - startTime) / 1000000;
	  if (elapsed > kasnjenje){
		  currFrejm++;
		  startTime = System.nanoTime();
	  }
	  
	  if (currFrejm == frejm.length){
		  currFrejm = 0;
		  vecPusteno = true;
	  }
  }
  
  public int getFrejm(){return currFrejm;}
  public BufferedImage getImage(){return frejm[currFrejm];}
  public boolean biloPusteno(){return vecPusteno;}
  
}
