# Texas-Hold-Em
A single player text-based version of Texas-Hold-Em

The object of this project is to make a fully functioning single-player version of the poker game, Texas Hold-Em. It is a personal project
that I decided to make using my free time. It is far from complete, however, as there are a lot of things left to do regarding the computer
players, as well as some bugs that I need to work through. 



11-15-2019
  I fixed some of the big bugs in the Game.java file by no longer delegating certain tasks to separate methods. I originally made separate
  methods to better organize the Game class, but quickly realized that this was a problem, since Java passes variables by value, and 
  there were multiple values passed in to those methods that needed to be changed by the method. I am still left with needing to develop
  the computer player reactions to how the user makes bets.
