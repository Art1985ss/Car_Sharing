package carsharing.models;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class used to operate company data.
 *
 * @version 1.0
 */
public class Company implements Comparable<Company> {
    /**
     * Used to increment entity id
     */
    private static final AtomicInteger count = new AtomicInteger(1);
    /**
     * ID of the company
     */
    private final int id;
    /**
     * Name of the company
     */
    private final String name;

    /**
     * This constructor will create entity with auto assigned id
     *
     * @param name {@link Company#name}
     */
    public Company(final String name) {
        this.id = count.getAndIncrement();
        this.name = name;
    }

    /**
     * This constructor will create entity with provided values.
     *
     * @param id   {@link Company#id}
     * @param name {@link Company#name}
     */
    public Company(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return {@link Company#id}
     */
    public int getId() {
        return id;
    }

    /**
     * @return {@link Company#name}
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }

    @Override
    public int compareTo(Company other) {
        return Integer.compare(this.id, other.id);
    }
}
