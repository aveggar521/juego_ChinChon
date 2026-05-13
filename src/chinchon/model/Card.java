package chinchon.model;

/**
 * Representa una carta de la baraja española.
 */
public class Card {

  /**
   * Palo de la carta (Oros, Copas, Espadas, Bastos).
   */
  private final Suit suit;

  /**
   * Rango de la carta (1-7, 10-12).
   */
  private final Rank rank;

  /**
   * Crea una carta con un palo y rango específicos. * @param suit Palo de la carta.
   * 
   * @param rank Rango de la carta.
   */
  public Card(Suit suit, Rank rank) {
    this.suit = suit;
    this.rank = rank;
  }

  /**
   * Obtiene el valor numérico de la carta para el conteo de puntos. * @return Valor entero de la carta.
   */
  public int getValue() {
    return rank.getValue();
  }

  /**
   * Obtiene el palo de la carta. Necesario para verificar combinaciones y escaleras. * @return El Enum Suit de la carta.
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * Obtiene el rango de la carta. * @return El Enum Rank de la carta.
   */
  public Rank getRank() {
    return rank;
  }

  /**
   * Representación textual de la carta con color. Ejemplo: "7 de Copas" (en rojo). * @return String formateado.
   */
  @Override
  public String toString() {
    // Usamos el valor numérico y el símbolo coloreado del palo
    return rank.getValue() + " de " + suit.getColoredSymbol();
  }
}
