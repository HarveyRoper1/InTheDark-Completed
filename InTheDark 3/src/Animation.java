import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {
//
	private int speed;
	private int frames;
	
	private int index = 0;
	private int count = 0;
	
	private BufferedImage[] images;
	private BufferedImage currentImg;
	
	public Animation(int speed, BufferedImage... args) { //Constructor takes an undefined number of Buffered images which are the frames of the animation
		this.speed = speed;
		images = new BufferedImage[args.length];
		for(int i = 0 ; i < args.length; i++) {
			images[i] = args[i];				//This compiles all of the buffered images into an array
		}
		frames = args.length;
	}
	
	public void runAnimation() {
		index++;
		
		if(index > speed) {
			index = 0;		//Speed dictates the rate of frames changing, and index will increase every tick
			nextFrame();
		}
		
	}
	
	public void nextFrame(){		
		   if(count < frames){
		      currentImg = images[count];		//This determines whether or not to change the image being displayed
		      count++;
		   }
		   else {
		      count = 0;
		   }	
		}
	
	public void drawAnimation(Graphics g, int x, int y) {
		g.drawImage(currentImg, x, y, null);		//Helper method
	}
	
	public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY) {
		g.drawImage(currentImg, x, y, scaleX, scaleY, null);	//Helper method with scaling
	}
	
	
	
	
	
}
