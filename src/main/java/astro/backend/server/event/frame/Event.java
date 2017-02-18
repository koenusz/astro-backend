
package astro.backend.server.event.frame;

import netty.Player;


// @JsonDeserialize(using = EventDeserializer.class)
public interface Event {

    void assignToPlayer(Player player);

    Player getPlayer();

   // List<String> getComponentNames();
}
