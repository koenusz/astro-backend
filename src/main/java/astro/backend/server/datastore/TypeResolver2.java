package astro.backend.server.datastore;

import astro.backend.server.engine.Component;
import com.orientechnologies.orient.core.record.impl.ODocument;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.function.BiFunction;
import java.util.function.Function;

public class TypeResolver2 {

    private BidiMap<String, Class> typeMap = new DualHashBidiMap<>();

    public void bindName(String typeName, Class type) {

        if (typeMap.containsKey(typeName)) {
            throw new RuntimeException("attempt to create a duplicate binding of typeName " + typeName);

        } else if (typeMap.containsValue(type)) {
            throw new RuntimeException("attempt to create a duplicate binding of type " + type.getName());
        }
        typeMap.put(typeName, type);
    }

    public Class getType(String typeName) {
        return typeMap.get(typeName);
    }

    public String getTypeName(Class type) {
        return typeMap.getKey(type);
    }

    public <C extends Component> BiFunction<C, ODocument, ODocument> docMapperFunction(String typeName) {
        return null;
    }

    public <C extends Component> Function<ODocument, Component> componentMapperFunction(String typeName) {
        return null;
    }
}
