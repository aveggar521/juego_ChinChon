package chinchon.model;

import java.util.ArrayList;
import java.util.List;

import chinchon.util.Console;

/**
 * Controla toda la lógica del juego Chinchón. Implementa el patrón Singleton para asegurar una única instancia de la partida. Se encarga de gestionar los turnos, el mazo, los descartes y las puntuaciones. * @author Tu Nombre
 * 
 */
public class Game {

  /** Instancia única de la clase Game. */
  private static Game instance;

  /** Lista de miembros (jugadores y máquinas) que participan en la partida. */
  private List<Member> players;

  /** Mazo de cartas utilizado en la partida. */
  private Deck deck;

  /** Pila de cartas descartadas por los jugadores. */
  private List<Card> discardPile;

  /** Límite de puntos para eliminar a un jugador. */
  private int pointLimit;

  /** Indica si la partida ha finalizado. */
  private boolean gameOver;

  /** Referencia al miembro que ha ganado la partida. */
  private Member winner;

  /** Contador de veces que el mazo ha sido reabastecido desde el descarte. */
  private int deckResetCount;

  /** Número máximo de veces que se puede reiniciar el mazo. */
  private static final int MAX_RESETS = 2;

  /** Interfaz de consola para la entrada y salida de datos. */
  private Console console;

  /**
   * Constructor privado para aplicar el patrón Singleton. Inicializa los componentes básicos de la partida. * @param pointLimit Límite de puntos establecido para la partida.
   * 
   * @param numberOfDecks Cantidad de mazos de cartas a utilizar.
   * @param console       Instancia de la utilidad de consola para la comunicación.
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
   * Obtiene la instancia única de la partida. Si no existe, la crea. * @param pointLimit Límite de puntos para la partida.
   * 
   * @param numberOfDecks Cantidad de mazos a utilizar.
   * @param console       Instancia de la utilidad de consola.
   * @return La instancia única de la clase Game.
   */
  public static Game getInstance(int pointLimit, int numberOfDecks, Console console) {
    if (instance == null) {
      instance = new Game(pointLimit, numberOfDecks, console);
    }
    return instance;
  }

  /**
   * Añade un nuevo jugador o máquina a la lista de participantes. * @param player El miembro que se unirá a la partida.
   */
  public void addPlayer(Member player) {
    players.add(player);
  }

  /**
   * Inicia el flujo principal de la partida. Reparte las cartas y mantiene el bucle de juego hasta que se determine un ganador.
   */
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

  /**
   * Reparte 7 cartas a cada jugador participante.
   */
  private void dealCards() {
    for (Member m : players) {
      for (int i = 0; i < 7; i++) {
        m.getHand().addCard(deck.drawCard());
      }
    }
  }

  /**
   * Gestiona una ronda completa de juego, recorriendo los turnos de los jugadores activos. Verifica si algún jugador ha conseguido hacer Chinchón al finalizar su movimiento.
   */
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

  /**
   * Ejecuta las acciones de un turno individual: robar, mostrar mano y descartar. Diferencia la lógica si el jugador es humano o máquina. * @param player El miembro que debe realizar su turno.
   */
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

  /**
   * Calcula los puntos de las cartas no combinadas de cada jugador y los suma a su puntuación acumulada. Elimina jugadores si superan el límite.
   */
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

  /**
   * Lee un entero por consola asegurando que se encuentra en un rango específico. * @param min Valor mínimo aceptado.
   * 
   * @param max Valor máximo aceptado.
   * @return El número entero validado introducido por el usuario.
   */
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

  /**
   * Verifica si el mazo se ha agotado y procede a reiniciarlo utilizando la pila de descartes si no se ha superado el límite máximo de reinicios.
   */
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

  /**
   * Comprueba si solo queda un jugador activo en la partida para proclamarlo ganador.
   */
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
