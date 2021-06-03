import java.util.LinkedList;

public class LevelCreator {
	
	static MazeGenerator mg;
	static Player p;
	static Raycaster rc;
	static EnemyHandler eh;
	static Clock clock;
	private static LeaderboardScreen lbs;
	
	public static int level = 0;
	
	public LinkedList<String> PlayID = new LinkedList<>();
	
	public LevelCreator(MazeGenerator mg, Player p, Raycaster rc, EnemyHandler eh, Clock clock, LeaderboardScreen lbs) {
		this.mg = mg;
		this.p = p;
		this.rc = rc;
		this.eh = eh;
		this.clock = clock;
		this.lbs = lbs;
	}
	//
	public static void createLevel() {
		level++;
		eh.removeEnemies(eh.enemies.size());
		mg.construct();
		mg.generateMaze();
		
		p.setX(200); //Can be defined by variable, this is just easy for now to make it always be at 0,0
		p.setY(200);
		p.crumbs.clear();
		p.setHealth(100);
		
		if(rc.getLines() != null) rc.resetLines();
		rc.linesAndRays();

		eh.createMultipleEnemies(5+level*2); //THE SLOW LAUNCH TIME IS CAUSED BY THIS
		
		
	}
	
	public static void newGame() {
		System.out.println(level);
		lbs.currentPlayerScore = level;
		level = 1;
		eh.removeEnemies(eh.enemies.size());
		mg.construct();
		mg.generateMaze();
		
		p.setX(200); //Can be defined by variable, this is just easy for now to make it always be at 0,0
		p.setY(200);
		p.crumbs.clear();
		p.setHealth(100);
		p.crumbsAvailable = 50;
		
		if(rc.getLines() != null) rc.resetLines();
		rc.linesAndRays();

		eh.createMultipleEnemies(5); //THE SLOW LAUNCH TIME IS CAUSED BY THIS
		
		
	}
	
}
