import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PauseScreen {

	public Rectangle menuButton = new Rectangle(100, 600, 150, 50);
	public Rectangle leaderboardButton = new Rectangle(325, 600, 150, 50);
	public Rectangle resumeButton = new Rectangle(550, 600, 150, 50);
	
	private Camera camera;
	private Raycaster rc;
	private EnemyHandler eh;
	private MazeGenerator mazeGenerator;
	private Player player;
	private Clock clock;
	private HUD hud;
	private MouseInput minput;
	
	public PauseScreen(Camera camera, Raycaster rc, EnemyHandler eh, MazeGenerator mazeGenerator, Player player, Clock clock, HUD hud, MouseInput minput) {
		this.camera = camera;
		this.rc = rc;
		this.eh = eh;
		this.mazeGenerator = mazeGenerator;
		this.player = player;
		this.clock = clock;
		this.hud = hud;
		this.minput = minput;
	}
	//
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.black);
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
                
        g2d.translate(camera.getX(), camera.getY()); //Begin of camera
        
        	rc.render(g);
	        eh.render(g);
	        mazeGenerator.render(g);
	        player.render(g);
	        
        g2d.translate(-camera.getX(), -camera.getY()); //end of camera
        
        clock.render(g);
        hud.render(g);
        
        
        g.setColor(new Color(0,0,0,80));
        g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        
        g.setFont(new Font("Monospaced", Font.PLAIN, 72));
        g.setColor(Color.white);
        g.drawString("Paused", Main.WIDTH/2-120, Main.HEIGHT/2-50);
		
        if(minput.mx >= 100 && minput.mx <= 250) {
			if(minput.my >= 600 && minput.my <= 650) {
				g.setColor(Color.gray);
				g.fillRect(menuButton.x, menuButton.y, menuButton.width	, menuButton.height);
			}
		}
		if(minput.mx >= 325 && minput.mx <= 475) {
			if(minput.my >= 600 && (minput.my <= 650)) {
				g.setColor(Color.gray);
				g.fillRect(leaderboardButton.x, leaderboardButton.y, leaderboardButton.width, leaderboardButton.height);
			}
		
		}
		
		if(minput.mx >= 550 && minput.mx <= 700) {
			if(minput.my >= 600 && (minput.my <= 650)) {
				g.setColor(Color.gray);
				g.fillRect(resumeButton.x, resumeButton.y, resumeButton.width, resumeButton.height);
			}
		}
        
        g.setColor(Color.white);
        
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(resumeButton);
        g2d.draw(leaderboardButton);
        g2d.draw(menuButton);
        
        Font fnt0 = new Font("Monospaced", Font.PLAIN, 24);
        g.setFont(fnt0);
        g.drawString("Menu", menuButton.width/2 + menuButton.x-27, menuButton.height/2 + menuButton.y+8);;
        Font fnt1 = new Font("Monospaced", Font.PLAIN, 22);
        g.setFont(fnt1);
        g.drawString("Leaderboard", leaderboardButton.width/2 + leaderboardButton.x-72, menuButton.height/2 + menuButton.y+8);
        g.setFont(fnt0);
        g.drawString("Resume", resumeButton.width/2+resumeButton.x-40, resumeButton.height/2+resumeButton.y+8);
        
       
        
	}
	
}
