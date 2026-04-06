package game;

import java.util.HashMap;
import java.util.ArrayList;

public class MapDB {

  public HashMap<Integer, ArrayList<Tuple>> maps = new HashMap<>();

  //Constructor - Fill in the hashmap (In the future it might be worth looking into doing this by parsin json files)
  //===========================================================================================
  MapDB()
  {
    //Square map - Index 0
    ArrayList<Tuple> squareMap = new ArrayList<Tuple>();
    squareMap.add(new Tuple(5, 5));
    squareMap.add(new Tuple(6, 5));
    squareMap.add(new Tuple(5, 6));

    squareMap.add(new Tuple(5, 15));
    squareMap.add(new Tuple(6, 15));
    squareMap.add(new Tuple(5, 14));

    squareMap.add(new Tuple(15, 5));
    squareMap.add(new Tuple(15, 6));
    squareMap.add(new Tuple(14, 5));
    
    squareMap.add(new Tuple(15, 15));
    squareMap.add(new Tuple(15, 14));
    squareMap.add(new Tuple(14, 15));
    maps.put(0, new ArrayList<Tuple>(squareMap));

    //Walls map - Index 1
    ArrayList<Tuple> wallsMap = new ArrayList<Tuple>();
    wallsMap.add(new Tuple(5, 6));
    wallsMap.add(new Tuple(5, 7));
    wallsMap.add(new Tuple(5, 8));
    wallsMap.add(new Tuple(5, 9));
    wallsMap.add(new Tuple(5, 10));
    wallsMap.add(new Tuple(5, 11));
    wallsMap.add(new Tuple(5, 12));
    wallsMap.add(new Tuple(5, 13));
    wallsMap.add(new Tuple(15, 6));
    wallsMap.add(new Tuple(15, 7));
    wallsMap.add(new Tuple(15, 8));
    wallsMap.add(new Tuple(15, 9));
    wallsMap.add(new Tuple(15, 10));
    wallsMap.add(new Tuple(15, 11));
    wallsMap.add(new Tuple(15, 12));
    wallsMap.add(new Tuple(15, 13));
    maps.put(1, new ArrayList<Tuple>(wallsMap));


    //Walls map - Index 1
    ArrayList<Tuple> eMap = new ArrayList<Tuple>();
    eMap.add(new Tuple(5, 9));
    eMap.add(new Tuple(6, 9));
    eMap.add(new Tuple(7, 9));
    eMap.add(new Tuple(8, 9));
    eMap.add(new Tuple(9, 9));
    eMap.add(new Tuple(10, 9));
    eMap.add(new Tuple(11, 9));
    eMap.add(new Tuple(12, 9));
    eMap.add(new Tuple(13, 9));
    eMap.add(new Tuple(14, 9));
    eMap.add(new Tuple(5, 5));
    eMap.add(new Tuple(6, 5));
    eMap.add(new Tuple(7, 5));
    eMap.add(new Tuple(8, 5));
    eMap.add(new Tuple(9, 5));
    eMap.add(new Tuple(10, 5));
    eMap.add(new Tuple(11, 5));
    eMap.add(new Tuple(12, 5));
    eMap.add(new Tuple(13, 5));
    eMap.add(new Tuple(14, 5));
    eMap.add(new Tuple(5, 14));
    eMap.add(new Tuple(6, 14));
    eMap.add(new Tuple(7, 14));
    eMap.add(new Tuple(8, 14));
    eMap.add(new Tuple(9, 14));
    eMap.add(new Tuple(10, 14));
    eMap.add(new Tuple(11, 14));
    eMap.add(new Tuple(12, 14));
    eMap.add(new Tuple(13, 14));
    eMap.add(new Tuple(14, 14));
    eMap.add(new Tuple(5, 13));
    eMap.add(new Tuple(5, 12));
    eMap.add(new Tuple(5, 11));
    eMap.add(new Tuple(5, 10));
    eMap.add(new Tuple(5, 8));
    eMap.add(new Tuple(5, 7));
    eMap.add(new Tuple(5, 6));
    maps.put(3, new ArrayList<Tuple>(eMap));
  }

  //GetArrayList - Get a particular map's Tuple collection
  //===========================================================================================
  public ArrayList<Tuple> GetArrayList(int key)
  {
    ArrayList<Tuple> toReturn = maps.get(key);

    //If key does not exist return empty array
    if(toReturn != null)
    {
      return toReturn;     
    } 
    else 
    {
      System.out.printf("No map with Index %d was found. Empty map loaded instead.\n", key);
      return new ArrayList<Tuple>();
    } 
  }
}
