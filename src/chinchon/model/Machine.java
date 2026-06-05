package chinchon.model;

import java.util.List;

import chinchon.util.ConsoleInput;

/**
 * Máquina con IA para jugar al Chinchón.
 * @author Alejandro Vega
 */
public class Machine extends Member {

  public Machine(String name) {
    super(name);
  }
  /**
   * Ejecuta la lógica completa del turno del participante (robar, actualizar mano y descartar).
   * Cada subclase define el comportamiento según su naturaleza (humano o máquina).
   * 
   * @param deck        El mazo de cartas de la partida listo para robar.
   * @param discardPile La pila de cartas descartadas por los jugadores.
   * @param console     La utilidad para interactuar con la consola de forma segura.
   */
  @Override
  public boolean playTurn(Deck deck, List<Card> discardPile, ConsoleInput console) {
    System.out.printf("\n--- Turno de la CPU: %s ---\n", getName());
    Card drawn = deck.drawCard();
    getHand().addCard(drawn);
    
    if (getHand().canClose()) {
      int index = chooseDiscardIndex();
      getHand().removeCard(index);
      System.out.printf("\n🚪 ¡La CPU %s HA CERRADO LA RONDA! Se procede al recuento de puntos.\n", getName());
      return true; // Cierra
    }
    
    int index = chooseDiscardIndex();
    Card discarded = getHand().removeCard(index);
    discardPile.add(discarded);
    System.out.printf("%s ha robado una carta y ha descartado: %s\n", getName(), discarded);
    
    return false; // Continúa
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
