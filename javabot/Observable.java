import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


class PlayerInfo {
  private int spent;
  private int stack;
  private boolean active;

  public int getSpent(){
    return spent;
  }

  public int getStack(){
    return stack;
  }

  public boolean getActive(){
    return active;
  }

  public String toString(){
    return "spent: " + spent + ", stack: " + stack + ", active: " + active;
  }
}

@JsonIgnoreProperties(ignoreUnknown = true)
public class Observable {
  public static Observable fromJson(String json) throws IOException{
    return new ObjectMapper().readValue(json, Observable.class);
  }

  private int smallBlind;
  private int bigBlind;
  private List<String> myHand;
  private int myIndex;
  private List<String> boardCards;
  private List<PlayerInfo> playerInfos;
  private String currentRound;
  private List<Integer> legalActions;

  public ArrayList<Integer> getLegalActions(){
    return new ArrayList<Integer>(legalActions);
  }

  public ArrayList<String> getMyHand(){
    return new ArrayList<String>(myHand);
  }

  public ArrayList<String> getBoardCards(){
    return new ArrayList<String>(boardCards);
  }

  public int getSmallBlind(){
    return smallBlind;
  }

  public int getBigBlind(){
    return bigBlind;
  }

  public int getMyIndex(){
    return myIndex;
  }

  public String getCurrentRound(){
    return currentRound;
  }

  public ArrayList<PlayerInfo> getPlayerInfos(){
    return new ArrayList<PlayerInfo>(playerInfos);
  }

  public String toString(){
    return "smallBlind: " + smallBlind + ", bigBlind: " + bigBlind + ", myHand: " + myHand + ", myIndex: " + myIndex + ", boardCards: " + boardCards + ", playerInfos: " + playerInfos + ", currentRound: " + currentRound + ", legalActions: " + legalActions;
  }
}
