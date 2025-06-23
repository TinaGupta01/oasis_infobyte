import java.text.SimpleDateFormat;
import java.util.*;

// LoginForm Class
class LoginForm {
    private HashMap<String, String> loginDetails;

    public LoginForm() {
        // Predefined users and passwords
        loginDetails = new HashMap<>();
        loginDetails.put("user1", "password1");
        loginDetails.put("user2", "password2");
    }

    public boolean login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Username: ");
        String username = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();

        if (loginDetails.containsKey(username) && loginDetails.get(username).equals(password)) {
            System.out.println("Login Successful!");
            return true;
        } else {
            System.out.println("Invalid Login. Please try again.");
            return false;
        }
    }
}

// ReservationSystem Class
class ReservationSystem {
    private String passengerName;
    private int trainNumber;
    private String trainName;
    private String classType;
    private String journeyDate;
    private String fromPlace;
    private String destination;
    private String pnr;
    
    public static HashMap<String, String> reservations = new HashMap<>();

    public void reserveTicket() {
        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        System.out.println("Enter Passenger Name: ");
        passengerName = sc.nextLine();

        System.out.println("Enter Train Number: ");
        trainNumber = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.println("Enter Train Name: ");
        trainName = sc.nextLine();

        System.out.println("Enter Class Type (e.g., Sleeper, AC): ");
        classType = sc.nextLine();

        // Journey Date Validation
        boolean validDate = false;
        while (!validDate) {
            System.out.println("Enter Date of Journey (DD/MM/YYYY): ");
            journeyDate = sc.nextLine();
            try {
                Date journey = sdf.parse(journeyDate);
                Date today = new Date();
                
                // Check if the journey date is after today
                if (journey.after(today)) {
                    validDate = true;
                } else {
                    System.out.println("The date of journey must be after today's date.");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format! Please enter the date in DD/MM/YYYY format.");
            }
        }

        System.out.println("Enter From (Place): ");
        fromPlace = sc.nextLine();

        System.out.println("Enter Destination: ");
        destination = sc.nextLine();

        // Generate PNR number and store reservation
        pnr = "PNR" + new Random().nextInt(100000);
        reservations.put(pnr, passengerName + ", " + trainName + " (" + trainNumber + "), Class: " + classType + ", From: " + fromPlace + ", To: " + destination + ", Date: " + journeyDate);

        System.out.println("Reservation Successful! Your PNR is " + pnr);
    }
}

// CancellationSystem Class
class CancellationSystem {
    private String pnr;

    public void cancelTicket() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter PNR Number: ");
        pnr = sc.nextLine();

        // Check if reservation exists
        if (ReservationSystem.reservations.containsKey(pnr)) {
            System.out.println("Displaying information for PNR: " + pnr);
            System.out.println(ReservationSystem.reservations.get(pnr));

            System.out.println("Do you want to confirm cancellation? (y/n)");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                ReservationSystem.reservations.remove(pnr);
                System.out.println("Cancellation Successful!");
            } else {
                System.out.println("Cancellation Aborted.");
            }
        } else {
            System.out.println("No reservation found with the given PNR number.");
        }
    }
}

// Main Class for Online Reservation System
public class OnlineReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginForm login = new LoginForm();

        if (login.login()) {
            while (true) {
                System.out.println("\n1. Reserve Ticket\n2. Cancel Ticket\n3. Exit");
                int choice = sc.nextInt();

                if (choice == 1) {
                    ReservationSystem reservation = new ReservationSystem();
                    reservation.reserveTicket();
                } else if (choice == 2) {
                    CancellationSystem cancellation = new CancellationSystem();
                    cancellation.cancelTicket();
                } else if (choice == 3) {
                    System.out.println("Exiting the system..!Thank you Please visit Again..");
                    break;
                } else {
                    System.out.println("Invalid choice! Please try again.");
                }
            }
        }
    }
}
