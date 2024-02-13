import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Observable {
  private JsonNode obs;
  public Observable(String[] args){
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      HelperFunctions.test();
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(args[args.length - 1]);
      this.obs = jsonNode;
    }
    catch (Exception e) {
      System.err.println(e);
    }
  }

  public ArrayList<Integer> getValidMoves(){
    return obs.get("legal_actions");
  }

  public ArrayList<String> getMyHand(){
    return obs.get("my_hand");
  }
}
