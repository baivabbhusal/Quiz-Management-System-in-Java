import java.sql.*;
import java.util.Scanner;

class users{

    private static final String URL = "jdbc:mysql://localhost:3306/baivab_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // Function to attempt quiz
    public static void attemptQuiz(Scanner sc) {
        String selectQuery = "SELECT * FROM questions";
        int score = 0;

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(selectQuery);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nQuiz Time! Answer the following questions:");

            while (rs.next()) {
                int questionId = rs.getInt("id");
                String question = rs.getString("question");
                String option1 = rs.getString("option1");
                String option2 = rs.getString("option2");
                String option3 = rs.getString("option3");
                String option4 = rs.getString("option4");
                String correctAnswer = rs.getString("correct_answer");

                // Display the question and options
                System.out.println("\nQuestion ID: " + questionId);
                System.out.println(question);
                System.out.println("1. " + option1);
                System.out.println("2. " + option2);
                System.out.println("3. " + option3);
                System.out.println("4. " + option4);

                // Get user's answer
                System.out.print("Your answer (1-4): ");
                int userChoice = sc.nextInt();
                sc.nextLine(); // Consume newline

                String userAnswer = "";
                switch (userChoice) {
                    case 1 -> userAnswer = option1;
                    case 2 -> userAnswer = option2;
                    case 3 -> userAnswer = option3;
                    case 4 -> userAnswer = option4;
                    default -> {
                        System.out.println("Invalid choice. Skipping this question.");
                        continue;
                    }
                }

                // Compare user's answer with the correct answer
                if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Wrong. The correct answer is: " + correctAnswer);
                }
            }

            System.out.println("\nQuiz completed! Your score: " + score);

        } catch (SQLException e) {
            System.out.println("Database operation failed!");
            e.printStackTrace();
        }
    }
}