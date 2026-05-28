package chinchon.model;

/**
 * Fábrica para crear participantes de la partida.
 * @author Alejandro Vega
 */
public class MemberFactory {

  /**
   * Crea un participante según el tipo indicado.
   * 
   * @param type Tipo de participante ("player" o "machine").
   * @param name Nombre del participante.
   * @return Participante creado.
   */
  public static Member createMember(String type, String name) {

    if (type.equalsIgnoreCase("player")) {
      return new Player(name);
    }

    if (type.equalsIgnoreCase("machine")) {
      return new Machine(name);
    }

    throw new IllegalArgumentException("Tipo de participante no válido");
  }
}
