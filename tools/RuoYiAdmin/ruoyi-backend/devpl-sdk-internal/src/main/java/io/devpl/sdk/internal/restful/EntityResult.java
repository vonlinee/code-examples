package io.devpl.sdk.internal.restful;

import java.io.Serializable;
import java.util.Map;

/**
 * 单条记录
 */
public class EntityResult extends Result<Map<String, Object>> implements EntityResultBuilder, Serializable {

    @Override
    public EntityResult build() {
        return this;
    }
}
