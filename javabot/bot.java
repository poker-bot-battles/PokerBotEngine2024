import java.io.IOException;

public class bot  {
    private static final String BOT_NAME = "Your bot name here!";
     public static void main(String[] args) throws IOException {

        // DO NOT REMOVE
        Observable obs = Observable.fromJson(args[args.length - 1]);

        Range top50 = new Range("22+, A2s+, K2s+, Q2s+, J2s+, T5s+, 96s+, 86s+, 75s+, A2o+, K5o+, Q7o+, J8o+, T8o+");
        HandType type = obs.getMyHandType();

        if (type.getValue() >= HandType.PAIR.getValue()) {
             System.out.println(obs.getMaxRaise());
        } else if (top50.isHandInRange(obs.getMyHand())) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }

 }