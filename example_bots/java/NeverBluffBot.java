/**
 * A bot that never bluffs
 *
 * This bot will only raise if it has a better hand than the board
 * otherwise it will call
 */
public class NeverBluffBot  {
    public static final String BOT_NAME = "Never bluff bot";

    public static int act(Observable obs) throws Exception {
        HandType myHand = obs.getMyHandType();
        HandType boardHand = obs.getBoardHandType();

        if(myHand.getValue() > boardHand.getValue()){
            return obs.getMinRaise();
        } else {
            return 1;
        }
    }

}