package chinchon.model;

import java.util.List;

import chinchon.util.ConsoleInput;

/**
 * Representa a un jugador humano.
 * @author Alejandro Vega
 */
public class Player extends Member {

  public Player(String name) {
    super(name);
  }

  @Override
  public boolean playTurn(Deck deck, List<Card> discardPile, ConsoleInput console, int turnCount, int totalMembers) {
	  System.out.printf("\n--- Turno de %s. Tu mano actual es: ", getName());
	  System.out.println(getHand());
      
      if (!discardPile.isEmpty()) {
          System.out.println("Carta en la pila de descartes: " + discardPile.get(discardPile.size() - 1));
      }

      System.out.print("Robar del mazo 1 | robar del descarte 2: ");
      boolean opcionRobo = console.readBooleanUsingChar('1', '2');
      
      Card cartaRobada;
      if (opcionRobo) {
          cartaRobada = deck.drawCard();
          System.out.println("Has robado del mazo: " + cartaRobada);
      } else {
          cartaRobada = discardPile.remove(discardPile.size() - 1);
          System.out.println("Has recogido del descarte: " + cartaRobada);
      }
      getHand().addCard(cartaRobada);
      boolean wantsToClose = false;

      if (turnCount >= totalMembers) {
          System.out.print("¿Quieres cerrar la ronda en este turno? (S/N): ");
          wantsToClose = console.readBooleanUsingChar('S', 'N');

          if (wantsToClose) {
              int uncombinedPoints = getHand().calculatePoints();
              if (uncombinedPoints > 5) {
                  System.out.printf("❌ No puedes cerrar. Tu carta suelta vale %d puntos (MÁXIMO PERMITIDO: 5).\n", uncombinedPoints);
                  wantsToClose = false; 
              }
          }
      }
      System.out.println("Tu mano actual tras robar: ");
      System.out.println(getHand()); 
      
      System.out.print("Elige la carta que quieres descartar (1 a 8): ");
      int indiceDescarteHumano = console.readIntInRange(1, 8);
      
      discardPile.add(getHand().removeCard(indiceDescarteHumano - 1));

      return wantsToClose;
  }
}