package Objekti;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import MainPackage.GamePanel;
import Mapa.MapaIgre;
import Mapa.Tile;
public abstract class ObjektiMape {

	protected MapaIgre TileMapa;
	protected int tileSize;
	protected double xmapa;
	protected double ymapa;
	
	//pozicija
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	//dimenzije
	protected int width;
	protected int height;
	
	//kolizije
	protected int kwidth;
	protected int kheight;
	
	protected int trenutniRed;
	protected int trenutnaKolona;
	protected double xdestinacija;
	protected double ydestinacija;
	protected double xTem;
	protected double yTem;
	protected boolean vrhLevo;
	protected boolean vrhDesno;
	protected boolean doleLevo;
	protected boolean doleDesno;
	
	//animacije
	protected Animacije animacija;
	protected int trenutnaAnimacija;
	protected int proslaAnimacija;
	protected boolean okretDesno;
	
	//pokreti
	protected boolean levo;
	protected boolean desno;
	protected boolean gore;
	protected boolean dole;
	protected boolean skok;
	protected boolean pad;
	//physics
	
	protected double brzinaKretanja;
	protected double maxBrzina;
	protected double brzinaZaustavljanja;
	protected double brzinaPada;
	protected double maxBrzinaPada;
	protected double skokMax;  //max visina skoka
	protected double brzinaPrizemljenja; //nakon skoka
	
	
	public ObjektiMape(MapaIgre tm){
		TileMapa = tm;
		tileSize = tm.getTileSize();
		
	}
	
	public boolean intersects(ObjektiMape o){
		Rectangle rect1 = getRectangle();
		Rectangle rect2 = o.getRectangle();
	    return rect1.intersects(rect2);
	
		
	}
	public Rectangle getRectangle(){
		
		return new Rectangle((int)x - kwidth,(int)y - kheight,kwidth,kheight);
		
	}
	
	public void kalkulisiUgao(double x, double y){
		int tileLevo = (int)(x - kwidth/2) / tileSize;
		int tileDesno = (int)(x + kwidth/2 - 1) / tileSize;
		int tileGore = (int)(y - kheight/2) / tileSize;
		int tileDole = (int)(y + kheight/2 - 1) / tileSize;
		
		if(tileGore < 0 || tileDole >= TileMapa.getBrRedova() ||
                tileLevo < 0 || tileDesno >= TileMapa.getBrKolona()) {
                vrhLevo = vrhDesno = doleLevo = doleDesno = false;
                return;
        }
		
		int tl = TileMapa.getTip(tileGore, tileLevo);
		int td = TileMapa.getTip(tileGore, tileDesno);
		int dl = TileMapa.getTip(tileDole,tileLevo);
		int dd = TileMapa.getTip(tileDole, tileDesno);
		
		vrhLevo = tl == Tile.BLOCKED;
		vrhDesno = td == Tile.BLOCKED;
	    doleLevo = dl == Tile.BLOCKED;
	    doleDesno = dd == Tile.BLOCKED;
		
		
		
		
		
		
	}
	
	public void checkCollision(){
		
		trenutnaKolona = (int)x / tileSize;
		trenutniRed = (int)y / tileSize;
		xdestinacija = x + dx;
		ydestinacija = y + dy;
		xTem = x;
		yTem = y;
		
		
		kalkulisiUgao(x, ydestinacija);
		if (dy < 0){
			if (vrhLevo || vrhDesno){
				dy = 0;
				yTem = trenutniRed * tileSize + kheight / 2;
				
				
			}
			else{
				yTem += dy;
			}
			
		}
		if(dy > 0){
			
			if(doleLevo || doleDesno){
				dy = 0;
				pad = false;
				yTem = (trenutniRed + 1) * tileSize - kheight /2;
				
			}
			else{
				yTem += dy;
			}
		}
		kalkulisiUgao(xdestinacija, y);
		if (dx < 0){
			if (vrhLevo || doleLevo){
				dx = 0;
				xTem = trenutnaKolona * tileSize + kwidth / 2;
			}
			else{
				xTem +=dx;
				
				
			}
		}
		if (dx > 0){
			if(vrhDesno || doleDesno){
				dx = 0;
				xTem = (trenutnaKolona +1) * tileSize - kwidth / 2;
			}
			else {
				xTem += dx;
			}
			
		}
		
		if (!pad){
			kalkulisiUgao(x, ydestinacija + 1);
			if(!doleLevo && !doleDesno){
				pad = true;
				
				
			}
		}
		
	}
	
	public int getX() {return (int)x;}
	public int getY() {return (int)y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public int getKWidth(){return kwidth;}
	public int getKHeight(){return kheight;}
	
	public void setPozicija(double x, double y){
		this.x = x;
		this.y = y;
	}
	public void setVektor(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	public void setMapPosition(){
		xmapa = TileMapa.getx();
		ymapa = TileMapa.gety();
	}
	public void setLevo(boolean b){levo = b;}
	public void setDesno(boolean b){desno = b;}
	public void setGore(boolean b){gore = b;}
	public void setDole(boolean b){dole = b;}
	public void setSkok(boolean b){skok = b;}
	
	public boolean notOnScreen(){
		return x + xmapa + width < 0 || x + xmapa - width > GamePanel.WIDTH || 
			y + ymapa + height < 0 || y + ymapa - height > GamePanel.HEIGHT;
	}
	
	public void draw(Graphics2D grafika){
		
		if(okretDesno) {
			grafika.drawImage(
				animacija.getImage(),
				(int)(x + xmapa - width / 2),
				(int)(y + ymapa - height / 2),
				null
			);
		}
		else {
			grafika.drawImage(
				animacija.getImage(),
				(int)(x + xmapa - width / 2 + width),
				(int)(y + ymapa - height / 2),
				-width,
				height,
				null
			);
			
		}
	}
 
	
}
