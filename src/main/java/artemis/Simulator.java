package artemis;


import astro.backend.server.ActionQueue;
import astro.backend.server.event.frame.EventDispatcher;
import com.artemis.World;
import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;


public class Simulator implements Runnable {

    private static final Logger logger = LogManager.getLogger();
    private boolean running = false;


    @Inject
    private ActionQueue actionQueue;
    @Inject
    private EventDispatcher dispatcher;
    @Inject
    private World world;
    @Inject
    private ExecutorService executorService;

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

    @Override
    public void run() {
        try {
            logger.info("running");
            float lastTime = System.nanoTime();
            final float ns = 1000000000.0F / 2.0F; //2 times per second
            float delta = 0;
            while (running) {

                float now = System.nanoTime();
                delta = delta + ((now - lastTime) / ns);
                lastTime = now;
                while (delta >= 1)//Make sure update is only happening 2 times a second
                {
                    while (actionQueue.nonEmpty()) {
                        logger.debug("dispatch at {}", delta);
                        dispatcher.dispatch(actionQueue.dequeue());
                    }
                    world.setDelta(delta);
                    world.process();
                    delta--;
                }
            }
        } catch (Throwable e) {
            logger.error(e);
            executorService.execute(this::run);
        }
    }

}
