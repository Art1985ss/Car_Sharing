package carsharing;

import carsharing.enums.CompanyMenu;
import carsharing.enums.CustomerMenu;
import carsharing.enums.MainMenu;
import carsharing.enums.ManagerMenu;
import carsharing.models.Car;
import carsharing.models.Company;
import carsharing.models.Customer;
import carsharing.repository.CarRepository;
import carsharing.repository.CompanyRepository;
import carsharing.repository.CustomerRepository;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

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
                    customerMenu();
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
                    companyListMenu();
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
     * This method displays and manages entity list menu
     */
    private void companyListMenu() {
        final List<Company> companyList = companyRepository.getAll();
        if (companyList.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }
        System.out.println("Choose the company:");
        companyList.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("0. Back");
        final int userSelection = Integer.parseInt(scanner.nextLine());
        if (userSelection == 0) return;
        companyList.stream()
                .filter(company -> company.getId() == userSelection)
                .findFirst()
                .ifPresent(this::companyMenu);
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
                    final Car car = new Car(name, company.getId());
                    if (carRepository.create(car)) System.out.println("The car was added!");
                    break;
                case BACK:
                    return;
            }
        }
    }

    private void customerListMenu() {
        final List<Customer> customerList = customerRepository.getAll();
        if (customerList.isEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }
        System.out.println("Choose the customer:");
        customerList.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("0. Back");
        final int userSelection = Integer.parseInt(scanner.nextLine());
        if (userSelection == 0) return;
        customerList.stream()
                .filter(customer -> customer.getId() == userSelection)
                .findFirst()
                .ifPresent(this::customerMenu);
    }

    private void customerMenu(final Customer customer) {
        final List<Customer> customers = customerRepository.getAll();
        if (customers)
        while (true) {
            System.out.println();
            System.out.print(ManagerMenu.asMenu());
            final CustomerMenu customerMenu = CustomerMenu.getByNum(Integer.parseInt(scanner.nextLine()));
            switch (customerMenu) {
                case RENT_CAR:

                case RETURN_CAR:
                case RENTED_CAR:
                case BACK: return;
            }
        }
    }
}
