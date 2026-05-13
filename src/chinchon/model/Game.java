package chinchon.model;

import java.util.ArrayList;
import java.util.List;

import chinchon.util.Console;

/**
 * Controla toda la lógica del juego Chinchón. Implementa el patrón Singleton.
 */
public class Game {

  private static Game instance;

  private List<Member> players;
  private Deck deck;
  private List<Card> discardPile;
  private int pointLimit;
  private boolean gameOver;
  private Member winner;
  private int deckResetCount;
  private static final int MAX_RESETS = 2;
  private Console console;

  /**
   * Constructor privado para Singleton.
   */
  private Game(int pointLimit, int numberOfDecks, Console console) {
    this.players = new ArrayList<>();
    this.deck = new Deck(numberOfDecks);
    this.discardPile = new ArrayList<>();
    this.pointLimit = pointLimit;
    this.gameOver = false;
    this.deckResetCount = 0;
    this.console = console;
  }

  /**
   * Obtiene la instancia única.
   */
  public static Game getInstance(int pointLimit, int numberOfDecks, Console console) {
    if (instance == null) {
      instance = new Game(pointLimit, numberOfDecks, console);
    }
    return instance;
  }

  public void addPlayer(Member player) {
    players.add(player);
  }

  public void startGame() {
    dealCards();
    discardPile.add(deck.drawCard());

    while (!gameOver) {
      playRound();
      if (!gameOver) {
        checkWinner();
        handleDeckResetIfNeeded();
      }
    }

    if (winner != null) {
      console.println("\n🏆 GANADOR DE LA PARTIDA: " + winner.getName());
    }
  }

  private void dealCards() {
    for (Member m : players) {
      for (int i = 0; i < 7; i++) {
        m.getHand().addCard(deck.drawCard());
      }
    }
  }

  private void playRound() {
    int i = 0;
    while (i < players.size() && !gameOver) {
      Member player = players.get(i);
      if (!player.isEliminated()) {
        executeTurn(player);
        if (player.getHand().hasChinchon()) {
          winner = player;
          gameOver = true;
          console.println("¡CHINCHÓN de " + player.getName() + "!");
        }
      }
      i++;
    }
    if (!gameOver)
      calculateScores();
  }

  private void executeTurn(Member player) {
    console.println("\n--- Turno de: " + player.getName() + " ---");
    Card drawn;
    if (player instanceof Machine) {
      drawn = deck.drawCard();
    } else {
      console.println("1. Mazo | 2. Descarte ("
          + (discardPile.isEmpty() ? "Vacío" : discardPile.get(discardPile.size() - 1)) + ")");
      int choice = readIntInRange(1, 2);
      if (choice == 2 && !discardPile.isEmpty()) {
        drawn = discardPile.remove(discardPile.size() - 1);
      } else {
        drawn = deck.drawCard();
      }
    }
    player.getHand().addCard(drawn);
    console.println("Has robado: " + drawn);
    console.println("Tu mano actual:\n" + player.getHand().toString());

    int index;
    if (player instanceof Machine) {
      index = ((Machine) player).chooseDiscardIndex();
    } else {
      console.println("Elige índice de carta a descartar (1-8):");
      index = readIntInRange(1, 8) - 1;
    }

    Card discarded = player.getHand().removeCard(index);
    discardPile.add(discarded);
    console.println(player.getName() + " ha descartado: " + discarded);
  }

  private void calculateScores() {
    for (Member m : players) {
      if (!m.isEliminated()) {
        int points = m.getHand().calculatePoints();
        m.addScore(points);
        console.println(m.getName() + " suma " + points + " puntos. Total: " + m.getScore());
        if (m.getScore() >= pointLimit) {
          m.eliminate();
          console.println("❌ " + m.getName() + " ha sido eliminado.");
        }
      }
    }
  }

  private int readIntInRange(int min, int max) {
    int value = -1;
    while (true) {
      try {
        value = Integer.parseInt(console.readLine());
        if (value >= min && value <= max)
          return value;
        console.println("Introduce un número entre " + min + " y " + max + ":");
      } catch (Exception e) {
        console.println("Entrada no válida. Introduce un número:");
      }
    }
  }

  private void handleDeckResetIfNeeded() {
    if (deck.isEmpty() && deckResetCount < MAX_RESETS) {
      console.println("\n🔄 El mazo se ha agotado. Barajando descarte...");
      deck.initializeDeck();
      deck.shuffle();
      discardPile.clear();
      deckResetCount++;
    } else if (deck.isEmpty()) {
      gameOver = true;
    }
  }

  private void checkWinner() {
    int active = 0;
    Member last = null;
    for (Member m : players) {
      if (!m.isEliminated()) {
        active++;
        last = m;
      }
    }
    if (active == 1) {
      winner = last;
      gameOver = true;
    }
  }
}
