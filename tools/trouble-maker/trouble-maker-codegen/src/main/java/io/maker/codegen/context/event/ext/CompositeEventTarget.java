package io.maker.codegen.context.event.ext;

import java.util.Set;

import io.maker.codegen.context.event.EventTarget;

public interface CompositeEventTarget extends EventTarget {
    Set<EventTarget> getTargets();

    boolean containsTarget(EventTarget target);
}
