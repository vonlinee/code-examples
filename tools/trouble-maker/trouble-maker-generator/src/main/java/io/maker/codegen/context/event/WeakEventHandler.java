package io.maker.codegen.context.event;

import java.lang.ref.WeakReference;

public final class WeakEventHandler<T extends Event> implements EventHandler<T> {
	private final WeakReference<EventHandler<T>> weakRef;

	public WeakEventHandler(EventHandler<T> var1) {
		this.weakRef = new WeakReference<>(var1);
	}

	public boolean wasGarbageCollected() {
		return this.weakRef.get() == null;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public void handle(T var1) {
		EventHandler var2 = this.weakRef.get();
		if (var2 != null) {
			var2.handle(var1);
		}
	}

	void clear() {
		this.weakRef.clear();
	}
}