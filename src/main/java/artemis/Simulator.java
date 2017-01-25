package artemis;


import astro.backend.server.engine.GameObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class Simulator implements Runnable {

    private static final Logger logger = LogManager.getLogger();
    private boolean running = false;
    private List<GameObject> gameObjects = new ArrayList<>();
    private Thread thread = new Thread(this);

    public synchronized void start() {
        running = true;
        thread.start();
    }
    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(GameObject gameObject){
        gameObject.initialise();
        gameObjects.add(gameObject);
    }

    public void unsubscribe(GameObject gameObject){
        gameObjects.remove(gameObject);
    }


    @Override
    public void run() {
        logger.info("running");
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 2.0 ;//2 times per second
        double delta = 0;
        while(running) {
            long now = System.nanoTime();
            delta = delta + ((now-lastTime) / ns);
            lastTime = now;
            while (delta >= 1)//Make sure update is only happening 2 times a second
            {
                for (GameObject go : gameObjects) {
                    go.update(delta);
                }
                delta--;
            }
            //render();//displays to the screen unrestricted time
        }
    }

//    @Override
//    public void listen(Action action) {
//        if (action.getType().equals("SIM_START")){
//            logger.info("starting");
//            start();
//        }
//        if (action.getType().equals("SIM_STOP")){
//            stop();
//        }
//    }


}
