import java.util.ArrayList;
/**
 *   This bot calculates the odds it is given to call and uses this to determine how good it's hand needs to be to call.
 *   If nobody has raised before, this bot uses some simple logic to determine if it should raise or not.
 *
 *   "open" == first raise in round
 */
public class OddsBot  {
  public static final String BOT_NAME = "Odds bot";

  private static Range top20 = new Range("55+, A3s+, K7s+, Q8s+, J9s+, T9s, A9o+, KTo+, QJo");
  private static Range top16 = new Range("66+, A5s+, K9s+, Q9s+, JTs, ATo+, KJo+, QJo");
  private static Range top10 = new Range("77+, A9s+, KTs+, QJs, AJo+, KQo");
  private static Range top6 = new Range("88+, ATs+, KJs+, AKo");

  public static boolean noRaises(Observable obs) {
    ArrayList<ActionInfo> actions = obs.getActionsThisRound();
    for (ActionInfo action : actions) {
        if (action.getAction() > 2) {
            return false;
        }
    }
    return true;
  }

  public static boolean isCardRankInHand(char cardRank, ArrayList<String> hand) {
    return hand.get(0).charAt(0) == cardRank || hand.get(1).charAt(0) == cardRank;
  }

  public static boolean isHandAceOrBetter(Observable obs) {
    HandType myHandType = obs.getMyHandType();
    return myHandType.getValue() >= HandType.PAIR.getValue() || isCardRankInHand('A', obs.getMyHand());
  }

  public static int doPreflopOpen(Observable obs) {
    if (top20.isHandInRange(obs.getMyHand())) {
      return obs.getFractionPotRaise(1);
    } else {
      return 1;
    }
  }

  public static int doPreflopResponse(Observable obs) {
    float callOdds = (float) obs.getCallSize() / obs.getPotSize();
    Range newRange = top6;
    if (callOdds < 0.1){
      return 1;
    }
    else if (callOdds < 0.3) {
      newRange = top16;
    }
    else if (callOdds < 0.6) {
      newRange = top10;
    }
    // If callOdds > 0.6, use top6
    if (newRange.isHandInRange(obs.getMyHand())) {
      return 1;
    } else {
      return 0;
    }
  }

  public static int doPreflop(Observable obs) {
    if (noRaises(obs)){
      return doPreflopOpen(obs);
    } else {
      return doPreflopResponse(obs);
    }
  }

  public static int doPostFlopOpen(Observable obs) {
    if (isHandAceOrBetter(obs)) {
      return obs.getFractionPotRaise(1);
    } else {
      return 0;
    }
  }

  public static int doPostFlopResponse(Observable obs) {
    float callOdds = (float) obs.getCallSize() / obs.getPotSize();
    HandType myHandType = obs.getMyHandType();
    if (callOdds < 0.1) {
      return 1;
    } else if(callOdds < 0.3) {
      if (myHandType.getValue() >= HandType.PAIR.getValue() && myHandType.getValue() > obs.getBoardHandType().getValue()) {
        return 1;
      }
    } else if(callOdds < 0.6) {
      if (myHandType.getValue() >= HandType.PAIR.getValue() && myHandType.getValue() > obs.getBoardHandType().getValue()+1) {
        return 1;
      }
    } else {
      if (myHandType.getValue() >= HandType.TWO_PAIR.getValue() && myHandType.getValue() > obs.getBoardHandType().getValue()+1) {
        return 1;
      }
    }
    return 0;
  }

  public static int doPostflop(Observable obs) {
    if(obs.getCallSize() == 0) {
      return doPostFlopOpen(obs);
    } else {
      return doPostFlopResponse(obs);
    }
  }
  public static int act(Observable obs) throws Exception {

    if (obs.getCurrentRound() == 0) {
      return doPreflop(obs);
    } else {
      return doPostflop(obs);
    }

  }
}