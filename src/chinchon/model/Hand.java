package chinchon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa la mano de cartas de un jugador y su lógica matemática de combinaciones.
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
   * Ordena las cartas por valor para facilitar la detección de escaleras y grupos.
   * Utiliza el método de la burbuja para ajustarse al nivel del curso.
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
   * Calcula los puntos de las cartas que NO están combinadas en series ni escaleras.
   * * @return Suma de valores de cartas sueltas.
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
   * Es válido tanto si tiene 7 cartas como si tiene 8 (antes de descartar).
   * * @return true si localiza una racha perfecta de 7 cartas consecutivas del mismo palo.
   */
  public boolean hasChinchon() {
    sortCards();
    
    // Buscamos si existe alguna racha de 7 cartas consecutivas del mismo palo
    for (int start = 0; start <= cards.size() - 7; start++) {
      boolean isSameSuit = true;
      boolean isConsecutive = true;
      Suit firstSuit = cards.get(start).getSuit();

      int i = start;
      while (i < start + 6) {
        if (cards.get(i).getSuit() != firstSuit || cards.get(i + 1).getSuit() != firstSuit) {
          isSameSuit = false;
        }
        if (!areConsecutive(cards.get(i), cards.get(i + 1))) {
          isConsecutive = false;
        }
        i++;
      }

      if (isSameSuit && isConsecutive) {
        return true;
      }
    }
    return false;
  }

  /**
   * Determina si el jugador puede cerrar la ronda (puntos sueltos <= 5).
   * * @return true si cumple las condiciones para cerrar.
   */
  public boolean canClose() {
    if (hasChinchon()) {
      return true;
    }
    return calculatePoints() <= 5;
  }

  /**
   * Método auxiliar para verificar si dos cartas son consecutivas respetando 
   * el salto de la baraja española tradicional (del 7 al 10).
   * * @param c1 Primera carta.
   * @param c2 Segunda carta (debería ser la inmediata superior).
   * @return true si son consecutivas numéricamente en la baraja.
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
   * Crea una máscara booleana identificando qué cartas forman combinaciones válidas.
   * Evalúa tanto grupos (mismo número) como escaleras (mismo palo y consecutivas).
   * * @return Un array booleano indexado igual que la lista de cartas.
   */
  /**
   * Crea una máscara booleana identificando qué cartas forman combinaciones válidas.
   * Evalúa tanto grupos (mismo número) como escaleras (mismo palo y consecutivas).
   * @return Un array booleano indexado igual que la lista de cartas.
   */
  private boolean[] getCombinedMask() {
    boolean[] combined = new boolean[cards.size()];
    sortCards();

    // --- PASO 1: DETECTAR GRUPOS (Mismo valor numérico, mínimo 3 cartas) ---
    int i = 0;
    while (i < cards.size()) {
      int valueToCompare = cards.get(i).getValue();
      int count = 0;

      for (Card c : cards) {
        if (c.getValue() == valueToCompare) {
          count++;
        }
      }

      if (count >= 3) {
        for (int k = 0; k < cards.size(); k++) {
          if (cards.get(k).getValue() == valueToCompare) {
            combined[k] = true;
          }
        }
      }
      i++;
    }

    // --- PASO 2: DETECTAR ESCALERAS (Mismo palo y consecutivas, mínimo 3 cartas) ---
    // Recorremos cada carta como posible inicio de una escalera
    for (int start = 0; start < cards.size(); start++) {
      List<Integer> rachaIndices = new ArrayList<>();
      rachaIndices.add(start);
      
      int current = start;
      // Buscamos hacia adelante cartas que continúen la secuencia del mismo palo
      for (int next = start + 1; next < cards.size(); next++) {
        Card cCurrent = cards.get(current);
        Card cNext = cards.get(next);
        
        if (cNext.getSuit() == cCurrent.getSuit() && areConsecutive(cCurrent, cNext)) {
          rachaIndices.add(next);
          current = next; // Avanzamos el listón de la racha
        }
      }
      
      // Si encontramos una racha consecutiva de 3 o más, las marcamos
      if (rachaIndices.size() >= 3) {
        for (int index : rachaIndices) {
          combined[index] = true;
        }
      }
    }

    return combined;
  }
  @Override
  public String toString() {
    sortCards(); 
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < cards.size(); i++) {
      sb.append((i + 1)).append(": ").append(cards.get(i)).append("\n");
    }
    return sb.toString();
  }
}