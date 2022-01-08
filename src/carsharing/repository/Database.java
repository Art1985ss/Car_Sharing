package carsharing.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is used to communicate with database.
 *
 * @version 1.0
 */
public class Database {
    /**
     * Driver used for database
     */
    private static final String JDBC_DRIVER = "org.h2.Driver";
    /**
     * Database file URL
     */
    private static final String DB_URL = "jdbc:h2:file:./src/carsharing/db/";

    /**
     * Database name
     */
    private final String dbName;

    /**
     * @param dbName Database name, if null then database name will be 'test'
     */
    public Database(final String dbName) {
        final String companySql =
                "CREATE TABLE IF NOT EXISTS COMPANY (" +
                        "ID INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
                        "NAME VARCHAR(64) UNIQUE NOT NULL" +
                        ")";
        final String carSql =
                "CREATE TABLE IF NOT EXISTS CAR\n" +
                "(\n" +
                "    ID         INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
                "    NAME       VARCHAR(64) UNIQUE NOT NULL,\n" +
                        "    COMPANY_ID INTEGER            NOT NULL,\n" +
                        "    IS_RENTED  BOOLEAN,\n" +
                        "    CONSTRAINT fk_company FOREIGN KEY (COMPANY_ID)\n" +
                "        REFERENCES COMPANY (ID)\n" +
                "        ON DELETE CASCADE\n" +
                "        ON UPDATE CASCADE\n" +
                ")";
        final String customerSql = "CREATE TABLE IF NOT EXISTS CUSTOMER\n" +
                "(\n" +
                "    ID            INTEGER PRIMARY KEY AUTO_INCREMENT,\n" +
                "    NAME          VARCHAR(64) UNIQUE NOT NULL,\n" +
                "    RENTED_CAR_ID INTEGER,\n" +
                "    CONSTRAINT FK_CAR_ID FOREIGN KEY (RENTED_CAR_ID)\n" +
                "        REFERENCES CAR (ID)\n" +
                "        ON DELETE CASCADE\n" +
                "        ON UPDATE CASCADE\n" +
                ")";

        if (dbName == null) this.dbName = "test";
        else this.dbName = dbName;
        try (final Connection conn = createConnection()) {
            final Statement statement = conn.createStatement();
            statement.execute(companySql);
            statement.execute(carSql);
            statement.execute(customerSql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method used to create new connection to database.
     *
     * @return {@link Connection connection}, can be null
     */
    Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL + dbName);
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
