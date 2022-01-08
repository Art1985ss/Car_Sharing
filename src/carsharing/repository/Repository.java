package carsharing.repository;

import carsharing.CRUD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This is base repository for entities
 *
 * @param <T> Entities that will be operated by this repository
 * @version 1.0
 */
public abstract class Repository<T> implements CRUD<T> {
    protected final Database database;
    private final String createStatement;
    private final String selectStatement;

    protected Repository(final Database database, final String createStatement, final String selectStatement) {
        this.database = database;
        this.createStatement = createStatement;
        this.selectStatement = selectStatement;
    }

    /**
     * Method used to add new record to the database table.
     *
     * @param entity entity that needs to be saved in database
     * @return execution status
     */
    @Override
    public boolean create(final T entity) {
        try (final Connection connection = database.createConnection()) {
            final PreparedStatement preparedStatement =
                    prepareStatement(connection.prepareStatement(createStatement), entity);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method used to retrieve all entities from database <T> table.
     *
     * @return list of <T> entities retrieved from database.
     */
    @Override
    public List<T> getAll() {
        final List<T> entityList = new ArrayList<>();
        try (final Connection connection = database.createConnection()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(selectStatement);
            processResultSet(resultSet, entityList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entityList;
    }

    /**
     * This method is used to prepare statement for execution
     *
     * @param preparedStatement {@link PreparedStatement prepared statement} that needs to be filled with data
     * @param t                 entity that needs to be processed
     * @throws SQLException if parameterIndex does not correspond to a parameter marker in the SQL statement; if a database access error occurs
     */
    protected abstract PreparedStatement prepareStatement(final PreparedStatement preparedStatement, final T t) throws SQLException;

    /**
     * This method is used to process result set to retrieve entity data
     *
     * @param resultSet  {@link ResultSet result set} that needs to be processed
     * @param entityList {@link  List empty entity list} that will be filled with entities after result set processing
     * @throws SQLException if a database access error occurs; if the columnIndex is not valid
     */
    protected abstract void processResultSet(final ResultSet resultSet, final List<T> entityList) throws SQLException;
}
