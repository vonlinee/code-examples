package io.maker.codegen.core.event;

import java.lang.ref.WeakReference;

public final class WeakEventHandler<T extends Event> implements EventHandler<T> {
	private final WeakReference<EventHandler<T>> weakRef;

	public WeakEventHandler(EventHandler<T> var1) {
		this.weakRef = new WeakReference<>(var1);
	}

	public boolean wasGarbageCollected() {
		return this.weakRef.get() == null;
	}

	public void handle(T var1) {
		EventHandler var2 = (EventHandler) this.weakRef.get();
		if (var2 != null) {
			var2.handle(var1);
		}
	}

	void clear() {
		this.weakRef.clear();
	}
}