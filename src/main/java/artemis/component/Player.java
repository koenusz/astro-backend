package artemis.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

    private static final Logger logger = LogManager.getLogger();

    public void sendMessage(String message){

        logger.info("sending message {} ", message);
    }
}
