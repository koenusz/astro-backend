package astro.backend.server.model.generators;

import astro.backend.server.engine.EntityMapper;
import astro.backend.server.model.entities.Planet;
import astro.backend.server.model.planet.components.*;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class PlanetGenerator {


    @Inject
    private EntityMapper<Planet> entityMapper;


    public void seed(){

        Tile tile = ImmutableTile.builder()
                .terrainType(Tile.TerrainType.Forest)
                .position(ImmutablePosition.builder().x(0).y(0).build())
                .build();

        Surface.Builder surfaceBuilder = ImmutableSurface.builder().addTiles(tile)
                .size(ImmutableSize.builder().x(1).y(1).build());

        entityMapper.create(Lists.newArrayList(surfaceBuilder));
    }

}
