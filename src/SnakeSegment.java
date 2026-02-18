public class SnakeSegment {
    private Tuple position;
    private Tuple lastPosition;

    //Constructor
    public SnakeSegment(Tuple pos)
    {
        position = pos;
    }

    //Move
    public void Move(Tuple newPos)
    {
        lastPosition = position;
        position = newPos;
    }

    //Get position
    public Tuple GetPos()       { return position; }
    public Tuple GetLastPos()   { return lastPosition; }
}
