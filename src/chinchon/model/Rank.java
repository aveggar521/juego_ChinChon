package chinchon.model;

/**
 * Enum que representa un rango de una carta.
 */
public enum Rank {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), TEN(10), ELEVEN(11), TWUELVE(12);

  private int value;

  private Rank(int value) {

    this.value = value;
  }

  /**
   * Obtiene el valor del rango.
   * 
   * @return Valor del rango
   */
  public int getValue() {
    return value;
  }
}
