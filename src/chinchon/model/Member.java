package chinchon.model;

/**
 * Representa un participante de la partida.
 * @author Alejandro Vega
 */
public abstract class Member {

  /**
   * Nombre del participante.
   */
  private String name;

  /**
   * Mano del participante.
   */
  private Hand hand;

  /**
   * Puntuación acumulada del participante.
   */
  private int score;

  /**
   * Indica si el participante ha sido eliminado.
   */
  private boolean eliminated;

  /**
   * Crea un participante con un nombre.
   * 
   * @param name Nombre del participante.
   */
  public Member(String name) {
    this.name = name;
    this.hand = new Hand();
    this.score = 0;
    this.eliminated = false;
  }

  /**
   * Obtiene el nombre del participante.
   * 
   * @return Nombre del participante.
   */
  public String getName() {
    return name;
  }

  /**
   * Obtiene la mano del participante.
   * 
   * @return Mano del participante.
   */
  public Hand getHand() {
    return hand;
  }

  /**
   * Obtiene la puntuación acumulada.
   * 
   * @return Puntuación acumulada.
   */
  public int getScore() {
    return score;
  }

  /**
   * Suma puntos a la puntuación acumulada.
   * 
   * @param points Puntos a sumar.
   */
  public void addScore(int points) {
    score += points;
  }

  /**
   * Comprueba si el participante está eliminado.
   * 
   * @return true si está eliminado, false en caso contrario.
   */
  public boolean isEliminated() {
    return eliminated;
  }

  /**
   * Elimina al participante.
   */
  public void eliminate() {
    eliminated = true;
  }

  /**
   * Realiza el turno del participante.
   */
  public abstract void playTurn();
}
