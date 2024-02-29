
from poker_game_runner.state import Observation
from poker_game_runner.utils import Range, HandType
import random

"""
This bot makes a random number which is either 0 or 1. If it is 1, it will always raise the minimum amount that it possibly can. Otherwise, it attempts to call.
"""

class Bot:
  def get_name(self):
      return "random_bot"

  def act(self, obs: Observation):
    shouldRaise = random.randint(0,1)
    if (shouldRaise == 1):
      return obs.get_min_raise()
    else:
      return 1