import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.Random;

public class Enemy {
	
	Player player;
	private int x;
	private int y;
	
	private float velX=2f;
	private float velY=2f;
	
	private int width=50,height=50;
	
	private LinkedList<Line2D.Float> edges = new LinkedList<>();
	
	private Line2D.Float top;
	private Line2D.Float bottom;
	private Line2D.Float left;
	private Line2D.Float right;
	
	private Random ran = new Random();
	
	private int alpha;
	
	private Color colour;
	
	private EnemyHandler eh;
	private MazeGenerator mg;
	private Cell homeCell;
	private Clock clock = new Clock();
	
	private int red=0;
	private int green=10;
	private int blue=10;
	
	private int explosionCount=50;
	
	private boolean isFleeing=false;
	private boolean hiding=false;
	private boolean exploded=false;
	
	public Enemy(Player player, EnemyHandler eh, MazeGenerator mg) {
		this.player = player;
		this.eh = eh;
		this.mg = mg;
		
		 int random = ran.nextInt(mg.fetchCellList().size()-1);
	        LinkedList<Cell> potentialCells = mg.fetchCellList();
	        
	        for(int i = 0 ; i < mg.fetchCellList().size() ; i++) {
		        if(!potentialCells.get(random).isClaimedByEnemy()) {
		        	
			        homeCell = mg.fetchCellList().get(random);
			        homeCell.setClaimedByEnemy(true);
		        	break;
		        }else {
		        	potentialCells.remove(random);
		        }
		        		
	        }
	        
	        x = homeCell.getX()+200;
	        y = homeCell.getY()+200;
	        
		
	}
	
	/*
	public void construct() {
		
        
        int random = ran.nextInt(mg.fetchCellList().size()-1);
        LinkedList<Cell> potentialCells = mg.fetchCellList();
        
        for(int i = 0 ; i < mg.fetchCellList().size() ; i++) {
	        if(!potentialCells.get(random).isClaimedByEnemy()) {
	        	
		        homeCell = mg.fetchCellList().get(random);
		        homeCell.setClaimedByEnemy(true);
	        	break;
	        }else {
	        	potentialCells.remove(random);
	        }
	        		
        }
        
        x = homeCell.getX()+200;
        y = homeCell.getY()+200;
        
      
	}
	*/
	
//

	public void tick() {
		x+=velX;
		y+=velY;
		
		if(red > 255) red = 254;
		if(blue > 255) blue = 254;
		if(green > 255) green = 254;
		
		if(red < 10) red = 10;
		//if(blue < 10) blue = 10;
		//if(green < 10) green = 10;
		
		colour = new Color(red, 0, 0, alpha);
		
		//Tracking Player
		if(!isFleeing) {
			
			double angle = Math.atan2((double)player.getY() - y,(double)player.getX() - x);
			int vel = 2;
			
			if(distToPlayer() < 1500) {
				velX = (float) ((vel) *  Math.cos(angle));
				velY = (float) ((vel) *  Math.sin(angle));
			}else {
				velX = 0; // Add Random movement at some point
				velY = 0;
			}
		}
		
		if(isFleeing) {
			
			double angle = Math.atan2(((double)homeCell.getY()+200)-y, ((double)homeCell.getX()+200)-x);
			int vel = 2;
			velX = (float) ((vel) * Math.cos(angle));
			velY = (float) ((vel) * Math.sin(angle));
			if(distToHomeCentre() < 50) {
				if(angleToMouse() < 24 || angleToMouse() > 360-24) {
					hiding=true;
				}
				else {
					hiding = false;
				}
			}
			else if(distToHomeCentre() < 290) isFleeing = false;
			//Work out the cardinal direction of the enemy relative to player then move to the nearest cell in that cardinal direction.
		}
		
		if(hiding) {
			width = 10; //add functionality
			height = 10;
			velX = 0;
			velY = 0;
		}else {
			width = 50;
			height = 50;
		}
		
		//
		
		//Alpha Distance
		if(distToPlayer() >= (400)) {
			
			alpha = 0;
		}
		else {
			alpha = 255-(distToPlayer()/2);
			if(alpha < 0) alpha = 0;
		}
		//
		
		//Explode
		 if(distToPlayer() <= 100) explodeCheck();
		 else coolDown();
		 
		 if(angleToMouse() < 24 || angleToMouse() > 360-24) {
			 if(distToPlayer() < 400) {
				 isFleeing = true;
			 }
			 
		 }
	}
	
	public void render(Graphics g) {
		if(player.getAngle(player.getMouseX(), x, player.getxCent(), player.getMouseY(), y, player.getyCent()) < 24 || player.getAngle(player.getMouseX(), x, player.getxCent(), player.getMouseY(), y, player.getyCent()) > 360 - 24) {
			g.setColor(colour);
			g.fillRect(x, y, width, height);
		}
		//g.setColor(colour);
		//g.fillRect(x, y, width, height);
		
	}
	
	public void explodeCheck() {

		explosionCount+=6;
		if(red <= 255) red = explosionCount-50;
		if(explosionCount >= 255) {
			//Explode animation here
			if(distToPlayer() <= 100) player.setHealth(player.getHealth()-40);
			clock.waitSeconds(1);
			for(int i = 0 ; i < ran.nextInt(4); i++) {
				player.getCrumbList().add(new PlayerBreadCrumb(x+ran.nextInt(20)-10, y+ran.nextInt(20)-10));
			}
			AudioPlayer.getSound("Explode").play();
			exploded = true;
			eh.createEnemy();
		}
	}
	
	public void coolDown() {
		if(explosionCount > 0 ) explosionCount--;
		if(red > 0) red-=2;
	}
	
	
	public LinkedList<Line2D.Float> returnEdges(){
		return edges;
	}
	
	public int distToHomeCentre() {
		return (int) Math.sqrt((homeCell.getX()-x)*(homeCell.getX()-x)+(homeCell.getY()-y)*(homeCell.getY()-y));
	}
	
	public int distToEnemy(Enemy e) {
		return (int) Math.sqrt((e.getX() - x)*(e.getX() - x) + (e.getY() - y)*(e.getY() - y));
	}

	public int distToPlayer() {
		return (int) Math.sqrt((player.getX() - x)*(player.getX() - x) + (player.getY() - y)*(player.getY()-y));
	}
	
	public float angleToMouse() {
		int a = getSideA(player.getMouseX(), player.getMouseY(), x, y);
		int b = getSideB(player.getMouseX(), player.getMouseY(), (int)player.getX(), (int)player.getY());
		int c = getSideC((int)player.getX(), (int)player.getY(), x, y);
		float twobc = (2*b*c);
		float a2b2c2 = (b*b)+(c*c)-(a*a);
		float div = (a2b2c2/twobc);
		float angle = (float) Math.toDegrees(Math.acos(div));
		return angle;
	}
	
	private int getSideA(int mx, int my, int ex, int ey) {
		return (int) Math.sqrt((ex-mx)*(ex-mx) + (ey-my)*(ey-my));
	}
	private int getSideB(int mx, int my, int px, int py) {
		return (int) Math.sqrt((px-mx)*(px-mx) + (py-my)*(py-my));
	}
	private int getSideC(int px, int py, int ex, int ey) {
		return (int) Math.sqrt((ex-px)*(ex-px) + (ey-py)*(ey-py));
	}
	
	
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public boolean isExploded() {
		return exploded;
	}

	public void setExploded(boolean exploded) {
		this.exploded = exploded;
	}

	
}
