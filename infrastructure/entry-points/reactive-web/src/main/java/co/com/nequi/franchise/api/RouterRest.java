package co.com.nequi.franchise.api;

import co.com.nequi.franchise.api.handler.FranchiseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandler handler) {
        return route(GET("/api/franchise/{id}"), handler::getFranchiseById)
                .andRoute(POST("/api/franchise"), handler::createFranchise)
                .andRoute(PUT("/api/franchise/{id}"), handler::updateFranchiseName);
    }
}
