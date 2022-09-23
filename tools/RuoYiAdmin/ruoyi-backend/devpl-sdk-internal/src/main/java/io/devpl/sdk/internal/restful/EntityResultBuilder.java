package io.devpl.sdk.internal.restful;

import java.util.Map;

public interface EntityResultBuilder extends ResultBuilder<Map<String, Object>> {

    @Override
    EntityResult build();
}
