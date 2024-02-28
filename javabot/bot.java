import java.io.IOException;

public class bot  {
    public static final String BOT_NAME = "FIRST YEAR STUDENT HELLO BOT";

     public static void main(String[] args) throws IOException {

        // DO NOT REMOVE
        Observable obs = Observable.fromJson(args[args.length - 1]);

        System.out.println(obs.getMaxRaise());
    }
 }