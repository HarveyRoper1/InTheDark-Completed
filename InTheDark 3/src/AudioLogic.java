
import java.util.Random;

public class AudioLogic {
	//
	Player p;
	EnemyHandler eh;
	Random ran = new Random();
	int ticks = 0;
	int ticks2 = 0;
	
	private boolean dingPlayed=false;

	public AudioLogic(Player p, EnemyHandler eh) {
		this.p = p;
		this.eh = eh;
	}
	
	public void tick() {
		int i = ran.nextInt(4)+1;
		ticks++;
		ticks2++;
		
		if(ticks > getSmallestDist()/3) {
			AudioPlayer.getSound("Wub").play();	//Controls when enemy proximity tone is played.
			ticks = 0;
		}
		
		if(p.mouseExitAngle() < 24 || p.mouseExitAngle() > 360-24) {
			dingPlayed = true;
			if(ticks > p.mouseExitAngle()) {
				AudioPlayer.getSound("DingQuiet").play();		//Finds the angle of the player to the exit
				ticks = 0;										//Plays a sound dependant on the size of the angle.
			}
		if(p.mouseExitAngle() > 24 || p.mouseExitAngle() < 360-24) {
			dingPlayed = false;						
		}
		
		//if(dingPlayed) {
		//	AudioPlayer.getSound("DingQuiet").play();
		//}
		
		
		if(ticks2 > 25 && p.isMoving()) {
			System.out.println("Step");
			if(i==1) AudioPlayer.getSound("Footstep1").play();
			if(i==2) AudioPlayer.getSound("Footstep2").play();	
			if(i==3) AudioPlayer.getSound("Footstep3").play();	
			if(i==4) AudioPlayer.getSound("Footstep4").play();	
			if(i==5) AudioPlayer.getSound("Footstep5").play();	
			
			ticks2 = 0;
		}
		}
	}
	
	public int getSmallestDist() {
		int smallestDist=100000;
		for(Enemy e : eh.enemies) {
			if(e.distToPlayer() < smallestDist) {
				smallestDist = e.distToPlayer();
			}
		}
		return smallestDist;
		
	}
	
}
