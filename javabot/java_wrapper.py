from poker_game_runner.state import Observation
import subprocess
import json
class Bot:
  def __init__(self) -> None:
     subprocess.run(["javac","-cp","javabot/libs/*", "javabot/bot.java"])
  def get_name(self):
      return "Java Bot"
  
  def act(self, obs: Observation):
    print("\n"*5)
    print("obs", obs)
    obsdict = {'small_blind': obs.small_blind,
               'big_blind': obs.big_blind, 
               'my_hand': obs.my_hand, 
               'my_index': obs.my_index,
               'board_cards': obs.board_cards,
               'player_infos': [p.__dict__ for p in obs.player_infos],
               'current_round': obs.current_round,
               'legal_actions': obs.legal_actions,
    }
    res = subprocess.run(["java", "-cp","./javabot:javabot/libs/*", "bot", json.dumps(obsdict)], capture_output=True, text=True)
    print("sterr     ", res.stderr)
    print("stdout   ", res.stdout)
    return int(res.stdout)