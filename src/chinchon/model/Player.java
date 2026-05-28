package chinchon.model;

/**
 * Representa a un jugador humano.
 * @author Alejandro Vega
 */
public class Player extends Member {

  /**
   * Crea un jugador con un nombre.
   * 
   * @param name Nombre del jugador.
   */
  public Player(String name) {
    super(name);
  }

  /**
   * Realiza el turno del jugador.
   */
  @Override
  public void playTurn() {
    System.out.println(getName() + " está jugando su turno.");
  }
}
