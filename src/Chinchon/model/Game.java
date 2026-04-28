package Chinchon.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controla toda la lógica del juego Chinchón.
 */
public class Game {

  private List<Member> players;
  private Deck deck;
  private List<Card> discardPile;

  private int pointLimit;
  private boolean gameOver;
  private Member winner;

  private int deckResetCount;
  private static final int MAX_RESETS = 2;

  private Scanner scanner;

  public Game(int pointLimit, int numberOfDecks) {
    this.players = new ArrayList<>();
    this.deck = new Deck(numberOfDecks);
    this.discardPile = new ArrayList<>();
    this.pointLimit = pointLimit;
    this.gameOver = false;
    this.deckResetCount = 0;
    this.scanner = new Scanner(System.in);
  }

  public void addPlayer(Member player) {
    players.add(player);
  }

  /**
   * Inicia el juego.
   */
  public void startGame() {

    dealCards();
    discardPile.add(deck.drawCard());

    while (!gameOver) {
      playRound();
      checkWinner();
      handleDeckResetIfNeeded();
    }

    System.out.println("\n🏆 GANADOR: " + winner.getName());
  }

  /**
   * Reparte cartas iniciales.
   */
  private void dealCards() {
    for (Member m : players) {
      for (int i = 0; i < 7; i++) {
        m.getHand().addCard(deck.drawCard());
      }
    }
  }

  /**
   * Ejecuta una ronda completa.
   */
  private void playRound() {

    for (Member player : players) {

      if (player.isEliminated())
        continue;

      executeTurn(player);

      if (player.getHand().hasChinchon()) {
        winner = player;
        gameOver = true;
        return;
      }
    }

    calculateScores();
  }

  /**
   * Turno de jugador o máquina.
   */
  private void executeTurn(Member player) {

    System.out.println("\n====================");
    System.out.println("Turno de: " + player.getName());
    System.out.println("====================");

    Card drawn;

    // ===== ROBO =====
    if (player instanceof Machine) {

      if (!discardPile.isEmpty() && Math.random() < 0.3) {
        drawn = discardPile.remove(discardPile.size() - 1);
        System.out.println(player.getName() + " roba del descarte");
      } else {
        drawn = deck.drawCard();
        System.out.println(player.getName() + " roba del mazo");
      }

    } else {

      System.out.println("1. Mazo | 2. Descarte");

      int choice = readInt();

      if (choice == 2 && !discardPile.isEmpty()) {
        drawn = discardPile.remove(discardPile.size() - 1);
      } else {
        drawn = deck.drawCard();
      }
    }

    player.getHand().addCard(drawn);
    System.out.println("Carta robada: " + drawn);

    // ===== DESCARTE =====
    List<Card> cards = player.getHand().getCards();

    System.out.println("\nMano:");

    for (int i = 0; i < cards.size(); i++) {
      System.out.println((i + 1) + ": " + cards.get(i));
    }

    int index;

    if (player instanceof Machine) {
      index = chooseMachineDiscard(player.getHand());
    } else {
      System.out.println("Elige carta a descartar (1-" + cards.size() + "):");
      index = readIntInRange(cards.size()) - 1;
    }

    Card discarded = player.getHand().removeCard(index);
    discardPile.add(discarded);

    System.out.println(player.getName() + " descarta: " + discarded);
  }

  /**
   * IA simple: descarta la carta de mayor valor.
   */
  private int chooseMachineDiscard(Hand hand) {

    List<Card> cards = hand.getCards();

    int worstIndex = 0;
    int worstValue = cards.get(0).getValue();

    for (int i = 1; i < cards.size(); i++) {
      if (cards.get(i).getValue() > worstValue) {
        worstValue = cards.get(i).getValue();
        worstIndex = i;
      }
    }

    return worstIndex;
  }

  /**
   * Calcula puntos de todos los jugadores.
   */
  private void calculateScores() {

    for (Member m : players) {

      if (m.isEliminated())
        continue;

      int points = m.getHand().calculatePoints();
      m.addScore(points);

      System.out.println(m.getName() + " suma " + points + " puntos");

      if (m.getScore() >= pointLimit) {
        m.eliminate();
        System.out.println(m.getName() + " eliminado");
      }
    }
  }

  /**
   * Comprueba si queda un solo jugador activo.
   */
  private void checkWinner() {

    List<Member> active = new ArrayList<>();

    for (Member m : players) {
      if (!m.isEliminated()) {
        active.add(m);
      }
    }

    if (active.size() == 1) {
      winner = active.get(0);
      gameOver = true;
    }
  }

  /**
   * Reinicio del mazo (máx 2 veces).
   */
  private void handleDeckResetIfNeeded() {

    if (!deck.isEmpty())
      return;

    if (deckResetCount >= MAX_RESETS) {
      gameOver = true;
      return;
    }

    System.out.println("\n🔄 Reiniciando mazo...");

    List<Card> newCards = new ArrayList<>(discardPile);
    discardPile.clear();

    deck = new Deck(1);
    deckResetCount++;

    System.out.println("Mazo reiniciado (" + deckResetCount + ")");
  }

  /**
   * Lee número seguro.
   */
  private int readInt() {

    while (true) {
      try {
        return Integer.parseInt(scanner.nextLine());
      } catch (Exception e) {
        System.out.println("Introduce un número válido:");
      }
    }
  }

  /**
   * Valida rango de entrada.
   */
  private int readIntInRange(int max) {

    while (true) {
      int value = readInt();

      if (value >= 1 && value <= max) {
        return value;
      }

      System.out.println("Valor inválido. Debe estar entre 1 y " + max);
    }
  }
}
