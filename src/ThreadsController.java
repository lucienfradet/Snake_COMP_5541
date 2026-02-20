import java.util.ArrayList;
import java.util.HashSet;

//import javax.swing.text.Segment;


//Controls all the game logic .. most important class in this project.
public class ThreadsController extends Thread {

	//Game Settings
	//=============
	public Boolean gameActive = true;
	public static int inputDirection;
	public int FPS = 10;
	private int currentDirection;		//1:right 2:left 3:top 4:bottom 0:nothing

	//Map
	//===
	//Content states of a square
	final Integer SNAKE = 0;
	final Integer FOOD = 1;
	final Integer EMPTY = 2;

	ArrayList<ArrayList<DataOfSquare>> Squares= new ArrayList<ArrayList<DataOfSquare>>();
	HashSet<Tuple> foodPositions = new HashSet<>();
	
	//Snake Data
	//==========
	private Snake snake;

	//Constructor
	//===========================================================================================
	ThreadsController(Tuple positionDepart){
		//Get all the threads
		Squares=Window.Grid;

		//Initialize direction values
		currentDirection = 1;

		//Initialize snake 
		snake = new Snake(3, positionDepart);
		
		//Spawn food
		SpawnFood();

	 }
	 
	 //GAME LOOP
	 //===========================================================================================
	 public void run() {
		 while(gameActive){
			 PollInput();
			 Update();
			 Draw();
			 Wait();
		 }
	 }
	 
	 //POLL INPUT - Checks user input
	 //===========================================================================================
	 private void PollInput()
	 {
		//If input direction is opposite to current direction, dont update
		if(
			(currentDirection == 1 && inputDirection == 2) ||
			(currentDirection == 2 && inputDirection == 1) ||
			(currentDirection == 3 && inputDirection == 4) ||
			(currentDirection == 4 && inputDirection == 3)
		) return;

		currentDirection = inputDirection;
	 }
	 
	 //UPDATE - Update game state (snake position, check collisions, etc)
	 //===========================================================================================
	 private void Update()
	 {
		snake.UpdateSnakePosition(currentDirection);
		CheckCollisions();
	 }

	 //DRAW - Draw game elements
	 //===========================================================================================
	 private void Draw(){

		//Flush screen
		for(int x = 0; x < 20; x ++)
		{
			for (int y = 0; y < 20; y++)
			{
				Squares.get(y).get(x).ChangeColor(EMPTY);
			}
		}

		//Draw snake
		for(SnakeSegment seg : snake.GetSegments())
		{
			int posX = seg.GetPos().x;
			int posY = seg.GetPos().y;

			Squares.get(posY).get(posX).ChangeColor(SNAKE);
		}

		//Draw food
		for(Tuple pos : foodPositions)
		{
			Squares.get(pos.y).get(pos.x).ChangeColor(FOOD);
		}
	 }
	
	 //Wait - waiting time between game cycle
	 //===========================================================================================
	 private void Wait(){

		 try {
			 //System.out.printf("Sleeping for %d milliseconds\n", speed);
			 sleep(1000/FPS);
		 } catch (InterruptedException e) {
			e.printStackTrace();
		 }
	 }
	 
	 //CheckCollisions - Checking if the snake bites itself or is eating
	 //===========================================================================================
	 private void CheckCollisions() {
		
		//check for food collisions
		//-------------------------
		if(foodPositions.contains(snake.GetHeadPos()))
		{
			foodPositions.remove(snake.GetHeadPos());

			snake.IncreaseSnakeSize();

		 	SpawnFood();
		}
		
		//check for self-collisions
		//-------------------------
		if(snake.SelfCollisionCheck()) GameOver();

	 }

	//GameOver - sets game active state to false
	//===========================================================================================
	 private void GameOver(){

		 gameActive = false;
	 }
	 
	 //SpawnFood - Spawn food at a random empty spot, then return its position
	 //===========================================================================================
	 private Tuple SpawnFood()
	 {
		Tuple foodPosition = GetEmptyCoords();
		foodPositions.add(foodPosition);

		return foodPosition;
	 }
	 
	 //GetEmptyCoords - returns a position not occupied by the snake
	 //===========================================================================================
	 //NEEDS FIXING: right now this returns a spot not occupied by food, not by the snake.
	 private Tuple GetEmptyCoords(){
		 Tuple p ;
		 int ranX= 0 + (int)(Math.random()*19); 
		 int ranY= 0 + (int)(Math.random()*19); 
		 p=new Tuple(ranX,ranY);

		while(foodPositions.contains(p))	//EMPTY = 2
		{
			ranX= 0 + (int)(Math.random()*19); 
		 	ranY= 0 + (int)(Math.random()*19); 
		 	p=new Tuple(ranX,ranY);
		}

		 return p;
	 }

}
