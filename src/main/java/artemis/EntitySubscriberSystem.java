package artemis;

import artemis.component.Subscription;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import javaslang.collection.*;
import javaslang.control.Option;
import netty.Player;
import netty.Responder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class keeps track of the entitydata that is requested by the frontend. This data is updated each processing tick of the engine.
 * TODO this can be optimised by keeping track of which components are already sent to the frontend and only sent updates and new requests instead of the whole component bag.
 */

public class EntitySubscriberSystem extends IteratingSystem {

    private static final Logger logger = LogManager.getLogger();

    private Map<Integer, Set<Player>> subscribers;
    private Map<Player, Set<Integer>> playerUpdate;

    private Map<Player, Set<Integer>> newSubscriptions;
    private List<Player> unsubscribed;
    private List<Responder> responders;

    private ComponentMapper<Subscription> subscriptionComponentMapper;

    /**
     * Creates a new EntityProcessingSystem.
     *
     * @param aspect the aspect to match entities
     */
    public EntitySubscriberSystem(Aspect.Builder aspect) {
        super(aspect);
        this.init();
    }


    private void init() {
        subscribers = HashMap.empty();
        unsubscribed = List.empty();
        newSubscriptions = HashMap.empty();
        playerUpdate = HashMap.empty();
        responders = List.empty();
    }

    public void registerResponder(Responder responder){
        responders = responders.append(responder);
    }

    public void subscribe(Player player, Set<Integer> entities) {
        logger.debug("subscribing {} ", player);
        newSubscriptions = newSubscriptions.put(player, entities);
    }

    public boolean isSubscribed(Player player){
        return subscribers.values().find(set -> set.contains(player)).isDefined();
    }

    public Option<Set<Integer>> getPlayerUpdate(Player player){
        return playerUpdate.get(player);
    }

    private void registerSubscriptions() {
        newSubscriptions.forEach(this::registerSubscription);
        newSubscriptions = HashMap.empty();
    }

    private void registerSubscription(Player player, Set<Integer> entities) {
       logger.debug("registering {}, entities {}", player, entities);
        Set<Player> players = HashSet.of(player);
        entities.forEach(entity -> {
            Option<Set<Player>> existing = subscribers.get(entity);
            if (existing.isDefined()) {
                subscribers = subscribers.put(entity, players.addAll(existing.get()));
            } else {
                subscribers = subscribers.put(entity, players);
            }
        });
    }

    public void unsubscribe(Player player) {
        logger.debug("unsubscribing {} ", player);
        unsubscribed = unsubscribed.prepend(player);
    }

    private void unregisterSubsciptions(int entityId) {
        //remove player from the entitysubscription
        Set<Player> result = subscribers.get(entityId).get().filter(player -> !unsubscribed.contains(player));
        subscribers = subscribers.put(entityId, result);
    }

    @Override
    protected void begin(){
        playerUpdate = HashMap.empty();
        registerSubscriptions();
    }

    @Override
    protected void end(){
        unsubscribed = List.empty();
        // new subscribers are registered at the end of the update
        responders.forEach(responder -> responder.respond(playerUpdate));
    }

    @Override
    protected void process(int entityId) {
        Subscription subscription = subscriptionComponentMapper.get(entityId);
      //  logger.debug("entity {} defined {} update {}", entityId, subscribers.get(entityId).isDefined(), subscription.updateFrontend );
        if (subscribers.get(entityId).isDefined() &&  subscription.updateFrontend) {
            unregisterSubsciptions(entityId);
            Set<Integer> entities = HashSet.of(entityId);

            Set<Player> subscribedToThisEntity = subscribers.get(entityId).get();
                    subscribedToThisEntity.forEach(player -> {
                if (playerUpdate.get(player).isDefined()) {
                    playerUpdate = playerUpdate.put(player, entities.addAll(playerUpdate.get(player).get()));
                } else {
                    playerUpdate = playerUpdate.put(player, entities);
                }
            });
            logger.debug("updating {}", subscription);
            subscription.updateFrontend = false;
        }

    }
}
