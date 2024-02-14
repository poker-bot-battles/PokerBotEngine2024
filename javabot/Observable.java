import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

class ActionInfo {
  private int player;
  private int action;

  public int getPlayer(){
    return player;
  }
  public int getAction(){
    return action;
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
  private int currentRound;
  private List<Integer> legalActions;
  private List<List<ActionInfo>> actionsInRound;

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

  public int getCurrentRound(){
    return currentRound;
  }
  
  public ArrayList<ActionInfo> getActionsThisRound(){
    return getActionsInRound(currentRound);
  }
  public ArrayList<ActionInfo> getActionsInRound(int roundNum){
    if (roundNum > 3 || roundNum < 0) {
      return null;
    }
    return new ArrayList<ActionInfo>(actionsInRound.get(roundNum));
  }

  public ArrayList<PlayerInfo> getPlayerInfos(){
    return new ArrayList<PlayerInfo>(playerInfos);
  }
  
  public String toString(){
    return "smallBlind: " + smallBlind + ", bigBlind: " + bigBlind + ", myHand: " + myHand + ", myIndex: " + myIndex + ", boardCards: " + boardCards + ", playerInfos: " + playerInfos + ", currentRound: " + currentRound + ", legalActions: " + legalActions;
  }
}
