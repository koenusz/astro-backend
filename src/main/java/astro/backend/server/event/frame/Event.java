
package astro.backend.server.event.frame;


public interface Event {

	default Class<? extends Event> getType(){
		return getClass();
	}
}
