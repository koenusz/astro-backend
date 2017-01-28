package astro.backend.server;

import artemis.Simulator;
import astro.backend.server.configuration.EventModule;
import astro.backend.server.configuration.ExecutorModule;
import astro.backend.server.configuration.OrientDBModule;
import astro.backend.server.configuration.WorldModule;
import astro.backend.server.engine.Engine;
import astro.backend.server.event.action.*;
import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.EventDispatcher;
import com.artemis.World;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.netty.channel.Channel;
import netty.Player;
import netty.WebSocketServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GameServer {


    private static final Logger logger = LogManager.getLogger();

    //  @TODO start the websocket from here.
//    public static void main(String[] args) {
//
//        final ResourceBundle configurationBundle = ResourceBundle.getBundle("configuration");
//        int port = Integer.valueOf(configurationBundle.getString("port"));
////        WebSocketServer pWebSocketServer = new WebSocketServer();
////        pWebSocketServer.start(port);
////        LOG.info("server started");
//}


    private final static String LOCALURL = "plocal:astro";
    private static GameServer gameServer;

    private List<Player> players;

    private Injector injector;
    @Inject
    private ActionQueue actionQueue;
    @Inject
    private ActionLog actionLog;
    @Inject
    private Simulator simulator;
    @Inject
    private Engine engine;
    @Inject
    private EventDispatcher dispatcher;
    private WebSocketServer webSocketServer;
    @Inject
    private World world;

    @Inject
    private ObjectMapper mapper;
    @Inject
    private ActionFilterChain actionFilterChain;

    @Inject
    public GameServer(Injector injector) {
        this.injector = injector;
        gameServer = this;
    }

    public static void main(String... args) {
        Injector injector = Guice.createInjector(new OrientDBModule(LOCALURL, "admin", "admin")
                , new ExecutorModule()
                , new WorldModule()
                , new EventModule()
        );

        gameServer = injector.getInstance(GameServer.class);
        gameServer.init();
    }

      private void init() {
        registerEventHandlers();
        players = new ArrayList<>();

          //TODO remove
          world.create();
          world.create();

        webSocketServer = new WebSocketServer();
        webSocketServer.startServer(this, mapper);


    }

    private void registerEventHandlers() {
        dispatcher.registerHandler(DataRequestEvent.class, injector.getInstance(DataRequestHandler.class));
    }

    public void addPlayer(Player player) {
        logger.info("player {} added", player);
        players.add(player);
    }

    private Player findPlayer(Channel channel) {
        Optional<Player> result = players.stream().filter(player -> player.hasChannel(channel)).findFirst();
        if (!result.isPresent()) {
            throw new RuntimeException("Player not found");
        }
        return result.get();
    }

    public void addActionToQueue(ActionEvent actionEvent, Channel channel) {
        Player player = findPlayer(channel);
        Objects.nonNull(player);
        Event filtered = actionFilterChain.filter(actionEvent, player);
        filtered.assignToPlayer(player);
        actionQueue.enqueue(filtered);
        if(!simulator.isRunning()) {
            simulator.start();
        }
    }


}


