//Task 1 For Oasis Infobyte
/*Online Examination System */
import java.util.*;
class User {
    private String username;
    private String password;
    private String profile;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profile = "Default Profile";
    }

    public String getUsername() {
        return username;
    }

    public String getProfile() {
        return profile;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void updateProfile(String newProfile) {
        this.profile = newProfile;
        System.out.println("Profile updated successfully!");
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password updated successfully!");
    }

    public void viewProfile() {
        System.out.println("User Profile:");
        System.out.println("Username: " + username);
        System.out.println("Profile Description: " + profile);
    }
}

class OnlineExaminationSystem {
    private Map<String, User> users;
    private User currentUser;
    private boolean sessionActive;

    public OnlineExaminationSystem() {
        users = new HashMap<>();
        sessionActive = false;
    }

    public void registerUser(String username, String password) {
        users.put(username, new User(username, password));
        System.out.println("User registered successfully.");
    }

    public void login(String username, String password) {
        if (users.containsKey(username) && users.get(username).checkPassword(password)) {
            currentUser = users.get(username);
            sessionActive = true;
            System.out.println("Login successful! Welcome " + currentUser.getUsername());
        } else {
            System.out.println("Please Enter valid username or password.");
        }
    }

    public void updateProfile(String newProfile) {
        if (sessionActive) {
            currentUser.updateProfile(newProfile);
        } else {
            System.out.println("Please login to update profile.");
        }
    }

    public void updatePassword(String newPassword) {
        if (sessionActive) {
            currentUser.updatePassword(newPassword);
        } else {
            System.out.println("Please login to update password.");
        }
    }

    public void viewProfile() {
        if (sessionActive) {
            currentUser.viewProfile();
        } else {
            System.out.println("Please login to view profile.");
        }
    }

    public void logout() {
        if (sessionActive) {
            System.out.println("Logging out..!");
            currentUser = null;
            sessionActive = false;
        } else {
            System.out.println("No active session.");
        }
    }

    public void startExam() {
        if (!sessionActive) {
            System.out.println("You need to log in before starting the exam.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int[] answers = new int[2];  // For simplicity, let's assume there are 2 questions
        System.out.println("Exam Started. You have 60 seconds to complete.");

        String[] questions = {
            "Question 1: What is 2 + 2? \n1) 3 \n2) 4 \n3) 5",
            "Question 2: What is 3 + 5? \n1) 7 \n2) 8 \n3) 9"
        };

        long startTime = System.currentTimeMillis();
        long timeLimit = 60000;  // 60 seconds

        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            System.out.print("Enter your answer (1/2/3): ");

            while (!sc.hasNextInt()) {
                sc.next();  // Consume invalid input
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
            }
            answers[i] = sc.nextInt();

            // Check if time exceeded
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime >= timeLimit) {
                System.out.println("Time's up! Auto-submitting...");
                break;
            }
        }

        System.out.println("Exam Submitted. Your answers:");
        for (int i = 0; i < answers.length; i++) {
            System.out.println("Question " + (i + 1) + ": Answer " + answers[i]);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OnlineExaminationSystem system = new OnlineExaminationSystem();

        // Sample data
        system.registerUser("user1", "pass1");

        while (true) {
            System.out.println("\n1. Login\n2. View Profile\n3. Update Profile\n4. Update Password\n5. Start Exam\n6. Logout\n7. Exit");
            System.out.println("Please select one of the choices above.");
            while (!sc.hasNextInt()) {
                sc.next();  // Consume invalid input
                System.out.println("Invalid input. Please select a valid option (1-7).");
            }
            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = sc.nextLine();
                    System.out.print("Enter password: ");
                    String password = sc.nextLine();
                    system.login(username, password);
                    break;
                case 2:
                    system.viewProfile();  // View profile details
                    break;
                case 3:
                    System.out.print("Enter new profile: ");
                    String newProfile = sc.nextLine();
                    system.updateProfile(newProfile);
                    break;
                case 4:
                    System.out.print("Enter new password: ");
                    String newPassword = sc.nextLine();
                    system.updatePassword(newPassword);
                    break;
                case 5:
                    system.startExam();  // Only allowed if logged in
                    break;
                case 6:
                    system.logout();
                    break;
                case 7:
                    System.out.println("Exiting..! Thank You for visiting..Please Visit Again!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
