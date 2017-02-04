package netty;

import javaslang.collection.Map;
import javaslang.collection.Set;

public interface Responder {

    void respond(Map<Player, Set<Integer>> responseEntities);
}
