package netty;

import com.artemis.Component;
import io.vavr.collection.Map;



public class Response {


    public Response(Map<Integer, Map<String, Component>> entities) {
        this.entities = entities;
    }

    public final Map<Integer, Map<String, Component>> entities;


    @Override
    public String toString() {
        return "Response{" +
                "entities=" + entities +
                '}';
    }
}
