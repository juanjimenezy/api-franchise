package co.com.nequi.franchise.api;

import co.com.nequi.franchise.api.handler.BranchHandler;
import co.com.nequi.franchise.api.handler.FranchiseHandler;
import co.com.nequi.franchise.api.handler.ProductHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    @Value("${api.endpoint.franchise}")
    private String endpointApiFranchise;

    @Value("${api.endpoint.branch}")
    private String endpointApiBranch;

    @Value("${api.endpoint.product}")
    private String endpointApiProduct;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(FranchiseHandler franchiseHandler, BranchHandler branchHandler, ProductHandler productHandler) {
        return route(GET(endpointApiFranchise.concat("/{id}")), franchiseHandler::getFranchiseById)
                .andRoute(POST(endpointApiFranchise), franchiseHandler::createFranchise)
                .andRoute(PUT(endpointApiFranchise.concat("/{id}")), franchiseHandler::updateFranchiseName)

                .andRoute(GET(endpointApiBranch.concat("/{id}")), branchHandler::getBranchById)
                .andRoute(POST(endpointApiBranch), branchHandler::createBranch)
                .andRoute(PUT(endpointApiBranch.concat("/{id}")), branchHandler::updateBranchName)

                .andRoute(GET(endpointApiProduct.concat("/{id}")), productHandler::getProductById)
                .andRoute(POST(endpointApiProduct), productHandler::createProduct)
                .andRoute(POST(endpointApiProduct.concat("/stock/{id}")), productHandler::changeProductAmount)
                .andRoute(DELETE(endpointApiProduct.concat("/{id}")), productHandler::deleteProduct)
                .andRoute(PUT(endpointApiProduct.concat("/{id}")), productHandler::updateProductName);
    }
}
