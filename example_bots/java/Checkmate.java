import java.util.ArrayList;
import java.io.IOException;

public class Checkmate  {
    public static boolean noRaises(Observable obs) {
        ArrayList<ActionInfo> actions = obs.getActionsThisRound();
        for (ActionInfo action : actions) {
            if (action.getAction() > 1) {
                return false;
            }
        }
        return true;
    }
     public static void main(String[] args) throws IOException {

         Observable obs = Observable.fromJson(args[args.length - 1]);

         if(noRaises(obs)) {
            System.out.println(obs.getMinRaise()); //attempt to steal the pot
         } else if (obs.getCurrentRound() == 0) {
            System.out.println(0); // Previous players have raised preflop, steal a later round
         } else {
            System.out.println(1); // Call and steal later
         }

     }
 }
