package Chinchon.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
  private List<Card> cards = new ArrayList<>();

  public Deck() {
    fill();
    shuffle();
  }

  /**
   * Llena la baraja de cartas nuevas.
   */
  private void fill() {
    for (Suit suit : Suit.values()) {
      for (Rank rank : Rank.values()) {
        Card card = new Card(suit, rank);
        cards.add(card);
      }
    }
  }

  /**
   * Desordena las cartas de la baraja.
   */
  private void shuffle() {
    Collections.shuffle(cards);
  }

  @Override
  public String toString() {
    String message = cards.size() == 1 ? "carta restante" : "cartas restantes";
    return String.format("%d %s", cards.size(), message);
  }
}
