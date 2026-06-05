package chinchon.util;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Clase encargada de gestionar la entrada de datos por teclado de forma segura.
 * Controla las excepciones de tipo de datos y valida los rangos requeridos.
 * @author Alejandro Vega
 * 
 */
public class ConsoleInput {

    /**
     * Objeto Scanner para leer desde el teclado.
     */
    private Scanner keyboard;

    /**
     * Constructor que recibe el Scanner del sistema.
     * @param keyboard Instancia de Scanner para asociar a la clase.
     */
    public ConsoleInput(Scanner keyboard) {
        this.keyboard = keyboard;
    }

    /**
     * Limpia el búfer del teclado para evitar lecturas incorrectas o saltos de línea.
     */
    private void cleanInput() {
        if (this.keyboard.hasNextLine()) {
            this.keyboard.nextLine();
        }
    }

    /**
     * Lee un número entero de forma segura.
     * * @return El número entero introducido por el usuario.
     */
    public int readInt() {
        int value = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                value = this.keyboard.nextInt();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.print("Error: Debe introducir un número entero válido. Inténtelo de nuevo: ");
            } finally {
                this.cleanInput();
            }
        }
        return value;
    }

    /**
     * Lee un entero estrictamente menor que el límite superior.
     * * @param upperBound Límite superior no incluido.
     * @return El número entero válido menor que upperBound.
     */
    public int readIntLessThan(int upperBound) {
        int value = this.readInt();
        while (value >= upperBound) {
            System.out.print("Error: El número debe ser menor que " + upperBound + ". Inténtelo de nuevo: ");
            value = this.readInt();
        }
        return value;
    }

    /**
     * Lee un entero menor o igual que el límite superior.
     * * @param upperBound Límite superior incluido.
     * @return El número entero válido menor o igual que upperBound.
     */
    public int readIntLessOrEqualThan(int upperBound) {
        int value = this.readInt();
        while (value > upperBound) {
            System.out.print("Error: El número debe ser menor o igual que " + upperBound + ". Inténtelo de nuevo: ");
            value = this.readInt();
        }
        return value;
    }

    /**
     * Lee un entero estrictamente mayor que el límite inferior.
     * * @param lowerBound Límite inferior no incluido.
     * @return El número entero válido mayor que lowerBound.
     */
    public int readIntGreaterThan(int lowerBound) {
        int value = this.readInt();
        while (value <= lowerBound) {
            System.out.print("Error: El número debe ser mayor que " + lowerBound + ". Inténtelo de nuevo: ");
            value = this.readInt();
        }
        return value;
    }

    /**
     * Lee un entero mayor o igual que el límite inferior.
     * * @param lowerBound Límite inferior incluido.
     * @return El número entero válido mayor o igual que lowerBound.
     */
    public int readIntGreaterOrEqualThan(int lowerBound) {
        int value = this.readInt();
        while (value < lowerBound) {
            System.out.print("Error: El número debe ser mayor o igual que " + lowerBound + ". Inténtelo de nuevo: ");
            value = this.readInt();
        }
        return value;
    }

    /**
     * Lee un entero dentro de un rango cerrado [lowerBound, upperBound].
     * * @param lowerBound Límite inferior incluido.
     * @param upperBound Límite superior incluido.
     * @return El número entero válido dentro del rango.
     */
    public int readIntInRange(int lowerBound, int upperBound) {
        int value = this.readInt();
        while (value < lowerBound || value > upperBound) {
            System.out.print("Error: El número debe estar entre " + lowerBound + " y " + upperBound + ". Inténtelo de nuevo: ");
            value = this.readInt();
        }
        return value;
    }

    /**
     * Lee un número decimal (double) de forma segura.
     * * @return El número decimal introducido por el usuario.
     */
    public double readDouble() {
        double value = 0.0;
        boolean isValid = false;

        while (!isValid) {
            try {
                value = this.keyboard.nextDouble();
                isValid = true;
            } catch (InputMismatchException e) {
                System.out.print("Error: Debe introducir un número decimal válido. Inténtelo de nuevo: ");
            } finally {
                this.cleanInput();
            }
        }
        return value;
    }

    /**
     * Lee un decimal estrictamente menor que el límite superior.
     * * @param upperBound Límite superior no incluido.
     * @return El número decimal válido menor que upperBound.
     */
    public double readDoubleLessThan(double upperBound) {
        double value = this.readDouble();
        while (value >= upperBound) {
            System.out.print("Error: El número debe ser menor que " + upperBound + ". Inténtelo de nuevo: ");
            value = this.readDouble();
        }
        return value;
    }

    /**
     * Lee un decimal menor o igual que el límite superior.
     * * @param upperBound Límite superior incluido.
     * @return El número decimal válido menor o igual que upperBound.
     */
    public double readDoubleLessOrEqualThan(double upperBound) {
        double value = this.readDouble();
        while (value > upperBound) {
            System.out.print("Error: El número debe ser menor o igual que " + upperBound + ". Inténtelo de nuevo: ");
            value = this.readDouble();
        }
        return value;
    }

    /**
     * Lee un decimal estrictamente mayor que el límite inferior.
     * * @param lowerBound Límite inferior no incluido.
     * @return El número decimal válido mayor que lowerBound.
     */
    public double readDoubleGreaterThan(double lowerBound) {
        double value = this.readDouble();
        while (value <= lowerBound) {
            System.out.print("Error: El número debe ser mayor que " + lowerBound + ". Inténtelo de nuevo: ");
            value = this.readDouble();
        }
        return value;
    }

    /**
     * Lee un decimal mayor o igual que el límite inferior.
     * * @param lowerBound Límite inferior incluido.
     * @return El número decimal válido mayor o igual que lowerBound.
     */
    public double readDoubleGreaterOrEqualThan(double lowerBound) {
        double value = this.readDouble();
        while (value < lowerBound) {
            System.out.print("Error: El número debe ser mayor o igual que " + lowerBound + ". Inténtelo de nuevo: ");
            value = this.readDouble();
        }
        return value;
    }

    /**
     * Lee un decimal dentro de un rango cerrado [lowerBound, upperBound].
     * * @param lowerBound Límite inferior incluido.
     * @param upperBound Límite superior incluido.
     * @return El número decimal válido dentro del rango.
     */
    public double readDoubleInRange(double lowerBound, double upperBound) {
        double value = this.readDouble();
        while (value < lowerBound || value > upperBound) {
            System.out.print("Error: El número debe estar entre " + lowerBound + " y " + upperBound + ". Inténtelo de nuevo: ");
            value = this.readDouble();
        }
        return value;
    }

    /**
     * Lee una cadena de caracteres completa.
     * * @return La cadena de caracteres introducida por el usuario.
     */
    public String readString() {
        String input = this.keyboard.nextLine();
        while (input.trim().isEmpty()) {
            System.out.print("Error: El texto no puede estar vacío. Inténtelo de nuevo: ");
            input = this.keyboard.nextLine();
        }
        return input;
    }

    /**
     * Lee una cadena de caracteres validando que no supere la longitud máxima.
     * * @param maxLength Longitud máxima permitida de la cadena.
     * @return La cadena de caracteres válida.
     */
    public String readString(int maxLength) {
        String input = this.readString();
        while (input.length() > maxLength) {
            System.out.print("Error: El texto supera el límite de " + maxLength + " caracteres. Inténtelo de nuevo: ");
            input = this.readString();
        }
        return input;
    }

    /**
     * Lee un único carácter de forma segura. Si el usuario escribe más de uno, repite la solicitud.
     * * @return El carácter introducido por el usuario.
     */
    public char readChar() {
        String input = this.keyboard.nextLine();
        while (input.length() != 1) {
            System.out.print("Error: Debe introducir exactamente un carácter. Inténtelo de nuevo: ");
            input = this.keyboard.nextLine();
        }
        return input.charAt(0);
    }

    /**
     * Retorna un booleano basado en la respuesta afirmativa o negativa del usuario (insensible a mayúsculas/minúsculas).
     * * @param affirmativeValue Carácter que representa 'true' (ej: 'S').
     * @param negativeValue    Carácter que representa 'false' (ej: 'N').
     * @return true si coincide con affirmativeValue, false si coincide con negativeValue.
     */
    public boolean readBooleanUsingChar(char affirmativeValue, char negativeValue) {
        boolean result = false;
        boolean isValid = false;
        
        // Convertimos a mayúsculas los parámetros para una comparación robusta
        char upperAffirmative = Character.toUpperCase(affirmativeValue);
        char upperNegative = Character.toUpperCase(negativeValue);

        while (!isValid) {
            char userInput = Character.toUpperCase(this.readChar());

            if (userInput == upperAffirmative) {
                result = true;
                isValid = true;
            } else if (userInput == upperNegative) {
                result = false;
                isValid = true;
            } else {
                System.out.print("Error: Debe introducir '" + affirmativeValue + "' o '" + negativeValue + "'. Inténtelo de nuevo: ");
            }
        }
        return result;
    }
}