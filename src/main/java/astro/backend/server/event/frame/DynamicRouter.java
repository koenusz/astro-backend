
package astro.backend.server.event.frame;

public interface DynamicRouter {


	void registerHandler(Class<? extends Event> contentType,
			Handler<? extends Event> handler);


	<E extends Event> void dispatch( E content);
}