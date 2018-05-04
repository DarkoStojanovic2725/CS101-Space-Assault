package Mapa;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;



import MainPackage.GamePanel;
public class MapaIgre {

   //pozicija
	private double x;
	private double y;
	
	//granice
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double smoothMove;
	
	//mapa
	private int[][] mapa;
	private int tileSize;
	private int brojRedova;
	private int brojKolona;
	private int width;
	private int height;
	
	//tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	//crtanje mape
	
	private int rowOffset; //koji red se crta
	private int colomnOffset;  //koja kolona se crta
    private int RowsToDraw;
    private int colomnsToDraw;
	
    public MapaIgre(int tileSize){
    	this.tileSize = tileSize;
    	RowsToDraw = GamePanel.HEIGHT / tileSize + 2;
    	colomnsToDraw = GamePanel.WIDTH / tileSize + 2;
    	smoothMove = 0.07;
    	
    	
    }
	
    public void loadTiles(String s){
    	try {
    		tileset = ImageIO.read(getClass().getResourceAsStream(s)
    				
    				);
    		numTilesAcross = tileset.getWidth() / tileSize;
    		tiles = new Tile[2][numTilesAcross];
    		
    		BufferedImage slikaMape;
    		for (int col = 0; col < numTilesAcross; col++){
    			slikaMape = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
    			
    			tiles[0][col] = new Tile(slikaMape, Tile.NORMAL);
    			slikaMape = tileset.getSubimage(col * tileSize,tileSize,tileSize,tileSize);
    			tiles[1][col] = new Tile(slikaMape,Tile.BLOCKED);
    			
    			
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
    
    public void loadMap(String s){
    	try{
    		InputStream ulaz = getClass().getResourceAsStream(s);
    		BufferedReader reader = new BufferedReader(new InputStreamReader(ulaz));
    		brojKolona = Integer.parseInt(reader.readLine());
    		brojRedova = Integer.parseInt(reader.readLine());
    		mapa = new int[brojRedova][brojKolona];
    		width = brojKolona * tileSize;
    		height = brojRedova * tileSize;
    		
    		xmin = GamePanel.WIDTH - width;
    		xmax = 0;
    		
    		ymin = GamePanel.HEIGHT - height;
    		ymax = 0;
    		
    		String delims = "\\s+";
    		for (int red = 0; red < brojRedova; red++){
    			String line = reader.readLine();
    			String[] tokens = line.split(delims);
    			for (int col = 0; col < brojKolona; col++){
    				mapa[red][col] = Integer.parseInt(tokens[col]);
    				
    			}
    		}
    		
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
    
    
    public int getTileSize() {return tileSize;}
    public int getx(){return (int)x;}
    public int gety(){return (int)y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    
    public int getBrKolona() { return brojKolona; }
    public int getBrRedova() { return brojRedova; }
    
    
    public int getTip(int red, int col){
    	int rc = mapa[red][col];
    	int r = rc / numTilesAcross;
    	int c = rc % numTilesAcross;
    	return tiles[r][c].getTip();
    	
    }
    
	public void setPosition(double x, double y){   //za smooth kretanje 
		this.x += (x - this.x) * smoothMove;
		this.y += (y - this.y) * smoothMove;
		
		fixBounds();
		colomnOffset = (int) - this.x /tileSize;
		rowOffset = (int) - this.y / tileSize;
		
	}
	
	private void fixBounds(){  //da se granice ne bi prelazile
		
		if (x < xmin) x = xmin;
		if (y < ymin) y = ymin;
		if (x > xmax) x = xmax;
		if (y > ymax) y = ymax;
	}
	
	public void draw(Graphics2D grafika){
		
		for (int red = rowOffset; red < rowOffset + RowsToDraw;red ++){
			if(red >= brojRedova) break;
			
			for(int col = colomnOffset; col < colomnOffset + colomnsToDraw; col++){
			
				
				if (col >= brojKolona) break;
				if (mapa[red][col] ==0) continue;
				
				int rc = mapa[red][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				grafika.drawImage(tiles[r][c].getImage(),(int)x + col * tileSize,(int)y + red * tileSize,null);
			}
			
		}
		
	}
	
	
	
	
}
