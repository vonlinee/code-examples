package sample.spring.boot.ext;

import org.springframework.context.LifecycleProcessor;

public class MyLifecycleProcessor implements LifecycleProcessor {
    @Override
    public void onRefresh() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
