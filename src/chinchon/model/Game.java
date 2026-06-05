package chinchon.model;

import java.util.ArrayList;
import java.util.List;

import chinchon.util.ConsoleInput;

/**
 * Controla toda la lógica del juego Chinchón. Implementa el patrón Singleton para asegurar una única instancia de la partida. Se encarga de gestionar los turnos, el mazo, los descartes y las puntuaciones.
 * @author Alejandro Vega
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

  /** C de consola para la entrada y salida de datos. */
  private ConsoleInput console;

  /**
   * Constructor privado para aplicar el patrón Singleton. Inicializa los componentes básicos de la partida. 
   * @param pointLimit Límite de puntos establecido para la partida.
   * @param numberOfDecks Cantidad de mazos de cartas a utilizar.
   * @param console       Instancia de la utilidad de consola para la comunicación.
   */
  private Game(int pointLimit, int numberOfDecks, ConsoleInput console) {
    this.players = new ArrayList<>();
    this.deck = new Deck(numberOfDecks);
    this.discardPile = new ArrayList<>();
    this.pointLimit = pointLimit;
    this.gameOver = false;
    this.deckResetCount = 0;
    this.console = console;
  }

  /**
   * Obtiene la instancia única de la partida. Si no existe, la crea. 
   * 
   * @param pointLimit Límite de puntos para la partida.
   * @param numberOfDecks Cantidad de mazos a utilizar.
   * @param console       Instancia de la utilidad de consola.
   * @return La instancia única de la clase Game.
   */
  public static Game getInstance(int pointLimit, int numberOfDecks, ConsoleInput console) {
    if (instance == null) {
      instance = new Game(pointLimit, numberOfDecks, console);
    }
    return instance;
  }

  /**
   * Añade un nuevo jugador o máquina a la lista de participantes. 
   * @param player El miembro que se unirá a la partida.
   */
  public void addPlayer(Member player) {
    players.add(player);
  }

  /**
   * Inicia el flujo principal de la partida. Reparte las cartas, inicializa
   * la pila de descartes y mantiene el bucle de juego por rondas.
   */
  public void startGame() {
    dealCards();
    if (!deck.isEmpty()) {
      discardPile.add(deck.drawCard());
    }
    System.out.println("\n¡Todo listo! Pulsa ENTER para comenzar la partida...");
    try {
        System.in.read(); 
    } catch (Exception e) {
    }

    while (!gameOver) {
      playRound();
      if (!gameOver) {
        checkWinner();
        handleDeckResetIfNeeded();
        
        if (winner == null) {
          dealCards();
          if (!deck.isEmpty()) {
            discardPile.add(deck.drawCard());
          }
        }
      }
    }

    if (winner != null) {
      System.out.printf("\n🏆 GANADOR DE LA PARTIDA: %s\n", winner.getName());
    }
  }

  /**
   * Permite a un miembro cerrar la ronda actual de forma voluntaria.
   * Modifica el estado del juego para finalizar la ronda.
   * * @param player El miembro que efectúa el cierre.
   */
  public void closeRound(Member player) {
    this.gameOver = true; // Detiene el bucle principal de la partida
    System.out.printf("\n🚪 ¡%s HA CERRADO LA RONDA! Se procede al recuento de puntos.\n", player.getName());
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
   * Gestiona una ronda completa de juego, recorriendo continuamente los turnos 
   * de los jugadores activos en bucle hasta que alguien decida cerrar la ronda 
   * o haga Chinchón.
   */
  private void playRound() {
    int i = 0;
    
    while (!gameOver) {
      Member player = players.get(i);
      
      if (!player.isEliminated()) {
        boolean haCerrado = player.playTurn(this.deck, this.discardPile, this.console);
        
        if (haCerrado) {
          gameOver = true;
        }
        
        if (!gameOver && player.getHand().hasChinchon()) {
          winner = player;
          gameOver = true;
          System.out.printf("¡CHINCHÓN de %s!\n", player.getName());
        }
      }
      
      if (deck.isEmpty()) {
        gameOver = true;
      }
      
      i = (i + 1) % players.size();
    }
    
    System.out.println("\n--- FIN DE LA RONDA: RECUENTO DE PUNTOS ---");
    calculateScores();
    
    if (winner == null) {
      for (Member m : players) {
        m.getHand().clear(); 
      }
      discardPile.clear();
      deckResetCount = 0;
      gameOver = false; 
    }
  }
  /**
   * Calcula los puntos de las cartas no combinadas de cada jugador y los suma a su puntuación acumulada. Elimina jugadores si superan el límite.
   */
  private void calculateScores() {
    for (Member m : players) {
      if (!m.isEliminated()) {
        int points = m.getHand().calculatePoints();
        m.addScore(points);
        System.out.printf("%s suma %d puntos. Total: %d\n", m.getName(), points, m.getScore());
        if (m.getScore() >= pointLimit) {
          m.eliminate();
          System.out.printf("❌ %s ha sido eliminado.\n", m.getName());
        }
      }
    }
  }
 
  /**
   * Verifica si el mazo se ha agotado al final de una ronda y procede a reiniciarlo 
   * utilizando de forma segura las cartas de la pila de descartes.
   */
  private void handleDeckResetIfNeeded() {
    if (deck.isEmpty() && deckResetCount < MAX_RESETS) {
      System.out.println("\n🔄 El mazo se ha agotado. Barajando la pila de descartes...");
      deck.replenishDeck(this.discardPile);
      deck.shuffle();
      this.discardPile.clear();
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
