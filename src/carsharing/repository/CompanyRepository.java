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
              "INSERT INTO COMPANY(NAME) VALUES (?)",
              "SELECT * FROM COMPANY",
              "SELECT * FROM COMPANY WHERE ID = ?",
              "UPDATE COMPANY SET NAME = ? WHERE ID = ?");
    }

    @Override
    protected void prepareUpdateStatement(final PreparedStatement preparedStatement, final Company company) throws SQLException {
        preparedStatement.setString(1, company.getName());
        preparedStatement.setInt(2, company.getId());
    }

    @Override
    protected PreparedStatement prepareStatement(final PreparedStatement preparedStatement,
                                                 final Company company) throws SQLException {
        preparedStatement.setString(1, company.getName());
        return preparedStatement;
    }

    @Override
    protected void processResultSet(final ResultSet resultSet, final List<Company> entityList) throws SQLException {
        while (resultSet.next()) {
            Company company = processResultSet(resultSet);
            entityList.add(company);
        }
    }

    @Override
    protected Company processResultSet(final ResultSet resultSet) throws SQLException {
        return new Company(resultSet.getInt(1), resultSet.getString(2));
    }
}
