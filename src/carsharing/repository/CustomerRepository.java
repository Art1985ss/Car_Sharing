package carsharing.repository;

import carsharing.models.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This repository used to operate {@link Customer Customer} class entities
 *
 * @version 1.0
 */
public class CustomerRepository extends Repository<Customer> {
    public CustomerRepository(final Database database) {
        super(database,
              "INSERT INTO CUSTOMER (NAME, RENTED_CAR_ID) VALUES (?, ?)",
              "SELECT * FROM CUSTOMER",
              "SELECT * FROM CUSTOMER WHERE ID = ?",
              "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?");
    }

    @Override
    protected void prepareUpdateStatement(final PreparedStatement preparedStatement, final Customer customer) throws SQLException {
        preparedStatement.setObject(1, customer.getCarId());
        preparedStatement.setInt(2, customer.getId());
    }

    @Override
    protected PreparedStatement prepareStatement(final PreparedStatement preparedStatement, final Customer customer) throws SQLException {
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setObject(2, customer.getCarId());
        return preparedStatement;
    }

    @Override
    protected void processResultSet(final ResultSet resultSet, final List<Customer> entityList) throws SQLException {
        while (resultSet.next()) {
            entityList.add(processResultSet(resultSet));
        }
    }

    @Override
    protected Customer processResultSet(final ResultSet resultSet) throws SQLException {
        return new Customer(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getObject(3, Integer.class));
    }
}
