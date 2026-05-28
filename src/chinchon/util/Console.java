package chinchon.util;

import java.util.Scanner;

/**
 * Clase para gestionar la entrada y salida de datos por consola.
 * @author Alejandro Vega
 */
public class Console {

  /**
   * Scanner interno para la lectura de la entrada estándar (teclado).
   */
  private Scanner scanner;

  /**
   * Constructor que inicializa el Scanner vinculado a System.in.
   */
  public Console() {
    this.scanner = new Scanner(System.in);
  }

  /**
   * Imprime un mensaje en la consola sin salto de línea.
   * 
   * @param message El texto a mostrar.
   */
  public void print(String message) {
    System.out.print(message);
  }

  /**
   * Imprime un mensaje en la consola seguido de un salto de línea.
   * 
   * @param message El texto a mostrar.
   */
  public void println(String message) {
    System.out.println(message);
  }

  /**
   * Lee una línea completa de texto introducida por el usuario.
   * 
   * @return El String introducido por el teclado.
   */
  public String readLine() {
    return scanner.nextLine();
  }

  /**
   * Cierra el recurso Scanner. Debe llamarse al finalizar el programa para liberar el flujo de entrada.
   */
  public void close() {
    scanner.close();
  }
}
