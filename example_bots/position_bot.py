
from poker_game_runner.state import Observation
from poker_game_runner.utils import Range, HandType

"""
  This bot plays differently depending on the amount of players left in the hand.
  This is very important in poker as 
    more poeple = more hands that can beat your hand, 
    and less people = less hands that can beat your hand (if any).
"""

class Bot:

  def __init__(self) -> None:
    self.r25 = Range("55+, A2s+, K5s+, Q8s+, J8s+, T9s, A8o+, K9o+, QTo+, JTo") # top 25%
    self.r20 = Range("55+, A3s+, K7s+, Q8s+, J9s+, T9s, A9o+, KTo+, QJo") # top 20%
    self.r16 = Range("66+, A5s+, K9s+, Q9s+, JTs, ATo+, KJo+, QJo") # 16%
    self.r10 = Range("77+, A9s+, KTs+, QJs, AJo+, KQo") # 10%
    self.r6 = Range("88+, ATs+, KJs+, AKo") # 6%

  def get_name(self):
      return "position_bot"
  
  def act(self, obs: Observation):
    if obs.current_round == 0: #preflop
      return self.do_preflop(obs)
    else:
      return self.do_postflop(obs)

  def do_preflop(self, obs:Observation):
    active_player_count = len(obs.get_active_players())

    if active_player_count <= 2:
      r = self.r25
    elif active_player_count == 3:
      r = self.r20
    elif active_player_count == 4 or active_player_count == 5:
      r = self.r16
    elif active_player_count == 6 or active_player_count == 7:
      r = self.r10
    else:
      r = self.r6

    if r.is_hand_in_range(obs.my_hand):
      return obs.get_fraction_pot_raise(1) # raise 1 pot
    else:
      return 0

  def do_postflop(self, obs:Observation):
    active_player_count = len(obs.get_active_players())
    r = False
    my_hand_type = obs.get_my_hand_type()

    if active_player_count <= 3:
      r = self.is_hand_ace_or_king_or_better(obs)
    elif active_player_count == 4 or active_player_count == 5:
      r = my_hand_type >= HandType.PAIR and my_hand_type.value > obs.get_board_hand_type().value
    elif active_player_count == 6 or active_player_count == 7:
      r = my_hand_type >= HandType.TWOPAIR and my_hand_type.value > obs.get_board_hand_type().value+1
    else:
      r = my_hand_type >= HandType.THREEOFAKIND and my_hand_type.value > obs.get_board_hand_type().value+1

    if r:
      return obs.get_fraction_pot_raise(1) # raise 1 pot
    else:
      return 0

  def is_hand_ace_or_king_or_better(self, obs:Observation):
    my_hand_type = obs.get_my_hand_type()
    return my_hand_type >= HandType.PAIR or self.is_card_rank_in_hand('A', obs.my_hand) or self.is_card_rank_in_hand('K', obs.my_hand)

  def is_card_rank_in_hand(self, rank, hand):
    return rank in hand[0] or rank in hand[1]