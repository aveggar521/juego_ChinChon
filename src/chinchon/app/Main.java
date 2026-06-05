package chinchon.app;

import chinchon.model.Game;
import chinchon.model.Machine;
import chinchon.model.Player;
import chinchon.util.Colors;
import chinchon.util.ConsoleInput;
import java.util.Scanner;

/**
 * Clase principal que arranca la ejecución del juego del Chinchón.
 * @author Alejandro Vega
 */
public class Main {

  public static void main(String[] args) {
    // Inicializamos el escáner y la herramienta de lectura segura
    Scanner scanner = new Scanner(System.in);
    ConsoleInput console = new ConsoleInput(scanner);

    // 1. Imprimimos nuestra flamante portada estética con colores centralizados
    System.out.println(Colors.VERDE + "♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣" + Colors.RESET);
    System.out.println(Colors.AMARILLO + "♦" + Colors.RESET + "                                                                    " + Colors.ROJO + "♥" + Colors.RESET);
    System.out.println("        " + Colors.AZUL + "♠" + Colors.RESET + "  B I E N V E N I D O   A L   C H I N C H Ó N  " + Colors.VERDE + "♣" + Colors.RESET);
    System.out.println("                     [ Versión Polimórfica ]                    ");
    System.out.println(Colors.ROJO + "♥" + Colors.RESET + "                                                                    " + Colors.AMARILLO + "♦" + Colors.RESET);
    System.out.println(Colors.VERDE + "♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣" + Colors.RESET);
    System.out.println();

    // 2. Configuración del límite de puntos (mínimo 10 para que tenga sentido jugar)
    System.out.println("Introduce el límite de puntos para la partida (ej. 100):");
    int limitPoints = console.readIntInRange(10, 500);

    // 3. Configuración del número de barajas (1 o 2 mazos)
    System.out.println("Introduce el número de barajas/mazos con los que jugar (1 o 2):");
    int numberDecks = console.readIntInRange(1, 2);

    // 4. Configuración de jugadores humanos
    System.out.println("¿Cuántos jugadores humanos van a jugar? (Mínimo 1, Máximo 4):");
    int humanPlayers = console.readIntInRange(1, 4);

    // 5. Configuración de oponentes máquina
    // Si hay 1 humano, obligamos a que haya mínimo 1 máquina para que se pueda jugar
    int minMachines = (humanPlayers == 1) ? 1 : 0;
    int maxMachines = 4 - humanPlayers; // No permitimos más de 4 jugadores en total en la mesa
    
    int aiPlayers = 0;
    if (maxMachines > 0) {
        System.out.printf("¿Cuántos oponentes máquina (IA) quieres añadir? (Mínimo %d, Máximo %d):\n", minMachines, maxMachines);
        aiPlayers = console.readIntInRange(minMachines, maxMachines);
    } else {
        System.out.println("Mesa llena con jugadores humanos. No se añaden máquinas.");
    }

    System.out.println("\n🎲 ¡Configuración completada con éxito! Creando tablero de juego...");

    // 6. Arrancamos la partida utilizando el patrón Singleton de tu clase Game
 // 6. Arrancamos la partida utilizando el patrón Singleton de tu clase Game
    Game game = Game.getInstance(limitPoints, numberDecks, console);
    
    // Añadimos dinámicamente los participantes configurados creando los objetos correspondientes
    for (int i = 1; i <= humanPlayers; i++) {
      game.addMember(new Player("Jugador " + i));
    }
    for (int i = 1; i <= aiPlayers; i++) {
      game.addMember(new Machine("CPU " + i));
    }

    game.startGame();
    
    scanner.close();
  }
}