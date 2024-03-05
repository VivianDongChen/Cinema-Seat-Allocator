import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * Class Row is a list of seats as it extends ArrayList of Seat objects. It contains information
 * about a row number (1 is closest to the screen, etc.) and whether it is wheelchair accessible.
 */
public class Row extends ArrayList<Seat> {
  private int rowNumber;
  private boolean wheelchairAccessible;

  /**
   * Constructor for class Row.
   * @param rowNumber row number, as a positive integer
   * @param wheelchairAccessible is wheelchair accessible, as a boolean
   */
  public Row(int rowNumber, boolean wheelchairAccessible) {
    if (rowNumber < 1) {
      throw new IllegalArgumentException("Row number must be a positive integer.");
    }
    this.rowNumber = rowNumber;
    this.wheelchairAccessible = wheelchairAccessible;
  }

  /**
   * Getter for row number.
   * @return row number, as a positive integer
   */
  public int getRowNumber() {
    return rowNumber;
  }

  /**
   * Getter for wheelchairAccessible.
   * @return is wheelchair accessible, as a boolean
   */
  public boolean isWheelchairAccessible() {
    return wheelchairAccessible;
  }

  /**
   * Override add method so that seats are sorted based on name from A to Z.
   * @param seat element whose presence in this collection is to be ensured
   * @return true
   */
  @Override
  public boolean add(Seat seat) {
    // Check for the existence of a seat with the same name
    for (Seat existingSeat : this) {
      if (existingSeat.getName().equals(seat.getName())) {
        // Seat with the same name already exists, do not add
        return false;
      }
    }

    super.add(seat);
    this.sortSeats();
    return true;
  }

  /**
   * Private helper method to sort the seats based on name from A to Z.
   */
  private void sortSeats() {
    this.sort(Comparator.comparing(Seat::getName));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Row seats = (Row) o;
    return rowNumber == seats.rowNumber && wheelchairAccessible == seats.wheelchairAccessible;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), rowNumber, wheelchairAccessible);
  }

  // Only used for testing
  public int superHashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("%-3d", rowNumber));

    for (int i = 0; i < this.size(); i++) {
      if (this.get(i).getReservedFor() != null) {
        sb.append("X");
      } else if (wheelchairAccessible) {
        sb.append("=");
      } else {
        sb.append("_");
      }
      if (i != this.size() - 1) {
        sb.append(" ");
      }
    }

    return sb.toString();
  }
}
