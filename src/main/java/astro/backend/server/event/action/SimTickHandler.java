package astro.backend.server.event.action;

import astro.backend.server.GameServer;
import astro.backend.server.event.SimulatorTickEvent;
import astro.backend.server.event.frame.Handler;
import com.artemis.World;

import javax.inject.Inject;

public class SimTickHandler implements Handler<SimulatorTickEvent>{


    @Inject
    private GameServer server;

    @Override
    public void onEvent(SimulatorTickEvent event) {
         SimulatorTickResponse tick = new SimulatorTickResponse(event.getTime());
        server.allPlayers().forEach(player -> player.send(tick));
    }
}
