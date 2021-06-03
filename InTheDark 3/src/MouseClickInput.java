import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

public class MouseClickInput extends MouseAdapter{

	public boolean mousePressed=false;
	public boolean mouseEntered=false;
	public int mx;
	public int my;
	
	private LeaderboardScreen lbs;
	private LevelCreator lc;
	private MainMenuScreen ms;

	public MouseClickInput(LeaderboardScreen lbs, LevelCreator lc, MainMenuScreen ms) {
		this.lbs = lbs;	
		this.lc = lc;
		this.ms = ms;
	}
	
	public LeaderboardScreen getLbs() {
		return lbs;
	}

	public void setLbs(LeaderboardScreen lbs) {
		this.lbs = lbs;
	}

	public void mouseEntered(MouseEvent e) {
		mouseEntered=true;
	}

	public void mouseExited(MouseEvent e) {
		mouseEntered=false;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		mousePressed = true;
		
		if(Main.Gamestate == GameState.MainMenu) {
		
			if(mx >= Main.WIDTH/2-100 && mx <= Main.WIDTH/2+100) {
				if(my >= 150 && my <= 200) {
					Main.Gamestate = GameState.InGame;
					
				}
			}
			if(mx >= Main.WIDTH/2-100 && mx <= Main.WIDTH/2+100) {
				if(my >= 250 && my <= 300) {
					Main.Gamestate = GameState.Leaderboard;
						
					}
				}
			
			if(mx >= Main.WIDTH/2-100 && mx <= Main.WIDTH/2+100) {
				if(my >= 350 && my <= 400) {
					System.exit(1);
					
				}
			}
			
			if(mx >= 475 && mx <= 800) {
				if(my >= 600 && my <= 800) {
					ms.openWebpage("https://www.rnib.org.uk/about-us");
				}
			}
			
		}
		
		if(Main.Gamestate == GameState.Leaderboard) {
			
			if(mx >= 50 && mx <= 200) {
				if(my >= 650 && my <= 700) {
					Main.Gamestate = GameState.MainMenu;
				}
			}
			if(mx >= 600 && mx <= 650) {
				if(my >= 650 && my <= 700) {
					if(lbs.pageIndex > 0) lbs.pageIndex--;
				}
			}
			if(mx >= 660 && mx <= 710) {
				if(my >= 650 && my <= 700) {
					if(lbs.pageIndex*10+10 < lbs.leaderboardList.size()) {
						lbs.pageIndex++;
					}
				}
			}
		}
			
		if(Main.Gamestate == GameState.Paused) {
			if(mx >= 100 && mx <= 250) {
				if(my >= 600 && my <= 650) {
					Main.Gamestate = GameState.MainMenu;
				}
			}
			if(mx >= 325 && mx <= 475) {
				if(my >= 600 && my <= 650) {
					Main.Gamestate = GameState.Leaderboard;
				}
			
			}
			
			if(mx >= 550 && mx <= 700) {
				if(my >= 600 && my <= 650) {
					Main.Gamestate = GameState.InGame;
				}
			}
		}
		
		if(Main.Gamestate == GameState.DeathScreen) {
			if(mx >= Main.WIDTH/5-100 && mx <= Main.WIDTH/5+200) {
				if(my >= 600 && my <= 650) {
					Main.Gamestate = GameState.MainMenu;
				}
			}
			if(mx >= Main.WIDTH/2 && mx <= Main.WIDTH/2+300) {
				if(my >= 600 && my <= 650) {
					String name = JOptionPane.showInputDialog("Enter your name: ");
					lbs.writeToFile(name, lbs.currentPlayerScore);
					lbs.leaderboardMap.put(name, lbs.currentPlayerScore);
					Main.Gamestate = GameState.MainMenu;
				}
			}
		}
		
		
			
	}
		
	
	public MainMenuScreen getMs() {
		return ms;
	}

	public void setMs(MainMenuScreen ms) {
		this.ms = ms;
	}

	public LevelCreator getLc() {
		return lc;
	}

	public void setLc(LevelCreator lc) {
		this.lc = lc;
	}
	
		
	


	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
	}

	public void mouseClicked(MouseEvent e) {
		
		
	}
	
	
}
//