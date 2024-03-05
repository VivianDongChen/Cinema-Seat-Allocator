import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SeatTest {
  Seat s1;

  @BeforeEach
  void setUp() {
    assertThrows(IllegalArgumentException.class, () -> new Seat("AA"));
    assertThrows(IllegalArgumentException.class, () -> new Seat("a"));

    s1 = new Seat("A");
  }

  @Test
  void getName() {
    assertEquals("A", s1.getName());
  }

  @Test
  void getReservedFor() {
    assertNull(s1.getReservedFor());

    s1.setReservedFor("Zack");
    assertEquals("Zack", s1.getReservedFor());
  }

  @Test
  void setReservedFor() {
    s1.setReservedFor("Zack");
    assertEquals("Zack", s1.getReservedFor());
  }

  @Test
  void testEquals() {
    assertTrue(s1.equals(s1));

    assertFalse(s1.equals("String"));

    assertFalse(s1.equals(null));

    Seat s2 = new Seat("A");
    assertTrue(s1.equals(s2));

    s2.setReservedFor("Zack");
    assertFalse(s1.equals(s2));

    s2 = new Seat("B");
    assertFalse(s1.equals(s2));
  }

  @Test
  void testHashCode() {
    int expected = Objects.hash(s1.getName(), s1.getReservedFor());
    assertEquals(expected, s1.hashCode());
  }

  @Test
  void testToString() {
    String expected = "Seat{" +
        "name='" + s1.getName() + '\'' +
        ", reservedFor='" + s1.getReservedFor() + '\'' +
        '}';
    assertEquals(expected, s1.toString());
  }
}