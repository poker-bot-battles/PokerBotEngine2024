from poker_game_runner.state import Observation
from poker_game_runner.utils import Range, HandType
import time
import random

class Bot:
  def get_name(self):
      return "Python Bot"
  
  def act(self, obs: Observation):
    #return random action
    return obs.legal_actions[random.randint(0, len(obs.legal_actions)-1)]
