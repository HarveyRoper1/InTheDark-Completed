import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class AudioPlayer {
//
	public static Map<String, Sound> soundMap = new HashMap<String, Sound>();
	public static Map<String, Music> musicMap = new HashMap<String, Music>();
	
	public static void load() {
		try {
			soundMap.put("Wub", new Sound("res/Wub_Effect.ogg"));
			soundMap.put("Ding", new Sound("res/DingEffect.ogg"));
			soundMap.put("DingQuiet", new Sound("res/DingEffectQuiet.ogg"));
			soundMap.put("Explode", new Sound("res/EnemyExplodeEffect.ogg"));
			soundMap.put("Footstep1", new Sound("res/FootStepQuiet.ogg"));
			soundMap.put("Footstep2", new Sound("res/FootStep2Quiet.ogg"));
			soundMap.put("Footstep3", new Sound("res/FootStep3Quiet.ogg"));
			soundMap.put("Footstep4", new Sound("res/FootStep4Quiet.ogg"));
			soundMap.put("Footstep5", new Sound("res/FootStep5Quiet.ogg"));
			
			musicMap.put("Ambience", new Music("res/Ambience.ogg"));
			musicMap.put("music", new Music("res/I Miss You.ogg"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static Music getMusic(String key) {
		return musicMap.get(key);
	}
	
	public static Sound getSound(String key) {
		return soundMap.get(key);
	}
	
}
