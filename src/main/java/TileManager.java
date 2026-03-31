import java.util.ArrayList;

public class TileManager {
    //Map
	//===
	private MapDB maps = new MapDB();
	//If performance becomes an issue, turn these ArrayLists into HashMaps for O(1) lookup
	private ArrayList<Tuple> foodPositions = new ArrayList<>();		
	private ArrayList<Tuple> wallPositions = new ArrayList<>();
    
	//Snake Data
	//==========
	private Snake snake;

    //Constructor
    //===========
    public TileManager(int mapSelection, Tuple positionDepart)
    {
        //Initialize walls, load desired map layout. Map is selected by index:
		//0 - "Square" Map
		//1 - "Walls" Map
		//else - Empty Map
		wallPositions = maps.GetArrayList(mapSelection);

        
		//Initialize snake 
		snake = positionDepart == null ? 
			new Snake(3, new Tuple(10, 10)) : 
			new Snake(3, positionDepart);
    }

    public boolean CheckForFood(Tuple tuple)
    {
        return foodPositions.contains(tuple);
    }

    public boolean CheckForWall(Tuple tuple)
    {
        return wallPositions.contains(tuple);
    }

    //SpawnFood - Spawn food at a random empty spot, then return its position
	//===========================================================================================
	public Tuple SpawnFood()
	{
        Tuple foodPosition = GetEmptyCoords();
        foodPositions.add(foodPosition);

        return foodPosition;
	}

	//GetEmptyCoords - returns a position not occupied by the snake
	//===========================================================================================
	private Tuple GetEmptyCoords()
	{
		//Get random coordinates between [0, 19]
		Tuple p ;
		int ranX = (int)(Math.random()*19); 
		int ranY = (int)(Math.random()*19); 
		p = new Tuple(ranX,ranY);

		//If either the snake or a wall is currently occupying those coordinates,
		//reroll the dice.
		while(wallPositions.contains(p) ||
				snake.ContainsPosition(p))
		{
			ranX = (int)(Math.random()*19); 
		 	ranY = (int)(Math.random()*19); 
		 	p = new Tuple(ranX,ranY);
		}

		 return p;
	}

    //CheckFoodCollisions
	//===========================================================================================
    //Checks for snake-food collisions
    public boolean CheckFoodCollisions()
    {
		if(CheckForFood(snake.GetHeadPos()))
		{
			GetFoodPos().remove(snake.GetHeadPos());

			snake.IncreaseSnakeSize();

		 	SpawnFood();

            return true;
		}
        else
        {
            return false;
        }
    }

    //CheckWallCollisions
	//===========================================================================================
    //Checks for snake-wall collisions
    public boolean CheckWallCollisions()
    {
        return CheckForWall(snake.GetHeadPos());
    }

    //Setters
    //=======
    public void SetFoodPos(ArrayList<Tuple> array) {foodPositions = array;}
    public void SetWallPos(ArrayList<Tuple> array) {wallPositions = array;}

    //Getters
    //=======
    public Snake GetSnake() {return snake;}
    public ArrayList<Tuple> GetFoodPos() { return foodPositions;}
    public ArrayList<Tuple> GetWallPositions() {return wallPositions;}
}
