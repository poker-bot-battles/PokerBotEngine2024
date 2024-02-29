public class HardcoreAIBot  {
    public static final String BOT_NAME = "Hardcore AI Bot";

    public static int act(Observable obs) throws Exception {
        int randomInt = (int)(Math.random() * 3);
        Range decentHand = new Range("77+, A8s+, K9s+, QTs+, AJo+, KQo");

        if(randomInt == 0){
            // Will 'call' if bot has a decent hand, else it will 'fold'
            if(decentHand.isHandInRange(obs.getMyHand())){
                return 1;
            } else {
                return 0;
            }
        }
        else if(randomInt == 1) {
            // Will go 'all in' if the round is 4, else it will 'call'
            if(obs.getCurrentRound() == 4){
                return obs.getMaxRaise();
            } else {
                return 1;
            }
        }
        else {
            if(obs.getMyHandType().equals(HandType.STRAIGHT_FLUSH)){
                // If handtype is a straightflush then 'all in'
                return obs.getMaxRaise();

            } else if(Math.random() > 0.99){
                // Are we feeling lucky? If so, 'all in'
                return obs.getMaxRaise();

            } else {
                // Otherwise 'call'
                return 1;
            }
        }
    }

 }