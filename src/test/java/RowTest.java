import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RowTest {
  Row r1;
  Row r2;

  @BeforeEach
  void setUp() {
    assertThrows(IllegalArgumentException.class, () -> new Row(0, false));

    r1 = new Row(1, false);
    r2 = new Row(2, true);
  }

  @Test
  void getRowNumber() {
    assertEquals(1, r1.getRowNumber());
  }

  @Test
  void isWheelchairAccessible() {
    assertFalse(r1.isWheelchairAccessible());
  }

  @Test
  void add() {
    Seat s1 = new Seat("A");
    r1.add(s1);
    assertEquals(1, r1.size());
    assertEquals(s1, r1.get(0));

    assertFalse(r1.add(new Seat("A")));
  }

  @Test
  void testEquals() {
    assertTrue(r1.equals(r1));

    Row r3 = new Row(1, false);
    assertTrue(r1.equals(r3));

    r3 = new Row(1, true);
    assertFalse(r1.equals(r3));

    assertFalse(r1.equals(r2));

    assertFalse(r1.equals(null));

    assertFalse(r1.equals("String"));
  }

  @Test
  void testHashCode() {
    int expected = Objects.hash(r1.superHashCode(), r1.getRowNumber(), r1.isWheelchairAccessible());
    assertEquals(expected, r1.hashCode());
  }

  @Test
  void testToString() {
    r1.add(new Seat("B"));
    r1.add(new Seat("A"));
    String e1 = "1  _ _";
    assertEquals(e1, r1.toString());

    r2.add(new Seat("B"));
    r2.add(new Seat("A"));
    String e2 = "2  = =";
    assertEquals(e2, r2.toString());

    r1.get(0).setReservedFor("Zack");
    String e3 = "1  X _";
    assertEquals(e3, r1.toString());
  }
}