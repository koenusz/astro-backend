package astro.backend.server.model.planet.components;

import astro.backend.server.engine.Component;
import org.immutables.value.Value;

@Value.Immutable
public interface Minerals extends Component{




    //boolean isMined();

   // String getName();

    int getIron();

    int getCopper();

    interface Builder extends ComponentBuilder{}

}
