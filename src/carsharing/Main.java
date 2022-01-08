package carsharing;

import carsharing.repository.CarRepository;
import carsharing.repository.CompanyRepository;
import carsharing.repository.CustomerRepository;
import carsharing.repository.Database;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        final Map<String, String> arguments = processArgs(args);
        final Database database = new Database(arguments.get("-databaseFileName"));
        final CompanyRepository companyRepository = new CompanyRepository(database);
        final CarRepository carRepository = new CarRepository(database);
        final CustomerRepository customerRepository = new CustomerRepository(database);
        final Application application = new Application(companyRepository, carRepository, customerRepository);

        application.execute();
    }

    private static Map<String, String> processArgs(final String[] args) {
        final Map<String, String> arguments = new HashMap<>();
        if (args.length == 0) {
            return arguments;
        }
        for (int i = 0; i < args.length; i += 2) {
            final String key = args[i];
            final String value = args[i + 1];
            arguments.put(key, value);
        }
        return arguments;
    }
}