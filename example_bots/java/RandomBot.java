/**
 * This bot makes a random number which is either over or under 0.5.
 * If it is over, it will always raise the minimum amount that it possibly can.
 * Otherwise, it attempts to call.
 */
public class RandomBot {
  public static final String BOT_NAME = "Random bot";

  public static int act(Observable obs) throws Exception {
    if (Math.random() > 0.5) {
      return obs.getMinRaise();
    } else {
      return 1;
    }
  }
}
