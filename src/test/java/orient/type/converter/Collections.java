package orient.type.converter;

import javaslang.Tuple2;
import javaslang.collection.*;

public class Collections {


    List l = List.of(1,2);

    protected Set s = HashSet.of(1,2);

    private Map m = HashMap.of(new Tuple2(1,2), new Tuple2(2,3));
}
