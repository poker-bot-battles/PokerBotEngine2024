import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;

public class Checkmate {
    // print arg to stdout
    public static void main(String[] args) throws IOException {
        Observable obs = Observable.fromJson(args[args.length - 1]);

        int action = act(obs);
        System.out.println(action);
    }

    public static int act(Observation obs) {
        if (noRaises(obs)) {
            return obs.getMinRaise(); // attempt to steal the pot
        }
        if (obs.getCurrentRound() == 0) {
            return 0; // fold preflop, steal later hand
        } else {
            return 1; // call and steal later round
        }
    }

    public static boolean noRaises(Observation obs) {
        ActionInfo[] actionsThisRound = obs.getActionsThisRound();
        int count = 0;
        for (ActionInfo actionInfo : actionsThisRound) {
            if (actionInfo.getAction() > 1) {
                count++;
            }
        }
        return count == 0;
    }
}