package io.devpl.sdk.support.spring.context;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;

public class DevplListener implements GenericApplicationListener {

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

    }
}
