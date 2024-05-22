package DB;
import Services.AuditService;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.sql.*;
import java.util.Map;

public abstract class JDBC {
    //le iau din application properties
    private static  String url ;
    private static String user ;
    private static  String password;


    static {
        Yaml yaml = new Yaml();
        try (InputStream in = JDBC.class.getClassLoader().getResourceAsStream("config.yml")) {
            if (in != null) {
                Map<String, Object> config = yaml.load(in);
                Map<String, String> dbConfig = (Map<String, String>) config.get("database");
                url = dbConfig.get("url");
                user = dbConfig.get("user");
                password = dbConfig.get("password");
            } else {
                throw new RuntimeException("Failed to load config.yml");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }
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
