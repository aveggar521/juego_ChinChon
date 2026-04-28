package Chinchon.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa el mazo de cartas.
 */
public class Deck {

  /**
   * Lista de cartas del mazo.
   */
  private List<Card> cards;

  /**
   * Número de barajas.
   */
  private int numberOfDecks;

  /**
   * Crea un mazo con un número de barajas.
   * 
   * @param numberOfDecks Número de barajas.
   */
  public Deck(int numberOfDecks) {
    this.numberOfDecks = numberOfDecks;
    this.cards = new ArrayList<>();
    initializeDeck();
    shuffle();
  }

  /**
   * Inicializa el mazo con las cartas.
   */
  public void initializeDeck() {
    cards.clear();

    for (int i = 0; i < numberOfDecks; i++) {
      for (Suit suit : Suit.values()) {
        for (Rank rank : Rank.values()) {
          cards.add(new Card(suit, rank));
        }
      }
    }
  }

  /**
   * Baraja las cartas.
   */
  public void shuffle() {
    Collections.shuffle(cards);
  }

  /**
   * Roba una carta del mazo.
   * 
   * @return Carta robada.
   */
  public Card drawCard() {
    if (cards.isEmpty()) {
      return null;
    }
    return cards.remove(0);
  }

  /**
   * Comprueba si el mazo está vacío.
   * 
   * @return true si está vacío, false en caso contrario.
   */
  public boolean isEmpty() {
    return cards.isEmpty();
  }

  /**
   * Obtiene el número de cartas restantes.
   * 
   * @return Número de cartas.
   */
  public int size() {
    return cards.size();
  }
}
