package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
// import javax.imageio.ImageIO;
// import java.io.IOException;

import javax.swing.JPanel;

public class ScreenGame extends JPanel{
    
    private static final long serialVersionUID = 1L;

    private int rows;
    private int columns;
    private int cellSize;
    private int maxCellSize = 50;
    private int minCellSize = 1;
    private int maxRows = 30;
    private int minRows = 10;
    private int maxColumns = 30;
    private int minColumns = 10;

    private Color emptyColor;
    private Color snakeColor;
    private Color wallColor;

    private ArrayList<Point> snakePos = new ArrayList<>();
    private ArrayList<Point> foodPos = new ArrayList<>();
    private ArrayList<Point> wallPos = new ArrayList<>();

    private BufferedImage snakeImg;

    //CONSTRUCTOR
	//===========================================================================================
    public ScreenGame(
        int _cellSize, 
        int _rows, 
        int _columns, 
        Color _emptyColor, 
        Color _snakeColor, 
        Color _wallColor)
    {
        //Validate and Initialize variables
        cellSize =  _cellSize <= maxCellSize    ? _cellSize : maxCellSize;
        cellSize =  cellSize > minCellSize      ? cellSize : minCellSize;
        rows =      _rows <= maxRows            ? _rows : maxRows;
        rows =      rows > minRows              ? rows : minRows;
        columns =   _columns <= maxColumns      ? _columns : maxColumns;
        columns =   columns > minColumns        ? columns : minColumns;

        //Initialize colors
        emptyColor = _emptyColor;
        snakeColor = _snakeColor;
        wallColor = _wallColor;

        //Load Sprites
        /*try{
            //Learn to implement Gradle
            snakeImg = ImageIO.read(getClass().getResource("/resources/sprites/snake00.png"));
        }
        catch(IOException | IllegalArgumentException e)
        {
            throw new RuntimeException("Failed to load sprites.", e);
        }*/

        //JPanel suggestion, swing can override it to avoid conflicts
        setPreferredSize(new Dimension(columns * cellSize, rows * cellSize));
        setBackground(emptyColor);
    }

    //DRAW CELL - Draw one single square (grid cell)
	//===========================================================================================
    public void DrawCell(int i, int j, Graphics g, Color targetColor)
    {
        g.setColor(targetColor);
        int x = i * cellSize;
        int y = j * cellSize;
        g.fillRect(x, y, cellSize, cellSize);
    }

    //(WIP - NOT YET IMPLEMENTED): DRAW SPRITE - Draw a given sprite 
	//===========================================================================================
    public void DrawSprite(int i, int j, Graphics g, Image img)
    {
        int x = i * cellSize;
        int y = j * cellSize;

        g.drawImage(snakeImg, x, y, getFocusCycleRootAncestor());
    }

    //UPDATE SNAKE POS - Updates internal positional data for the snake
	//===========================================================================================
    public void UpdateSnakePos(Snake snake)
    {
        //Flush all previous snakePos information
        snakePos.clear();

        for(SnakeSegment seg : snake.segments)
        {
            snakePos.add(new Point(seg.GetPos().x, seg.GetPos().y));
        }
    }

    //UPDATE FOOD POS - Updates internal positional data for food items
	//===========================================================================================
    public void UpdateFoodPos(ArrayList<Tuple> foodPositions)
    {
        //Flush all previous foodPos information
        foodPos.clear();

        for(Tuple tuple : foodPositions)
        {
            foodPos.add(new Point (tuple.getX(), tuple.getY()));
        }
    }

    //UPDATE FOOD POS - Updates internal positional data for food items
	//===========================================================================================
    public void UpdateWallPos(ArrayList<Tuple> wallPositions)
    {
        for(Tuple tuple : wallPositions)
        {
            wallPos.add(new Point (tuple.getX(), tuple.getY()));
        }
    }

    //REPAINT OVERRIDE - roverrides JPanel's ".repaint()" function. This draws each frame.
	//===========================================================================================
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //System.out.println("Panel size: " + getWidth() + " x " + getHeight());

        //Flush Background
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++)
            {
                DrawCell(i, j, g, emptyColor);
            }
        }
        
        //Draw Snake
        for(Point p : snakePos)
        {
            DrawCell(p.x, p.y, g, snakeColor);  
        }
            
        //Draw Food
        for(Point p : foodPos)
        {
            DrawCell(p.x, p.y, g, snakeColor);
        }

        //Draw Walls
        for(Point p : wallPos)
        {
            DrawCell(p.x, p.y, g, wallColor);  
        }
                
    }
}
