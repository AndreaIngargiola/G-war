package model.events;

import java.util.Objects;

import model.entities.Entity;


/**
 * Implementation class for {@link EntityEvent}.
 */

public class AbstractEntityEvent implements EntityEvent {

    private final Entity source;

    /**
     * 
     * @param source
     *            The {@link Entity} source of this event.
     */
    public AbstractEntityEvent(final Entity source) {
        this.source = Objects.requireNonNull(source);
    }

    @Override
    public final Entity getSource() {
        return source;
    }
}
