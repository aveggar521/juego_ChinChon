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
      System.out.printf("\n--- Turno de %s (IA) ---\n", getName());
      
      // 1. FASE DE ROBO: La IA decide de dónde robar de forma inteligente
      Card cartaRobada;
      boolean prefiereMazo = true;
      
      // Si la pila de descartes no está vacía, evalúa si la carta es buena (valor bajo <= 5)
      if (!discardPile.isEmpty()) {
          Card cartaDescarte = discardPile.get(discardPile.size() - 1);
          if (cartaDescarte.getValue() <= 5) {
              prefiereMazo = false; // Le sirve, la recoge
          }
      }
      
      if (prefiereMazo) {
          cartaRobada = deck.drawCard();
          System.out.printf("🤖 %s ha robado una carta del mazo.\n", getName());
      } else {
          cartaRobada = discardPile.remove(discardPile.size() - 1);
          System.out.printf("🤖 %s ha recogido del descarte: %s\n", getName(), cartaRobada);
      }
      getHand().addCard(cartaRobada);

      // 2. FASE DE DECISIÓN DE CIERRE: ¿Cumple los requisitos para cerrar?
      boolean aiWantsToClose = false;
      if (turnCount >= totalMembers) {
          int uncombinedPoints = getHand().calculatePoints();
          if (uncombinedPoints <= 5) {
              aiWantsToClose = true;
          }
      }

      // 3. FASE DE DESCARTE: Si no cierra la ronda, obligatoriamente debe descartar
      if (!aiWantsToClose) {
          int indiceDescarte = chooseDiscardIndex();
          Card cartaDescartada = getHand().removeCard(indiceDescarte);
          System.out.printf("📤 %s ha descartado una carta.\n", getName());
          discardPile.add(cartaDescartada);
      } else {
          // Si va a cerrar, el enunciado dice que "al cerrar ya se está descartando",
          // tiramos su peor carta para finalizar con 7 cartas en mano
          int indiceDescarte = chooseDiscardIndex();
          discardPile.add(getHand().removeCard(indiceDescarte));
      }

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
