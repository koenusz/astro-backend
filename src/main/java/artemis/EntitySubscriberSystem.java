package artemis;

import artemis.component.Player;
import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;
import javaslang.collection.*;
import javaslang.control.Option;

/**
 * This class keeps track of the entitydata that is requested by the frontend. This data is updated each processing tick of the engine.
 * TODO this can be optimised by keeping track of which components are already sent to the frontend and only sent updates and new requests instead of the whole component bag.
 */

public class EntitySubscriberSystem extends IteratingSystem {

    private Map<Integer, Set<Player>> subscribers;
    private Map<Player, Set<Integer>> playerUpdate;

    private Map<Player, Set<Integer>> newSubscriptions;
    private List<Player> unsubscribed;

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
    }

    public void subscribe(Player player, Set<Integer> entities) {
        newSubscriptions.put(player, entities);
    }

    public void unsubscribe(Player player) {
        unsubscribed.prepend(player);
    }

    public Option<Set<Integer>> getPlayerUpdate(Player player){
        return playerUpdate.get(player);
    }

    private void registerSubscriptions() {
        newSubscriptions.forEach(this::registerSubscription);
        newSubscriptions = HashMap.empty();
    }

    private void registerSubscription(Player player, Set<Integer> entities) {
        Set<Player> players = HashSet.of(player);
        entities.forEach(entity -> {
            Option<Set<Player>> existing = subscribers.get(entity);
            if (existing.isDefined()) {
                subscribers.put(entity, players.addAll(existing.get()));
            } else {
                subscribers.put(entity, players);
            }
        });
    }



    private void unregisterSubsciptions(int entityId) {
        Set<Player> result = subscribers.get(entityId).get().filter(player -> !unsubscribed.contains(player));
        subscribers.put(entityId, result);
    }

    @Override
    protected void begin(){
        playerUpdate = HashMap.empty();
    }

    @Override
    protected void end(){
        unsubscribed = List.empty();
        registerSubscriptions();
    }

    @Override
    protected void process(int entityId) {
        if (subscribers.get(entityId).isDefined()) {
            unregisterSubsciptions(entityId);
            Set<Integer> entities = HashSet.of(entityId);

            Set<Player> subscribedToThisEntity = subscribers.get(entityId).get();
                    subscribedToThisEntity.forEach(player -> {
                if (playerUpdate.get(player).isDefined()) {
                    playerUpdate.put(player, entities.addAll(playerUpdate.get(player).get()));
                } else {
                    playerUpdate.put(player, entities);
                }
            });
        }

    }
}
