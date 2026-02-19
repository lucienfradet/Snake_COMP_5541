import java.util.ArrayList;

public class Snake {
    
    //Snake body
    //==========
    public ArrayList<SnakeSegment> segments = new ArrayList<SnakeSegment>();
	public SnakeSegment snakeHead;
    private int size;


    //Constructor
    //===========
    Snake(int initSize, Tuple initPosition)
    {
        //initialize variables
        size = initSize;

        for(int i = 0; i < size; i++)
		{
			Tuple pos = new Tuple(initPosition.getX(), initPosition.getY() - i);
			segments.add(new SnakeSegment(pos));
		}

		//Set 
		snakeHead = segments.get(0);
    }

    //MoveSnakeBody - Moves all snake segments to the previous segment's last position. Head
	//					is moved to position given as parameter
	//===========================================================================================
	private void MoveSnakeBody(Tuple newHeadPos)
	{
        snakeHead.Move(newHeadPos);

        for(int i = 1; i < segments.size(); i++)
        {
            //set each segment's position to last segment's last position
            SnakeSegment prevSegment = segments.get(i - 1);
            segments.get(i).Move(prevSegment.GetLastPos());
        }
	}

	private void MoveSnakeBody(int x, int y)
	{
        Tuple newHeadPos = new Tuple(x, y);
        MoveSnakeBody(newHeadPos);
	}

    //UpdateSnakePosition - Calls 'MoveSnakeBody' to move snake in a direction given 
	// 						by 'currentDirection'. Deals with "screen wraparound"
	//===========================================================================================
	public void UpdateSnakePosition(int dir){

        switch(dir){
            case 4:
                MoveSnakeBody(snakeHead.GetPos().x,(snakeHead.GetPos().y+1)%20);
                break;
            case 3:
                if(snakeHead.GetPos().y-1<0){
                    MoveSnakeBody(snakeHead.GetPos().x,19);
                }
                else{
                MoveSnakeBody(snakeHead.GetPos().x,Math.abs(snakeHead.GetPos().y-1)%20);
                }
                break;
            case 2:
                if(snakeHead.GetPos().x-1<0){
                    MoveSnakeBody(19,snakeHead.GetPos().y);
                }
                else{
                    MoveSnakeBody(Math.abs(snakeHead.GetPos().x-1)%20,snakeHead.GetPos().y);
                }
                break;
            case 1:
                MoveSnakeBody(Math.abs(snakeHead.GetPos().x+1)%20,snakeHead.GetPos().y);
                break;

            default:
                MoveSnakeBody(snakeHead.GetPos().x,(snakeHead.GetPos().y+1)%20);
                break;
        }
	}

    //IncreaseSnakeSize - increases snake size by adding one snake segment to 'segments'
	//===========================================================================================
	public void IncreaseSnakeSize()
	{
		size = size + 1;

		//add new segment at current last segment's previous position
		Tuple lastPos = segments.getLast().GetLastPos();
		if(lastPos != null)
		{
			segments.add(new SnakeSegment(lastPos));
		}
	}

    //SelfCollisionCheck - check for self-collisions
    //===========================================================================================
    //We start at index 1 because we don't want to compare the head's position against
    //itself
    public boolean SelfCollisionCheck()
    {
		for(int i = 1; i < size; i++)
		{
			if(snakeHead.GetPos().equals(segments.get(i).GetPos()))
			{
				System.out.printf("Collision head and segment #%d\n", i);
                return true;
			}
		}

        return false;
    }

    //Getters
    //===========================================================================================
    public ArrayList<SnakeSegment>  GetSegments()   {return segments;}
    public Tuple                    GetHeadPos()    {return snakeHead.GetPos();}
}
