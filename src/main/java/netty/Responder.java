package netty;

import io.vavr.collection.Map;
import io.vavr.collection.Set;

public interface Responder {

    void respond(Map<Player, Set<Integer>> responseEntities);
}
