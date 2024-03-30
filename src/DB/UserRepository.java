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

    private static final String query = "SELECT * FROM User WHERE email=? AND password=?;";

    public ArrayList<User> getUser(String email, String password) {
        ArrayList<User> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameters for the PreparedStatement
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    // Assuming User has a constructor that matches the columns in your query
                    // Adjust this based on your User model
                    User user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"));
                    users.add(user);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
