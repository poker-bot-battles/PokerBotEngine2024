
from poker_game_runner.state import Observation
from poker_game_runner.utils import Range, HandType

"""
  This bot folds until it has less that 20 big blinds left. It then goes into panic mode to try and stay in the tournament.
  It's important to be aware of how many big blinds you have left so you dont just lose to the increasing blinds.
"""

class Bot:

  def __init__(self) -> None:
      self.r25 = Range("44+, A2s+, K4s+, Q6s+, J7s+, T8s+, 98s, A7o+, K9o+, QTo+, JTo") # 25%
      self.r16 = Range("66+, A5s+, K9s+, Q9s+, JTs, ATo+, KJo+, QJo") # 16%
      self.r10 = Range("77+, A9s+, KTs+, QJs, AJo+, KQo") # 10%

  def get_name(self):
      return "panic_bot"
  
  def act(self, obs: Observation):
    my_stack_in_blinds = obs.get_my_player_info().stack / obs.big_blind
    if my_stack_in_blinds < 20:
      return self.do_preflop_panic(obs, my_stack_in_blinds)
    else:
      return 0
  
  def do_preflop_panic(self, obs:Observation, my_stack_in_blinds):
    # smaller stack size = larger range because small stack size will not last long. We need to go all in faster with smaller stacks
    if my_stack_in_blinds < 10:
      r = self.r25
    elif my_stack_in_blinds < 15:
      r = self.r16
    else:
      r = self.r10
    
    if r.is_hand_in_range(obs.my_hand):
      return obs.get_max_raise() # all in
    else:
      return 0