package DB;
import Services.AuditService;
import java.sql.*;

public abstract class JDBC {
    //le iau din application properties
    private static final String url = "jdbc:mysql://89.42.218.32:3306/r116501soft_song_catalog";
    private static final String user = "r116501soft_root";
    private static final String password ="zYP~#3oFg&n=";

    protected static AuditService auditService = new AuditService();
    protected void logQuery(String operation, String query) {
        auditService.log(operation, query);
    }
    protected static Connection getConnection() {
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
