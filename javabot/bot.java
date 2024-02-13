import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;

public class bot {
   // print arg to stdout
    public static void main(String[] args) throws IOException {

        Observable obs = Observable.fromJson(args[args.length - 1]);

        ArrayList<Integer> validMoves = obs.getLegalActions();
        Random random = new Random();
        int randomIndex = random.nextInt(validMoves.size());
        int randomMove = validMoves.get(randomIndex);
        System.out.println(randomMove);

    }
}