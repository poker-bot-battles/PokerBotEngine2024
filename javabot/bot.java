import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;

public class bot {
   // print arg to stdout
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Observable obs = objectMapper.readValue(args[args.length - 1], Observable.class);


        ArrayList<Integer> validMoves = obs.getValidMoves();
        Random random = new Random();
        int randomIndex = random.nextInt(validMoves.size());
        int randomMove = validMoves.get(randomIndex);
        System.out.println(randomMove);

    }
}