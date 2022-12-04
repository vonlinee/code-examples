package io.devpl.sdk.util;

import java.util.UUID;

public final class IdUtils {

    private IdUtils() {
    }

    public static String simple36UUID() {
        return UUID.randomUUID().toString();
    }

    public static String simple32UUID() {
        final String rawUUID = simple36UUID();
        return rawUUID.substring(0, 8) + rawUUID.substring(9, 13) + rawUUID.substring(14, 18) + rawUUID.substring(19, 23) + rawUUID.substring(24);
    }

    public static String simpleULID() {
        return ULID.randomULID().toString();
    }
}
