package io.fxtras;

import com.google.inject.AbstractModule;

public class JavaFXModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IService.class).to(ServiceImpl.class);
        bind(Controller.class).to(ControllerImpl.class);
    }
}
