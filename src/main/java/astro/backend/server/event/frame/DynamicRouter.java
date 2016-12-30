
package astro.backend.server.event.frame;

public interface DynamicRouter<E extends Event> {


	public void registerHandler(Class<? extends E> contentType,
			Handler<? extends E> handler);


	public abstract void dispatch(E content);
}