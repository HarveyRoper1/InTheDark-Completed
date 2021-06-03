import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD {
	
	private String hatchDialogue = "Press Space to enter hatch";

	Player player;
	KeyInput input;
	
	private Color hatchDialogueColour = new Color(255,255,255,100);
	
	private int healthX=25;
	private int healthY=25;
	private float healthWidth;
	private int healthHeight=10;
	
	public HUD(Player player, KeyInput input) {
		this.player = player;
		healthWidth = player.getHealth()*(Main.WIDTH/800);
	}
	//
	public void tick() {
		healthWidth = player.getHealth()*(Main.WIDTH/800);
		
	}

	
	public void render(Graphics g) {
		g.setFont(new Font("Monospaced", Font.PLAIN, 36));
		g.setColor(new Color(20,20,20));
		g.fillRect(0, 0, Main.WIDTH, 100);
		g.setColor(Color.white);
		
		g.drawString("Level: " + LevelCreator.level, Main.WIDTH/2-60, 40);
		g.drawString("Crumbs: " + player.getCrumbCount(), healthX, healthY+50);
		
		if(player.isIntersectingExit()) {
			g.setColor(hatchDialogueColour);
			  g.setFont(new Font("Monospaced", Font.PLAIN, 36));
			  g.drawString(hatchDialogue, Main.WIDTH/7, Main.HEIGHT-100);
		  }
		
		g.setColor(Color.red);
		g.fillRect(healthX, healthY, (int)healthWidth*3, healthHeight);
		
	}
	
}
