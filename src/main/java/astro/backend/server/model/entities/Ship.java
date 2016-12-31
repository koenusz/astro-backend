package astro.backend.server.model.entities;

import astro.backend.server.engine.Component;
import astro.backend.server.engine.Entity;

import java.util.List;


public class Ship extends Entity{

    private Ship(long id, List<Component> components) {
        super(id, components);
    }

}
