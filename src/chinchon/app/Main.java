package chinchon.app;

import java.util.Scanner;

import chinchon.model.Game;
import chinchon.model.MemberFactory;
import chinchon.util.ConsoleInput;

/**
 * Clase principal que actúa como el punto de entrada de la aplicación.
 * Se encarga de gestionar la interfaz inicial por consola, solicitar los
 * parámetros de configuración al usuario y arrancar el motor del juego Chinchón.
 * * @author Alejandro Vega
 */
public class Main {

    /**
     * Método de entrada de la aplicación (main).
     * Controla el flujo inicial: lectura de configuración (puntos, mazos y jugadores),
     * inicialización del juego mediante el patrón Singleton y creación de participantes
     * utilizando una Factoría.
     * * @param args Argumentos de la línea de comandos (no se utilizan en este proyecto).
     */
    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
    	
        ConsoleInput console = new ConsoleInput(scanner);

        String RESET = "\u001B[0m";
        String ROJO = "\u001B[31m";
        String VERDE = "\u001B[32m";
        String AMARILLO = "\u001B[33m";
        String AZUL = "\u001B[34m";

        System.out.println(VERDE + "♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣" + RESET);
        System.out.println(AMARILLO + "♦" + RESET + "                                                                    " + ROJO + "♥" + RESET);
        System.out.println("        " + AZUL + "♠" + RESET + "  B I E N V E N I D O   A L   C H I N C H Ó N  " + VERDE + "♣" + RESET);
        System.out.println("                                        ");
        System.out.println(ROJO + "♥" + RESET + "                                                                    " + AMARILLO + "♦" + RESET);
        System.out.println(VERDE + "♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣ ♣" + RESET);
        System.out.println();
        
        // 1. Pedimos el límite de puntos
        System.out.println("Introduce el límite de puntos para la partida (ej. 100):");
        int limitePuntos = console.readIntGreaterOrEqualThan(0);

        // 2. Pedimos el número de mazos
        System.out.println("Introduce el número de barajas/mazos con los que jugar (ej. 1 o 2):");
        int numeroMazos = console.readIntInRange(1, 2);

        // 3. Inicializamos la instancia única de Game con los datos introducidos
        Game juego = Game.getInstance(limitePuntos, numeroMazos, console);

        // 4. Pedimos el número de jugadores para crearlos con la Factory
        System.out.println("¿Cuántos jugadores humanos van a jugar?");
        int numHumanos = console.readIntGreaterOrEqualThan(0);

        System.out.println("¿Cuántos oponentes máquina (IA) quieres añadir?");
        int numMaquinas = console.readIntGreaterOrEqualThan(0);

        // 5. Creamos y añadimos los jugadores usando "PLAYER" y "MACHINE"
        for (int i = 1; i <= numHumanos; i++) {
            juego.addPlayer(MemberFactory.createMember("PLAYER", "Jugador " + i));
        }

        for (int i = 1; i <= numMaquinas; i++) {
            juego.addPlayer(MemberFactory.createMember("MACHINE", "CPU " + i));
        }

        // 6. Arrancamos el motor del juego
        juego.startGame();
        scanner.close();   
        }
}