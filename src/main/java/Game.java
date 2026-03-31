//Controls all the game logic .. most important class in this project.
public class Game extends Thread {

	//Game Settings
	//=============
	private ScreenGame gameScreen;
	public Boolean gameActive = true;
	public int FPS;

	public static int inputDirection;
	private int currentDirection;		//1:right 2:left 3:top 4:bottom 0:nothing

	//Map
	//===
	private TileManager tileManager;

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

		//Initialize TileManager
		tileManager = new TileManager(mapSelection, positionDepart);
		
		gameScreen.UpdateWallPos(tileManager.GetWallPositions());	//Wall positions are only updated once
		
		//Spawn first food
		tileManager.SpawnFood();
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
		tileManager.GetSnake().UpdateSnakePosition(currentDirection);
		CheckCollisions();
	 }

	 //DRAW - Draw game elements
	 //===========================================================================================
	 private void Draw(){

		gameScreen.UpdateSnakePos(tileManager.GetSnake());
		gameScreen.UpdateFoodPos(tileManager.GetFoodPos());
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
		
		//check food collisions
		tileManager.CheckFoodCollisions();
		
		//check for self-collisions
		if(tileManager.GetSnake().SelfCollisionCheck()) GameOver();

		//check for wall collisions
		if(tileManager.CheckWallCollisions()) 
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

}
