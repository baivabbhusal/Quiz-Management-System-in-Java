import java.util.Scanner;

class Quiz {
    public static void admin(Scanner input) {
        System.out.println("Do you already have an account or not? Yes or No:");
        String response = input.nextLine(); // Read user input
        if (response.equalsIgnoreCase("yes")) { // Use equalsIgnoreCase for string comparison
            System.out.println("Please login here.");
            System.out.println("Username:");
            String name = input.nextLine(); // Read username
            System.out.println("Password:");
            String password = input.nextLine(); // Read password
        } 
        else if (response.equalsIgnoreCase("no")) {
            System.out.println("You need to create an account first.");
        } 
        else {
            System.out.println("Invalid response. Please enter Yes or No.");
        }
    }

    public static void user() {
        System.out.println("Hi");
    }

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.println("\n\nWelcome to Quiz Game Management System");
        System.out.println("\n");
        System.out.println("1. Admin");
        System.out.println("2. User");
        System.out.println("Enter your mode:");
        int selectMode = input.nextInt();
        input.nextLine(); // Consume the leftover newline character

        if (selectMode == 1) {
            admin(input); // Pass Scanner object to admin
        } else if (selectMode == 2) {
            user();
        } else {
            System.out.println("Invalid selection.");
        }

        input.close();
    }
}
