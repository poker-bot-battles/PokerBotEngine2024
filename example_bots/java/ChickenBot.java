import java.io.IOException;

/**
 * A bot that always chickens out
 *
 * This bot checks if the current call size is greater than 0,
 * that is, it checks if another player has raised the pot.
 * If that is the case, it chickens out and folds!
 */
public class ChickenBot {

     public static void main(String[] args) throws IOException {
        // DO NOT REMOVE
        Observable obs = Observable.fromJson(args[args.length - 1]);

        int amountToCall = obs.getCallSize();
        if (amountToCall > 0) {
            System.out.println(0);
        } else {
            System.out.println(1);
        }
    }
}
