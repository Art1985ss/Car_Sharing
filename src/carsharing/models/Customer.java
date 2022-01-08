package carsharing.models;

/**
 * Class used to operate customer data
 *
 * @version 1.0
 */
public class Customer extends Entity {
    /**
     * Represents id of the car that customer has rented
     */
    private final Integer carId;

    public Customer(final int id, final String name, final Integer carId) {
        super(id, name);
        this.carId = carId;
    }

    public Customer(final String name, final Integer carId) {
        super(name);
        this.carId = carId;
    }

    public Customer setCarId(final Integer carId) {
        return new Customer(getId(), getName(), carId);
    }

    public Integer getCarId() {
        return carId;
    }
}
