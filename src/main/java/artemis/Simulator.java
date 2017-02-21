package artemis;


import astro.backend.server.ActionQueue;
import astro.backend.server.event.frame.EventDispatcher;
import com.artemis.BaseSystem;
import com.artemis.World;
import com.google.inject.Inject;
import javaslang.collection.HashMap;
import javaslang.collection.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;

public class Simulator implements Runnable {

    private static final Logger logger = LogManager.getLogger();
    private boolean running = false;

    private int speed;

    private HashMap<Integer, List<World>> speedmap = HashMap.empty();

    private World world;

    @Inject
    private ActionQueue actionQueue;
    @Inject
    private EventDispatcher dispatcher;
    @Inject
    private ExecutorService executorService;

    @Inject
    public Simulator(World world) {
        this.world = world;
        setSpeed(0, world);
    }

    public synchronized void start() {
        running = true;
        executorService.execute(this::run);
    }

    public synchronized void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public int getSpeed() {
        return speed;
    }

    //Todo remove this, the frontend should sent which worlds it wants to adjust speed on.
    @Deprecated
    public void setSpeed(int speed) {
        setSpeed(speed, world);
    }

    public void setSpeed(int speed, World world) {
        this.speed = speed;
        if (speed == 0) {
            //on pause disable all processing except giving responses to the client
            enableSystems(world, false);
        } else if (speedmap.getOrElse(0, List.empty()).contains(world)) {
            enableSystems(world, true);
        }

        speedmap.flatMap((index, worlds) -> speedmap = speedmap.put(index, worlds.remove(world)));
        speedmap = speedmap.put(speed, speedmap.getOrElse(speed, List.empty()).append(world));
    }


    @Override
    public void run() {
        try {
            logger.info("running");
            float lastTime = System.nanoTime();
            final float ns = 1000000000.0F / 30.0F; //30 times per second
            float delta = 0;
            while (running) {
                if (actionQueue.nonEmpty()) {
                    dispatcher.dispatch(actionQueue.dequeue());
                }
                float now = System.nanoTime();
                delta = delta + ((now - lastTime) / ns);
                lastTime = now;
                while (delta >= 1)//Make sure update is only happening 1 times a second
                {
                    processWorlds(delta);
                    delta--;
                }
            }
        } catch (Throwable e) {
            logger.error(e);
            e.printStackTrace();
            executorService.execute(this::run);
        }
    }

    private void processWorlds(float delta) {
        speedmap.forEach((speed, worldList) -> worldList.forEach(world -> {
            //for now we multiply the delta time with the speed to simulate expired gametime during higher speeds. This way the real time length of a turn stays the same.
            world.setDelta(delta * speed);
                    world.process();
                }
        ));
    }

    private void enableSystems(World world, boolean enabled) {
        for (BaseSystem system : world.getSystems()) {
            if (system instanceof EntitySubscriberSystem) {
                return;
            }
            system.setEnabled(enabled);
        }
    }

}
