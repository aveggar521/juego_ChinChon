package Chinchon.model;

/**
 * Enum que representa los palos de una carta.
 */
public enum Suit {
  COINS("Moneda", "\u001B[33m"), CUPS("Copa", "\u001B[31m"), STAVES("Basto", "\u001B[32m"),
  SWORDS("Espada", "\u001B[34m");

  private String symbol;
  private String color;

  Suit(String symbol, String color) {
    this.symbol = symbol;
    this.color = color;
  }

  public String getColoredSymbol() {
    return color + symbol + "\u001B[0m";
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
