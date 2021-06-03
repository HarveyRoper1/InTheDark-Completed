import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;
import java.awt.geom.QuadCurve2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

public class Player{

	private int width=32, height=32;
	
	private float x;
	private float y;
	private float velX;
	private float velY;
	
	private float _acc = 1f;
	private float _dcc = 0.5f;
	private int ticks = 0;
	
	private float rawVel = 6.5f;

	private MazeGenerator mg;
	private MouseInput minput;
	private KeyInput input;
	private Camera camera;
	private EnemyHandler eh;
	private MouseClickInput m_clickinput;
	private Raycaster rc;
	private LevelCreator lc;
	
	private PlayerAnimation playerAnimation;
	private Animation testAnim;
	
	private Rectangle north;
	private Rectangle east;
	private Rectangle south;
	private Rectangle west;
	
	private int xCent;
	private int yCent;
	
	private float health = 1f;
	
	private Clock clock = new Clock();
	
	private int mouseX,mouseY;
	
	private String hatchDialogue = "Press Space to enter hatch";
	private boolean isShowingDialogue = false;
	private boolean isMoving = false;
	private boolean intersectingExit = false;
	private boolean shooting = false;
	
	public LinkedList<PlayerBreadCrumb> crumbs = new LinkedList<>();
	private int crumbCooldown=40;
	public int crumbsAvailable;
	
	private Line2D.Float laser;
	

	Texture tex = Main.getInstance();
	
	public Player(float x, float y, KeyInput input, MazeGenerator mg, MouseInput minput, MouseClickInput m_clickinput, Camera camera, EnemyHandler eh, Raycaster rc, LevelCreator lc) {
		this.x = x;
		this.y = y;
		this.input = input;
		this.mg = mg;
		this.minput = minput;
		this.camera = camera;
		this.eh = eh;
		this.m_clickinput = m_clickinput;
		this.rc = rc;
		this.lc = lc;
	
		north = new Rectangle((int)x,(int)y, width, 2);
		east = new Rectangle((int)x+width,(int)y,2,height);
		south = new Rectangle((int)x,(int)y+height,width,2);
		west = new Rectangle((int)x,(int)y,2,height);

		
		playerAnimation = new PlayerAnimation(60, this, tex.player[0], tex.player[1], tex.player[2]);
		testAnim = new Animation(60, tex.player[0], tex.player[1]);
	 
		crumbsAvailable = 50;
		
		laser = new Line2D.Float();
	}
	
	
	public void tick() {
		
		if(health <= 0) {
			
			Main.Gamestate = GameState.DeathScreen;
			lc.newGame();
			
		}
		else if(health < 100) health+=0.001f;
		

		
		
		xCent = (int) (x + (width/2));
		yCent = (int) (y + (height/2));
		
		x += velX;
		y += velY;
		
		mouseX = (int)x+minput.mx-400;
		mouseY = (int)y+minput.my-400;
		
		if(x <= 0) x = 0;
		if(y <= 0) y = 0;
		if(x >= 6000-width) x = 6000-width;
		if(y >= 6000-height) y = 6000-height;
	
		
		keyHandler();
		
		crumbCooldown--;
		if(crumbCooldown < 0) crumbCooldown = 0;
		
		velX = clamp(velX, rawVel, -rawVel);
		velY = clamp(velY, rawVel, -rawVel);
		
		
		Collisions();
		
		//System.out.println("Row: " + getCurrentCell(mg.fetchCellList()).getRow() + "Col: " + getCurrentCell(mg.fetchCellList()).getCol());
		//System.out.println("Cell: " + getCurrentCell(mg.fetchCellList()));
		
		if(velX > 0 || velY > 0) {
			isMoving = true;
		}
		else {
			isMoving = false;
		}
		
		playerAnimation.runAnimation();
		//testAnim.runAnimation();
		
		if(crumbs.size() > 20) {
			crumbs.removeFirst();
		}
		try {
		for(PlayerBreadCrumb c : crumbs){
			//System.out.println(getDistTo((int)x,(int)y, c.getX(), c.getY()));
			if(getDistTo(xCent,yCent, c.getX(), c.getY()) < 60) {
				if(input.keys[4]) {
					crumbs.remove(c);
					crumbsAvailable++;
				}
			}
		}
		}catch(ConcurrentModificationException e){
			e.printStackTrace();
		}
		
		if(m_clickinput.mousePressed) shooting = true;
		
	
		
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//g.setColor(Color.black);
		//g.fillOval(xCent, yCent, 8, 8);
		//testAnim.drawAnimation(g, (int)x, (int)y);
		//g.drawImage(tex.player[0], (int)x, (int)y, null);
		try{playerAnimation.drawAnimation(g, (int)x, (int)y, 50, 50); }
		catch(NullPointerException e) {	g.drawImage(tex.player[0], (int)x, (int)y, 50, 50, null); }
		
		for(PlayerBreadCrumb c : crumbs){
			if(getDistTo(xCent,yCent, c.getX(), c.getY()) < 60) {
				g.setFont(new Font("Monospaced", Font.PLAIN, 12));
				g.setColor(Color.gray);
				g.drawString("Press 'space' to pickup breadcrumb", c.getX()+5, c.getY());
			}
		}

		
		for(PlayerBreadCrumb c : crumbs) {
			c.render(g);
		}
		
		/*
		for(Enemy e : eh.getEnemies()) {
			g.setColor(Color.red);
			g2d.setStroke(new BasicStroke(3));
			g.drawLine((int)x, (int)y, e.getX(), e.getY());
		}
		*/
	   
		//if(getDistTo((int)x,(int)y,getClosestEnemy(eh).getX(),getClosestEnemy(eh).getY()) < 500) {
		//	g.setColor(Color.red);
		//	g2d.setStroke(new BasicStroke(5));
	//		QuadCurve2D curve = 
		//}
		
	}
	
	 public static BufferedImage rotate(BufferedImage img, int rotation) {
	int w = img.getWidth();  
	int h = img.getHeight();  
	BufferedImage newImage = new BufferedImage(32, 32, img.getType());
	    Graphics2D g2 = newImage.createGraphics();
	    g2.rotate(Math.toRadians(rotation), w/2, h/2);  
	    g2.drawImage(img,null,0,0);
	return newImage;  
	}
	 
	 public Enemy getClosestEnemy(EnemyHandler eh) {
		 int dist = Integer.MAX_VALUE;
		 Enemy chosen = null;
		 for(Enemy e : eh.getEnemies()) {
			 if(getDistTo((int)x,(int)y,e.getX(),e.getY()) < dist) {
				 chosen = e;
				 dist = getDistTo((int)x,(int)y, e.getX(), e.getY());
			 }
		 }
		 return chosen;
	 }
	 
	 public float mousePlayerAngle() {
		int a = getDistTo(mouseX, mouseY, (int)getX(), (int)getY()-10);
		int b = getDistTo(mouseX, mouseY, (int)getX(), (int)getY());
		int c = getDistTo((int)getX(), (int)getY(), (int)getX(), (int)getY()-10);
		float twobc = (2 * b * c);
		float a2b2c2 = ((b*b) + (c*c) - (a*a));
		float div = (a2b2c2/twobc);
		float angle = (float) Math.toDegrees(Math.acos(div));
		return angle;
	 }
	 
	 public float getAngle(int x1, int x2, int x3, int y1, int y2, int y3) {	//xy1 = mouse, xy2 = enemy, xy3 = player
		 
		 int a = getDistTo(x1, y1, x2, y2);
		 int b = getDistTo(x1,y1, x3, y3);
		 int c = getDistTo(x3, y3, x2, y2);
		 float twobc = (2 * b * c);
		 float a2b2c2 = ((b*b) + (c*c) - (a*a));
		 float div = (a2b2c2/twobc);
		 float angle = (float) Math.toDegrees(Math.acos(div));
		 
		 return angle;
		 
	 }
	 
	public float mouseExitAngle() {
		int a = getDistTo(mouseX, mouseY, mg.getExit().getX(), mg.getExit().getY()); 
		int b = getDistTo(mouseX, mouseY, (int)x, (int)y);
		int c = getDistTo((int)x,(int)y, mg.getExit().getX(),mg.getExit().getY());
		float twobc = (2 * b * c);
		float a2b2c2 = ((b*b) + (c*c) - (a*a));
		float div = (a2b2c2/twobc);
		float angle = (float) Math.toDegrees(Math.acos(div));
		return angle;
	}
	
	private void shoot(int mx, int my) {
       
 
    }
	
	
	public void keyHandler() {
		if(input.keys[0]) velX = rawVel;
        else if(input.keys[1]) velX  = -rawVel;
        else if(!input.keys[0] && !input.keys[1]) {
            if(velX > 0 ) velX = 0 ;
            else if(velX < 0) velX = 0;
        }
		
		
		if(input.keys[2]) velY = -rawVel;
		else if(input.keys[3]) velY = rawVel;
		else if(!input.keys[2] && !input.keys[3]) {
			velY = 0;
		}
		
		if(input.keys[5] && crumbCooldown == 0 && crumbsAvailable >= 1) {
			crumbs.add(new PlayerBreadCrumb((int)x,(int)y));
			crumbCooldown=40;
			crumbsAvailable--;
		}
	}
	
	public Cell getCurrentCell(LinkedList<Cell> cellsList) {
		Cell current = null;

		for(Cell c : cellsList) {
			if(xCent >= c.getX() && xCent <= c.getX()+400 && yCent >= c.getY() && yCent <= c.getY()+400) {
				current = c;
				}
			}
		
		return current;
	}

	public void Collisions() {
		Cell cell = getCurrentCell(mg.fetchCellList());
		//System.out.println(cell.getRow() +  " " + cell.getCol());
		if(cell != null) {
			if(cell.isNorthWallActive()) {
				if(getBoundsTop().intersects(cell.getNorthColl())){ // NN
					y = (cell.getY()+3);
					velY=0;
				}
			}
		}
		if(cell != null) {
			if(cell.isSouthWallActive()) {
				if(getBoundsBottom().intersects(cell.getSouthColl())) { //SS
					y = cell.getY()+(400-height);
					velY = 0;
				}
	
			}
		}
		if(cell != null) {
			if(cell.isEastWallActive()) {
				if(getBoundsRight().intersects(cell.getEastColl())) { //EE
					x = cell.getX()+(400-width);
					velX = 0;
				}
				
			}
		}
		
		if(cell != null) {
			if(cell.isWestWallActive()) {
				if(getBoundsLeft().intersects(cell.getWestColl())) { // WW
					x = cell.getX()+3;
					velX = 0;
				}
				
			}
		}
		if(getBounds().intersects(mg.getExit().getBounds())) {
			intersectingExit = true;
			if(input.keys[4]) {
				intersectingExit = false;
				lc.createLevel();
			}
		
		}
		if(!getBounds().intersects(mg.getExit().getBounds())) {
			intersectingExit=false;
		}
		
	}
	public boolean isIntersectingExit() {
		return intersectingExit;
	}

	public void setIntersectingExit(boolean intersectingExit) {
		this.intersectingExit = intersectingExit;
	}

	private float clamp(float value, float max, float min) {
		
		if(value >= max) value = max;
		else if(value <= min) value = min;
	
		return value;
	}
	
	
	
	private double b2c2a2(int a, int b, int c) {
		double sum = (b*b + c*c)-(a*a);
		return sum/(2*b*c);
	}
	private double getAngleA(int x1, int y1, int x2, int y2) {
		return Math.atan2(y2-y1,x2-x1);
	}
	
	
	public int getDistTo(int x, int y, int x2, int y2) {
		return (int)Math.sqrt((x2-x)*(x2-x) + (y2-y)*(y2-y));
	}
	

	private float relativeToAbsoluteY() {
		return minput.my - (getY()+16+camera.getY());
	}

	private float relativeToAbsoluteX() {
		return minput.mx - (getX()+16+camera.getX());
	}
	
	public Rectangle getBoundsTop() {
	    return new Rectangle((int)x+width/2-((width/2)/2),(int)y, width/2, height/2);
	    }
	
	public Rectangle getBoundsBottom() {
	    return new Rectangle((int)x+width/2-((width/2)/2),(int)y+(height/2), width/2, height/2);
	    }
	
	public Rectangle getBoundsLeft() {
	    return new Rectangle((int)x,(int)y, 5, height-2);
	    }
	
	public Rectangle getBoundsRight() {
	    return new Rectangle((int)x+width-5,(int)y, 5, height-2);
	    }
	
	public Rectangle getBounds() {//Horizontal Collision
		
		float bx = x + velX;
		float by = y + 2;
		float bw = 32 + velX/3;
		float bh = 28;
		
		return new Rectangle((int)bx,(int)by,(int)bw,(int)bh);
	}
	
	public Rectangle getBounds2() {//Vertical Collision
		
		float bx = x + 2;
		float by = y + velY;
		float bw = 28;
		float bh = 32 + velY/2;
		
		return new Rectangle((int)bx,(int)by,(int)bw,(int)bh);
	}
	

	
	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
	public int getxCent() {
		return xCent;
	}

	public void setxCent(int xCent) {
		this.xCent = xCent;
	}

	public int getyCent() {
		return yCent;
	}

	public void setyCent(int yCent) {
		this.yCent = yCent;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public MazeGenerator getMg() {
		return mg;
	}

	public void setMg(MazeGenerator mg) {
		this.mg = mg;
	}


	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
	
	public int getCrumbCount() {
		return crumbsAvailable;
	}

	public LinkedList<PlayerBreadCrumb> getCrumbList() {
		return crumbs;
	}

	public EnemyHandler getEh() {
		return eh;
	}

	public void setEh(EnemyHandler eh) {
		this.eh = eh;
	}
	
	public LevelCreator getLc() {
		return lc;
	}

	public void setLc(LevelCreator lc) {
		this.lc = lc;
	}

	public Raycaster getRc() {
		return rc;
	}

	public void setRc(Raycaster rc) {
		this.rc = rc;
	}

	
	
}
