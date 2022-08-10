package code.sample.cloud.gateway.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

//@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        // 第一个参数是路由的唯一id
        // http://localhost:9527/payment/*  =>  localhost:8004/payment/*
        Function<PredicateSpec, Route.AsyncBuilder> fn =
                predicateSpec -> predicateSpec.path("/payment/*").uri("http://localhost:8004/");
        routes.route("path_route_baidu", fn).build();
        return routes.build();
    }
}
