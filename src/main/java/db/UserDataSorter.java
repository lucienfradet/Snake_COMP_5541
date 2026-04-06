package db;

import java.util.Arrays;
import java.util.Comparator;

public class UserDataSorter {
    private String lastAttribute = null;
    private boolean ascending = true;

    public void sortBy(UserData[] stats, String attribute){
        if(attribute.equals(lastAttribute)) ascending = !ascending;
        else ascending = true;

        lastAttribute = attribute;

        Comparator<UserData> comparator = getComparator(attribute);
        if(!ascending) comparator = comparator.reversed();

        Arrays.sort(stats, comparator);
    }

    private Comparator<UserData> getComparator(String attribute){
        return switch (attribute){
            case "username"     -> Comparator.comparing(UserData::getUsername);
            case "score"        -> Comparator.comparingInt(UserData::getScore);
            case "moves"        -> Comparator.comparingInt(UserData::getTotalMoveCount);
            case "length"       -> Comparator.comparingInt(UserData::getSnakeLength);
            case "time"         -> Comparator.comparingLong(UserData::getGameTime);
            case "difficulty"   -> Comparator.comparing(UserData::getDifficulty);
            case "maze"         -> Comparator.comparingInt(UserData::getMaze);
            case "id"           -> Comparator.comparingInt(UserData::getId);  
            default             -> Comparator.comparingInt(UserData::getId);
        };
    }
}