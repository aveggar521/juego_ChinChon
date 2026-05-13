package chinchon.app;

import chinchon.model.Game;
import chinchon.model.Member;
import chinchon.model.MemberFactory;
import chinchon.util.Console;

public class Main {
  public static void main(String[] args) {
    // 1. Instanciar la consola (la herramienta que usará todo el juego)
    Console console = new Console();

    try {
      console.println("=== BIENVENIDO AL CHINCHÓN ===");

      // 2. Configuración inicial
      int puntosLimite = 100;
      int numeroDeMazos = 1;

      // 3. Obtener la instancia del juego pasando la consola (Singleton)
      Game game = Game.getInstance(puntosLimite, numeroDeMazos, console);

      // 4. Crear jugadores usando la Factoría
      Member p1 = MemberFactory.createMember("player", "Jugador 1");
      Member machine = MemberFactory.createMember("machine", "IA de Entrenamiento");

      // 5. Añadir los jugadores a la partida
      game.addPlayer(p1);
      game.addPlayer(machine);

      // 6. ¡Comenzar la partida!
      game.startGame();

    } catch (Exception e) {
      console.println("Se ha producido un error inesperado: " + e.getMessage());
    } finally {
      // 7. Cerrar la consola al terminar (si tu clase Console tiene close())
      console.close();
    }
  }
}
