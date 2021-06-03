import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
 
public class Main extends Canvas implements Runnable, KeyListener, MouseListener{

	private static final long serialVersionUID = -2939737161012219840L;
	
	public static int WIDTH = 800;
    public static int HEIGHT = 800;
    public String title = "In The Dark - Harvey Roper A Level Coursework Project";
   
    private Thread thread;
   
    private boolean isRunning = false;
   
    //Instances

    private Player player;
    private KeyInput input;
    private Raycaster rc;
    private MouseInput minput;
    private MouseClickInput m_clickinput;
    private Camera camera;
    private Clock clock;
    private MazeGenerator mazeGenerator;
    private Camera cam;
    private EnemyHandler eh;
    private AudioLogic al;
    private LevelCreator levelCreator;
    private HUD hud;
    private static Texture tex;
    public static GameState Gamestate;
    
    //GameState classes
    private PauseScreen pauseScreen;
    private IngameScreen ingameScreen;
    private MainMenuScreen menuScreen;
    private LeaderboardScreen leaderboardScreen;
    private DeathScreen deathScreen;
    
    public Main() {
       
        init();
        new Window(WIDTH, HEIGHT, title, this);
        start();
        
        
    }
   
    public void init() {

    	AudioPlayer.load();
    	//AudioPlayer.getMusic("Ambience").loop();
    	

    	clock = new Clock();
    	minput = new MouseInput();
    	this.addMouseMotionListener(minput);	
    	m_clickinput = new MouseClickInput(leaderboardScreen, levelCreator, menuScreen);
    	this.addMouseListener(m_clickinput);
    	
     	input = new KeyInput();
    	this.addKeyListener(input);
    	tex = new Texture();
    	
    	player = new Player(200, 200, input, mazeGenerator, minput, m_clickinput, camera, eh, rc, levelCreator);
    	hud = new HUD(player, input);
    	mazeGenerator = new MazeGenerator(player);
    	//mazeGenerator.generateMaze();
    	player.setMg(mazeGenerator);
    	
    	camera = new Camera(player.getX(),player.getY());
    	player.setCamera(camera);
    	
    	eh = new EnemyHandler(player, mazeGenerator);
    	player.setEh(eh);
    	
    	al = new AudioLogic(player, eh);
    	
    	rc = new Raycaster(player, minput, camera, mazeGenerator, eh);
    	player.setRc(rc);
    	
    	pauseScreen = new PauseScreen(camera, rc, eh, mazeGenerator, player, clock, hud, minput);
    	ingameScreen = new IngameScreen(camera, rc, eh, mazeGenerator, player, clock, hud, al, levelCreator);
    	menuScreen = new MainMenuScreen(minput,this);
    	m_clickinput.setMs(menuScreen);
    	leaderboardScreen = new LeaderboardScreen(m_clickinput, minput);
    	deathScreen = new DeathScreen(leaderboardScreen,minput);
    	
    	m_clickinput.setLbs(leaderboardScreen);
    	
    	levelCreator = new LevelCreator(mazeGenerator, player, rc, eh, clock, leaderboardScreen);
    	m_clickinput.setLc(levelCreator);
    	player.setLc(levelCreator);
    	ingameScreen.setLc(levelCreator);
    	
    	levelCreator.createLevel();
    	Gamestate = GameState.MainMenu;
    	
    }
   
    private synchronized void start() {
    	
        if(isRunning) return;
       
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }
   
    private synchronized void stop() {
        if(!isRunning) return;
       
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        isRunning = false;
    }
   
    //gameloop
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;
                   
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }
 
    public void tick() {
    int ticks = 0;
    
    	if(Gamestate == GameState.MainMenu) {
    		menuScreen.tick();
    	}
    
    	if(Gamestate == GameState.InGame) {
    		ingameScreen.tick();
    	}

    	
    	if(input.keys[6] && Gamestate == GameState.InGame) {
    		input.keys[6] = false;
    		Gamestate = GameState.Paused;
    		}
    	
    	if(input.keys[6] && Gamestate == GameState.Paused) {
    		input.keys[6] = false;
    		Gamestate = GameState.InGame;
    	}
    	
    	    	
    	if(Gamestate == GameState.Paused) {

    	}
    	if(Gamestate == GameState.Leaderboard) {
    		leaderboardScreen.tick();
    	}
    }
   //
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
       RenderingHints rh = new RenderingHints(
               RenderingHints.KEY_TEXT_ANTIALIASING,
               RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
       RenderingHints rh2 = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         g2d.setRenderingHints(rh);
         g2d.setRenderingHints(rh2);
        ///
        
        
        if (Gamestate == GameState.InGame) {
	      ingameScreen.render(g);

        }
        else if(Gamestate == GameState.DeathScreen) {
        	deathScreen.render(g);
        }
        
        else if(Gamestate == GameState.Paused) {
        	pauseScreen.render(g);
        }
        else if(Gamestate == GameState.MainMenu) {
        	menuScreen.render(g);
        }
        else if(Gamestate == GameState.Leaderboard) {
        	leaderboardScreen.render(g);
        }
        bs.show();
        g.dispose();
       
    }
   
    public static Texture getInstance() {
    	return tex;
    }
    
	public static void main(String[] args) {
		new Main();

	}
    
    
    public void keyPressed(KeyEvent arg0) {
       
    }
 
    public void keyReleased(KeyEvent e) {
       
    }
 
    public void keyTyped(KeyEvent e) {
       
    }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
   
}