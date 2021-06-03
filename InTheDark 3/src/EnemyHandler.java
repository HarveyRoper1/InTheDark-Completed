import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.Random;

public class EnemyHandler {

	public LinkedList<Enemy> enemies = new LinkedList<>();
	
	private Player player;
	private MazeGenerator mg;
	Random ran = new Random();
	

	public EnemyHandler(Player player, MazeGenerator mg) {
		this.player = player;
		this.mg = mg;
	}
	
	public void tick() {
		for(int i = 0 ; i < enemies.size() ; i++) {
			enemies.get(i).tick();
			if(enemies.get(i).isExploded()) {
				enemies.remove(enemies.get(i));
			}
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0 ; i < enemies.size() ; i++) {
			enemies.get(i).render(g);
		}
	}
	//
	public void createEnemy() {
		enemies.add(new Enemy(player, this, mg));
		
	}
	
	
	public void createMultipleEnemies(int num) {
		
		for(int i = 0 ; i < num; i++) {
			enemies.add(new Enemy(player, this, mg));
			
		}
	}
	
	public void removeEnemies(int num) {
		for(int i = 0 ; i < num; i++) {
			int random = ran.nextInt(enemies.size());
			enemies.remove(random);
		}
	}

	public LinkedList<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(LinkedList<Enemy> enemies) {
		this.enemies = enemies;
	}
	
}
