import java.awt.Color;
import java.awt.Graphics;

public class PlayerHealthBar {

	Player player;
	private int x=25;
	private int y=25;
	private float width;
	private int height=10;
	
	public PlayerHealthBar(Player player) {
		this.player = player;
		width = player.getHealth();
	}
	
	public void tick() {
		width = player.getHealth();
	}
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, (int)width*3, height);
	}
	
}
//