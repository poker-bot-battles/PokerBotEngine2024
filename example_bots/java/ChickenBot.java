/**
 * A bot that always chickens out
 *
 * This bot checks if the current call size is greater than 0,
 * that is, it checks if another player has raised the pot.
 * If that is the case, it chickens out and folds!
 */
public class ChickenBot {
    public static final String BOT_NAME = "Chicken bot";

    public static int act(Observable obs) throws Exception {
        int amountToCall = obs.getCallSize();
        if (amountToCall > 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
