
from poker_game_runner.state import Observation
from poker_game_runner.utils import Range, HandType

"""
  This bot calculates the odds it is given to call and uses this to determine how good it's hand needs to be to call.
  If nobody has raised before, this bot uses some simple logic to determine if it should raise or not.

  "open" == first raise in round
"""

class Bot:

  def __init__(self) -> None:
      self.r20 = Range("55+, A3s+, K7s+, Q8s+, J9s+, T9s, A9o+, KTo+, QJo") # top 20%
      self.r16 = Range("66+, A5s+, K9s+, Q9s+, JTs, ATo+, KJo+, QJo") # 16%
      self.r10 = Range("77+, A9s+, KTs+, QJs, AJo+, KQo") # 10%
      self.r6 = Range("88+, ATs+, KJs+, AKo") # 6%

  def get_name(self):
      return "odds_bot"
  
  def act(self, obs: Observation):
    if obs.current_round == 0: #preflop
      return self.do_preflop(obs)
    else:
      return self.do_postflop(obs)
      
  def do_preflop(self, obs: Observation):
    raise_actions = [action for action in obs.get_actions_this_round() if action.action > 1]
    if len(raise_actions) == 0: # Open 
      return self.do_preflop_open(obs)

    if len(raise_actions) > 0:
      return self.do_preflop_response(obs)

  def do_postflop(self, obs:Observation):
    if obs.get_call_size() == 0:
      return self.do_post_flop_open(obs)
    else:
      return self.do_post_flop_response(obs)
  
  def do_post_flop_open(self, obs:Observation):
    if self.is_hand_ace_or_better(obs):
        return obs.get_fraction_pot_raise(1)

  def is_hand_ace_or_better(self, obs:Observation):
    my_hand_type = obs.get_my_hand_type()
    return my_hand_type >= HandType.PAIR or self.is_card_rank_in_hand('A', obs.my_hand)

  def is_card_rank_in_hand(self, rank, hand):
    return rank in hand[0] or rank in hand[1]

  def do_post_flop_response(self, obs: Observation):
    call_odds = obs.get_call_size() / obs.get_pot_size()
    my_hand_type = obs.get_my_hand_type()
    if call_odds < 0.1:
      return 1 #call all small raises
    elif call_odds < 0.3:
      if my_hand_type >= HandType.PAIR and my_hand_type.value > obs.get_board_hand_type().value:
        return 1
    elif call_odds < 0.6:
      if my_hand_type > HandType.PAIR and my_hand_type.value > obs.get_board_hand_type().value+1:
        return 1
    else:
      if my_hand_type > HandType.TWOPAIR and my_hand_type.value > obs.get_board_hand_type().value+1:
        return 1

    return 0

  def do_preflop_open(self, obs:Observation):
    open_raise_range = self.r20
    if open_raise_range.is_hand_in_range(obs.my_hand):
      return obs.get_fraction_pot_raise(1) # raise 1 pot
    else:
      return 0

  def do_preflop_response(self, obs:Observation):
    call_odds = obs.get_call_size() / obs.get_pot_size()
    if call_odds < 0.1:
      return 1 #call all small raises
    elif call_odds < 0.3:
      r = self.r16
    elif call_odds < 0.6:
      r = self.r10
    else:
      r = self.r6

    if r.is_hand_in_range(obs.my_hand):
      return 1
    else:
      return 0