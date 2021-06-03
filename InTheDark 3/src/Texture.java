import java.awt.image.BufferedImage;

public class Texture {

	SpriteSheet ps;
	
	private BufferedImage playerSheet = null;
	
	public BufferedImage[] player = new BufferedImage[16];
	
	public Texture() {
		
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			playerSheet = loader.loadImage("/PlayerTorch.png"); //Player spritesheet goes here
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		ps = new SpriteSheet(playerSheet);
		
		getTextures();
	}
	
	private void getTextures() {
		player[0] = ps.grabImage(1, 1, 32, 32); 
		player[1] = ps.grabImage(2, 1, 32, 32); 
		player[2] = ps.grabImage(3, 1, 32, 32); 
		

	}
	
}
//