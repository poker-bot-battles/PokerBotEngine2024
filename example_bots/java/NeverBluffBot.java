import java.io.IOException;

/**
 * A bot that never bluffs
 *
 * This bot will only raise if it has a better hand than the board
 * otherwise it will call
 */
public class NeverBluffBot {
    public static final String BOT_NAME = "Never bluff bot";

    public static void main(String[] args) throws IOException {
        // DO NOT REMOVE
        Observable obs = Observable.fromJson(args[args.length - 1]);

        HandType myHand = obs.getMyHandType();
        HandType boardHand = obs.getBoardHandType();

        if (myHand.getValue() > boardHand.getValue()) {
            System.out.println(obs.getMinRaise());
        } else {
            System.out.println(1);
        }
    }

}