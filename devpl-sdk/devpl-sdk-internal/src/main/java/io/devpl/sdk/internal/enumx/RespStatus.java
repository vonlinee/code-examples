package io.devpl.sdk.internal.enumx;

import lombok.Data;

@Data
public class RespStatus {

    private int code;
    private String message;

    private static final EnumPool<RespStatus> pool = new KeyedEnumPool<String, RespStatus>() {
        @Override
        public void put(String name, RespStatus instance) {

        }
    };


}
