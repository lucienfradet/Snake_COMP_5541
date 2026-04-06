package game;

import java.util.HashMap;
import java.util.ArrayList;

public class MapDB {

  public HashMap<Integer, ArrayList<Tuple>> maps = new HashMap<>();

  //Constructor - Fill in the hashmap (In the future it might be worth looking into doing this by parsin json files)
  //===========================================================================================
  MapDB()
  {
    //Blank map - index 0
    ArrayList<Tuple> blankMap = new ArrayList<Tuple>();
    maps.put(0, new ArrayList<Tuple>(blankMap));

    //Square map - index 1
    ArrayList<Tuple> squareMap = new ArrayList<Tuple>();
    squareMap.add(new Tuple(4, 5));
    squareMap.add(new Tuple(5, 5));
    squareMap.add(new Tuple(6, 5));
    squareMap.add(new Tuple(5, 6));
    squareMap.add(new Tuple(5, 4));
    squareMap.add(new Tuple(4, 15));
    squareMap.add(new Tuple(5, 15));
    squareMap.add(new Tuple(6, 15));
    squareMap.add(new Tuple(5, 14));
    squareMap.add(new Tuple(5, 16));
    squareMap.add(new Tuple(15, 4));
    squareMap.add(new Tuple(15, 5));
    squareMap.add(new Tuple(15, 6));
    squareMap.add(new Tuple(14, 5));
    squareMap.add(new Tuple(16, 5));
    squareMap.add(new Tuple(15, 14));
    squareMap.add(new Tuple(15, 16));
    squareMap.add(new Tuple(15, 15));
    squareMap.add(new Tuple(14, 15));
    squareMap.add(new Tuple(16, 15));
    maps.put(1, new ArrayList<Tuple>(squareMap));

    //Desert map - Index 2
    ArrayList<Tuple> desertMap = new ArrayList<Tuple>();
    desertMap.add(new Tuple(3, 2));
    desertMap.add(new Tuple(4, 2));
    desertMap.add(new Tuple(5, 2));
    desertMap.add(new Tuple(4, 3));
    desertMap.add(new Tuple(13, 13));
    desertMap.add(new Tuple(14, 12));
    desertMap.add(new Tuple(16, 4));
    desertMap.add(new Tuple(16, 5));
    desertMap.add(new Tuple(4, 15));
    desertMap.add(new Tuple(5, 16));
    desertMap.add(new Tuple(7, 16));
    desertMap.add(new Tuple(12, 18));
    desertMap.add(new Tuple(13, 18));
    maps.put(2, new ArrayList<Tuple>(desertMap));

    //Box map - Index 3
    ArrayList<Tuple> boxMap = new ArrayList<Tuple>();
    for (int x = 0; x < 20; x++) {
      boxMap.add(new Tuple(x, 0));
      boxMap.add(new Tuple(x, 19));
    }
    for (int y = 1; y < 19; y++) {
      boxMap.add(new Tuple(0, y));
      boxMap.add(new Tuple(19, y));
    }
    maps.put(3, new ArrayList<Tuple>(boxMap));
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