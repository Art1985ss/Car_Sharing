package carsharing.models;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class used to operate car data
 *
 * @version 1.0
 */
public class Car implements Comparable<Car> {
    /**
     * Used to increment entity id
     */
    private final AtomicInteger count = new AtomicInteger(1);
    /**
     * Id of the car
     */
    private final int id;
    /**
     * Name of the car
     */
    private final String name;
    /**
     * Reference to Company id which owns this car
     */
    private final int companyId;

    /**
     * @param name      {@link Car#name}
     * @param companyId {@link Car#companyId}
     */
    public Car(final String name, final int companyId) {
        this.id = count.getAndIncrement();
        this.name = name;
        this.companyId = companyId;
    }

    /**
     * @param id        {@link Car#id}
     * @param name      {@link Car#name}
     * @param companyId {@link Car#companyId}
     */
    public Car(final int id, final String name, final int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    /**
     * @return {@link Car#id}
     */
    public int getId() {
        return id;
    }

    /**
     * @return {@link Car#name}
     */
    public String getName() {
        return name;
    }

    /**
     * @return {@link Car#companyId}
     */
    public int getCompanyId() {
        return companyId;
    }

    @Override
    public int compareTo(final Car other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }
}
