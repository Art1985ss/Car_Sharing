package carsharing;

import java.util.List;

/**
 * Generic intarface for standard CRUD operations (Create, Read, Update, Delete)
 *
 * @param <T>
 */
public interface CRUD<T> {
    /**
     * Method for creating single entity
     *
     * @param t single entity that needs to be created
     * @return creation status, true if creation was successful
     */
    boolean create(T t);

    /**
     * Method for getting list all entities
     *
     * @return list of entities
     */
    List<T> getAll();
}
