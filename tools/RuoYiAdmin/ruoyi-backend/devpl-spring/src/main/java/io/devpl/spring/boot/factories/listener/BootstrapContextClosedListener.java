package io.devpl.spring.boot.factories.listener;

import org.springframework.boot.BootstrapContextClosedEvent;
import org.springframework.context.ApplicationListener;

public class BootstrapContextClosedListener implements ApplicationListener<BootstrapContextClosedEvent> {
    @Override
    public void onApplicationEvent(BootstrapContextClosedEvent event) {

    }
}
