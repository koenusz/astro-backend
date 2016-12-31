package astro.backend.server.model.entities;

import astro.backend.server.engine.Component;
import astro.backend.server.engine.Entity;

import java.util.List;

public class Planet extends Entity{

    public Planet(long id, List<Component> components) {
        super(id, components);
    }
}
