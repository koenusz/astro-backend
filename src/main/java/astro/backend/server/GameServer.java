package astro.backend.server;

import astro.backend.server.configuration.EngineModule;
import astro.backend.server.configuration.OrientDBModule;
import astro.backend.server.engine.Engine;
import astro.backend.server.engine.Simulator;
import astro.backend.server.event.action.ActionEvent;
import astro.backend.server.event.action.ActionFilterChain;
import astro.backend.server.event.action.ActionLog;
import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.EventDispatcher;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
    private Queue<ActionEvent> actionQueue;
    private ActionLog actionLog;
    private Simulator sim;
    private Engine engine;
    private EventDispatcher dispatcher;
    private WebSocketServer webSocketServer;
    private ObjectMapper mapper;
    private ActionFilterChain actionFilterChain;

    @Inject
    public GameServer(Injector injector) {
        this.injector = injector;
        gameServer = this;
    }

    public static void main(String... args){
        Injector injector = Guice.createInjector(new OrientDBModule(LOCALURL, "admin", "admin"), new EngineModule());
        gameServer = injector.getInstance(GameServer.class);
        gameServer.init();
    }

    public void init(){

        actionQueue = new LinkedList<>();
        actionLog = new ActionLog();
        actionFilterChain = injector.getInstance(ActionFilterChain.class);
        sim = injector.getInstance(Simulator.class);
        engine = injector.getInstance(Engine.class);
        dispatcher = injector.getInstance(EventDispatcher.class);
        players = new ArrayList<>();
        mapper = injector.getInstance(ObjectMapper.class);
        webSocketServer = new WebSocketServer();
        webSocketServer.startServer(this, mapper );
    }

    public void addPlayer(Player player){
        logger.info("player {} added", player);
        players.add(player);
    }

    public void dispatchAction(ActionEvent actionEvent, Channel channel){
      Event filtered = actionFilterChain.filter(actionEvent);
      dispatcher.dispatch(filtered);
    }



}


