package carsharing.repository;

import carsharing.models.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * This repository used to operate {@link Company Company} class entities
 *
 * @version 1.0
 */
public class CompanyRepository extends Repository<Company> {


    /**
     * @param database will create and operate connections to database
     */
    public CompanyRepository(final Database database) {
        super(database,
              "INSERT INTO COMPANY(ID, NAME) VALUES (?, ?)",
              "SELECT * FROM COMPANY");
    }

    @Override
    protected PreparedStatement prepareStatement(final PreparedStatement preparedStatement,
                                                 final Company company) throws SQLException {
        preparedStatement.setInt(1, company.getId());
        preparedStatement.setString(2, company.getName());
        return preparedStatement;
    }

    @Override
    protected void processResultSet(final ResultSet resultSet, final List<Company> entityList) throws SQLException {
        while (resultSet.next()) {
            Company company = new Company(resultSet.getInt(1), resultSet.getString(2));
            entityList.add(company);
        }
    }
}
