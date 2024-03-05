import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class Theater contains information of a name, a list of rows, and a non-empty list of integers
 * indicating which of the rows are wheelchair accessible.
 */
public class Theater {
  private String name;
  private List<Row> rows;
  private List<Integer> accessibleRowNums;

  /**
   * Constructor of class Theater
   * @param name name, as a String
   * @param rows rows, as a List of Row objects
   * @param accessibleRowNums a non-empty list of integers indicating which of the rows are
   *                          wheelchair accessible
   */
  public Theater(String name, List<Row> rows, List<Integer> accessibleRowNums) {
    validateAccessibleRowsNotEmpty(accessibleRowNums);
    validateAccessibleRowNumbers(rows, accessibleRowNums);
    sortRows(rows);
    accessibleRowNums.sort((a, b) -> a - b);

    this.name = name;
    this.rows = rows;
    this.accessibleRowNums = accessibleRowNums;
  }

  /**
   * Private helper method to check if the list of integers is empty.
   * @param accessibleRowNums the list of integers
   */
  private void validateAccessibleRowsNotEmpty(List<Integer> accessibleRowNums) {
    if (accessibleRowNums.isEmpty()) {
      throw new IllegalArgumentException("List of accessible row numbers can't be empty.");
    }
  }

  /**
   * Private helper method to check if the list of integers matches the accessible row numbers.
   * @param rows rows, as a List of Row objects
   * @param accessibleRowNums a non-empty list of integers indicating which of the rows are
   *                          wheelchair accessible
   */
  private void validateAccessibleRowNumbers(List<Row> rows, List<Integer> accessibleRowNums) {
    // Set of row numbers from rows that are marked as accessible
    Set<Integer> actualAccessibleRowNumbers = rows.stream()
        .filter(Row::isWheelchairAccessible)
        .map(Row::getRowNumber)
        .collect(Collectors.toSet());

    // Convert accessibleRowNums to a Set for equality check
    Set<Integer> accessibleRowNumsSet = new HashSet<>(accessibleRowNums);

    // Check if the sets are equal
    if (!actualAccessibleRowNumbers.equals(accessibleRowNumsSet)) {
      throw new IllegalArgumentException("The sets of accessible row numbers do not match.");
    }
  }

  /**
   * Private helper method to sort rows based on the row number in ascending order.
   * @param rows the List of Row objects
   */
  private void sortRows(List<Row> rows) {
    rows.sort(Comparator.comparingInt(Row::getRowNumber));
  }

  /**
   * Getter for name.
   * @return name, as a String
   */
  public String getName() {
    return name;
  }

  /** Getter for rows.
   * @return the List of Row objects
   */
  public List<Row> getRows() {
    return rows;
  }

  /**
   * Getter for non-empty list of integers indicating which of the rows are wheelchair accessible.
   * @return the List of integers
   */
  public List<Integer> getAccessibleRowNums() {
    return accessibleRowNums;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Theater theater = (Theater) o;
    return Objects.equals(name, theater.name) && Objects.equals(rows,
        theater.rows) && Objects.equals(accessibleRowNums, theater.accessibleRowNums);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, rows, accessibleRowNums);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Row row : rows) {
      sb.append(row.toString()).append("\n");
    }
    return sb.toString();
  }
}

