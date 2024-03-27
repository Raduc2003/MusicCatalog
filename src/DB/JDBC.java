package DB;

import java.sql.*;

public class JDBC {
    private static final String url = "jdbc:mysql://89.42.218.32:3306/r116501soft_song_catalog";
    private static final String user = "r116501soft_root";
    private static final String password ="zYP~#3oFg&n=";


    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            // Tratează excepțiile pentru JDBC
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // Tratează excepția pentru Class.forName
            e.printStackTrace();
        }
        return connection;
    }




}
