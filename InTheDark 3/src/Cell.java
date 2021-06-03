import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
 
public class Cell {
   
    public boolean northWallActive;
    public boolean eastWallActive;
    public boolean southWallActive;
    public boolean westWallActive;
   
    private int width,height;
    private int x,y;
   
    private int row,col;
   
    private boolean visited;
   
    Random ran = new Random();
    
    private boolean claimedByEnemy;
   
    private LinkedList<Line2D.Float>lines = new LinkedList<>();
   
    private Line2D.Float northLine;
    private Line2D.Float westLine;
    private Line2D.Float eastLine;
    private Line2D.Float southLine;
    
    private Rectangle northColl;
    private Rectangle eastColl;
    private Rectangle southColl;
    private Rectangle westColl;
    
    public Cell(boolean northWallActive, boolean eastWallActive, boolean southWallActive, boolean westWallActive, int width, int height, int x, int y, int row, int col, boolean visited, boolean claimedByEnemy) {
       
        this.northWallActive = northWallActive;
        this.southWallActive = southWallActive;
        this.eastWallActive = eastWallActive;
        this.westWallActive = westWallActive;
       
        this.width=width;
        this.height = height;
        
        this.x = x;
        this.y = y;
       
        this.row = row;
        this.col = col;
       
        this.visited = visited;
        this.claimedByEnemy = claimedByEnemy;
        
        northLine = new Line2D.Float(x,y,x+width,y); //north
        westLine = new Line2D.Float(x,y,x,y+height); //west
        eastLine = new Line2D.Float(x+width,y,x+width,y+height); //east
        southLine = new Line2D.Float(x,y+height,x+width,y+height); //south
        
        northColl = new Rectangle((int)x,(int)y, width, 2);
		eastColl = new Rectangle((int)x+width,(int)y,2,height);
		southColl = new Rectangle((int)x,(int)y+height,width,2);
		westColl = new Rectangle((int)x,(int)y,2,height);
       
        if(northWallActive) lines.add(northLine);
        if(westWallActive)  lines.add(westLine);
        if(eastWallActive)  lines.add(eastLine);
        if(southWallActive) lines.add(southLine);
       
    }
   
//
	public void tick() {
       
    }
   
    public LinkedList<Line2D.Float> commitLines(){
        return lines;
    }
   
    public Cell checkNeighbours(Cell[][] cells) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        Cell right = null;
        Cell left = null;
        Cell top = null;
        Cell bottom = null;
       
       
        if(col != MazeGenerator.cols) {
            right = cells[row][col+1];
            
        }
        if(col != 0) {
            left = cells[row][col-1];
            
        }
        if(row != 0) {
            top = cells[row-1][col];
            
        }
        if(row != MazeGenerator.rows) {
            bottom = cells[row+1][col];
           
        }
       
        if(top != null    && !top.isVisited()) {
            neighbours.add(top);
           
        }
        if(bottom != null && !bottom.isVisited()) {
            neighbours.add(bottom);
           
        }
        if(left != null   && !left.isVisited()) {
            neighbours.add(left);
           
        }
        if(right != null  && !right.isVisited()) {
            neighbours.add(right);
           
        }
       
        if(neighbours.size() > 0) {
            int r = ran.nextInt(neighbours.size());
            return neighbours.get(r);
        }else {
            return null;
        }
           
    }
   
    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void render(Graphics g) {
 
		Graphics2D g2d = (Graphics2D) g;
		//g.setColor(Color.white);
		//g.setFont(new Font("Arial", 16, 16));
		//g.drawString(row + ", " + col, x+10, y+20);
		
       // g.setColor(Color.red);
       // if(northWallActive) g.fillRect(x, y, width, 5); // north
      //  if(westWallActive)  g.fillRect(x, y, 5, height); // west
       // if(eastWallActive)  g.fillRect(x+width, y, 5, height+5); //east
      //  if(southWallActive) g.fillRect(x, y+height, width, 5); //south
        
        //g.setColor(Color.green);
        //g2d.draw(northColl);
        //g2d.draw(southColl);
        //g2d.draw(eastColl);
        //g2d.draw(westColl);
        //if(visited) {
        //    g.setColor(Color.MAGENTA);
        //    g.fillRect(x, y, width, height);
       // }
    }
   
 
    public Line2D.Float getNorthLine() {
        return northLine;
    }
 
    public void setNorthLine(Line2D.Float northLine) {
        this.northLine = northLine;
    }
 
    public Line2D.Float getWestLine() {
        return westLine;
    }
 
    public void setWestLine(Line2D.Float westLine) {
        this.westLine = westLine;
    }
 
    public Line2D.Float getEastLine() {
        return eastLine;
    }
 
    public void setEastLine(Line2D.Float eastLine) {
        this.eastLine = eastLine;
    }
 
    public Line2D.Float getSouthLine() {
        return southLine;
    }
 
    public void setSouthLine(Line2D.Float southLine) {
        this.southLine = southLine;
    }
 
    public boolean isNorthWallActive() {
        return northWallActive;
    }
 
    public void setNorthWallActive(boolean northWallActive) {
        this.northWallActive = northWallActive;
    }
 
    public boolean isEastWallActive() {
        return eastWallActive;
    }
 
    public void setEastWallActive(boolean eastWallActive) {
        this.eastWallActive = eastWallActive;
    }
 
    public boolean isSouthWallActive() {
        return southWallActive;
    }
 
    public void setSouthWallActive(boolean southWallActive) {
        this.southWallActive = southWallActive;
    }
 
    public boolean isWestWallActive() {
        return westWallActive;
    }
 
    public void setWestWallActive(boolean westWallActive) {
        this.westWallActive = westWallActive;
    }
 
    public int getRow() {
        return row;
    }
 
    public void setRow(int row) {
        this.row = row;
    }
 
    public int getCol() {
        return col;
    }
 
    public void setCol(int col) {
        this.col = col;
    }
   
    public boolean isVisited() {
        return visited;
    }
 
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
 
    public Rectangle getNorthColl() {
		return northColl;
	}

	public void setNorthColl(Rectangle northColl) {
		this.northColl = northColl;
	}

	public Rectangle getEastColl() {
		return eastColl;
	}

	public void setEastColl(Rectangle eastColl) {
		this.eastColl = eastColl;
	}

	public Rectangle getSouthColl() {
		return southColl;
	}

	public void setSouthColl(Rectangle southColl) {
		this.southColl = southColl;
	}

	public Rectangle getWestColl() {
		return westColl;
	}

	public void setWestColl(Rectangle westColl) {
		this.westColl = westColl;
	}

	public boolean isClaimedByEnemy() {
		return claimedByEnemy;
	}

	public void setClaimedByEnemy(boolean claimedByEnemy) {
		this.claimedByEnemy = claimedByEnemy;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}
   
}