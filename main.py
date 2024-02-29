import sys
from test import run_benchmark, run_table
from poker_game_runner.bots import randombot
from example_bots import panic_bot, odds_bot, checkmate, position_bot
import my_bot
import javabot.java_wrapper as java_wrapper
bots = [odds_bot, panic_bot]

lang, type = sys.argv[1], sys.argv[2]

if lang == "java":
    bots.append(java_wrapper)
else:
    bots.append(my_bot)

if type == "benchmark":
    run_benchmark(bots, int(sys.argv[3]))
else:
    run_table(bots)


