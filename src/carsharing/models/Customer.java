package carsharing.models;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class used to operate customer data
 *
 * @version 1.0
 */
public class Customer {
    private final AtomicInteger count = new AtomicInteger(1);
    private final int id;
    private final String name;
    /**
     * Represents id of the car that customer has rented
     */
    private final Integer carId;

    public Customer(final int id, final String name, final Integer carId) {
        this.id = id;
        this.name = name;
        this.carId = carId;
    }

    public Customer(final String name, final Integer carId) {
        this.id = count.getAndIncrement();
        this.name = name;
        this.carId = carId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCarId() {
        return carId;
    }
}
