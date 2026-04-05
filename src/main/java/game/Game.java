package game;
import app.Main;
import screens.ScreenGame;
import screens.ScreenManager;

//Controls all the game logic .. most important class in this project.
public class Game extends Thread {
	//Game Settings
	//=============
	private ScreenGame gameScreen;
	public Boolean gameActive = true;	//false value ends the game
	public Boolean gamePaused = false;
	public int mapSelection;
	public int FPS;
  	public static final int START_SNAKE_LENGTH = 3;
	public Tuple positionDepart;
	public static boolean pausePressed;
	private final Runnable onGameOver;
	private int score = 0;

	//Input
	//=====
	public static int inputDirection;
	private int currentDirection;		//1:right 2:left 3:top 4:bottom 0:nothing

	//Managers
	//========
	private TileManager tileManager;
	private Timer timer;

	//Constructor
	//===========================================================================================
	public Game(
		int mapSelection, 
		Tuple positionDepart, 
		int fps, 
		ScreenGame gameScreen, 
		Runnable onGameOver){
		//Get all the threads
		//Squares=ScreenGame.Grid;
		// gameScreen = Window.gScreen;
		this.gameScreen = gameScreen;
		this.onGameOver = onGameOver;

		//Initialize direction values
		inputDirection = 4;
		currentDirection = 4;
		FPS = fps >= 1 ? fps : 1;		//minimum fps value = 1
		FPS = FPS <= 120 ? FPS : 120;	//maximum fps value = 120

		//Initialize TileManager
		this.mapSelection = mapSelection;
		this.positionDepart = positionDepart;
		tileManager = new TileManager(this.mapSelection, this.positionDepart);
		
		gameScreen.UpdateWallPos(tileManager.GetWallPositions());	//Wall positions are only updated once
		
		//Spawn first food
		tileManager.SpawnFood();

		//Start game timer
		timer = new Timer();
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
		//Check if pause was pressed
		if(pausePressed)
		{
			PauseToggle();
			pausePressed = false;
		}

		if(gamePaused) return;

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
		if(gamePaused) return;
		tileManager.GetSnake().UpdateSnakePosition(currentDirection);
		CheckCollisions();

		//update userData
		Main.loginUser.setGameTime(timer.GetAccumulatedTime());
		Main.loginUser.setScore(score);
		Main.loginUser.setSnakeLength(tileManager.GetSnake().GetSize());

	 }

	 //DRAW - Draw game elements
	 //===========================================================================================
	 private void Draw()
	 {
		if(gamePaused) return;
		gameScreen.UpdateSnakePos(tileManager.GetSnake());
		gameScreen.UpdateFoodPos(tileManager.GetFoodPos());
		gameScreen.repaint();
	 }
	
	 //RELOAD - Refresh all game data
	 //===========================================================================================
	 public void Reload()
	 {
		tileManager = new TileManager(this.mapSelection, this.positionDepart);
		
		gameScreen.UpdateWallPos(tileManager.GetWallPositions());	//Wall positions are only updated once
		
		//Spawn first food
		tileManager.SpawnFood();
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
		if(tileManager.CheckFoodCollisions()) score++;
		
		//check for self-collisions
		if(tileManager.GetSnake().SelfCollisionCheck()) GameOver();

		//check for wall collisions
		if(tileManager.CheckWallCollisions()) 
		{
			System.out.println("Collided with a wall!");
			GameOver();
		}
	 }

	//GameOver 
	//===========================================================================================
	//Ends game
	private void GameOver(){
		
		System.out.printf("Game Over.\nPlayer for: %.3f seconds.\nFinal score: %d", 
		(float)timer.GetAccumulatedTime()/1000.0f,
		Main.loginUser.getScore());
		
		gameActive = false;
		onGameOver.run();
	}
	
	//PauseToggle
	//===========================================================================================
	//sets gameActive status to false and stops timer if game is not paused
	//Sets gameActive status to true and resumes timer if game is paused
	public void PauseToggle()
	{
		if(!gamePaused)
		{
			gamePaused = true;
			timer.StopTimer();
			ScreenManager.getInstance().showScreen(ScreenManager.PAUSE);
		}
		else
		{
			gamePaused = false;
			timer.StartTimer();
			ScreenManager.getInstance().showScreen(ScreenManager.GAME);
		}
	}

	//Getters
	//===========================================================================================
	public ScreenGame GetScreenGame() {return gameScreen;}
	public long GetAccumulatedTime() {return timer.GetAccumulatedTime();}
}
