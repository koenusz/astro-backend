package artemis;


import astro.backend.server.ActionQueue;
import astro.backend.server.engine.GameObject;
import astro.backend.server.event.frame.Event;
import astro.backend.server.event.frame.EventDispatcher;
import com.artemis.World;
import com.google.inject.Inject;
import javaslang.Tuple2;
import javaslang.collection.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class Simulator implements Runnable {

    private static final Logger logger = LogManager.getLogger();
    private boolean running = false;
    private Thread thread = new Thread(this);

    @Inject
    private ActionQueue actionQueue;
    @Inject
    private EventDispatcher dispatcher;
    @Inject
    private World world;

    public synchronized void start() {
        running = true;
        thread.start();
    }
    public synchronized void stop() {
        running = false;
    }

    public boolean isRunning(){
        return running;
    }

    @Override
    public void run() {
        logger.info("running");
        float lastTime = System.nanoTime();
        final float ns = 1000000000.0F / 2.0F ; //2 times per second
        float delta = 0;
        while(running) {
            float now = System.nanoTime();
            delta = delta + ((now - lastTime) / ns);
            lastTime = now;
            while (delta >= 1)//Make sure update is only happening 2 times a second
            {

                while(actionQueue.nonEmpty()) {
                    logger.debug("dispatch at {}", delta);
                    dispatcher.dispatch(actionQueue.dequeue());
                }
                world.setDelta(delta);
                world.process();
                delta--;
            }
        }
    }

}
