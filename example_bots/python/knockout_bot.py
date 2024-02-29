from poker_game_runner.state import Observation
from poker_game_runner.utils import Range, HandType
"""
  This bot scans the board, for the lowest cash stack, and will raise the pot to match, in an attempt to knockout the player.
"""


class Bot:
    def get_name(self):
        return "knockout_bot"

    def act(self, obs: Observation):
        stack_sizes = []
        players = obs.get_active_players()

        for player in players:
            if player.stack == 0:  #removes 0 from stack sizes, to avoid folding
                continue

            stack_sizes.append(player.stack + player.spent)

        lowest_stack = min(stack_sizes)
        max_raise = obs.get_max_raise()
        action = min(lowest_stack, max_raise)  #make sure raise is possible

        return action
