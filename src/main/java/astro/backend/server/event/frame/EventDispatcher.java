// This file is part of MinDispatch
// Copyright (C) 2012 Gio Carlo Cielo Borje
//
// MinDispatch is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// MinDispatch is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package astro.backend.server.event.frame;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EventDispatcher implements DynamicRouter {
	private Map<Class<? extends Event>, Handler<? extends Event>> handlers;

	public EventDispatcher() {
		handlers = new HashMap<>();
	}

	@Override
	public void registerHandler(Class<? extends Event> contentType, Handler<? extends Event> handler) {
		handlers.put(contentType, handler);
	}

	@SuppressWarnings("unchecked")
    @Override
	public <E extends Event> void dispatch(E content) {
		Handler handler = handlers.get(Objects.requireNonNull(content).getClass());
		Objects.requireNonNull(handler);
		handler.onEvent(content);
	}
}
