package co.com.nequi.franchise.api;

import co.com.nequi.franchise.api.handler.BranchHandler;
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
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandler franchiseHandler, BranchHandler branchHandler) {
        return route(GET("/api/franchise/{id}"), franchiseHandler::getFranchiseById)
                .andRoute(POST("/api/franchise"), franchiseHandler::createFranchise)
                .andRoute(PUT("/api/franchise/{id}"), franchiseHandler::updateFranchiseName)
                .andRoute(GET("/api/branch/{id}"), branchHandler::getBranchById)
                .andRoute(POST("/api/branch"), branchHandler::createBranch)
                .andRoute(PUT("/api/branch/{id}"), branchHandler::updateBranchName);
    }
}
