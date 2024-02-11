import os
from poker_game_runner.runner import play_tournament_table
import json

def run_benchmark(bots, run_count):
    bot_instances = [b.Bot() for b in bots]
    data = [{'name': b.get_name(), 'wins': 0} for b in bot_instances]
    for i in range(run_count):
        res, _ = play_tournament_table(bot_instances, 1000)
        data[res[0]['id']]['wins'] += 1
        print(chr(27) + "[2J")
        print("--- " + str(i+1) + "/" + str(run_count) + " ---")
        [print(d) for d in data]
    return data

def run_table(bots):
    bot_instances = [b.Bot() for b in bots]

    res, _ = play_tournament_table(bot_instances, 100, use_timeout=False, console_output=True)
    print()
    print("bots ordered by final position")
    print([r['name'] for r in res])
    return res