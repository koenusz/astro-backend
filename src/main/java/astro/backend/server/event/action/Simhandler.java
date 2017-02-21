package astro.backend.server.event.action;


import artemis.Simulator;
import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.Handler;
import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Simhandler implements Handler<SimulatorEvent> {
    protected Simulator simulator;

    private static final Logger logger = LogManager.getLogger();

    @Inject
    public Simhandler(Simulator simulator) {
        this.simulator = simulator;
    }

    @Override
    public void onEvent(SimulatorEvent event) {
            if(event.isStart()) {
                logger.debug("starting speed {}", event.getSpeed());
                simulator.setSpeed(event.getSpeed());
            } else {
                logger.debug("stopping speed {}", event.getSpeed());
                simulator.setSpeed(0);
            }
            SimulatorResponse response = new SimulatorResponse(event.isStart(), false);
            event.getPlayer().send(response);
    }
}