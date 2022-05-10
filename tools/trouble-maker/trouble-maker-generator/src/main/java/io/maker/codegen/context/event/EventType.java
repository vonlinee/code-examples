package io.maker.codegen.context.event;

import java.io.Serializable;
import java.util.Iterator;
import java.util.WeakHashMap;

public final class EventType<T extends Event> implements Serializable {
	public static final EventType<Event> ROOT = new EventType("EVENT", (EventType) null);
	private WeakHashMap<EventType<? extends T>, Void> subTypes;
	private final EventType<? super T> superType;
	private final String name;

	@Deprecated
	public EventType() {
		this((EventType) ROOT, (String) null);
	}

	public EventType(String var1) {
		this(ROOT, var1);
	}

	public EventType(EventType<? super T> var1) {
		this((EventType) var1, (String) null);
	}

	public EventType(EventType<? super T> var1, String var2) {
		if (var1 == null) {
			throw new NullPointerException("Event super type must not be null!");
		} else {
			this.superType = var1;
			this.name = var2;
			var1.register(this);
		}
	}

	EventType(String var1, EventType<? super T> var2) {
		this.superType = var2;
		this.name = var1;
		if (var2 != null) {
			if (var2.subTypes != null) {
				Iterator var3 = var2.subTypes.keySet().iterator();

				label30: while (true) {
					EventType var4;
					do {
						if (!var3.hasNext()) {
							break label30;
						}

						var4 = (EventType) var3.next();
					} while ((var1 != null || var4.name != null) && (var1 == null || !var1.equals(var4.name)));

					var3.remove();
				}
			}

			var2.register(this);
		}

	}

	public final EventType<? super T> getSuperType() {
		return this.superType;
	}

	public final String getName() {
		return this.name;
	}

	public String toString() {
		return this.name != null ? this.name : super.toString();
	}

	private void register(EventType<? extends T> var1) {
		if (this.subTypes == null) {
			this.subTypes = new WeakHashMap();
		}
		Iterator var2 = this.subTypes.keySet().iterator();
		EventType var3;
		do {
			if (!var2.hasNext()) {
				// this.subTypes.put(var1, (Object) null);
				return;
			}
			var3 = (EventType) var2.next();
		} while ((var3.name != null || var1.name != null) && (var3.name == null || !var3.name.equals(var1.name)));

		throw new IllegalArgumentException(
				"EventType \"" + var1 + "\"" + "with parent \"" + var1.getSuperType() + "\" already exists");
	}
}