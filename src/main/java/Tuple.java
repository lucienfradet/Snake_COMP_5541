package rework;

public class Tuple { 
	  public  int x; 
	  public  int y; 
	  
	  public Tuple(int x, int y) { 
	    this.x = x; 
	    this.y = y; 
	  } 
	  public void ChangeData(int x, int y){
		    this.x = x; 
		    this.y = y; 
	  }
	  public int getX(){
		  return x;
	  }
	  public int getY(){
		  return y;
	  }
		  
	//For use as a hashmap key
	//========================
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;

		Tuple other = (Tuple)obj;
		return this.x == other.x && this.y == other.y;
	}
	
	@Override
	public int hashCode()
	{
		return 31 * x + y;
	}
} 