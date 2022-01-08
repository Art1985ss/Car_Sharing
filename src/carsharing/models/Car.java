package carsharing.models;

/**
 * Class used to operate car data
 *
 * @version 1.0
 */
public class Car extends Entity {

    /**
     * Reference to Company id which owns this car
     */
    private final int companyId;
    private final boolean isRented;

    /**
     * @param name      {@link Entity#getName()}
     * @param companyId {@link Car#companyId}
     * @param isRented  is this car is in the rent
     */
    public Car(final String name, final int companyId, final boolean isRented) {
        super(name);
        this.companyId = companyId;
        this.isRented = isRented;
    }

    /**
     * @param id        {@link Entity#getId()}
     * @param name      {@link Entity#getName()}
     * @param companyId {@link Car#companyId}
     * @param isRented  if this car is rented
     */
    public Car(final int id, final String name, final int companyId, final boolean isRented) {
        super(id, name);
        this.companyId = companyId;
        this.isRented = isRented;
    }

    /**
     * @return {@link Car#companyId}
     */
    public int getCompanyId() {
        return companyId;
    }

    public Car setRented(final boolean isRented) {
        return new Car(getId(), getName(), getCompanyId(), isRented);
    }

    public boolean isRented() {
        return isRented;
    }
}
