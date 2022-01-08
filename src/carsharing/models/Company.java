package carsharing.models;

/**
 * Class used to operate company data.
 *
 * @version 1.0
 */
public class Company extends Entity {
    /**
     * This constructor will create entity with auto assigned id
     *
     * @param name {@link Entity#getName() company} name
     */
    public Company(final String name) {
        super(name);
    }

    /**
     * This constructor will create entity with provided values.
     *
     * @param id   {@link Entity#getId() company} id
     * @param name {@link Entity#getName() company} name
     */
    public Company(final int id, final String name) {
        super(id, name);
    }
}
