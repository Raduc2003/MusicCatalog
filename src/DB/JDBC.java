package DB;

import java.sql.*;

public class JDBC {
    private static final String url = "jdbc:mysql://89.42.218.32:3306/r116501soft_song_catalog";
    private static final String user = "r116501soft_root";

    private static final String password ="zYP~#3oFg&n=";


    public static void getConnection(){


    }
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                     "jdbc:mysql://89.42.218.32:3306/r116501soft_song_catalog",
                    "r116501soft_root",
                    "zYP~#3oFg&n="
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from test;");
        while (resultSet.next()){
            System.out.println(resultSet.getString("Id"));

        }
    }
}
