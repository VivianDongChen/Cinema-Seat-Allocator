import java.util.List;
import java.util.Optional;

public class ReservationsService {
  private Theater theater;

  public ReservationsService(Theater theater) {
        this.theater = theater;
    }

  /**
  * Attempts to reserve a specified number of seats in the theater, considering wheelchair accessibility.
  * @param numSeats The number of seats to reserve.
  * @param needsWheelchairAccess Whether the seats need to be wheelchair accessible.
  * @param name The name of the person for whom the reservation is made.
  * @return A string indicating the outcome of the reservation attempt.
  */
  public String reserveSeats(int numSeats, boolean needsWheelchairAccess, String name) {

    Row bestRow = null;

    if (!needsWheelchairAccess) {
        bestRow = findBestRow(numSeats, false);
        if (bestRow == null) {
          bestRow = findBestRow(numSeats, true);
        }
    } else {
      bestRow = findBestRow(numSeats, true);
    }

    if (bestRow == null)
       return "Sorry, we don’t have that many seats together for you.";

    return allocateSeats(bestRow, numSeats, name);
  }

  /**
   * Finds the best row in the theater that can accommodate the given number of seats while
   * considering wheelchair accessibility requirements.
   *
   * The method starts searching from the center row and moves outward, checking both directions.
   * It prioritizes rows based on their proximity to the center of the theater.
   *
   * @param numSeats The number of contiguous seats required.
   * @param needsWheelchairAccess Whether the reserved seats need to be wheelchair accessible.
   * @return The first row found that meets the requirements, or null if no suitable row is found.
   */
  private Row findBestRow(int numSeats, boolean needsWheelchairAccess) {
    List<Row> rows = theater.getRows();
    int centerRow;
    if (rows.size() % 2 == 0) {
      centerRow = rows.size() / 2;
    } else {
      centerRow = (rows.size()+1) / 2;
    }

//    int centerRow = rows.size() / 2;
    int totalRows = rows.size();

    for (int dist = 0; dist < centerRow; dist++) {
      int rowAboveCenter = centerRow - dist - 1;
      int rowBelowCenter = centerRow + dist - (totalRows % 2 == 0 ? 1 : 0);

      Row rowAbove = rowAboveCenter >= 0 && rowAboveCenter < totalRows ? rows.get(rowAboveCenter) : null;
      Row rowBelow = rowBelowCenter >= 0 && rowBelowCenter < totalRows ? rows.get(rowBelowCenter) : null;

      Row suitableRow = findSuitableRow(rowAbove, rowBelow, numSeats, needsWheelchairAccess);
      if (suitableRow != null) {
        return suitableRow;
      }
    }
    return null;
  }

  /**
   * Finds a suitable row from the given two rows (one above and one below the center row)
   * that meets the specific number of contiguous seats and wheelchair accessibility requirements.
   *
   * This method is used within the process of finding the best suitable row.
   *
   * @param rowAbove The row above the center row to check for suitability.
   * @param rowBelow The row below the center row to check for suitability.
   * @param numSeats The number of contiguous seats required.
   * @param needsWheelchairAccess Whether the seats need to be wheelchair accessible.
   * @return The first suitable row found (either above or below), or null if neither is suitable.
   */
  private Row findSuitableRow(Row rowAbove, Row rowBelow, int numSeats, boolean needsWheelchairAccess) {
    if (rowAbove != null && rowAbove.isWheelchairAccessible() == needsWheelchairAccess &&
            hasEnoughContiguousSeats(rowAbove, numSeats)) {
      return rowAbove;
    }
    if (rowBelow != null && rowBelow.isWheelchairAccessible() == needsWheelchairAccess &&
            hasEnoughContiguousSeats(rowBelow, numSeats)) {
      return rowBelow;
    }
    return null;
  }

    /**
     * Checks if a row has enough contiguous unreserved seats to fulfill a reservation request.
     * @param row The row to check.
     * @param numSeats The number of contiguous seats required.
     * @return True if there are enough contiguous unreserved seats, false otherwise.
     */
    private boolean hasEnoughContiguousSeats(Row row, int numSeats) {
      int contiguousSeats = 0;
       for (Seat seat : row) {
         if (seat.getReservedFor() == null) {
           contiguousSeats++;
           if (contiguousSeats == numSeats) {
             return true;
           }
         } else {
           contiguousSeats = 0;
         }
       }
      return false;
    }

    /**
     * Allocates the specified number of seats in a given row and marks them as reserved.
     * @param row The row in which seats are to be allocated.
     * @param numSeats The number of seats to allocate.
     * @param name The name of the person for whom the seats are reserved.
     * @return A string confirmation of the seats reserved.
     */
    private String allocateSeats(Row row, int numSeats, String name) {
      int seatsAllocated = 0;
      for (Seat seat : row) {
        if (seat.getReservedFor() == null) {
          seat.setReservedFor(name);
          seatsAllocated++;
          if (seatsAllocated == numSeats) {
            break;
          }
        }
      }
        return "I’ve reserved " + numSeats + " seats for you at the " + theater.getName() +" in row " + row.getRowNumber() + ", " + name + ".";
    }

    /**
     * Provides a string representation of the current seating arrangement in the theater.
     * @return A string showing the current seating arrangement.
     */
    public String showSeating() {
      return theater.toString();
    }
}

