import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Raycaster {


	private static final int WIDTH = 1600, HEIGHT = 1216;

	
	private int x = 0;
	private int y = 0;
	
	private Random r = new Random();
	private LinkedList<Line2D.Float> lines;
	
	public Color halfWhite = new Color(255,255,255,30);
	
	private Player player;
	private MouseInput minput;
	private Camera camera;
	private MazeGenerator gen;
	private EnemyHandler eh;
	
	LinkedList<Line2D.Float> rays;
	LinkedList<Line2D.Float> companionRays;
	
	public Raycaster(Player player, MouseInput minput, Camera camera, MazeGenerator gen, EnemyHandler eh) {
		this.player = player;
		this.minput = minput;
		this.camera = camera;
		this.eh = eh;
		this.gen = gen;
		
	}
	
	public void linesAndRays() {
		lines = buildLines();
		rays = calcRays(lines, (int)player.getX()+16, (int)player.getY()+16, 10, 400);
		companionRays = calcCompanions(lines, (int)player.getX()+16, (int)player.getY()+16, 10, 400);
	}
	
	public void resetLines() {
		lines.clear();
	}
	
	public LinkedList<Line2D.Float> getLines() {
		return lines;
	}

	public void setLines(LinkedList<Line2D.Float> lines) {
		this.lines = lines;
	}

	public void tick() {

		rays = calcRays(lines, (int)player.getX()+16, (int)player.getY()+16,10, 400);
		companionRays = calcCompanions(lines, (int)player.getX()+16, (int)player.getY()+16, 30, 400);
	}
	
	public LinkedList<Line2D.Float> buildLines(){
		LinkedList<Line2D.Float> lines = new LinkedList<>();
		lines.addAll(gen.fetchCellLines());
		//lines.addAll(eh.returnELines());
		return lines;
	}
	
	public void render(Graphics g) {
		
		
		
		Graphics2D g2d = (Graphics2D) g;
		
		 Color startColor = new Color(255,255,255,255);
		 Color endColor = new Color(0,0,0,255);

		//To Display the maze walls, uncomment this code
		// vv
		 /*
		 g.setColor(Color.blue);
		for(Line2D.Float line : lines) {
			g.drawLine((int)line.x1, (int)line.y1, (int)line.x2, (int)line.y2);
		}
		 */
		// ^^
		 
		
		g2d.setStroke(new BasicStroke(25, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g.setColor(Color.white);
		for(Line2D.Float ray : rays) { 				//TORCH ANGLE IS 46 DEGREES
			GradientPaint gradient = new GradientPaint((int)ray.x1, (int)ray.y1, startColor, (int)ray.x2, (int)ray.y2, endColor);
		    g2d.setPaint(gradient);
		    
			 g.drawLine((int)ray.x1, (int)ray.y1, (int)ray.x2, (int)ray.y2); //add +10 to x1y1
		}
	
		for(Line2D.Float companion : companionRays) {
			g.setColor(Color.black);
			g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g.drawLine((int)companion.x1, (int)companion.y1, (int)companion.x2, (int)companion.y2);
		}
		
	}
	
	
	private float relativeToAbsoluteY() {
		return minput.my - (player.getY()+16+camera.getY());
	}

	private float relativeToAbsoluteX() {
		return minput.mx - (player.getX()+16+camera.getX());
	}
	
	private int getLength(int x1, int y1, int x2, int y2) {
		
		return (int) Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
		
	}
	
	
	private LinkedList<Line2D.Float> calcCompanions(LinkedList<Line2D.Float> lines, int x, int y, int resolution, int maxDist){
		LinkedList<Line2D.Float> companions = new LinkedList<>();
		
		for(int i = -resolution-20 ; i < resolution+20; i++) {
			
			double dir =   (Math.PI*2) * (double) i / (resolution) /15 + ((float) Math.atan2(relativeToAbsoluteY(), relativeToAbsoluteX())); 
			float minDist = maxDist;
			for(Line2D.Float line : lines) {
				float dist = getRayCast(x, y, (player.getX()+16)+(float) Math.cos(dir) * maxDist, (player.getY()+16)+(float) Math.sin(dir) * maxDist, line.x1, line.y1, line.x2, line.y2);
				if(dist < minDist && dist > 0) {
					minDist = dist;
				}
			}
			companions.add(new Line2D.Float( (player.getX()+16)+(float) Math.cos(dir) * minDist, (player.getY()+16)+(float) Math.sin(dir) * minDist, (player.getX()+16)+(float) Math.cos(dir) * maxDist, (player.getY()+16)+(float) Math.sin(dir) * maxDist ));
		}
		return companions;
	}
	
	private LinkedList<Line2D.Float> calcRays(LinkedList<Line2D.Float> lines, int x, int y, int resolution, int maxDist){
		
		LinkedList<Line2D.Float> rays = new LinkedList<>();
		//field of view affected by i
		for(int i = -resolution ; i < resolution; i++) {
			double dir =   (Math.PI*2) * (double) i / (resolution) /15 + ((float) Math.atan2(relativeToAbsoluteY(), relativeToAbsoluteX())); 
			float minDist = maxDist;
			for(Line2D.Float line : lines) {
				float dist = getRayCast(x, y, (player.getX()+16)+(float) Math.cos(dir) * maxDist, (player.getY()+16)+(float) Math.sin(dir) * maxDist, line.x1, line.y1, line.x2, line.y2);
				if(dist < minDist && dist > 0) {
					minDist = dist;
				}
			}
			rays.add(new Line2D.Float(x, y, (player.getX()+16)+(float) Math.cos(dir) * maxDist, (player.getY()+16)+(float) Math.sin(dir) * maxDist));
			
		}
		return rays;
	}
	
	public static float dist(float LineStartX, float LineStartY, float LineEndX, float LineEndY) {
		
		return (float) Math.sqrt((LineEndX - LineStartX) * (LineEndX - LineStartX) + (LineEndY - LineStartY) * (LineEndY - LineStartY));
		
	}

	public static float getRayCast(float RayStartX, float RayStartY, float RayCosineDist, float RaySineDist, float LineStartX, float LineStartY, float LineEndX, float LineEndY) {
		float RayEndX, RayEndY, LineXDiff, LineYDiff;
		RayEndX = RayCosineDist - RayStartX;
		RayEndY = RaySineDist - RayStartY;
		LineXDiff = LineEndX - LineStartX;
		LineYDiff = LineEndY - LineStartY;

		float s, t;
		s = (-RayEndY * (RayStartX - LineStartX) + RayEndX * (RayStartY - LineStartY)) / (-LineXDiff * RayEndY + RayEndX * LineYDiff);
		t = (LineXDiff * (RayStartY - LineStartY) - LineYDiff * (RayStartX - LineStartX)) / (-LineXDiff * RayEndY + RayEndX * LineYDiff);

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
			// Collision detected
			float x = RayStartX + (t * RayEndX);
			float y = RayStartY + (t * RayEndY);

			return dist(RayStartX, RayStartY, x, y);
		}

		return -1; // No collision
	}
	
	
}//
