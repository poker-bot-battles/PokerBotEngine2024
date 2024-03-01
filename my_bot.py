from poker_game_runner.state import Observation
from poker_game_runner.utils import Range, HandType
import time
import random

class Bot:
  def get_name(self):
      return "Python Bot"

  def act(self, obs: Observation):
    # Your code here
    return obs.get_max_raise() # All-in
