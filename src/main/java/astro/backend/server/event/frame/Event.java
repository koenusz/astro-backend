
package astro.backend.server.event.frame;

import netty.Player;

public interface Event {

    void assignToPlayer(Player player);

    Player getPlayer();
}
