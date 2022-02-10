# Game of Life

## About

John Conway's famous game of life recreated using Java Swing. It displays statistics about the current generation along with how many cells are alive. Additionally, it contains two other buttons: 'p' to pause the game, and 'r' to resume. Currently, the 'e' button does nothing.

<p align="center">
  <img src="https://user-images.githubusercontent.com/73836176/153352829-b46a9611-9b70-436d-bc81-6a11ab9d2004.png" alt="game" width="400" height="400"/>
</p>


## Medium

Made solely using Java and the Swing framework.



## Process

After learning the basics of Java Swing, I wanted to try and make a game using it. Ultimately, I decided on recreating John Conway's Game of Life. I tried making the experience as close as possible to those [game of life websites](https://playgameoflife.com/).

It started with me recreating the 'board' in a 2D array. I had to implement all the rules for cells as well as keep track of the current generation. With that done, I made a basic interface using Java Swing and used a cells in a grid to represent a cell corresponding to a pair of indices in a 2D array. I then implemented the pause and resume buttons.

## Download Instructions

Download the latest .jar from the releases.

You can check if you have Java in your computer by going into the cmd and typing **java -version**. If so, go to the directory of the .jar file you downloaded and type, in the cmd:

> java -jar gameoflife.jar

If you do not have Java, you can [download it from their official website](https://www.java.com/en/download/manual.jsp) - instructions included

Otherwise, if you have any other application that can run .jar files, you can use those too.
