package astro.backend.server.event.action;

import astro.backend.server.event.frame.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import netty.Player;

import java.util.ArrayList;
import java.util.List;

public class ActionFilterChain {

    private final List<ActionFilter> filters = new ArrayList<>();
    private ObjectMapper mapper;

    @Inject
    public ActionFilterChain(ObjectMapper mapper) {
        this.mapper = mapper;
        filters.add(new ActionFilter<>(DataRequestEvent.class, mapper));
    }

    public Event filter(ActionEvent actionEvent, Player player) {
        for (ActionFilter filter : filters) {
            if (filter.identify(actionEvent.getActionType())) {
                return filter.filter(actionEvent);
            }
        }
        throw new RuntimeException("no filter in the chain for " + actionEvent.getType());
    }
}
