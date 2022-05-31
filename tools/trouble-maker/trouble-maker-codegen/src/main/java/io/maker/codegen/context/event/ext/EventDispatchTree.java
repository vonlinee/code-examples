package io.maker.codegen.context.event.ext;

import io.maker.codegen.context.event.EventDispatchChain;
import io.maker.codegen.context.event.EventDispatcher;

public interface EventDispatchTree extends EventDispatchChain {
    EventDispatchTree createTree();

    EventDispatchTree mergeTree(EventDispatchTree tree);

    @Override
    EventDispatchTree append(EventDispatcher eventDispatcher);

    @Override
    EventDispatchTree prepend(EventDispatcher eventDispatcher);
}
