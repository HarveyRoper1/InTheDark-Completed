import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class DeathScreen {

	public Rectangle menuButton = new Rectangle(Main.WIDTH/5-100, 600, 300, 50);
	private Rectangle submitScoreButton = new Rectangle(Main.WIDTH/2, 600, 300, 50);
	
	private LeaderboardScreen lbs;
	private MouseInput minput;
	
	private boolean newGame = false;
	
	public DeathScreen(LeaderboardScreen lbs, MouseInput minput) {
		this.lbs = lbs;
		this.minput = minput;
		newGame = true;
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.black);
    	g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
    	g.setFont(new Font("Monospaced", Font.PLAIN, 72));
    	g.setColor(Color.white);
    	g.drawString("Game Over.", Main.WIDTH/2-220, Main.HEIGHT/2-150);
    	g.setFont(new Font("Monospaced", Font.PLAIN, 56));
    	g.drawString("Score: " + lbs.currentPlayerScore, Main.WIDTH/2-220, Main.HEIGHT/2-50);
    	
    	g.setColor(Color.gray);
    	if(minput.mx >= Main.WIDTH/5-100 && minput.mx <= Main.WIDTH/5+200) {
			if(minput.my >= 600 && minput.my <= 650) {
				g2d.fill(menuButton);
				
			}
		}
		if(minput.mx >= Main.WIDTH/2 && minput.mx <= Main.WIDTH/2+300) {
			if(minput.my >= 600 && minput.my <= 650) {
				g2d.fill(submitScoreButton);
			}
		}
    	
		g.setColor(Color.white);
    	
    	g2d.draw(menuButton);
    	g2d.draw(submitScoreButton);
    	g.setFont(new Font("Monospaced", Font.PLAIN, 32));
    	g.drawString("Menu", Main.WIDTH/5+10, 635);
    	g.drawString("Submit Score", Main.WIDTH/2+40, 635);
		
	}
	
	
}
//