package Chinchon.app;

import java.util.Scanner;

import Chinchon.model.Game;
import Chinchon.model.MemberFactory;

/**
 * Clase principal del juego.
 */
public class Main {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    System.out.println("=== CHINCHÓN ===");

    // ===== NÚMERO DE MAZOS =====
    System.out.println("¿Con cuántos mazos quieres jugar? (1-2): ");
    int decks = scanner.nextInt();

    while (decks < 1 || decks > 2) {
      System.out.println("Valor inválido. Introduce 1 o 2:");
      decks = scanner.nextInt();
    }

    // ===== NÚMERO DE JUGADORES =====
    System.out.println("Introduce número de jugadores (2-5): ");
    int numPlayers = scanner.nextInt();

    while (numPlayers < 2 || numPlayers > 5) {
      System.out.println("Número inválido. Introduce entre 2 y 5:");
      numPlayers = scanner.nextInt();
    }

    scanner.nextLine(); // limpiar buffer

    Game game = new Game(100, decks);

    for (int i = 1; i <= numPlayers; i++) {

      System.out.println("Nombre del jugador " + i + ": ");
      String name = scanner.nextLine();

      if (i == 1) {
        game.addPlayer(MemberFactory.createMember("player", name));
      } else {
        game.addPlayer(MemberFactory.createMember("machine", name));
      }
    }

    game.startGame();
  }
}
