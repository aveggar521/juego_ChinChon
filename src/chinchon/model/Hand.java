package chinchon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la mano de cartas de un jugador y su lógica de combinaciones.
 * @author Alejandro Vega
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

  /**
   * Ordena las cartas por valor para facilitar la detección de escaleras. Utiliza el método de la burbuja para evitar herramientas no explicadas.
   */
  public void sortCards() {
    for (int i = 0; i < cards.size() - 1; i++) {
      for (int j = 0; j < cards.size() - i - 1; j++) {
        if (cards.get(j).getValue() > cards.get(j + 1).getValue()) {
          Card temp = cards.get(j);
          cards.set(j, cards.get(j + 1));
          cards.set(j + 1, temp);
        }
      }
    }
  }

  /**
   * Calcula los puntos de las cartas que NO están combinadas. 
   * 
   * @return Suma de valores de cartas sueltas.
   */
  public int calculatePoints() {
    boolean[] combined = getCombinedMask();
    int points = 0;

    for (int i = 0; i < cards.size(); i++) {
      if (!combined[i]) {
        points += cards.get(i).getValue();
      }
    }
    return points;
  }

  /**
   * Verifica si el jugador tiene Chinchón (7 cartas consecutivas del mismo palo).
   */
  public boolean hasChinchon() {
    boolean isChinchon = false;
    if (cards.size() == 7) {
      sortCards();
      boolean isSameSuit = true;
      boolean isConsecutive = true;
      Suit firstSuit = cards.get(0).getSuit();

      int i = 0;
      while (i < cards.size() - 1) {
        if (cards.get(i).getSuit() != firstSuit) {
          isSameSuit = false;
        }
        if (!areConsecutive(cards.get(i), cards.get(i + 1))) {
          isConsecutive = false;
        }
        i++;
      }
      isChinchon = isSameSuit && isConsecutive;
    }
    return isChinchon;
  }

  /**
   * Determina si el jugador puede cerrar la ronda.
   */
  public boolean canClose() {
    if (hasChinchon()) {
      return true;
    }
    // Para cerrar necesita 6 combinadas y una suelta <= 5, o las 7 combinadas.
    // Eso equivale a que los puntos de las cartas NO combinadas sean <= 5.
    return calculatePoints() <= 5;
  }

  /**
   * Método auxiliar para verificar si dos cartas son consecutivas respetando el salto de la baraja española (7 al 10).
   */
  private boolean areConsecutive(Card c1, Card c2) {
    int v1 = c1.getValue();
    int v2 = c2.getValue();
    if (v1 == 7) {
      return v2 == 10;
    }
    return v2 == v1 + 1;
  }

  /**
   * Crea una máscara booleana identificando qué cartas forman grupos (mismo número). Se marca como 'true' toda carta que pertenezca a un grupo de 3 o más.
   */
  private boolean[] getCombinedMask() {
    boolean[] combined = new boolean[cards.size()];
    sortCards();

    // Identificar grupos de cartas con el mismo valor (Tríos o Cuartetos)
    int i = 0;
    while (i < cards.size()) {
      int valueToCompare = cards.get(i).getValue();
      int count = 0;
      int j = 0;

      // Contamos cuántas cartas hay con el mismo valor
      while (j < cards.size()) {
        if (cards.get(j).getValue() == valueToCompare) {
          count++;
        }
        j++;
      }

      // Si hay 3 o más, marcamos todas las cartas de ese valor como combinadas
      if (count >= 3) {
        int k = 0;
        while (k < cards.size()) {
          if (cards.get(k).getValue() == valueToCompare) {
            combined[k] = true;
          }
          k++;
        }
      }
      i++;
    }

    return combined;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < cards.size(); i++) {
      sb.append((i + 1)).append(": ").append(cards.get(i)).append("\n");
    }
    return sb.toString();
  }
}
