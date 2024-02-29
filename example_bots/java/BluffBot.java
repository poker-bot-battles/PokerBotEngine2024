/**
 * A bot that always bluffs
 *
 * This bot will always raise the maximum amount (going all in if possible)
 */
public class BluffBot  {
   public static final String BOT_NAME = "Bluff bot";

   public static int act(Observable obs) throws Exception {

      return obs.getMaxRaise();
   }
 }