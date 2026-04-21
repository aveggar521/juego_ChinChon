package Chinchon.model;

public class Game {
  private static Game instance;
  private Deck deck;
  private Member player;
  private Member machine;

  private Game() {
    deck = new Deck();
    player = MemberFactory.createMember("Player", "Jugador");
    machine = MemberFactory.createMember("Machine", "Máquina");
  }

  public static Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }
    return instance;
  }

  public void start() {
    System.out.println("Iniciando partida...");
  }
}
