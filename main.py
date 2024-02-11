from test import run_benchmark, run_table
from poker_game_runner.bots import randombot
from example_bots import panic_bot, odds_bot, checkmate, position_bot
import my_bot
import javabot.java_wrapper as java_wrapper
bots = [my_bot, java_wrapper]

run_table(bots)

#run_benchmark(bots, 2)
 
