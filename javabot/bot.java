import java.io.IOException;

public class bot  {
     public static void main(String[] args) throws IOException {

         Observable obs = Observable.fromJson(args[args.length - 1]);
         Range top50 = new Range("22+, A2s+, K2s+, Q2s+, J2s+, T5s+, 96s+, 86s+, 75s+, A2o+, K5o+, Q7o+, J8o+, T8o+");

         if (top50.isHandInRange(obs.getMyHand())) {
             System.out.println(1);
         } else {
             System.out.println(0);
         }

     }
 }