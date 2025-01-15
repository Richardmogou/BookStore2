package block.connection;

import java.sql.Connection;

public interface DatabaseOperations {
    Connection getConnection() throws Exception;
}
