package astro.backend.server;

import astro.backend.server.configuration.EngineModule;
import astro.backend.server.configuration.OrientDBModule;
import astro.backend.server.engine.Simulator;
import astro.backend.server.event.action.ActionEvent;
import astro.backend.server.event.action.ActionLog;
import astro.backend.server.event.action.SimStartAction;
import astro.backend.server.event.action.Simhandler;
import astro.backend.server.event.frame.EventDispatcher;
import astro.backend.server.service.ShipService;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

public class Server {

    private static final Logger logger = LogManager.getLogger();
    static ShipService service;
    private Queue<ActionEvent> actionQueue;
    final SocketIOServer server;

    private final static String LOCALURL = "plocal:astro";
    private final static String DOCKERURL = "remote:192.168.99.100:32769/astro";



    public static void main(String[] args) throws InterruptedException {

        logger.info("starting server");

        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);


        Injector injector = Guice.createInjector(new OrientDBModule(LOCALURL, "admin", "admin"), new EngineModule());

       // service = injector.getInstance(ShipService.class);

        new Server(config);

    }

    public Server(Configuration config) throws InterruptedException {

        actionQueue = new LinkedList<>();
        ActionLog actionLog = new ActionLog();

        server = new SocketIOServer(config);
        server.addConnectListener((client -> server.getBroadcastOperations().sendEvent("connect", "connecting to the server")));
//        server.addEventListener("SHIP_REQUEST_NEW", ActionEvent.class,
//                (client, data, ackRequest) -> actionLog.dispatch(data));
        server.addConnectListener(c -> logger.info("connecting"));
//        server.addEventListener("SHIP_REQUEST_NEW", ActionEvent.class,
//                (client, data, ackRequest) -> {
////                    ActionEvent action = service.getAction();
////                    server.getBroadcastOperations().sendEvent("action", action );
////                    logger.info("returning {}", action);
//                }
//        );


        Simulator sim = new Simulator();

        EventDispatcher dispatcher = new EventDispatcher();

//        dispatcher.registerChannel(SimStartAction.class, new Simhandler(sim){
//
//            @Override
//            public void dispatch(Event event ){
//                SimStartAction start = (SimStartAction) event;
//                simulator.start();
//            }
//
//        });

        server.addEventListener("SIM_START", SimStartAction.class,
                (client, data, ackRequest) -> dispatcher.dispatch(data));

        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
    }

}
