import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Admin {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/baivab_db";
    private static final String USERNAME = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "root"; // Replace with your MySQL password

    public static void registration(Scanner sc) {
        System.out.println("First Name:");
        String fName = sc.nextLine();
        System.out.println("Last Name:");
        String lName = sc.nextLine();
        System.out.println("User Name:");
        String userName = sc.nextLine();
        System.out.println("Email:");
        String email = sc.nextLine();
        System.out.println("Password:");
        String userPassword = sc.nextLine();

        saveToDatabase(fName, lName, userName, email, userPassword);
    }

    // Save user data to the database
    public static void saveToDatabase(String fName, String lName, String userName, String email, String userPassword) {
        String insertQuery = "INSERT INTO users (first_name, last_name, username, email, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, fName);
            stmt.setString(2, lName);
            stmt.setString(3, userName);
            stmt.setString(4, email);
            stmt.setString(5, userPassword);

            if (stmt.executeUpdate() > 0) {
                System.out.println("Registration successful!");
            }
        } catch (SQLException e) {
            System.out.println("Database operation failed!");
            e.printStackTrace();
        }
    }

    public static boolean login(Scanner sc) {
        System.out.println("Enter Username:");
        String userName = sc.nextLine();
        System.out.println("Enter Password:");
        String userPassword = sc.nextLine();

        String loginQuery = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(loginQuery)) {
            stmt.setString(1, userName);
            stmt.setString(2, userPassword);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Login successful! Welcome, " + rs.getString("first_name") + " " + rs.getString("last_name") + ".");
                return true;
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }
        } catch (SQLException e) {
            System.out.println("Database operation failed!");
            e.printStackTrace();
        }
        return false;
    }

    public static void manageQuestions(Scanner sc) {
        while (true) {
            System.out.println("\nQuestion Management:");
            System.out.println("1. Add Question");
            System.out.println("2. Update Question");
            System.out.println("3. Delete Question");
            System.out.println("4. View All Questions");
            System.out.println("5. Logout");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addQuestion(sc);
                    break;
                case 2:
                    updateQuestion(sc);
                    break;
                case 3:
                    deleteQuestion(sc);
                    break;
                case 4:
                    viewQuestions();
                    break;
                case 5:
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Add a question
    public static void addQuestion(Scanner sc) {
        System.out.println("Enter Question:");
        String question = sc.nextLine();
        System.out.println("Enter Option 1:");
        String option1 = sc.nextLine();
        System.out.println("Enter Option 2:");
        String option2 = sc.nextLine();
        System.out.println("Enter Option 3:");
        String option3 = sc.nextLine();
        System.out.println("Enter Option 4:");
        String option4 = sc.nextLine();
        System.out.println("Enter the Correct Answer (Option 1/Option 2/Option 3/Option 4):");
        String correctAnswer = sc.nextLine();

        String insertQuery = "INSERT INTO questions (question, option1, option2, option3, option4, correct_answer) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, question);
            stmt.setString(2, option1);
            stmt.setString(3, option2);
            stmt.setString(4, option3);
            stmt.setString(5, option4);
            stmt.setString(6, correctAnswer);

            if (stmt.executeUpdate() > 0) {
                System.out.println("Question added successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Database operation failed!");
            e.printStackTrace();
        }
    }

    // Update a question
    public static void updateQuestion(Scanner sc) {
        System.out.println("Enter Question ID to update:");
        int questionId = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.println("Enter New Question:");
        String question = sc.nextLine();
        System.out.println("Enter New Option 1:");
        String option1 = sc.nextLine();
        System.out.println("Enter New Option 2:");
        String option2 = sc.nextLine();
        System.out.println("Enter New Option 3:");
        String option3 = sc.nextLine();
        System.out.println("Enter New Option 4:");
        String option4 = sc.nextLine();
        System.out.println("Enter the New Correct Answer (Option 1/Option 2/Option 3/Option 4):");
        String correctAnswer = sc.nextLine();

        String updateQuery = "UPDATE questions SET question = ?, option1 = ?, option2 = ?, option3 = ?, option4 = ?, correct_answer = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setString(1, question);
            stmt.setString(2, option1);
            stmt.setString(3, option2);
            stmt.setString(4, option3);
            stmt.setString(5, option4);
            stmt.setString(6, correctAnswer);
            stmt.setInt(7, questionId);

            if (stmt.executeUpdate() > 0) {
                System.out.println("Question updated successfully!");
            } else {
                System.out.println("Question not found.");
            }
        } catch (SQLException e) {
            System.out.println("Database operation failed!");
            e.printStackTrace();
        }
    }// Delete a question
    public static void deleteQuestion(Scanner sc) {
        System.out.println("Enter Question ID to delete:");
        int questionId = sc.nextInt();
        sc.nextLine(); // Consume newline

        String deleteQuery = "DELETE FROM questions WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setInt(1, questionId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Question deleted successfully!");
            } else {
                System.out.println("Question not found.");
            }
        } catch (SQLException e) {
            System.out.println("Database operation failed!");
            e.printStackTrace();
        }
    }


    // View all questions
    public static void viewQuestions() {
        String selectQuery = "SELECT * FROM questions";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(selectQuery);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nQuestions:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Question: " + rs.getString("question"));
                System.out.println("Option 1: " + rs.getString("option1"));
                System.out.println("Option 2: " + rs.getString("option2"));
                System.out.println("Option 3: " + rs.getString("option3"));
                System.out.println("Option 4: " + rs.getString("option4"));
                System.out.println("Correct Answer: " + rs.getString("correct_answer"));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Database operation failed!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose a role:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");

            int roleChoice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (roleChoice) {
                case 1 -> adminMenu(sc);
                case 2 -> userMenu(sc);
                case 3 -> {
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Admin Menu
    public static void adminMenu(Scanner sc) {
        System.out.println("Enter Admin Username:");
        String adminUsername = sc.nextLine();
        System.out.println("Enter Admin Password:");
        String adminPassword = sc.nextLine();

        // Replace with your actual admin credentials
        if (adminUsername.equals("admin") && adminPassword.equals("admin")) {
            System.out.println("Login successful!");

            while (true) {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Add Question");
                System.out.println("2. Update Question");
                System.out.println("3. Delete Question");
                System.out.println("4. View All Questions");
                System.out.println("5. Logout");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> addQuestion(sc);
                    case 2 -> updateQuestion(sc);
                    case 3 -> deleteQuestion(sc);
                    case 4 -> viewQuestions();
                    case 5 -> {
                        System.out.println("Logged out.");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid Admin credentials!");
        }
    }

    // User Menu
    public static void userMenu(Scanner sc) {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Register");
            System.out.println("2. Login and Attempt Quiz");
            System.out.println("3. Back to Main Menu");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registration(sc);
                case 2 -> {
                    if (login(sc)) {
                        System.out.println("You can now attempt the quiz.");
                        users.attemptQuiz(sc);
                    }
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

