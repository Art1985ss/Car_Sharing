package carsharing.models;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Entity implements Comparable<Entity> {
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

    protected Entity(final String name) {
        this.id = count.getAndIncrement();
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
}
