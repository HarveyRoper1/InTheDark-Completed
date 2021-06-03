import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Clock {
 
    private int ticks=0;
    private int x=520;
    private int y=80;
    private int seconds=0;
    
    private int maxTime=300;
    
    public Clock() {
    	
    }
    
    public void tick() {
        if(++ticks >= 60) {
        	seconds++;
        	ticks=0;
        }
    }
    
//
	public void render(Graphics g) {
    	if(maxTime-seconds > 60) g.setColor(Color.white);
    	else if(maxTime-seconds <= 60) g.setColor(Color.red);
    	g.setFont(new Font("Monospaced", Font.PLAIN, 28));
    	g.drawString("Time Left: "+(maxTime-seconds) + "s", x, y);
    }
   
    public void waitSeconds(double seconds) {
       
        if(++ticks >= seconds*60) {
            //System.out.println("Second");
            ticks=0;
            return;
        }
        else {
            waitSeconds(seconds);
        }
    }
    
    public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}
   
}