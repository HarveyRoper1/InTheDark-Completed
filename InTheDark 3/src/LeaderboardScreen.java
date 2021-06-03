import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class LeaderboardScreen {
	
	String path = String.format(System.getProperty("user.home")+"\\Desktop\\Leaderboard.txt");
	
	File leaderboard = new File(path);

	private Rectangle backButton = new Rectangle(50, 650, 150, 50);
	private Rectangle pageFwd = new Rectangle(600, 650, 50, 50);
	private Rectangle pageBck = new Rectangle(660, 650, 50, 50);
	public int pageIndex = 0;
	
	private int ticks=0;
	private boolean clickable=true;
	
	MouseClickInput m_clickInput;
	MouseInput minput;
	
	LinkedList<String> leaderboardList = new LinkedList<>();
	private boolean leaderboardCreated=false;
	
	public int currentPlayerScore=0;
	
	private Random ran = new Random();
	
	
	public Map<String, Integer> leaderboardMap = new HashMap<String, Integer>();
	
	//private String[] na = new String[] {"Imdad", "Louis", "Ryan", "Dylan", "Liam", "Kian", "Gurmann", "Aman", "Sheldon", "Gavin", "Eric", "Manas", "Jason", "Aaron", "Oliver", "Mr Sandman", "Mr Correia", "Mr Loizou"};

	
	public LeaderboardScreen(MouseClickInput m_clickInput, MouseInput minput) {

		leaderboardMap = sortByValue(leaderboardMap);
		
		//printMap(leaderboardMap);
		
		
		this.m_clickInput = m_clickInput;
		this.minput = minput;
		
		
		findFile();
		
		toLinkedList(leaderboard);
		
		
		

		
	}
	
	public static <K, V> void printMap(Map<K, V> map) {
	    for (Map.Entry<K, V> entry : map.entrySet()) {
	        System.out.println("Key : " + entry.getKey()
	                + " Value : " + entry.getValue());
	    }
	}
	
	private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

	    // 1. Convert Map to List of Map
	    List<Map.Entry<String, Integer>> list =
	            new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

	    // 2. Sort list with Collections.sort(), provide a custom Comparator
	    //    Try switch the o1 o2 position for a different order
	    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
	        public int compare(Map.Entry<String, Integer> o1,
	                           Map.Entry<String, Integer> o2) {
	            return (o2.getValue()).compareTo(o1.getValue());
	        }
	    });

	    // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
	    Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
	    for (Map.Entry<String, Integer> entry : list) {
	        sortedMap.put(entry.getKey(), entry.getValue());
	    }
	    return sortedMap;
	}
	
	
	public void tick(){
		leaderboardList.clear();
		
		leaderboardMap = sortByValue(leaderboardMap);
		for(Map.Entry entry : leaderboardMap.entrySet()) {
				leaderboardList.add(new StringBuilder().append(entry.getKey() + ": " + entry.getValue()).toString());
				//System.out.println(entry.getKey());
			}	
		}
	
	
	/*
	public void generateEntries() {
		int size = names.size();
		for(int i = 0 ; i < size ;i++) {
			
			int y = ran.nextInt(9)+1;
			scores[i] = y;
		}
		scores = sortScores(scores);
		StringBuilder builder = new StringBuilder();
		for(int i = 0 ; i < names.size() ; i++){
			builder.append(names.get(i));
			builder.append(": " + scores[i]);
			entries.add(builder.toString());
			builder.setLength(0);
		}
	}
	*/
	public int[] sortScores(int[] input) {
		int inputLength = input.length;
	    int temp;
	    boolean is_sorted;

	    for (int i = 0; i < inputLength; i++) {

	        is_sorted = true;

	        for (int j = 1; j < (inputLength - i); j++) {

	            if (input[j - 1] < input[j]) {
	                temp = input[j - 1];
	                input[j - 1] = input[j];
	                input[j] = temp;
	                is_sorted = false;
	            }

	        }

	        // is sorted? then break it, avoid useless loop.
	        if (is_sorted) break;
	    }
	    return input;
	}
	
	
	public void findFile() {
		try {
			if(leaderboard.createNewFile()) {
				leaderboardMap.put("Mr Loizou", ran.nextInt(9)+1);
				leaderboardMap.put("Imdad", ran.nextInt(9)+1);
				leaderboardMap.put("Louis", ran.nextInt(9)+1);
				leaderboardMap.put("Ryan", ran.nextInt(9)+1);
				leaderboardMap.put("Dylan", ran.nextInt(9)+1);
				leaderboardMap.put("Liam", ran.nextInt(9)+1);
				leaderboardMap.put("Mr Correia", ran.nextInt(9)+1);
				leaderboardMap.put("Gurmann", ran.nextInt(9)+1);
				leaderboardMap.put("Aman", ran.nextInt(9)+1);
				leaderboardMap.put("Sheldon", ran.nextInt(9)+1);
				leaderboardMap.put("Gavin", ran.nextInt(9)+1);
				leaderboardMap.put("Eric", ran.nextInt(9)+1);
				leaderboardMap.put("Manas", ran.nextInt(9)+1);
				leaderboardMap.put("Jason", ran.nextInt(9)+1);
				leaderboardMap.put("Aaron", ran.nextInt(9)+1);
				leaderboardMap.put("Oliver", ran.nextInt(9)+1);
				leaderboardMap.put("Mr Sandman", ran.nextInt(9)+1);
				for(Map.Entry entry : leaderboardMap.entrySet()) {
					writeToFile((String)entry.getKey(), (int)entry.getValue());
				}
			}
			else {
				
				}
			}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public void writeToFile(String name, int score) {
		try {
		      FileWriter myWriter = new FileWriter(path,true);
		      myWriter.append(new StringBuilder().append(name + ": " + score).toString() + "\n");
		      myWriter.close();
		      
		    } catch (IOException e) {
		    	System.out.println("An error occurred writing to the leaderboard");
		    	e.printStackTrace();
		    }
		}
	
	
	public void toLinkedList(File leaderboard) {
		try {
			Scanner scanner = new Scanner(leaderboard);
			while(scanner.hasNextLine()) {
				String entry = scanner.nextLine();
				String[] arrOfString = entry.split(": ");
				System.out.println(arrOfString[0].toString());
				leaderboardMap.put(arrOfString[0], Integer.parseInt(arrOfString[1]));
				leaderboardList.add(entry);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.black);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		Font fnt0 = new Font("Monospaced", Font.PLAIN, 48);
		g.setFont(fnt0);
		
		g.setColor(Color.white);
		if(leaderboardList.size() - pageIndex*10 >= 10) {
			for(int i = pageIndex*10 ; i < (pageIndex*10)+10 ; i++) {
				
				int pos = ((pageIndex*10)+10)-i;
				g.drawString(leaderboardList.get(i).toString(), Main.WIDTH/2-180, (10-pos)*50+150);
				
				}
		}else {
			int left = leaderboardList.size() - pageIndex*10;
			for(int i = 0 ; i < left ; i++) {
				
				g.drawString(leaderboardList.get(i+(pageIndex*10)), Main.WIDTH/2-180, i*50+150);
			}
		}
		
		if(minput.mx >= 50 && minput.mx <= 200) {
			if(minput.my >= 650 && minput.my <= 700) {
				g.setColor(Color.gray);
				g.fillRect(backButton.x, backButton.y, backButton.width, backButton.height);
			}
		}
		if(minput.mx >= 600 && minput.mx <= 650) {
			if(minput.my >= 650 && minput.my <= 700) {
				g.setColor(Color.gray);
				g.fillRect(pageFwd.x, pageFwd.y, pageFwd.width, pageFwd.height);
			}
		}
		if(minput.mx >= 660 && minput.mx <= 710) {
			if(minput.my >= 650 && minput.my <= 700) {
				g.setColor(Color.gray);
				g.fillRect(pageBck.x, pageBck.y, pageBck.width, pageBck.height);
			}
		}
		
		g.setColor(Color.white);
		
		Font fnt1 = new Font("Monospaced", Font.PLAIN, 16);
		g.setFont(fnt1);
		g2d.draw(backButton);
		g.drawString("Menu", 105, 680);
		g2d.draw(pageBck);
		g.drawString("<<", 615, 680);
		g2d.draw(pageFwd);
		g.drawString(">>", 675, 680);
		
		Font fnt2 = new Font("Monospaced", Font.BOLD, 48);
		g.setFont(fnt2);
		g.drawString("Leaderboard:", Main.WIDTH/2-200, 80);
		
		
		
	}
	
}
