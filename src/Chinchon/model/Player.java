package Chinchon.model;

public class Player extends Member {
  public Player(String name) {
    super(name);
  }

  @Override
  public String toString() {
    return String.format("%s: %s (Balance: %d)", name, hand);
  }
}
