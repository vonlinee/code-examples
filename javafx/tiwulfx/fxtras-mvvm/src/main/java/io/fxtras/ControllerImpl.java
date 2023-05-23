package io.fxtras;

import com.google.inject.Inject;

public class ControllerImpl implements Controller {

    @Inject
    IService service;
}
