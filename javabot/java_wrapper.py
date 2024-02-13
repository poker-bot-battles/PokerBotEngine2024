from poker_game_runner.state import Observation
import subprocess
import json
class Bot:
  def __init__(self) -> None:
     subprocess.run(["javac","-cp","./javabot:javabot/libs/*", "javabot/bot.java"])
  def get_name(self):
      return "Java Bot"

  def act(self, obs: Observation):
    print("\n"*5)
    print("obs", obs)
    obsdict = {'smallBlind': obs.small_blind,
               'bigBlind': obs.big_blind,
               'myHand': obs.my_hand,
               'myIndex': obs.my_index,
               'boardCards': obs.board_cards,
               'playerInfos': [p.__dict__ for p in obs.player_infos],
               'currentRound': obs.current_round,
               'legalActions': obs.legal_actions,
    }
    print("obsdict", json.dumps(obsdict))
    res = subprocess.run(["java", "-cp","./javabot:javabot/libs/*", "bot", json.dumps(obsdict)], capture_output=True, text=True)
    print("sterr     ", res.stderr)
    print("stdout   ", res.stdout)
    return int(res.stdout)