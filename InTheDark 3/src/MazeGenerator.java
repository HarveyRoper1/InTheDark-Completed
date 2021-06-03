import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
 
public class MazeGenerator {
 
    private int[][] northWalls;
    private int[][] eastWalls;
    private int[][] southWalls;
    private int[][] westWalls;
   
    private int cellWidth=400,cellHeight=400;
    public static int rows=10;
    public static int cols=10;
   
    private int ticks=0;
   
    private Cell[][] cells = new Cell[rows+1][cols+1];
   
    private Clock clock;
   
    private LinkedList<Line2D.Float> cellsLines = new LinkedList<>();
    private LinkedList<Cell> cellsList = new LinkedList<>();
   
    private Cell current;
   
    private Stack<Cell> cellStack = new Stack<>();
   
    Random ran = new Random();
    
    private ExitHatch exit;
    private Player p;
    
    public MazeGenerator(Player p) {
    	this.p = p;
    	construct();
    }
    
    public void construct() {
    	cellStack.clear();
    	cellsList.clear();
    	cellsLines.clear();
    	
    	p.setxCent(200);
    	p.setyCent(200);
        for(int i = 0 ; i < rows ; i++) {
            for(int j = 0 ; j < cols ; j++) {
                cells[i][j] = new Cell(true, true, true, true, cellWidth,cellHeight,cellWidth*i,cellHeight*j, i, j, false, false);
                commitCell(cells[i][j]);
                }
            }
        exit = new ExitHatch(this, p);
        current = cells[0][0]; //starting cell
    }

   
    
    
    
    
    
    
    //
	public void generateMaze() {
        current.setVisited(true);
        Cell next = current.checkNeighbours(cells);
        if(next != null) {
            removeWalls(current, next);
            next.setVisited(true);
            cellStack.push(current);
            current = next;
            generateMaze();
        }
        else if(cellStack.size() > 0) {
            Cell cell = cellStack.pop();
            current = cell;
            generateMaze();
        } 
       
        for(Cell c : cellsList) {
            if(!c.isNorthWallActive()) {
                cellsLines.remove(c.getNorthLine());
            }
            if(!c.isEastWallActive()) {
                cellsLines.remove(c.getEastLine());
            }
            if(!c.isWestWallActive()) {
                cellsLines.remove(c.getWestLine());
            }
            if(!c.isSouthWallActive()) {
                cellsLines.remove(c.getSouthLine());
            }
        }
       
        
        
    }
	
	
	
	
	
   
    public void removeWalls(Cell current, Cell next) {
        //Coding train uses i for Col, j for row
       
        if(current.getRow()+1 == next.getRow()) {
            current.setEastWallActive(false);
            next.setWestWallActive(false);
        }
        if(current.getCol()+1 == next.getCol()) {
            current.setSouthWallActive(false);
            next.setNorthWallActive(false);
        }
        if(current.getRow()-1 == next.getRow()) {
            current.setWestWallActive(false);
            next.setEastWallActive(false);
        }
        if(current.getCol()-1 == next.getCol()) {
            current.setNorthWallActive(false);
            next.setSouthWallActive(false);
        }
    }
   
    public void commitCell(Cell cell){
        cellsLines.addAll(cell.commitLines());
        cellsList.add(cell);
    }
   
   
    public LinkedList<Line2D.Float> fetchCellLines(){
        return cellsLines;
    }
   
    public LinkedList<Cell> fetchCellList(){
    	return cellsList;
    }
    
    public void tick() {
        ticks++;
        exit.tick();
    }
   
    public void render(Graphics g) {
       
        for(int i = 0 ; i < cellsList.size(); i++) {
        	cellsList.get(i).render(g);
        }
        exit.render(g);
    }
   
   
    public void removeNorthWall(int i, int j) {
        northWalls[i][j] = 0;
    }
    public void removeEastWall(int i, int j) {
        eastWalls[i][j] = 0;
    }
    public void removeSouthWall(int i, int j) {
        southWalls[i][j] = 0;
    }
    public void removeWestWall(int i, int j) {
        westWalls[i][j] = 0;
    }
    
    public ExitHatch getExit() {
  		return exit;
  	}

  	public void setExit(ExitHatch exit) {
  		this.exit = exit;
  	}

  	public Player getP() {
  		return p;
  	}

  	public void setP(Player p) {
  		this.p = p;
  	}

}