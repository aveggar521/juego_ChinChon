package Chinchon.model;

public class MemberFactory {

  public static Member createMember(String type, String name) {
    if (type.equalsIgnoreCase("player")) {
      return new Player(name);
    } else if (type.equalsIgnoreCase("machine")) {
      return new Machine(name);
    }
    throw new illegalArgumentException("Tipo no válido");
  }
}
