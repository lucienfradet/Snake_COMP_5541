package db;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Pattern;

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
      case "gameId"           -> Comparator.comparingInt(UserData::getGameId);  
        default             -> Comparator.comparingInt(UserData::getGameId);
    };
  }

  private UserData[] searchUsername(UserData[] userDatas, String searchPattern) {
    // Convert wildcard pattern to regex (e.g., "joh*" -> "joh.*")
    String regexPattern = ".*" + Pattern.quote(searchPattern) + ".*";
    Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);

    // Filter matching usernames
    List<UserData> matches = new ArrayList<>();
    for (UserData userData : userDatas) {
      if (pattern.matcher(userData.getUsername()).matches()) {
        matches.add(userData);
      }
    }

    // Convert list to array and return
    return matches.toArray(new UserData[0]);
  }
}
