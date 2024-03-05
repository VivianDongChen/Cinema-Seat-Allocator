import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class ReservationsServiceTest {

    private Theater theater;
    private ReservationsService service;

    @BeforeEach
    void setUp() {
        // Set up a mock theater with rows and seats.
        theater = createMockTheater();
        service = new ReservationsService(theater);
    }

    private Theater createMockTheater() {
        List<Row> rows = new ArrayList<>();
        // Let's assume we have a theater with 5 rows, each with 10 seats.
        for (int i = 1; i <= 5; i++) {
            Row row = new Row(i, i == 3); // Let's assume row 3 is wheelchair accessible
            for (char seatName = 'A'; seatName <= 'J'; seatName++) {
                row.add(new Seat(String.valueOf(seatName)));
            }
            rows.add(row);
        }

        // Make sure that the list of accessible rows is mutable if it is sorted or modified in the Theater class.
        List<Integer> accessibleRowNums = new ArrayList<>();
        accessibleRowNums.add(3); // Assuming row 3 is wheelchair accessible
        return new Theater("Mock Theater", rows, accessibleRowNums);
    }

    @Test
    void testReserveSeatsSuccessfullyWithWheelchairAccessNotNeeded() {
        String result = service.reserveSeats(5, false, "John");
        assertTrue(result.contains("reserved 5 seats for you"));
    }

    @Test
    void testReserveSeatsSuccessfullyWithWheelchairAccessNeeded() {
        String result = service.reserveSeats(5, true, "John");
        assertTrue(result.contains("reserved 5 seats for you"));
    }

    @Test
    void testReserveSeatsNotEnoughSeats() {
        String result = service.reserveSeats(20,false, "Charlie");
        assertEquals("Sorry, we don’t have that many seats together for you.", result);
    }

    @Test
    void testReserveSeatsInAccessibleRowWhenNonAccessibleFull() {
        // First, fill up all non-wheelchair-accessible rows
        for (int i = 1; i <= theater.getRows().size() -1; i++) {
            service.reserveSeats(10, false, "Person" + i); // Assuming 10 seats per row
        }

        // Now try to reserve in a wheelchair-accessible row
        String result = service.reserveSeats(2, false, "Bob");
        System.out.println(result);
        assertTrue(result.contains("reserved 2 seats for you"));
    }

    @Test
    void testReserveSeatsUnsuccessfulWhenAllRowsFull() {
        // Fill up all rows
        for (int i = 1; i <= theater.getRows().size(); i++) {
            service.reserveSeats(10, false, "Person" + i); // Assuming 10 seats per row
        }

        // Try to reserve when all rows are full
        String result = service.reserveSeats(2, false, "Charlie");
        assertEquals("Sorry, we don’t have that many seats together for you.", result);
    }

    @Test
    void testShowSeating() {
        service.reserveSeats(2, false, "Eve");
        String seatingArrangement = service.showSeating();
        assertNotNull(seatingArrangement);
    }
}

