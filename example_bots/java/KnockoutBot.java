import java.io.IOException;
import java.util.ArrayList;

/**
 * This bot scans the board, for the lowest cash stack,
 * and will raise the pot to match, in an attempt to
 * knock out the player with the lowest stack.
 */
public class KnockoutBot {
 public static void main(String[] args) throws IOException {
        // DO NOT REMOVE
        Observable obs = Observable.fromJson(args[args.length - 1]);
        ArrayList<PlayerInfo> players = obs.getActivePlayers();

        int lowestRaise = Integer.MAX_VALUE;

        for (PlayerInfo player : players) {
            if(player.getStack() > 0) {
                lowestRaise = Math.min(lowestRaise, player.getStack() + player.getSpent());
            }
        }
        int maxRaise = obs.getMaxRaise();

        // if the lowest stack is greater than the max raise, then raise the max raise
        if (lowestRaise > maxRaise) {
            System.out.println(maxRaise);
        } else {
            System.out.println(lowestRaise);
        }

    }
}
