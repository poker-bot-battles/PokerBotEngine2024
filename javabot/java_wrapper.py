from poker_game_runner.state import Observation
import subprocess
import json

class Bot:

  def __init__(self) -> None:
    subprocess.run(["javac","-cp","./javabot:javabot/libs/*", "javabot/PokerUtilities.java"])
    subprocess.run(["javac","-cp","./javabot:javabot/libs/*", "javabot/Observable.java"])
    subprocess.run(["javac","-cp","./javabot:javabot/libs/*", "javabot/bot.java"])
    subprocess.run(["javac","-cp","./javabot:javabot/libs/*", "javabot/SuperBot.java"])

    self.p = subprocess.Popen("""java -cp "./javabot:javabot/libs/*" SuperBot""", shell=True, stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

  def get_name(self):
      with open("javabot/bot.java") as f:
          for line in f:
              if "public static final String BOT_NAME" in line:
                  return line.split('"')[1]
      return "Java Bot"



  def act(self, obs: Observation):
    obsdict = {'smallBlind': obs.small_blind,
               'bigBlind': obs.big_blind,
               'myHand': obs.my_hand,
               'myIndex': obs.my_index,
               'boardCards': obs.board_cards,
               'playerInfos': [p.__dict__ for p in obs.player_infos],
               'currentRound': obs.current_round,
               'legalActions': obs.legal_actions,
               'history': [[a.__dict__ for a in r] for r in obs.history],
               'myHandType': str(obs.get_my_hand_type()),
               'boardHandType': str(obs.get_board_hand_type()),
    }
    # print(json.dumps(obsdict))
    self.p.stdin.write(json.dumps(obsdict) + "\n")
    self.p.stdin.flush()
    res = self.p.stdout.readline()
    try:
      int_res = int(res)
      return int(int_res)
    except:
      print("Java bot ("+self.get_name()+") caused an exception")
      print("Error:", res)
      return 0


  def __del__(self):
      self.p.stdin.write("exit\n")
      self.p.stdin.close()
      subprocess.run(["rm", "javabot/bot.class"])
      subprocess.run(["rm", "javabot/Observable.class"])
      subprocess.run(["rm", "javabot/PokerUtilities.class"])
      subprocess.run(["rm", "javabot/PlayerInfo.class"])
      subprocess.run(["rm", "javabot/HandType.class"])
      subprocess.run(["rm", "javabot/ActionInfo.class"])
      subprocess.run(["rm", "javabot/SuperBot.class"])
      subprocess.run(["rm", "javabot/Range.class"])