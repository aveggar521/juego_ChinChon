package Chinchon.app;

import Chinchon.model.Game;
import Chinchon.util.Console;

public class Main {

  public static void main(String[] args) {
    Console console = new Console();

    Game game = Game.getInstance();
    game.start();
  }
}
