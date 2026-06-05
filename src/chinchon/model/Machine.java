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
  public boolean playTurn(Deck deck, List<Card> discardPile, ConsoleInput console, int turnCount, int totalMembers) {
      System.out.printf("\n--- Turno de %s (IA). Su mano actual es: \n", getName());
      System.out.println(getHand());
      
      if (!discardPile.isEmpty()) {
          System.out.println("Carta en la pila de descartes: " + discardPile.get(discardPile.size() - 1));
      }

      Card cartaRobada;
      boolean prefiereMazo = true;
      
      if (!discardPile.isEmpty()) {
          Card cartaDescarte = discardPile.get(discardPile.size() - 1);
          if (cartaDescarte.getValue() <= 5) {
              prefiereMazo = false;
          }
      }
      
      if (prefiereMazo) {
          cartaRobada = deck.drawCard();
          System.out.printf("🤖 %s ha robado del mazo: %s\n", getName(), cartaRobada);
      } else {
          cartaRobada = discardPile.remove(discardPile.size() - 1);
          System.out.printf("📥 %s ha recogido del descarte: %s\n", getName(), cartaRobada);
      }
      getHand().addCard(cartaRobada);

      boolean aiWantsToClose = false;
      if (turnCount >= totalMembers) {
          int uncombinedPoints = getHand().calculatePoints();
          if (uncombinedPoints <= 5) {
              aiWantsToClose = true;
          }
      }

      System.out.printf("%s está evaluando su mano tras robar: \n", getName());
      System.out.println(getHand());

      if (!aiWantsToClose) {
          int indiceDescarte = chooseDiscardIndex();
          Card cartaDescartada = getHand().removeCard(indiceDescarte);
          System.out.printf("📤 %s ha descartado: %s\n", getName(), cartaDescartada);
          discardPile.add(cartaDescartada);
      } else {
          int indiceDescarte = chooseDiscardIndex();
          Card cartaDescartada = getHand().removeCard(indiceDescarte);
          discardPile.add(cartaDescartada);
      }

      System.out.println("------------------------------------------------");

      return aiWantsToClose;
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
