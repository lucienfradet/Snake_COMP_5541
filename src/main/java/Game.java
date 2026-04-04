

import java.util.ArrayList;

//Controls all the game logic .. most important class in this project.
public class Game extends Thread {

	//Game Settings
	//=============
	private ScreenGame gameScreen;
	public Boolean gameActive = true;
	public static int inputDirection;
	public int FPS;
	private int currentDirection;		//1:right 2:left 3:top 4:bottom 0:nothing

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
	//===========================================================================================
	Game(int mapSelection, Tuple positionDepart, int fps){
		//Get all the threads
		//Squares=ScreenGame.Grid;
		gameScreen = Window.gScreen;
		
		//Initialize direction values
		currentDirection = 1;
		FPS = fps >= 1 ? fps : 1;		//minimum fps value = 1
		FPS = FPS <= 120 ? FPS : 120;	//maximum fps value = 120

		//Initialize snake 
		snake = positionDepart == null ? 
			new Snake(3, new Tuple(10, 10)) : 
			new Snake(3, positionDepart);

		//Initialize walls, load desired map layout. Map is selected by index:
		//0 - "Square" Map
		//1 - "Walls" Map
		//else - Empty Map
		wallPositions = maps.GetArrayList(0);
		gameScreen.UpdateWallPos(wallPositions);	//Wall positions are only updated once
		
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

		gameScreen.UpdateSnakePos(snake);
		gameScreen.UpdateFoodPos(foodPositions);
		gameScreen.repaint();
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

		//check for wall collisions
		//-------------------------
		if(wallPositions.contains(snake.GetHeadPos())) 
		{
			System.out.println("Collided with a wall!");
			GameOver();
		}

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

}
