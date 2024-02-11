import java.util.Random;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class bot {
   // print arg to stdout
    public static void main(String[] args) throws IOException {
    try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(args[args.length - 1]);
        System.out.println(act(jsonNode));
    }
    catch (Exception e) {
        System.err.println(e);
    }
    }

    private static int act(JsonNode obs) {
        JsonNode validMoves = obs.get("legal_actions"); 
        System.err.println("hello");
        Random random = new Random();
        int randomIndex = random.nextInt(validMoves.size());
        int randomMove = validMoves.get(randomIndex).asInt();

        return randomMove;
    }
}