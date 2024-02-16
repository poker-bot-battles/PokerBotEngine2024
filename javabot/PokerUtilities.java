enum HandType {
  ERROR (0),
  HIGH_CARD (1),
  PAIR (2),
  TWO_PAIR (3),
  THREE_OF_A_KIND (4),
  STRAIGHT (5),
  FLUSH (6),
  FULL_HOUSE (7),
  FOUR_OF_A_KIND (8),
  STRAIGHT_FLUSH (9 );

  private final int value;
  HandType(int value){
    this.value = value;
  }

  public int getValue(){
    return value;
  }
}

public class PokerUtilities {


  public static HandType handtypeStringToEnum(String handtypeString) {
    String handtypeStringLower = handtypeString.toLowerCase();
    switch (handtypeStringLower) {
      case "highcard":
        return HandType.HIGH_CARD;
      case "pair":
        return HandType.PAIR;
      case "twopair":
        return HandType.TWO_PAIR;
      case "trips":
        return HandType.THREE_OF_A_KIND;
      case "straight":
        return HandType.STRAIGHT;
      case "flush":
        return HandType.FLUSH;
      case "fullhouse":
        return HandType.FULL_HOUSE;
      case "quads":
        return HandType.FOUR_OF_A_KIND;
      case "straightflush":
        return HandType.STRAIGHT_FLUSH;
      default:
        return HandType.ERROR;
    }
  }
}
