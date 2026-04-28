package Chinchon.model;

import java.util.List;

/**
 * Máquina con IA simple para jugar al Chinchón.
 */
public class Machine extends Member {

  public Machine(String name) {
    super(name);
  }

  @Override
  public void playTurn() {
    // La lógica REAL está en Game (executeTurn)
  }

  /**
   * Decide qué carta descartar de forma simple.
   * 
   * @return índice de la carta a descartar.
   */
  public int chooseDiscardIndex() {

    List<Card> cards = getHand().getCards();

    int worstIndex = 0;
    int worstValue = cards.get(0).getValue();

    for (int i = 1; i < cards.size(); i++) {
      int value = cards.get(i).getValue();

      if (value > worstValue) {
        worstValue = value;
        worstIndex = i;
      }
    }

    return worstIndex;
  }
}
