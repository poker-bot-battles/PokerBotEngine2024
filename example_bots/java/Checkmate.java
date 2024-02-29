import java.util.ArrayList;

public class Checkmate  {
    public static final String BOT_NAME = "Checkmate";

    public static boolean noRaises(Observable obs) {
        ArrayList<ActionInfo> actions = obs.getActionsThisRound();
        for (ActionInfo action : actions) {
            if (action.getAction() > 1) {
                return false;
            }
        }
        return true;
    }

    public static int act(Observable obs) throws Exception {

        if(noRaises(obs)) {
            return obs.getMinRaise(); //attempt to steal the pot
        } else if (obs.getCurrentRound() == 0) {
            return 0; // Previous players have raised preflop, steal a later round
        } else {
            return 1; // Call and steal later
        }

     }
 }
