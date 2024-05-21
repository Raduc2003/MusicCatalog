package DB;

import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository extends JDBC {

    private static UserRepository userRepository = null;

    private UserRepository() {}

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    private static final String getQuery = "SELECT * FROM User WHERE email=? AND password=?;";

    private static final String postQuery="INSERT INTO User (email,password) VALUES(?,?);";
    public User getUser(String email, String password) {
        // Declare the user variable outside the try block to widen its scope
        User user = null; // Initialize to null, indicating no user found by default

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(getQuery)) {
            // Set the parameters for the PreparedStatement
            statement.setString(1, email);
            statement.setString(2, password);
            logQuery("SELECT", getQuery);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"));
                }
            }

        } catch (SQLException e) {
            // Consider logging the exception details here
            throw new RuntimeException("Failed to retrieve user", e);
        }

        return user; // This could be null if no user was found or the User object if found
    }

    public void addUser(String email, String password) {
        // Try-with-resources statement for Connection
        try (Connection connection = getConnection();
             // Try-with-resources statement for PreparedStatement ensures it's also closed
             PreparedStatement statement = connection.prepareStatement(postQuery)) {

            statement.setString(1, email);
            statement.setString(2, password);
            logQuery("INSERT", postQuery);
            // Execute the update
            statement.executeUpdate(); // Use executeUpdate for INSERT, UPDATE, DELETE operations

        } catch (SQLException e) {
            // Consider a more specific handling or logging of the SQL exception here
            throw new RuntimeException("Failed to add user", e);
        }
    }

}
