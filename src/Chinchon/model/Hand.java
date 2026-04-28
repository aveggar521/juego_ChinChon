package Chinchon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la mano de cartas de un jugador.
 */
public class Hand {

  private List<Card> cards;

  public Hand() {
    cards = new ArrayList<>();
  }

  public void addCard(Card card) {
    cards.add(card);
  }

  public Card removeCard(int index) {
    return cards.remove(index);
  }

  public List<Card> getCards() {
    return cards;
  }

  public int getSize() {
    return cards.size();
  }

  public void clear() {
    cards.clear();
  }

  public int calculatePoints() {
    int points = 0;

    for (Card card : cards) {
      points += card.getValue();
    }

    return points;
  }

  public boolean hasChinchon() {
    return false; // pendiente lógica completa
  }

  public boolean canClose() {
    return false; // pendiente lógica completa
  }

  /**
   * Muestra la mano con índices para evitar errores al descartar.
   */
  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < cards.size(); i++) {
      sb.append(i).append(": ").append(cards.get(i)).append("\n");
    }

    return sb.toString();
  }
}
