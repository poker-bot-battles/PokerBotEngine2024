import java.io.IOException;
import java.util.Scanner;

public class SuperBot {
  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      String line = scanner.nextLine();
      if (line.equals("exit")) {
        scanner.close();
        break;
      }
      Observable obs = Observable.fromJson(line);
      int res;
      try{
        res = bot.act(obs);
        System.out.println(res);
      } catch(Exception e) {
        System.out.println(e);
      }
      System.out.flush();
    }
  }

}
