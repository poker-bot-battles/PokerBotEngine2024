from poker_game_runner.state import Observation
from poker_game_runner.utils import Range, HandType
import random


class Bot:
    def get_name(self):
        return "hardcore_ai_bot"
        # Also known as 'if else' https://img.devrant.com/devrant/rant/r_2134966_hjkcf.jpg

    def act(self, obs: Observation):
        random_int = random.randint(0, 3)

        decent_hand = Range("77+, A8s+, K9s+, QTs+, AJo+, KQo")

        if (random_int == 0):
            # Will 'call' if bot has a decent hand else it will 'fold'
            if (decent_hand.is_hand_in_range(obs.my_hand)):
                return 1
            return 0

        elif (random_int == 1):
            # Will go 'all in' if the round is 4 else it will 'call'
            if (obs.current_round == 4):
                return obs.get_max_raise()
            return 1

        else:
            # If handtype is a straightflush then all in
            if obs.get_my_hand_type() == HandType.STRAIGHTFLUSH:
                return obs.get_max_raise()
            # Are we feeling lucky?
            # If yes then 'all in' else 'call'
            return obs.get_max_raise() if random.randint(0,
                                                         1000) == 1000 else 1
