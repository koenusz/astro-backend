package astro.backend.server;

import artemis.EntitySubscriberSystem;
import artemis.Simulator;
import artemis.component.Surface;
import artemis.component.Terrain;
import artemis.component.builder.CelesialBodyBuilder;
import astro.backend.server.configuration.EventModule;
import astro.backend.server.configuration.ExecutorModule;
import astro.backend.server.configuration.OrientDBModule;
import astro.backend.server.configuration.WorldModule;
import astro.backend.server.event.action.*;
import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.EventDispatcher;
import com.artemis.World;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.module.guice.ObjectMapperModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.netty.channel.Channel;

import io.vavr.jackson.datatype.VavrModule;
import netty.Player;
import netty.ResponderImpl;
import netty.WebSocketServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GameServer {


    private static final Logger logger = LogManager.getLogger();

    private final static String LOCALURL = "plocal:astro";
    private static GameServer gameServer;

    private List<Player> players;

    private Injector injector;
    @Inject
    private ActionQueue actionQueue;
    @Inject
    private Simulator simulator;
    @Inject
    private EventDispatcher dispatcher;
    private WebSocketServer webSocketServer;
    @Inject
    private World world;
    @Inject
    private ResponderImpl responderImpl;

    @Inject
    private ObjectMapper mapper;
    @Inject
    private CelesialBodyBuilder celesialBodyBuilder;

    @Inject
    public GameServer(Injector injector) {
        this.injector = injector;
        gameServer = this;
    }

    public static Injector initInjector(){
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Event.class, new EventDeserializer());
        objectMapper.registerModule(module);

        return Guice.createInjector(new OrientDBModule(LOCALURL, "admin", "admin")
                , new ExecutorModule()
                , new WorldModule()
                , new EventModule()
                , new ObjectMapperModule().withObjectMapper(objectMapper)
                        .registerModule(new VavrModule())

        );
    }

    public static void main(String... args) {
        Injector injector = initInjector();
        gameServer = injector.getInstance(GameServer.class);
        gameServer.init();
    }

      private void init() {
        registerEventHandlers();
        players = new ArrayList<>();


        int star = celesialBodyBuilder.buildStar();
        int planet1 = celesialBodyBuilder.buildPlanet(Surface.Size.Small, Terrain.TerrainType.Forest, star, 10, 0);
//        celesialBodyBuilder.buildPlanet(Surface.Size.Tiny, Terrain.TerrainType.Forest, planet1, 1, 0);
//        celesialBodyBuilder.buildPlanet(Surface.Size.Large, Terrain.TerrainType.Jungle, star, 15, 0);
//
//          for (int i = 0; i < 10; i++) {
//              celesialBodyBuilder.buildAsteroid( star, 20 + i, i);
//          }

        EntitySubscriberSystem system = world.getSystem(EntitySubscriberSystem.class);
        system.registerResponder(responderImpl);

        webSocketServer = new WebSocketServer();
        webSocketServer.startServer(this, mapper);


    }

    private void registerEventHandlers() {
        dispatcher.registerHandler(DataRequestEvent.class, injector.getInstance(DataRequestHandler.class));
        dispatcher.registerHandler(SimulatorEvent.class, injector.getInstance(Simhandler.class));
    }

    public void addPlayer(Player player) {
        logger.info("player {} added", player);
        players.add(player);
    }

    public void removePlayer(Player player){
        players.remove(player);
        EntitySubscriberSystem entitySubscriberSystem = world.getSystem(EntitySubscriberSystem.class);
        entitySubscriberSystem.unsubscribe(player);
    }

    private Player findPlayer(Channel channel) {
        Optional<Player> result = players.stream().filter(player -> player.hasChannel(channel)).findFirst();
        if (!result.isPresent()) {
            throw new RuntimeException("Player not found");
        }
        return result.get();
    }

    public void addEventToQueue(Event actionEvent, Channel channel) {
        Player player = findPlayer(channel);
        Objects.nonNull(player);
        actionEvent.assignToPlayer(player);
        actionQueue.enqueue(actionEvent);
        if(!simulator.isRunning()){
            simulator.start();
        }
    }


}


