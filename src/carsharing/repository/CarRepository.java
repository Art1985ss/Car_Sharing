package carsharing.repository;

import carsharing.models.Car;
import carsharing.models.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This repository used to operate {@link Car Car} class entities
 *
 * @version 1.0
 */
public class CarRepository extends Repository<Car> {
    public CarRepository(final Database database) {
        super(database,
              "INSERT INTO CAR(NAME, COMPANY_ID) VALUES (?, ?)",
              "SELECT * FROM CAR");
    }

    /**
     * This method used to retrieve all cars that belong to this company
     *
     * @param companyId {@link Company company} id
     * @return list of cars that this company owns
     */
    public List<Car> getByCompanyId(final int companyId) {
        final String sql = "SELECT * FROM CAR WHERE COMPANY_ID = ?";
        final List<Car> entityList = new ArrayList<>();
        try (final Connection connection = database.createConnection()) {
            final PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, companyId);
            final ResultSet resultSet = statement.executeQuery();
            processResultSet(resultSet, entityList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entityList;
    }

    @Override
    protected PreparedStatement prepareStatement(final PreparedStatement preparedStatement, final Car car) throws SQLException {
        preparedStatement.setString(1, car.getName());
        preparedStatement.setInt(2, car.getCompanyId());
        return preparedStatement;
    }

    @Override
    protected void processResultSet(final ResultSet resultSet, final List<Car> entityList) throws SQLException {
        while (resultSet.next()) {
            final Car car = new Car(resultSet.getInt(1),
                                    resultSet.getString(2),
                                    resultSet.getInt(3));
            entityList.add(car);
        }
    }
}
