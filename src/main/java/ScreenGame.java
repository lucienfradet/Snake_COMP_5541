package rework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ScreenGame extends JPanel{
    
    private static final long serialVersionUID = 1L;

    private final int rows = 20;
    private final int columns = 20;
    private final int cellSize;

    private Color emptyColor;
    private Color snakeColor;
    private Color wallColor;

    private ArrayList<Point> snakePos = new ArrayList<>();
    private ArrayList<Point> foodPos = new ArrayList<>();
    private ArrayList<Point> wallPos = new ArrayList<>();

    //CONSTRUCTOR
	//===========================================================================================
    public ScreenGame(int panelSize, Color _emptyColor, Color _snakeColor, Color _foodColor)
    {
        //Make cell size fit grid dimensions
        cellSize = panelSize / rows;

        emptyColor = _emptyColor;
        snakeColor = _snakeColor;
        wallColor = _foodColor;

        setSize(columns * cellSize, rows * cellSize);
        setBackground(emptyColor);

    }

    //DRAW CELL - Draw one single square (grid cell)
	//===========================================================================================
    public void DrawCell(int i, int j, Graphics g, Color targetColor)
    {
        g.setColor(targetColor);
        int x = i * cellSize;
        int y = j * cellSize;
        g.fillRect(x, y, cellSize, cellSize); //FillRect might be better
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
