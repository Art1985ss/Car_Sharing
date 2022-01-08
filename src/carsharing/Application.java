package carsharing;

import carsharing.enums.CompanyMenu;
import carsharing.enums.CustomerMenu;
import carsharing.enums.MainMenu;
import carsharing.enums.ManagerMenu;
import carsharing.models.Car;
import carsharing.models.Company;
import carsharing.models.Customer;
import carsharing.models.Entity;
import carsharing.repository.CarRepository;
import carsharing.repository.CompanyRepository;
import carsharing.repository.CustomerRepository;
import carsharing.repository.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Application {
    private final Scanner scanner = new Scanner(System.in);
    private final CompanyRepository companyRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public Application(final CompanyRepository companyRepository,
                       final CarRepository carRepository,
                       final CustomerRepository customerRepository) {
        this.companyRepository = companyRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * This method starts main logic of Application
     */
    public void execute() {
        mainMenu();
    }

    /**
     * This method displays and manages main app menu
     */
    private void mainMenu() {
        while (true) {
            System.out.println();
            System.out.print(MainMenu.asMenu());
            final MainMenu mainMenu = MainMenu.getByNum(Integer.parseInt(scanner.nextLine()));
            switch (mainMenu) {
                case LOG_AS_MANAGER:
                    managerMenu();
                    break;
                case LOG_AS_CUSTOMER:
                    listMenu(Customer.class, customerRepository, null);
                    break;
                case CREATE_CUSTOMER:
                    System.out.println("Enter the customer name:");
                    final String name = scanner.nextLine();
                    if (customerRepository.create(new Customer(name, null)))
                        System.out.println("The customer was added!");
                    break;
                case EXIT:
                    return;
            }
        }
    }

    /**
     * This method displays and manages manager menu
     */
    private void managerMenu() {
        while (true) {
            System.out.println();
            System.out.print(ManagerMenu.asMenu());
            final ManagerMenu managerMenu = ManagerMenu.getByNum(Integer.parseInt(scanner.nextLine()));
            switch (managerMenu) {
                case COMPANY_LIST:
                    listMenu(Company.class, companyRepository, null);
                    break;
                case CREATE_COMPANY:
                    System.out.println();
                    System.out.println("Enter the company name:");
                    final Company company = new Company(scanner.nextLine());
                    if (companyRepository.create(company)) System.out.println("The company was created!");
                    break;
                case BACK:
                    return;
            }
        }
    }

    /**
     * This method used to create menu for provided company
     *
     * @param company {@link Company object} for creating its menu
     */
    private void companyMenu(final Company company) {
        while (true) {
            System.out.println();
            System.out.println("'" + company.getName() + "' company");
            System.out.println(CompanyMenu.asMenu());
            final CompanyMenu companyMenu = CompanyMenu.getByNum(Integer.parseInt(scanner.nextLine()));
            switch (companyMenu) {
                case CAR_LIST:
                    final List<Car> carList = carRepository.getByCompanyId(company.getId());
                    if (carList.isEmpty()) {
                        System.out.println("The car list is empty!");
                        break;
                    }
                    final AtomicInteger atomicInteger = new AtomicInteger(1);
                    System.out.println("Car list:");
                    carList.stream()
                            .sorted()
                            .map(car -> atomicInteger.getAndIncrement() + ". " + car.getName())
                            .forEach(System.out::println);
                    break;
                case CREATE_CAR:
                    System.out.println();
                    System.out.println("Enter the car name:");
                    final String name = scanner.nextLine();
                    final Car car = new Car(name, company.getId(), false);
                    if (carRepository.create(car)) System.out.println("The car was added!");
                    break;
                case BACK:
                    return;
            }
        }
    }

    private <T extends Entity> void listMenu(final Class<T> tClass,
                                             final Repository<T> repository,
                                             final Customer customer) {
        final String name = tClass.getSimpleName().toLowerCase();
        final List<T> entityList = repository.getAll();
        if (entityList.isEmpty()) {
            System.out.println("The " + name + " list is empty!");
            return;
        }
        System.out.println("Choose the " + name + ":");
        for (int i = 0; i < entityList.size(); i++) {
            System.out.println(i + 1 + ". " + entityList.get(i).getName());
        }
        System.out.println("0. Back");
        final int userSelection = Integer.parseInt(scanner.nextLine());
        if (userSelection == 0) return;
        final T entity = entityList.get(userSelection - 1);
        if (customer == null) {
            if (entity instanceof Customer) customerMenu(entity.getId());
            if (entity instanceof Company) companyMenu((Company) entity);
        } else {
            if (entity instanceof Company) companyCarsMenu((Company) entity, customer);
        }
    }

    private void companyCarsMenu(final Company company, Customer customer) {
        final List<Car> cars = carRepository.getByCompanyId(company.getId()).stream()
                .filter(car -> !car.isRented())
                .sorted()
                .collect(Collectors.toList());
        if (cars.isEmpty()) {
            System.out.println("No available cars in the '" + company.getName() + "' company");
            return;
        }
        System.out.println("Choose a car:");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println(i + 1 + ". " + cars.get(i).getName());
        }
        final int userSelection = Integer.parseInt(scanner.nextLine());
        if (userSelection == 0) return;
        final Car car = cars.get(userSelection - 1).setRented(true);
        final Customer c = customer.setCarId(car.getId());
        customerRepository.update(c);
        carRepository.update(car);
        System.out.println("You rented '" + car.getName() + "'");
    }

    private void customerMenu(final int customerId) {
        while (true) {
            final Customer customer = customerRepository.getById(customerId)
                    .orElseThrow(() -> new NoSuchElementException("No customer with id " + customerId));
            System.out.println();
            System.out.print(CustomerMenu.asMenu());
            final CustomerMenu customerMenu = CustomerMenu.getByNum(Integer.parseInt(scanner.nextLine()));
            switch (customerMenu) {
                case RENT_CAR:
                    if (customer.getCarId() != null) {
                        System.out.println("You've already rented a car!");
                        break;
                    }
                    listMenu(Company.class, companyRepository, customer);
                    break;
                case RETURN_CAR:
                    if (customer.getCarId() == null)
                        System.out.println("You didn't rent a car!");
                    else {
                        final Integer carId = customer.getCarId();
                        final Car car = carRepository.getById(carId)
                                .orElseThrow(()-> new NoSuchElementException("No car with id : " + carId))
                                .setRented(false);
                        final Customer c = customer.setCarId(null);
                        customerRepository.update(c);
                        carRepository.update(car);
                        System.out.println("You've returned a rented car!");
                    }
                    break;
                case RENTED_CAR:
                    if (customer.getCarId() != null) {
                        final Car car = carRepository.getById(customer.getCarId())
                                .orElseThrow(() -> new NoSuchElementException("No car was found with such id"));
                        final Company company = companyRepository.getById(car.getCompanyId())
                                .orElseThrow(() -> new NoSuchElementException("No company was found with such id"));
                        System.out.println("Your rented car:");
                        System.out.println(car.getName());
                        System.out.println("Company:");
                        System.out.println(company.getName());
                    } else {
                        System.out.println("You didn't rent a car!");
                    }
                    break;
                case BACK:
                    return;
            }
        }
    }
}
