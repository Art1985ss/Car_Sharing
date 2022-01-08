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
              "SELECT * FROM CUSTOMERS");
    }

    @Override
    protected PreparedStatement prepareStatement(final PreparedStatement preparedStatement, final Customer customer) throws SQLException {
        preparedStatement.setString(1, customer.getName());
        preparedStatement.setInt(2, customer.getCarId());
        return preparedStatement;
    }

    @Override
    protected void processResultSet(final ResultSet resultSet, final List<Customer> entityList) throws SQLException {
        while (resultSet.next()) {
            final int id = resultSet.getInt(1);
            final String name = resultSet.getString(2);
            final Integer carId = resultSet.getObject(3, Integer.class);
            entityList.add(new Customer(id, name, carId));
        }
    }
}
