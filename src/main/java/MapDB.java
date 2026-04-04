package rework;

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
		squareMap.add(new Tuple(5, 15));
		squareMap.add(new Tuple(15, 5));
		squareMap.add(new Tuple(15, 15));
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
