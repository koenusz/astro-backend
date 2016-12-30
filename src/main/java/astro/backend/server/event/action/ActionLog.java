package astro.backend.server.event.action;

import astro.backend.server.event.frame.Handler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;

public class ActionLog implements Handler<ActionEvent> {

    private static final Logger logger = LogManager.getLogger();

    private ObjectMapper mapper = new ObjectMapper();

    private Queue<ActionEvent> log = new CircularFifoQueue<>(150);

    public void log(ActionEvent action){
        try {
            logger.info("action type: {} data {}", action.getActionType(), mapper.writeValueAsString(action.getData()));
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        log.add(action);
    }

    @Override
    public void onEvent(ActionEvent event) {
        log(event);
    }
}
