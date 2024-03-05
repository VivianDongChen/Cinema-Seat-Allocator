import java.util.Objects;

/**
 * Class Seat contains information about name, which is a string value representing a capital letter
 * from A to Z, and “reserved for” value representing the name of the person for whom it has been
 * reserved, or null if the seat has not been reserved.
 */
public class Seat {
  private String name;
  private String reservedFor;

  /**
   * Constructor for class Seat.
   * @param name a string value representing a capital letter from A to Z
   */
  public Seat(String name) {
    if (!validName(name)) {
      throw new IllegalArgumentException("Seat name should be a capital letter from A to Z.");
    }
    this.name = name;
    this.reservedFor = null;
  }

  /**
   * Private helper method to verify if the name is a capital letter from A to Z.
   * @param name a string value
   * @return true if it is a capital letter from A to Z, false otherwise
   */
  private boolean validName(String name) {
    return name.matches("^[A-Z]$");
  }

  /**
   * Getter for name.
   * @return name, as a String
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for reserved for.
   * @return reserved for, as a String
   */
  public String getReservedFor() {
    return reservedFor;
  }

  /**
   * Setter for reserved for.
   * @param reservedFor the name of the person for whom it has been reserved, as a String
   */
  public void setReservedFor(String reservedFor) {
    this.reservedFor = reservedFor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Seat seat = (Seat) o;
    return Objects.equals(name, seat.name) && Objects.equals(reservedFor,
        seat.reservedFor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, reservedFor);
  }

  @Override
  public String toString() {
    return "Seat{" +
        "name='" + name + '\'' +
        ", reservedFor='" + reservedFor + '\'' +
        '}';
  }
}
