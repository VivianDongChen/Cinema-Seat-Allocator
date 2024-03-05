import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationSystem {
  public static void main(String[] args) {


    List<Row> rows = new ArrayList<>();
    List<Integer> accessibleRowNums = new ArrayList<>();

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

    Theater theater = new Theater("Cinemark Lincoln Square", rows, accessibleRowNums);
    ReservationsService newReservation = new ReservationsService(theater);

    // Create a Scanner object to read input from the command line
    Scanner scanner = new Scanner(System.in);


    Boolean terminator = false;
    while (!terminator) {
      int numberOfSeats = 0;
      System.out.println("What would you like to do?");

      String input = scanner.nextLine();

      if (input.startsWith("reserve ")) {

        String[] parts = input.split(" ");
        if(parts.length != 2) {
          System.out.println("Invalid command. Usage: reserve <number of seat>");
          continue;
        }

        String regex = "[1-9][0-9]*";
        if(!parts[1].matches(regex)) {
          continue;
        }

        numberOfSeats = Integer.parseInt(parts[1]);
        System.out.println("What is your name?");
        String name = scanner.nextLine();
        boolean wheelchairAccess = false;
        boolean validWheelchairInput = false;
        while (!validWheelchairInput) {
          System.out.print("Do you need wheelchair access? (true/false): ");
          String wheelchairResponse = scanner.nextLine().toLowerCase();
          if (wheelchairResponse.equals("true") || wheelchairResponse.equals("false")) {
            wheelchairAccess = Boolean.parseBoolean(wheelchairResponse);
            validWheelchairInput = true;
          } else {
            System.out.println("Invalid input. Please enter 'true' or 'false'.");
          }
        }
        System.out.println(newReservation.reserveSeats(numberOfSeats, wheelchairAccess, name));

      } else if (input.equals("show")) {
        System.out.println(theater);
      } else if (input.equals("done")) {
        terminator = true;
        System.out.println("Have a nice day!");
      } else {
        System.out.println("Invalid command. Please enter a valid command.(Example: 'reserve 5' to reserve 5 seats, 'show' to show the theater layout, or 'done' to terminate the program.)");
      }
    }


    scanner.close();
  }
}

