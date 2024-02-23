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
  STRAIGHT_FLUSH (9);

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
      case "1":
        return HandType.HIGH_CARD;
      case "2":
        return HandType.PAIR;
      case "3":
        return HandType.TWO_PAIR;
      case "4":
        return HandType.THREE_OF_A_KIND;
      case "5":
        return HandType.STRAIGHT;
      case "6":
        return HandType.FLUSH;
      case "7":
        return HandType.FULL_HOUSE;
      case "8":
        return HandType.FOUR_OF_A_KIND;
      case "9":
        return HandType.STRAIGHT_FLUSH;
      default:
        return HandType.ERROR;
    }
  }
}
