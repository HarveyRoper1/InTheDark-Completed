import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PlayerAnimation {

	private int speed;
	private int frames;
	
	private int index = 0;
	private int count = 0;
	
	private BufferedImage[] images;
	private BufferedImage currentImg;

	private Player player;
	
	public PlayerAnimation(int speed, Player player, BufferedImage... args) {
		this.speed = speed;
		this.player = player;
		images = new BufferedImage[args.length];
		for(int i = 0 ; i < args.length; i++) {
			images[i] = args[i];
		}
		frames = args.length;
	}

//
	public void runAnimation() {
		index++;
		
		if(index > 10) {
			index = 0;
			nextFrame();
		}
		
	}
	
	public void nextFrame(){		
		   if(count < frames){
		      currentImg = images[count];
		      count++;
		   }
		   else {
		      count = 0;
		   }	
		}
	
	public void drawAnimation(Graphics g, int x, int y) {
		if(player.getMouseX() > (int)player.getX()) {
			System.out.println(currentImg);
			g.drawImage(player.rotate(currentImg, (int)player.mousePlayerAngle()), x, y, null);
		}
		else {
			System.out.println(currentImg);
			g.drawImage(player.rotate(currentImg, (int)player.mousePlayerAngle()), x, y, null);

		}
	}
	
	public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY) {
		if(player.getMouseX() > (int)player.getX()) {
			g.drawImage(player.rotate(currentImg, (int)player.mousePlayerAngle()), x, y, scaleX, scaleY, null);
		}
		else {
			g.drawImage(player.rotate(currentImg, -(int)player.mousePlayerAngle()), x, y, scaleX, scaleY, null);

		}
	}
	
}
