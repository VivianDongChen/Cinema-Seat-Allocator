import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TheaterTest {
  Theater t1;
  List<Row> rows;
  List<Integer> accessibleRowNums;

  @BeforeEach
  void setUp() {
    rows = new ArrayList<>();
    accessibleRowNums = new ArrayList<>();

    for (int i = 1; i <= 15; i++) {
      Row row = new Row(i, i == 5 || i == 6); // Rows 5 and 6 are accessible
      for (char seatName = 'A'; seatName <= 'J'; seatName++) {
        row.add(new Seat(String.valueOf(seatName)));
      }
      rows.add(row);
      if (row.isWheelchairAccessible()) {
        accessibleRowNums.add(i);
      }
    }

    t1 = new Theater("Cinemark Lincoln Square", rows, accessibleRowNums);

    List<Integer> otherRowNums = new ArrayList<>();
    otherRowNums.add(5);
    assertThrows(IllegalArgumentException.class, () ->
        new Theater("X", rows, otherRowNums));

    assertThrows(IllegalArgumentException.class, () ->
        new Theater("X", new ArrayList<>(), new ArrayList<>()));
  }

  @Test
  void getName() {
    assertEquals("Cinemark Lincoln Square", t1.getName());
  }

  @Test
  void getRows() {
    assertEquals(rows, t1.getRows());
  }

  @Test
  void getAccessibleRowNums() {
    assertEquals(accessibleRowNums, t1.getAccessibleRowNums());
  }

  @Test
  void testEquals() {
    assertTrue(t1.equals(t1));

    List<Row> r2 = new ArrayList<>();
    List<Integer> a2 = new ArrayList<>();
    for (int i = 1; i <= 15; i++) {
      Row row = new Row(i, i == 5 || i == 6);
      for (char seatName = 'A'; seatName <= 'J'; seatName++) {
        row.add(new Seat(String.valueOf(seatName)));
      }
      r2.add(row);
      if (row.isWheelchairAccessible()) {
        a2.add(i);
      }
    }
    Theater t2 = new Theater("Cinemark Lincoln Square", r2, a2);

    assertTrue(t2.equals(t2));

    assertFalse(t1.equals(null));

    assertFalse(t1.equals("String"));

    t2 = new Theater("Cinemark Lincoln Square 21+", r2, a2);
    assertFalse(t1.equals(t2));

    List<Row> r3 = new ArrayList<>();
    List<Integer> a3 = new ArrayList<>();
    for (int i = 1; i <= 15; i++) {
      Row row = new Row(i, i == 5);
      for (char seatName = 'A'; seatName <= 'J'; seatName++) {
        row.add(new Seat(String.valueOf(seatName)));
      }
      r3.add(row);
      if (row.isWheelchairAccessible()) {
        a3.add(i);
      }
    }
    t2 = new Theater("Cinemark Lincoln Square", r3, a3);
    assertFalse(t1.equals(t2));

    List<Row> r4 = new ArrayList<>();
    List<Integer> a4 = new ArrayList<>();
    for (int i = 1; i <= 20; i++) {
      Row row = new Row(i, i == 5 || i == 6);
      for (char seatName = 'A'; seatName <= 'J'; seatName++) {
        row.add(new Seat(String.valueOf(seatName)));
      }
      r4.add(row);
      if (row.isWheelchairAccessible()) {
        a4.add(i);
      }
    }
    t2 = new Theater("Cinemark Lincoln Square", r4, a4);
    assertFalse(t1.equals(t2));

    List<Integer> a5 = new ArrayList<>();
    a5.add(6);
    a5.add(5);
    t2 = new Theater("Cinemark Lincoln Square", r2, a5);
    assertTrue(t1.equals(t2));
  }

  @Test
  void testHashCode() {
    int expected = Objects.hash(t1.getName(), t1.getRows(), t1.getAccessibleRowNums());
    assertEquals(expected, t1.hashCode());
  }

  @Test
  void testToString() {
    String expected = """
        1  _ _ _ _ _ _ _ _ _ _
        2  _ _ _ _ _ _ _ _ _ _
        3  _ _ _ _ _ _ _ _ _ _
        4  _ _ _ _ _ _ _ _ _ _
        5  = = = = = = = = = =
        6  = = = = = = = = = =
        7  _ _ _ _ _ _ _ _ _ _
        8  _ _ _ _ _ _ _ _ _ _
        9  _ _ _ _ _ _ _ _ _ _
        10 _ _ _ _ _ _ _ _ _ _
        11 _ _ _ _ _ _ _ _ _ _
        12 _ _ _ _ _ _ _ _ _ _
        13 _ _ _ _ _ _ _ _ _ _
        14 _ _ _ _ _ _ _ _ _ _
        15 _ _ _ _ _ _ _ _ _ _
        """;
    assertEquals(expected, t1.toString());
  }
}