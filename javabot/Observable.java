import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * A class to represent the state of a player
 * */
class PlayerInfo {
  private int spent;
  private int stack;
  private boolean active;

  /**
   * @return the amount the player has spent in this game
   */
  public int getSpent(){
    return spent;
  }

  /**
   * @return the amount of money the player has left
   */
  public int getStack(){
    return stack;
  }

  /**
   * whether the player is still active in the game
   * @return true if the player has not folded, false otherwise
   */
  public boolean getActive(){
    return active;
  }

  public String toString(){
    return "spent: " + spent + ", stack: " + stack + ", active: " + active;
  }
}

/**
 * A class to represent the action of a player
 */
class ActionInfo {

  private int player;
  private int action;

  /**
   * @return the index of the player taking this action
   */
  public int getPlayer(){
    return player;
  }

  /**
   * @return the action of the player
   */
  public int getAction(){
    return action;
  }

  public String toString(){
    String playerString = "Player " + player + ": ";
    if(action == 0){
      return(playerString+ "Fold");
    }
    if(action == 1){
      return (playerString + "Call");
    }
    return (playerString + "Raise to " + action);
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
  private List<List<ActionInfo>> history;
  private List<String> boardCards;
  private List<PlayerInfo> playerInfos;
  private int currentRound;
  private List<Integer> legalActions;
  private String myHandType;
  private String boardHandType;

  /* ------ GETTERS ------ */
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

  public ArrayList<List<ActionInfo>> getHistory(){
    return new ArrayList<>(history);
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
    return new ArrayList<ActionInfo>(history.get(roundNum));
  }

  public ArrayList<PlayerInfo> getPlayerInfos(){
    return new ArrayList<PlayerInfo>(playerInfos);
  }

  public HandType getMyHandType(){
    return PokerUtilities.handtypeStringToEnum(myHandType);
  }

  public HandType getBoardHandType(){
    return PokerUtilities.handtypeStringToEnum(boardHandType);
  }

  /* ----- DERIVATIVES ----- */
  public PlayerInfo getMyInfo(){
    return playerInfos.get(myIndex);
  }

  public int getPlayerCount(){
    return playerInfos.size();
  }

  public ArrayList<PlayerInfo> getActivePlayers(){
    ArrayList<PlayerInfo> activePlayers = new ArrayList<PlayerInfo>();
    for (PlayerInfo playerInfo : playerInfos){
      if (playerInfo.getActive()){
        activePlayers.add(playerInfo);
      }
    }
    return activePlayers;
  }

  public int getMaxSpent(){
    int maxSpent = 0;
    for (PlayerInfo playerInfo : playerInfos){
      maxSpent = Math.max(maxSpent, playerInfo.getSpent());
    }
    return maxSpent;
  }

  public int getCallSize(){
    return getMaxSpent() - getMyInfo().getSpent();
  }

  public int getPotSize(){
    int potSize = 0;
    for (PlayerInfo playerInfo : playerInfos){
      potSize += playerInfo.getSpent();
    }
    return potSize;
  }

  public boolean canRaise(){
    for (Integer action : legalActions){
      if(action > 1){
        return true;
      }
    }
    return false;
  }

  public int getMinRaise(){
    if (!canRaise()){
      return 1;
    }
    int minRaise = Integer.MAX_VALUE;
    // TODO: Kan vi regne med at actions er sorterede? i så fald er legalActions.get(2) fint nok
    for(int action : legalActions) {
      if(action > 1){
        minRaise = Math.min(minRaise, action);
      }
    }
    return minRaise;
  }

  public int getMaxRaise() {
    if (!canRaise()){
      return 1;
    }
    int maxRaise = 0;
    // TODO: Kan vi regne med at actions er sorterede? i så fald er legalActions.get(legalActions.size()-1) fint nok
    for(int action : legalActions) {
      if(action > 1){
        maxRaise = Math.max(maxRaise, action);
      }
    }
    return maxRaise;
  }

  public int getFractionPotRaise(float fraction) {
    if(!canRaise()) {
      return 1;
    }
    int pot = getPotSize();
    int amountToCall = getCallSize();
    int potWithCall = pot + amountToCall;
    int raiseSize = amountToCall + (int) (fraction * potWithCall);
    int raiseTo = getMyInfo().getSpent() + raiseSize;
    if (raiseTo < getMinRaise()) {
      return getMinRaise();
    }
    if (raiseTo > getMaxRaise()) {
      return getMaxRaise();
    }
    return raiseTo;
  }

  public String toString(){
    return "smallBlind: " + smallBlind + ", bigBlind: " + bigBlind + ",\nmyHand: " + myHand + ", myIndex: " + myIndex + ", boardCards: " + boardCards + ",\nplayerInfos: " + playerInfos + ", currentRound: " + currentRound + ",\nlegalActions: " + legalActions + ",\nmyHandType: " + myHandType + ", boardHandType: " + boardHandType + ", history: " + history;
  }
}

