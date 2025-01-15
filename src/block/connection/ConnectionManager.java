package block.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager implements DatabaseOperations {
    private static final String URL = "jdbc:postgresql://localhost:5432/bookdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "pkf";

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
