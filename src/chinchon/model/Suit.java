package chinchon.model;

import chinchon.util.Colors;
/**
 * Enum que representa los palos de una carta.
 * @author Alejandro Vega
 */
public enum Suit {
  COINS("Moneda", Colors.AMARILLO), CUPS("Copa", Colors.ROJO), STAVES("Basto", Colors.VERDE),
  SWORDS("Espada", Colors.AZUL);

  private String symbol;
  private String color;

  Suit(String symbol, String color) {
    this.symbol = symbol;
    this.color = color;
  }

  public String getColoredSymbol() {
    return color + symbol + Colors.RESET;
  }

  /**
   * Obtiene el símbolo del palo.
   * 
   * @return Símbolo del palo
   */
  public String getSymbol() {
    return symbol;
  }
}
