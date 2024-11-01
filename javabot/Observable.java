import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
      return(playerString + "Fold");
    }
    if(action == 1){
      return (playerString + "Call");
    }
    return (playerString + "Raise to " + action);
  }
}

/**
 * A class representing the state of the game
 */
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

  /**
   * @return The cards in the current players hand
   */
  public ArrayList<String> getMyHand(){
    return new ArrayList<String>(myHand);
  }

  /**
   * @return The community cards on the board
   */
  public ArrayList<String> getBoardCards(){
    return new ArrayList<String>(boardCards);
  }

  /**
   * @return The current small blind
   */
  public int getSmallBlind(){
    return smallBlind;
  }

  /**
   * @return The current big blind
   */
  public int getBigBlind(){
    return bigBlind;
  }

  /**
   * @return The history of all actions taken so far grouped by game round
   */
  public ArrayList<List<ActionInfo>> getHistory(){
    return new ArrayList<>(history);
  }

  /**
   * @return The index of the current player out of all players in the game
   */
  public int getMyIndex(){
    return myIndex;
  }

  /**
   * @return The current game round
   */
  public int getCurrentRound(){
    return currentRound;
  }

  /**
   * @return The player info of the current player
   *<p>
   * A PlayerInfo object contains the following fields:<p>
   * <code>spent</code>: the amount the player has spent in this game<p>
   * <code>stack</code>: the amount of money the player has left<p>
   * <code>active</code>: whether the player is still active in the game<p>
   *
   * @return Current state of all players in the game
   * @see PlayerInfo
   */
  public ArrayList<PlayerInfo> getMyPlayerInfo() {
    return new ArrayList<>(playerInfos);
  }

  /**
   * The information about players in the game, as PlayerInfo objects
   *<p>
   * A PlayerInfo object contains the following fields:<p>
   * <code>spent</code>: the amount the player has spent in this game<p>
   * <code>stack</code>: the amount of money the player has left<p>
   * <code>active</code>: whether the player is still active in the game<p>
   *
   * @return Current state of all players in the game
   * @see PlayerInfo
   */
  public ArrayList<PlayerInfo> getPlayerInfos(){
    return new ArrayList<PlayerInfo>(playerInfos);
  }

  /**
   * @return The type of the current players hand
   */
  public HandType getMyHandType(){
    return PokerUtilities.handtypeStringToEnum(myHandType);
  }

  /**
   * @return The hand type of the community cards on the board
   */
  public HandType getBoardHandType(){
    return PokerUtilities.handtypeStringToEnum(boardHandType);
  }

  /* ----- DERIVATIVES ----- */

  /**
   * information about the current player as PlayerInfo objects
   *<p>
   * A PlayerInfo object contains the following fields:<p>
   * <code>spent</code>: the amount the player has spent in this game<p>
   * <code>stack</code>: the amount of money the player has left<p>
   * <code>active</code>: whether the player is still active in the game<p>
   * @return PlayerInfo about the current player
   * @see PlayerInfo
   */
  public PlayerInfo getMyInfo(){
    return playerInfos.get(myIndex);
  }

  /**
   * @return The number of players in the game
   */
  public int getPlayerCount(){
    return playerInfos.size();
  }

  /**
   * @return The players that are active in the hand (have not folded)
   */
  public ArrayList<PlayerInfo> getActivePlayers(){
    ArrayList<PlayerInfo> activePlayers = new ArrayList<PlayerInfo>();
    for (PlayerInfo playerInfo : playerInfos){
      if (playerInfo.getActive()){
        activePlayers.add(playerInfo);
      }
    }
    return activePlayers;
  }

  /**
   * @return the ActionInfo's from the current round
   * @see ActionInfo
   */
  public ArrayList<ActionInfo> getActionsThisRound(){
    return getActionsInRound(currentRound);
  }

  /**
   * @param roundNum the round number to get the actions from (0,1,2 or 3)
   * @return the ActionInfo's from the specified round
   * @see ActionInfo
   */
  public ArrayList<ActionInfo> getActionsInRound(int roundNum){
    if (roundNum > 3 || roundNum < 0) {
      return null;
    }
    return new ArrayList<ActionInfo>(history.get(roundNum));
  }

  /**
   * @return The max amount of money spent from any player this game
   */
  public int getMaxSpent(){
    int maxSpent = 0;
    for (PlayerInfo playerInfo : playerInfos){
      maxSpent = Math.max(maxSpent, playerInfo.getSpent());
    }
    return maxSpent;
  }

  /**
   * @return The amount of money needed to call the current bet
   */
  public int getCallSize(){
    return getMaxSpent() - getMyInfo().getSpent();
  }

  /**
   * @return The amount of money in the pot
   */
  public int getPotSize(){
    int potSize = 0;
    for (PlayerInfo playerInfo : playerInfos){
      potSize += playerInfo.getSpent();
    }
    return potSize;
  }

  /**
   * @return true if the current player can raise, false otherwise
   */
  public boolean canRaise(){
    for (Integer action : legalActions){
      if(action > 1){
        return true;
      }
    }
    return false;
  }

  /**
   * @return The minimum amount the current player can raise. Will return 1 (call) if the current player cannot raise
   */
  public int getMinRaise(){
    if (!canRaise()){
      return 1;
    }
    for (Integer action : legalActions){
      if(action > 1){
        return action;
      }
    }
    return 1; // should never reach here
  }

  /**
   * @return The maximum amount the current player can raise (all in). Will return 1 (call) if the current player cannot raise
   */
  public int getMaxRaise() {
    if (!canRaise()){
      return 1;
    }
    return legalActions.get(legalActions.size()-1);
  }

  /**
   * @param fraction The relative size of the pot to raise
   * @return The amount to raise to, to raise the specified fraction in relation pot
   */
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

