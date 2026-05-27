package chinchon.app;

import chinchon.model.Game;
import chinchon.model.MemberFactory;
import chinchon.util.Console;

public class Main {
    public static void main(String[] args) {
        Console console = new Console();

        console.println("=== BIENVENIDO AL CHINCHÓN ===");
        
        // 1. Pedimos el límite de puntos
        console.println("Introduce el límite de puntos para la partida (ej. 100):");
        int limitePuntos = Integer.parseInt(console.readLine());

        // 2. Pedimos el número de mazos
        console.println("Introduce el número de barajas/mazos con los que jugar (ej. 1 o 2):");
        int numeroMazos = Integer.parseInt(console.readLine());

        // 3. Inicializamos la instancia única de Game con los datos introducidos
        Game juego = Game.getInstance(limitePuntos, numeroMazos, console);

        // 4. Pedimos el número de jugadores para crearlos con la Factory
        console.println("¿Cuántos jugadores humanos van a jugar?");
        int numHumanos = Integer.parseInt(console.readLine());

        console.println("¿Cuántos oponentes máquina (IA) quieres añadir?");
        int numMaquinas = Integer.parseInt(console.readLine());

        // 5. Creamos y añadimos los jugadores usando "PLAYER" y "MACHINE"
        for (int i = 1; i <= numHumanos; i++) {
            juego.addPlayer(MemberFactory.createMember("PLAYER", "Jugador " + i));
        }

        for (int i = 1; i <= numMaquinas; i++) {
            juego.addPlayer(MemberFactory.createMember("MACHINE", "CPU " + i));
        }

        // 6. Arrancamos el motor del juego
        juego.startGame();
    }
}
