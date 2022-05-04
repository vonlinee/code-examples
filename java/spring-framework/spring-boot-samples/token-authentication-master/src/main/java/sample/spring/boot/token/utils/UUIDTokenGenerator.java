package sample.spring.boot.token.utils;

import java.util.UUID;

public class UUIDTokenGenerator implements TokenGenerator {
    @Override
    public String generate(String... params) {
        return UUID.randomUUID().toString();
    }
}
