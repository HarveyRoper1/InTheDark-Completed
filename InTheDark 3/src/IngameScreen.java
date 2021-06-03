import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class IngameScreen {

	private Camera camera;
	private Raycaster rc;
	private EnemyHandler eh;
	private MazeGenerator mazeGenerator;
	private Player player;
	private Clock clock;
	private HUD hud;
	private AudioLogic al;
	private LevelCreator lc;
	
	public IngameScreen(Camera camera, Raycaster rc, EnemyHandler eh, MazeGenerator mazeGenerator, Player player, Clock clock, HUD hud, AudioLogic al, LevelCreator lc) {
		this.camera = camera;
		this.rc = rc;
		this.clock = clock;
		this.eh = eh;
		this.mazeGenerator = mazeGenerator;
		this.player = player;
		this.hud = hud;
		this.al = al;
		this.lc = lc;
	}
	

	public void tick() {
		player.tick();
    	rc.tick();
    	camera.tick(player);
    	eh.tick();
    	al.tick();
    	clock.tick();
    	mazeGenerator.tick();
    	hud.tick();

    	if(clock.getSeconds() >= clock.getMaxTime()) {
    		Main.Gamestate = GameState.DeathScreen;
    		lc.newGame();
    	}
    	
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
        
        hud.render(g);
        clock.render(g);
	}
	

	public LevelCreator getLc() {
		return lc;
	}

	public void setLc(LevelCreator lc) {
		this.lc = lc;
	}
	
}
