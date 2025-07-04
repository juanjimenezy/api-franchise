package co.com.nequi.franchise.api;

import co.com.nequi.franchise.api.handler.BranchHandler;
import co.com.nequi.franchise.api.handler.FranchiseHandler;
import co.com.nequi.franchise.api.handler.ProductHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;

class RouterRestTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        FranchiseHandler franchiseHandler = Mockito.mock(FranchiseHandler.class);
        BranchHandler branchHandler = Mockito.mock(BranchHandler.class);
        ProductHandler productHandler = Mockito.mock(ProductHandler.class);

        Mockito.when(franchiseHandler.getFranchiseById(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));
        Mockito.when(franchiseHandler.createFranchise(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));
        Mockito.when(franchiseHandler.updateFranchiseName(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));

        Mockito.when(branchHandler.getBranchById(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));
        Mockito.when(branchHandler.createBranch(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));
        Mockito.when(branchHandler.updateBranchName(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));

        Mockito.when(productHandler.getProductById(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));
        Mockito.when(productHandler.createProduct(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));
        Mockito.when(productHandler.changeProductAmount(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));
        Mockito.when(productHandler.deleteProduct(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));
        Mockito.when(productHandler.updateProductName(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));
        Mockito.when(productHandler.getProductsWithMaxStockByBranch(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));
        Mockito.when(productHandler.getProductsByFranchiseIdAndMaxStock(any())).thenReturn(Mono.just(Objects.requireNonNull(ServerResponse.ok().build().block())));

        RouterRest routerRest = new RouterRest();
        setField(routerRest, "endpointApiFranchise", "/api/franchise");
        setField(routerRest, "endpointApiBranch", "/api/branch");
        setField(routerRest, "endpointApiProduct", "/api/product");

        webTestClient = WebTestClient.bindToRouterFunction(
                routerRest.routerFunction(franchiseHandler, branchHandler, productHandler)
        ).build();
    }

    private void setField(Object target, String field, String value) {
        try {
            java.lang.reflect.Field f = target.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void allRoutesAreConfigured() {
        webTestClient.get().uri("/api/franchise/1").exchange().expectStatus().isOk();
        webTestClient.post().uri("/api/franchise").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
        webTestClient.put().uri("/api/franchise/1").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();

        webTestClient.get().uri("/api/branch/1").exchange().expectStatus().isOk();
        webTestClient.post().uri("/api/branch").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
        webTestClient.put().uri("/api/branch/1").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();

        webTestClient.get().uri("/api/product/1").exchange().expectStatus().isOk();
        webTestClient.post().uri("/api/product").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
        webTestClient.post().uri("/api/product/1").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
        webTestClient.delete().uri("/api/product/1").exchange().expectStatus().isOk();
        webTestClient.put().uri("/api/product/1").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
        webTestClient.get().uri("/api/product/maxStock/products").exchange().expectStatus().isOk();
        webTestClient.get().uri("/api/product/franchise/maxStock/1").exchange().expectStatus().isOk();
    }


}
