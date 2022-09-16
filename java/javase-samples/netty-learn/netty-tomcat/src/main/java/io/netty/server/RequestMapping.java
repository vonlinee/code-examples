package io.netty.server;

import java.lang.annotation.Documented;

@Documented
public @interface RequestMapping {

    String uri();
}
