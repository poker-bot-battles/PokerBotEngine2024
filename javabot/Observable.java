import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class PlayerInfo {
  private String spent;
  private String stack;
  private String active;
}

public class Observable {
  private String small_blind;
  private String big_blind;
  private List<String> my_hand;
  private String my_index;
  private List<String> board_cards;
  private List<PlayerInfo> player_infos;
  private String current_round;
  private List<String> legal_actions;


  public ArrayList<Integer> getValidMoves(){
    ArrayList<Integer> validMoves = new ArrayList<Integer>();
    for (String action : legal_actions) {
      validMoves.add(Integer.parseInt(action));
    }
    return validMoves;
  }

  public ArrayList<String> getMyHand(){
    return new ArrayList<String>(my_hand);
  }
}
