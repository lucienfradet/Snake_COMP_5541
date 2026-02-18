import java.util.ArrayList;
import java.util.HashSet;

//import javax.swing.text.Segment;


//Controls all the game logic .. most important class in this project.
public class ThreadsController extends Thread {

	//Game Settings
	//=============
	public Boolean gameActive = true;
	public static int inputDirection;
	private int currentDirection;		//1:right 2:left 3:top 4:bottom 0:nothing
	public int FPS = 10;

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
	public ArrayList<SnakeSegment> segments = new ArrayList<SnakeSegment>();
	public SnakeSegment head;
	int sizeSnake=3;

	//Constructor
	//===========================================================================================
	ThreadsController(Tuple positionDepart){
		//Get all the threads
		Squares=Window.Grid;

		//Initialize direction values
		currentDirection = 1;

		//Initialize snake body
		for(int i = 0; i < sizeSnake; i++)
		{
			Tuple pos = new Tuple(positionDepart.getX(), positionDepart.getY() - i);
			segments.add(new SnakeSegment(pos));
		}

		//Set 
		head = segments.get(0);
		
		Tuple foodPosition= new Tuple(Window.height-1,Window.width-1);
		SpawnFood(foodPosition);

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
		UpdateSnakePosition(currentDirection);
		checkCollision();
	 }

	 //DRAW - Draw game elements
	 //===========================================================================================
	 private void Draw(){

		//Flush screen
		for(int x = 0; x < 20; x ++)
		{
			for (int y = 0; y < 20; y++)
			{
				Squares.get(y).get(x).lightMeUp(EMPTY);
			}
		}

		//Draw snake
		for(SnakeSegment seg : segments)
		{
			int posX = seg.GetPos().x;
			int posY = seg.GetPos().y;

			Squares.get(posY).get(posX).lightMeUp(SNAKE);
		}

		//Draw food
		for(Tuple pos : foodPositions)
		{
			Squares.get(pos.y).get(pos.x).lightMeUp(FOOD);
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
	 private void checkCollision() {
		
		//check for food collisions
		//-------------------------
		if(foodPositions.contains(head.GetPos()))
		{
			foodPositions.remove(head.GetPos());

			IncreaseSnakeSize();
		 	Tuple foodPosition = GetEmptyCoords();

		 	SpawnFood(foodPosition);
		}
		
		//check for self-collisions
		//-------------------------
		//We start at index 1 because we don't want to compare the head's position against
		//itself
		for(int i = 1; i < sizeSnake; i++)
		{
			if(head.GetPos().equals(segments.get(i).GetPos()))
			{
				System.out.printf("Collision head and segment #%d\n", i);
				GameOver();
			}
		}

	 }
	
	//IncreaseSnakeSize - increases snake size by adding one snake segment to 'segments'
	//===========================================================================================
	public void IncreaseSnakeSize()
	{
		sizeSnake = sizeSnake + 1;

		//add new segment at current last segment's previous position
		Tuple lastPos = segments.getLast().GetLastPos();
		if(lastPos != null)
		{
			segments.add(new SnakeSegment(lastPos));
		}
	}

	//GameOver - sets game active state to false
	//===========================================================================================
	 private void GameOver(){

		 gameActive = false;
	 }
	 
	 //SpawnFood - Put food in a position and displays it
	 //===========================================================================================
	 private void SpawnFood(Tuple foodPositionIn){

			foodPositions.add(foodPositionIn);
	 }
	 
	 //GetEmptyCoords - returns a position not occupied by the snake
	 //===========================================================================================
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
	 
	 //MoveSnakeBody - Moves all snake segments to the previous segment's last position. Head
	 //					is moved to position given as parameter
	 //===========================================================================================
	 public void MoveSnakeBody(Tuple newHeadPos)
	 {
		head.Move(newHeadPos);

		for(int i = 1; i < segments.size(); i++)
		{
			//set each segment's position to last segment's last position
			SnakeSegment prevSegment = segments.get(i - 1);
			segments.get(i).Move(prevSegment.GetLastPos());
		}
	 }

	 public void MoveSnakeBody(int x, int y)
	 {
		Tuple newHeadPos = new Tuple(x, y);
		MoveSnakeBody(newHeadPos);
	 }

	 //UpdateSnakePosition - Calls 'MoveSnakeBody' to move snake in a direction given 
	 // 						by 'currentDirection'. Deals with "screen wraparound"
	 //===========================================================================================
	 private void UpdateSnakePosition(int dir){

		 switch(dir){
		 	case 4:
				 MoveSnakeBody(head.GetPos().x,(head.GetPos().y+1)%20);
		 		break;
		 	case 3:
		 		if(head.GetPos().y-1<0){
		 			 MoveSnakeBody(head.GetPos().x,19);
		 		 }
		 		else{
				 MoveSnakeBody(head.GetPos().x,Math.abs(head.GetPos().y-1)%20);
		 		}
		 		break;
		 	case 2:
		 		 if(head.GetPos().x-1<0){
		 			 MoveSnakeBody(19,head.GetPos().y);
		 		 }
		 		 else{
		 			 MoveSnakeBody(Math.abs(head.GetPos().x-1)%20,head.GetPos().y);
		 		 }
		 		break;
		 	case 1:
				 MoveSnakeBody(Math.abs(head.GetPos().x+1)%20,head.GetPos().y);
		 		 break;

			default:
				MoveSnakeBody(head.GetPos().x,(head.GetPos().y+1)%20);
		 		break;
		 }
	 }
	 

	 
}
