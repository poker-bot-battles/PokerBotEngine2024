# Installation

## Requirements

- python 3.8 or higher
- pip
- git

## Steps

1. Clone the repository

```bash
git clone https://github.com/poker-bot-battles/PokerBotEngine2024.git
```

2. Install the requirements

```bash
cd PokerBotEngine2024
pip install eval7 open-spiel --no-cache-dir
pip install poker-game-runner --no-cache-dir --no-deps
```

3. Run the engine

```bash
python main.py
```

# Welcome to the poker bot challenge

- [Installation](#installation)
  - [Requirements](#requirements)
  - [Steps](#steps)
- [Welcome to the poker bot challenge](#welcome-to-the-poker-bot-challenge)
  - [Introduction to the rules of poker](#introduction-to-the-rules-of-poker)
    - [In summary](#in-summary)
    - [Tournament setup](#tournament-setup)
    - [Special rules for this tournament](#special-rules-for-this-tournament)
  - [Setup](#setup)
  - [Introduction to the development environment](#introduction-to-the-development-environment)
    - [`main.py`](#mainpy)
    - [`my_bot_dev.py` and `my_bot_master.py`](#my_bot_devpy-and-my_bot_masterpy)
    - [`example_bots` folder](#example_bots-folder)
  - [Bot setup](#bot-setup)
  - [Details](#details)
    - [Card](#card)
    - [Hand / private cards](#hand--private-cards)
    - [Poker hand / hand type](#poker-hand--hand-type)
    - [Board cards / Public cards / Community cards](#board-cards--public-cards--community-cards)
    - [Range](#range)

In this challenge you will write a poker playing bot to compete against the other participants.

## Introduction to the rules of poker

The bots will be playing no-limit texas holdem poker.
The detailed rules for this game can be found here: https://upswingpoker.com/poker-rules/texas-holdem-rules/

### In ary

Each player is delt 2 private cards face down.

Over the course of the game 5 public community cards are delt (called board cards).

To win the player must either get every other player to fold (given up on their hand) or have the best poker hand at the end of the game.

The poker hand is made up of the 5 best cards out of the 2 private cards the player is delt and the 5 board cards shared with all players.

The poker hand rankings are described here: https://www.cardplayer.com/rules-of-poker/hand-rankings

The game takes place over 4 betting rounds as described below:

- the 2 players next to the dealer input the small blind and big blind to the pot.
- each player is delt 2 cards
  - PreFlop (betting round 0)
    - players can fold, call, or raise
    - this continues until all players have either folded or called the last raise.
    - if 2 or more players have not folded, play continues to the next betting round
  - Flop (betting round 1)
    - 3 board cards are delt
    - if no bet has been made the payers can check (bet nothing)
    - this continues until all players have either folded or called the last raise.
    - if 2 or more players have not folded, play continues to the next betting round
  - Turn (betting round 2)
    - 1 board cards is delt to a total of 4
    - play continues as described in the flop section
  - River (betting round 3)
    - 1 board cards is delt to a total of 5
    - play continues as described in the flop section
    - if 2 or more players have not folded, all players reveal their hands, and the best hand wins the pot

### Tournament setup

The bots will play poker in a tournament format, winner takes all.

The tournament will play up to 10 bots against each other until only one of them has any money left.

During the Tournament the blinds will increase between games at specific intervals to make sure the tournament ends and discourage passive plays

### Special rules for this tournament

If a bot has less than 1 big blind at the end of a hand they are taken out of the tournament as if they had no money left.

There is a raise count limitation of 5. This means that the number of raises in one betting round is limited to 5.

## Setup

We recommend you use replit.com to develop your bot. It is an online IDE which makes the environment setup painless.

If you want to use something else, please be aware that we use some libraries that are linux only and you will have to copy your code to replit for submission.

Guide:

- open link https://replit.com/@frederikblund/pokerdevenv
- press `fork repl`
- log in with google or other
- Press `Run` at the top.
- Wait for the necessary packages to be installed (1-2 min)
- Share your repl with 'nctbk' by clicking the `invite` button in the top right. (ignore the "no results" warning when searching for 'nctbk')
- You are now ready to code.

## Introduction to the development environment

### `main.py`

This file will be run when the green run button is pressed.

In this file you can change which bots will play in the tournament by adding/removing bots from the bots list.

There are 2 ways to test your bot:

- `run_table(bots)` runs 1 single tournament to the end, and outputs details to the console. This can be used for debugging and general development.
- `run_benchmark(bots, n)` runs n tournaments and outputs win count for each bot. This is used for getting an indication of the overall strength of the bots compared to each other.

### `my_bot_dev.py` and `my_bot_master.py`

these files are where you will write you own bot.

`my_bot_master.py` is the file that will be downloaded and run the in the tournaments, so make sure the code in this file works!

`my_bot_dev.py` can be use for general development and experimentation.

You can add as many files as you want with different versions of your bot (or other participant’s bots) which can be useful to see if your improvements actually help

### `example_bots` folder

In this folder there are examples of bots to get inspiration from.

## Bot setup

The main entry point into your bot is the `act` method.

The `act` method is called with an `observation` object and expects an `integer` in return.

The return integer represents the action your bot is taking:

- 0 = fold
- 1 = call/check
- x > 1 = raise to x :warning:raise to x includes any money you have already put into the pot:warning:

The observation object has all the information needed to make a decision. It has many attributes and helper functions to make coding easier, check them out in the [DOCS](https://poker-game-runner.readthedocs.io/en/latest/poker_game_runner.html) or in the example bots.

The observation object has an important attribute `legal_actions` that returns a list of integers, which are legal actions at the current state of the game.

:warning: Make sure your bot returns a legal action :warning: if the returned action is not in legal_actions your bot will fold.

:warning: There is a time limit of 1 second for your bot to return :warning: if your bot takes longer, your bot will fold.

## Details

### Card

a card is represented by a 2-character string. 1 char with the rank and 1 char with the suit

examples: `'As'` (ace of spades), `'6d'` (six of diamonds), `'Tc'` (ten of clubs)

### Hand / private cards

A hand is the 2 private cards each player is delt. the hand is represented by a tuple of card strings

examples: `('As', 'Ah')`

### Poker hand / hand type

A poker hand or hand type is the type of poker hand that 5 cards can make. hand types are represented by the enum `HandType`

example `HandType.FLUSH`, `HandType.PAIR`

### Board cards / Public cards / Community cards

the Board cards are the shared cards delt on the board that any player can use to create their best poker hand. the board cards are represented by a tuple of card strings

examples: `('3h', '5c', '2s')` or `('4h', 'Qd' 'Jh', 'Kd', 'Th')`

### Range

A range is a way to group cards that you will play the same way. A range is represented by the class `Range`.

The `Range` class constructor takes a rangeString. The rangeString can be generated from https://openpokertools.com/.

To check if your current hand is in the range you have create use the `is_hand_in_range(current_hand)` method
