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
  public boolean playTurn(Deck deck, List<Card> discardPile, ConsoleInput console) {
    System.out.printf("\n--- Turno de: %s ---\n", getName());
    
    // 1. Mostrar la mano al inicio del turno (tiene 7 cartas)
    System.out.printf("Tu mano actual:\n%s\n", getHand().toString());
    
    String descarteInfo = discardPile.isEmpty() ? "Vacío" : discardPile.get(discardPile.size() - 1).toString();
    System.out.printf("¿De dónde quieres robar? -> 1. Mazo | 2. Descarte (%s)\n", descarteInfo);
    
    // 2. Ejecutar la acción de robar
    int choice = console.readIntInRange(1, 2);
    Card drawn;
    
    if (choice == 2 && !discardPile.isEmpty()) {
      drawn = discardPile.remove(discardPile.size() - 1);
    } else {
      if (choice == 2) {
        System.out.println("La pila de descartes estaba vacía. Robas del mazo automáticamente.");
      }
      drawn = deck.drawCard();
    }
    
    getHand().addCard(drawn); // Ahora el jugador tiene 8 cartas
    System.out.printf("Has robado: %s\n", drawn);
    System.out.printf("Tu mano con la nueva carta:\n%s\n", getHand().toString());
    
    // 3. Comprobar si puede y quiere cerrar la ronda voluntariamente
    if (getHand().canClose()) {
      System.out.print("⚠️ Tienes una combinación válida para cerrar la ronda. ¿Deseas cerrar? (S/N): ");
      boolean decesCerrar = console.readBooleanUsingChar('S', 'N');
      
      if (decesCerrar) {
        System.out.println("Elige el índice de la carta que dejas boca abajo para cerrar (1-8):");
        int index = console.readIntInRange(1, 8) - 1;
        Card discarded = getHand().removeCard(index);
        discardPile.add(discarded);
        
        System.out.printf("\n🚪 ¡%s HA CERRADO LA RONDA! Se procede al recuento de puntos.\n", getName());
        return true; // Finaliza el turno y rompe la ronda
      }
    }
    
    // 4. Si no puede cerrar (o no quiere), descarta de manera ordinaria
    System.out.println("Elige el índice de la carta que deseas descartar (1-8):");
    int index = console.readIntInRange(1, 8) - 1;
    Card discarded = getHand().removeCard(index);
    discardPile.add(discarded);
    System.out.printf("Has descartado: %s\n", discarded);
    
    return false; // La ronda continúa normal
  }
}