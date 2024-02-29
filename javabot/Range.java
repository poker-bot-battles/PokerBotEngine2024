import java.util.ArrayList;

public class Range {
  private static String RANKS = "23456789TJQKA";
  private static String SUITS = "cdhs";

  private ArrayList<String> internalRangeList;

  public Range(String rangeString) {
    String[] rangeList = rangeString.split(", ");
    internalRangeList = new ArrayList<String>();
    for(String rangeElem : rangeList) {
      if(rangeElem.charAt(0) == rangeElem.charAt(1) && RANKS.contains( rangeElem.subSequence(0,1))) { // pocket pair
        if(rangeElem.length() == 2) {
          internalRangeList.add(rangeElem);
        }
        else {
          int startIndex = RANKS.indexOf(rangeElem.charAt(0));
          int endIndex;
          if(rangeElem.length() == 3 && rangeElem.charAt(2) == '+') {
            endIndex = 12;
          }
          else if(rangeElem.length() == 5 && rangeElem.charAt(2) == '-' && rangeElem.charAt(3) == rangeElem.charAt(4)) {
            endIndex = RANKS.indexOf(rangeElem.charAt(3));
          } else {
            throw new IllegalArgumentException("Invalid range string");
          }
          String includedRanks = RANKS.substring(startIndex, endIndex + 1);
          for(char rank : includedRanks.toCharArray()) {
            internalRangeList.add(rank + "" + rank);
          }
        }
      }
      else if (RANKS.contains( rangeElem.subSequence(0,1)) && RANKS.contains( rangeElem.subSequence(1,2)) && "os".contains(rangeElem.subSequence(2, 3))) {
        if(rangeElem.length() == 3) {
          internalRangeList.add(rangeElem);
        }
        else {
          int highCardIndex = RANKS.indexOf(rangeElem.charAt(0));
          int startIndex = RANKS.indexOf(rangeElem.charAt(1));
          int endIndex;
          if (rangeElem.length() == 4 && rangeElem.charAt(3) == '+') {
            endIndex = highCardIndex -1;
          } else if (rangeElem.length() == 7 && rangeElem.charAt(3) == '-' && rangeElem.charAt(0) == rangeElem.charAt(4) && RANKS.contains(rangeElem.subSequence(5, 6)) && rangeElem.charAt(6) == rangeElem.charAt(2)) {
            endIndex = RANKS.indexOf(rangeElem.charAt(5));
          } else {
            throw new IllegalArgumentException("Invalid range string");
          }
          String includedRanks = RANKS.substring(startIndex, endIndex + 1);
          for(char rank : includedRanks.toCharArray()) {
            internalRangeList.add(rangeElem.charAt(0) + "" + rank + rangeElem.charAt(2));
          }
        }
      }
      else {
        throw new IllegalArgumentException("Invalid range string");
      }
    }
  }

  public boolean isHandInRange(ArrayList<String> handCards){
    if(handCards.size() != 2){
      throw new IllegalArgumentException("Invalid hand size");
    }
    for (String card : handCards) {
      if(!validateCard(card)) {
        throw new IllegalArgumentException("Invalid card string");
      }
    }
    ArrayList<String> sortedHandCards = new ArrayList<String>(handCards);
    sortedHandCards.sort((a,b) -> (RANKS.indexOf(b.charAt(0)) - RANKS.indexOf(a.charAt(0))));
    String highCard = sortedHandCards.get(0);
    String lowCard = sortedHandCards.get(1);
    String handString = highCard.charAt(0) +""+ lowCard.charAt(0);

    if(highCard.charAt(0) != lowCard.charAt(0)) {
      if (highCard.charAt(1) == lowCard.charAt(1)) {
        handString = handString + "s";
      } else {
        handString = handString + "o";
      }
    }
    return internalRangeList.contains(handString);
  }

  private boolean validateCard(String cardString) {
    if(cardString.length() != 2) {
      return false;
    }
    if(!RANKS.contains(cardString.subSequence(0, 1))) {
      return false;
    }
    if(!SUITS.contains(cardString.subSequence(1, 2))) {
      return false;
    }
    return true;
  }
}
