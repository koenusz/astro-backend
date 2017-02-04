package netty;

import com.artemis.Component;
import javaslang.collection.Map;
import javaslang.collection.Set;



public class Response {


    public Response(Map<Integer, Set<Component>> entities) {
        this.entities = entities;
    }

    public final Map<Integer, Set<Component>> entities;

}
