package carsharing.models;

public abstract class Entity implements Comparable<Entity> {
    /**
     * Id of the Entity
     */
    private final int id;
    /**
     * Name of the Entity
     */
    private final String name;

    protected Entity(final String name) {
        this.id = 0;
        this.name = name;
    }

    protected Entity(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(final Entity other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return id + ". " + name;
    }
}
