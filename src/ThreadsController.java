import java.util.ArrayList;
import java.util.HashSet;

//import javax.swing.text.Segment;


//Controls all the game logic .. most important class in this project.
public class ThreadsController extends Thread {

	//Game Settings
	//=============
	public Boolean gameActive = true;
	public static int directionSnake ;
	long speed = 1000/2;

	//Cells
	//=====
	//Content states of a cell
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

	 //Constructor of ControlleurThread 
	 ThreadsController(Tuple positionDepart){
		//Get all the threads
		Squares=Window.Grid;
		directionSnake = 1;

		//Initialize snake body
		for(int i = sizeSnake - 1; i >= 0; i--)
		{
			Tuple pos = new Tuple(positionDepart.getX(), positionDepart.getY() - i);
			segments.add(new SnakeSegment(pos));
		}
		head = segments.get(0);
		
		Tuple foodPosition= new Tuple(Window.height-1,Window.width-1);
		spawnFood(foodPosition);

	 }
	 
	 //GAME LOOP
	 //=========
	 public void run() {
		 while(gameActive){
			 PollInput();
			 Update();
			 Draw();
			 pauser();
		 }
	 }
	 
	 //POLL INPUT
	 //==========
	 private void PollInput()
	 {

	 }
	 
	 //UPDATE
	 //======
	 private void Update()
	 {
		UpdateSnakePosition(directionSnake);
		checkCollision();
	 }

	 //delay between each move of the snake
	 private void pauser(){
		 try {
			 //System.out.printf("Sleeping for %d milliseconds\n", speed);
			 sleep(speed);
		 } catch (InterruptedException e) {
				e.printStackTrace();
		 }
	 }
	 
	 //Checking if the snake bites itself or is eating
	 private void checkCollision() {

		//check for food collisions
		//-------------------------
		if(foodPositions.contains(head.GetPos()))	//SquareType.food = 1
		{
			foodPositions.remove(head.GetPos());

			IncreaseSnakeSize();
		 	Tuple foodPosition = getValAleaNotInSnake();

		 	spawnFood(foodPosition);
		}
		
		//check for self-collisions
		//-------------------------
		//We start at index 1 because we don't want to compare the head's position against
		//itself
		for(int i = 1; i < sizeSnake; i++)
		{
			if(head.GetPos() == segments.get(i).GetPos())
			{
				GameOver();
			}
		}

	 }
	 
	public void IncreaseSnakeSize()
	{
		sizeSnake = sizeSnake + 1;

		//set last position cell to type snake
		Tuple lastPos = segments.getLast().GetLastPos();
		if(lastPos != null)
		{
			segments.add(new SnakeSegment(lastPos));
		}
	}

	//Stops The Game
	 private void GameOver(){

		 System.out.println("COLISION! \n");
		 gameActive = false;
	 }
	 
	 //Put food in a position and displays it
	 private void spawnFood(Tuple foodPositionIn){

			foodPositions.add(foodPositionIn);
	 }
	 
	 //return a position not occupied by the snake
	 private Tuple getValAleaNotInSnake(){
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
	 
	 public void moveSnakeBody(Tuple newHeadPos)
	 {
		head.Move(newHeadPos);

		for(int i = 1; i < segments.size(); i++)
		{
			//set each segment's position to last segment's last position
			SnakeSegment prevSegment = segments.get(i - 1);
			segments.get(i).Move(prevSegment.GetLastPos());
		}
	 }

	 public void moveSnakeBody(int x, int y)
	 {
		Tuple newHeadPos = new Tuple(x, y);
		moveSnakeBody(newHeadPos);
	 }

	 //Moves the head of the snake and refreshes the positions in the arraylist
	 //1:right 2:left 3:top 4:bottom 0:nothing
	 private void UpdateSnakePosition(int dir){
		 switch(dir){
		 	case 4:
				 moveSnakeBody(head.GetPos().x,(head.GetPos().y+1)%20);
		 		break;
		 	case 3:
		 		if(head.GetPos().y-1<0){
		 			 moveSnakeBody(head.GetPos().x,19);
		 		 }
		 		else{
				 moveSnakeBody(head.GetPos().x,Math.abs(head.GetPos().y-1)%20);
		 		}
		 		break;
		 	case 2:
		 		 if(head.GetPos().x-1<0){
		 			 moveSnakeBody(19,head.GetPos().y);
		 		 }
		 		 else{
		 			 moveSnakeBody(Math.abs(head.GetPos().x-1)%20,head.GetPos().y);
		 		 }
		 		break;
		 	case 1:
				 moveSnakeBody(Math.abs(head.GetPos().x+1)%20,head.GetPos().y);
		 		 break;

			default:
				moveSnakeBody(head.GetPos().x,(head.GetPos().y+1)%20);
		 		break;
		 }
	 }
	 
	 //Refresh the squares that needs to be 
	 private void Draw(){

		//Update screen
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
	 
}
