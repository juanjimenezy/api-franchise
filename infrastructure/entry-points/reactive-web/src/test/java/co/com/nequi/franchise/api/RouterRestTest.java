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

        // Mockear todos los handlers para devolver un ServerResponse.ok()
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
        // Inyectar los endpoints manualmente
        setField(routerRest, "endpointApiFranchise", "/franchise");
        setField(routerRest, "endpointApiBranch", "/branch");
        setField(routerRest, "endpointApiProduct", "/product");

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
        // Franchise
        webTestClient.get().uri("/franchise/1").exchange().expectStatus().isOk();
        webTestClient.post().uri("/franchise").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
        webTestClient.put().uri("/franchise/1").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();

        // Branch
        webTestClient.get().uri("/branch/1").exchange().expectStatus().isOk();
        webTestClient.post().uri("/branch").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
        webTestClient.put().uri("/branch/1").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();

        // Product
        webTestClient.get().uri("/product/1").exchange().expectStatus().isOk();
        webTestClient.post().uri("/product").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
        webTestClient.post().uri("/product/stock/1").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
        webTestClient.delete().uri("/product/1").exchange().expectStatus().isOk();
        webTestClient.put().uri("/product/1").contentType(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
        webTestClient.get().uri("/product/maxStock/products").exchange().expectStatus().isOk();
        webTestClient.get().uri("/product/franchise/maxStock/1").exchange().expectStatus().isOk();
    }


}
