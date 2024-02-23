import java.io.IOException;

/**
 * A bot that always bluffs
 *
 * This bot will always raise the maximum amount (going all in if possible)
 */
public class BluffBot  {
     public static void main(String[] args) throws IOException {
        // DO NOT REMOVE
        Observable obs = Observable.fromJson(args[args.length - 1]);

        System.out.println(obs.getMaxRaise());
    }

 }