import java.awt.Color;
import java.awt.Graphics;

public class PlayerBreadCrumb {
//
	private int x,y;
	
	public PlayerBreadCrumb(int x, int y) {
		this.x = x;
		this.y = y;
	}
		
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(x, y, 5, 5);
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
