import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MainMenuScreen {
	
	public Rectangle playButton = new Rectangle(Main.WIDTH/2-100, 150, 200, 50);
	public Rectangle leaderboardButton = new Rectangle(Main.WIDTH/2-100, 250, 200, 50);
	public Rectangle quitButton = new Rectangle(Main.WIDTH/2-100, 350, 200, 50);
	
	private MouseInput minput;
	private Main main;
	
	private BufferedImage RNIBLogo;
	private BufferedImageLoader loader = new BufferedImageLoader();
	
	public MainMenuScreen(MouseInput minput, Main main) {
		this.minput = minput;
		this.main = main;
	}
	
	public void tick() {
		if(minput.mx >= 475 && minput.mx <= 800) {
			if(minput.my >= 600 && minput.my <= 800) {
				main.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			else {
				main.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
		else {
			main.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Font fnt = new Font("Monospaced", Font.PLAIN, 50);
		g.setColor(Color.black);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.setFont(fnt);
		g.setColor(Color.white);
		g.drawString("In The Dark", 250, 100);
		
		if(minput.mx >= Main.WIDTH/2-100 && (minput.mx <= Main.WIDTH/2+100)) {
			if(minput.my >= 150 && minput.my <= 200) {
				g.setColor(Color.gray);
				g.fillRect(playButton.x, playButton.y, playButton.width	, playButton.height);
			}
		}
		if(minput.mx >= Main.WIDTH/2-100 && (minput.mx <= Main.WIDTH/2+100)) {
			if(minput.my >= 250 && (minput.my <= 300)) {
				g.setColor(Color.gray);
				g.fillRect(leaderboardButton.x, leaderboardButton.y, leaderboardButton.width, leaderboardButton.height);
			}
		
		}
		
		if(minput.mx >= Main.WIDTH/2-100 && (minput.mx <= Main.WIDTH/2+100)) {
			if(minput.my >= 350 && (minput.my <= 400)) {
				g.setColor(Color.gray);
				g.fillRect(quitButton.x, quitButton.y, quitButton.width, quitButton.height);
			}
		}
		
		g.setColor(Color.white);
		Font fnt1 = new Font("Monospaced", Font.PLAIN, 30);
		g.setFont(fnt1);
		g.drawString("Play", playButton.x + 65, playButton.y+ 35);
		g2d.draw(playButton);
		g.drawString("Leaderboard", leaderboardButton.x + 3, leaderboardButton.y+ 35);
		g2d.draw(leaderboardButton);
		g.drawString("Quit", quitButton.x + 65, quitButton.y+ 35);
		g2d.draw(quitButton);
		
		try {
			g2d.drawImage(loader.loadImage("/RNIB.png"),475,600, null);
		} catch (IOException e) {
			
			
		}
		
		
		
		
	}
	
	public void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	

}
	

