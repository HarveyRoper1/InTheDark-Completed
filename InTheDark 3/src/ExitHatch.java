import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class ExitHatch {
	
	MazeGenerator mg;
	
	private Cell homeCell;
	
	Random ran = new Random();
	
	private int x,y;
	private int width=75,height=75;
	
	private int alpha;
	
	private Color color = new Color(5,5,5,255);;
	
	private Player player;
	
	public ExitHatch(MazeGenerator mg, Player player) {
		this.mg = mg;
		this.player = player;
		int random = ran.nextInt(mg.fetchCellList().size()-1);
		
        while(homeCell == null) {
	        if(!mg.fetchCellList().get(random).isClaimedByEnemy()) {
	        	
		        	homeCell = mg.fetchCellList().get(random);
		        	homeCell.setClaimedByEnemy(true);
	        	
	        }
	        else random = ran.nextInt(mg.fetchCellList().size()-1);
        }
        
        x = homeCell.getX()+(homeCell.getWidth()/2) - (width/2);
        y = homeCell.getY()+(homeCell.getHeight()/2) - (height/2);
	}
	//
	public void tick() {
		//System.out.println(homeCell.getRow() + ", " + homeCell.getCol());
		
		
		
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

	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, 75, 75);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}
	
	public int distToPlayer() {
		return (int) Math.sqrt((player.getX() - x)*(player.getX() - x) + (player.getY() - y)*(player.getY()-y));
	}

}
