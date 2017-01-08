package astro.backend.server.datastore;

import com.google.inject.Inject;

import java.util.Map;
import java.util.Objects;

public class DoaFinder {

    private Map<Class, ComponentStore> doaMap;

    @Inject
    private DoaFinder(Map<Class, ComponentStore> doaMap) {
        Objects.requireNonNull(doaMap);
        this.doaMap = doaMap;
    }

    public ComponentStore find(Class type){
        return doaMap.get(type);
    }

}
