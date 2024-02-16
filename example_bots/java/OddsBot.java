import java.io.IOException;
import java.util.ArrayList;

public class OddsBot  {

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

  public static void doPreflopOpen(Observable obs) {
    if (top20.isHandInRange(obs.getMyHand())) {
      System.out.println(obs.getFractionPotRaise(1));
    } else {
      System.out.println(1);
    }
  }

  public static void doPreflopResponse(Observable obs) {
    float callOdds = (float) obs.getCallSize() / obs.getPotSize();
    Range newRange = top6;
    if (callOdds < 0.1){
      System.out.println(1);
    }
    else if (callOdds < 0.3) {
      newRange = top16;
    }
    else if (callOdds < 0.6) {
      newRange = top10;
    }
    // If callOdds > 0.6, use top6
    if (newRange.isHandInRange(obs.getMyHand())) {
      System.out.println(1);
    } else {
      System.out.println(0);
    }
  }

  public static void doPreflop(Observable obs) {
    if (noRaises(obs)){
      doPreflopOpen(obs);
    } else {
      doPreflopResponse(obs);
    }
  }

  public static void doPostFlopOpen(Observable obs) {
    if (isHandAceOrBetter(obs)) {
      System.out.println(obs.getFractionPotRaise(1));
    } else {
      System.out.println(0);
    }
  }

  public static void doPostFlopResponse(Observable obs) {
    float callOdds = (float) obs.getCallSize() / obs.getPotSize();
    HandType myHandType = obs.getMyHandType();
    if (callOdds < 0.1) {
      System.out.println(1);
    } else if(callOdds < 0.3) {
      if (myHandType.getValue() >= HandType.PAIR.getValue() && myHandType.getValue() > obs.getBoardHandType().getValue()) {
        System.out.println(1);
      }
    } else if(callOdds < 0.6) {
      if (myHandType.getValue() >= HandType.PAIR.getValue() && myHandType.getValue() > obs.getBoardHandType().getValue()+1) {
        System.out.println(1);
      }
    } else {
      if (myHandType.getValue() >= HandType.TWO_PAIR.getValue() && myHandType.getValue() > obs.getBoardHandType().getValue()+1) {
        System.out.println(1);
      }
    }
    System.out.println(0);
  }

  public static void doPostflop(Observable obs) {
    if(obs.getCallSize() == 0) {
      doPostFlopOpen(obs);
    } else {
      doPostFlopResponse(obs);
    }
  }
     public static void main(String[] args) throws IOException {

         Observable obs = Observable.fromJson(args[args.length - 1]);

         if (obs.getCurrentRound() == 0) {
             doPreflop(obs);
         } else {
             doPostflop(obs);
         }

     }
 }