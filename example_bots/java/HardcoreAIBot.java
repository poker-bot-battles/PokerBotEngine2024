import java.io.IOException;

public class HardcoreAIBot  {
     public static void main(String[] args) throws IOException {
        // DO NOT REMOVE
        Observable obs = Observable.fromJson(args[args.length - 1]);

        int randomInt = (int)(Math.random() * 3);
        Range decentHand = new Range("77+, A8s+, K9s+, QTs+, AJo+, KQo");

        if(randomInt == 0){
            // Will 'call' if bot has a decent hand, else it will 'fold'
            if(decentHand.isHandInRange(obs.getMyHand())){
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }
        else if(randomInt == 1) {
            // Will go 'all in' if the round is 4, else it will 'call'
            if(obs.getCurrentRound() == 4){
                System.out.println(obs.getMaxRaise());
            } else {
                System.out.println(1);
            }
        }
        else {
            if(obs.getMyHandType().equals(HandType.STRAIGHT_FLUSH)){
                // If handtype is a straightflush then 'all in'
                System.out.println(obs.getMaxRaise());

            } else if(Math.random() > 0.99){
                // Are we feeling lucky? If so, 'all in'
                System.out.println(obs.getMaxRaise());

            } else {
                // Otherwise 'call'
                System.out.println(1);
            }
        }
    }

 }