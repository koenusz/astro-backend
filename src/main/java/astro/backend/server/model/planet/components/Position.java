package astro.backend.server.model.planet.components;

import org.immutables.value.Value;

@Value.Immutable
public interface Position {

    int getX();

    int getY();
}
