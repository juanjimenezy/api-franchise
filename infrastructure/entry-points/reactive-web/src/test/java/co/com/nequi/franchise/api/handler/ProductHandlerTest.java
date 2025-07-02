package co.com.nequi.franchise.api.handler;

import co.com.nequi.franchise.model.product.Product;
import co.com.nequi.franchise.usecase.product.ProductUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductHandlerTest {
    private ProductUseCase productUseCase;
    private ProductHandler productHandler;

    @BeforeEach
    void setUp() {
        productUseCase = mock(ProductUseCase.class);
        productHandler = new ProductHandler(productUseCase);
    }

    @Test
    void getProductById_returnsProduct_whenFound() {
        Product product = new Product();
        product.setId(1L);
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(productUseCase.getProductById(1L)).thenReturn(Mono.just(product));

        Mono<ServerResponse> response = productHandler.getProductById(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void getProductById_returnsNotFound_whenNotFound() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(productUseCase.getProductById(1L)).thenReturn(Mono.empty());

        Mono<ServerResponse> response = productHandler.getProductById(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void createProduct_returnsProduct_whenCreated() {
        Product product = new Product();
        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(Product.class)).thenReturn(Mono.just(product));
        when(productUseCase.createProduct(product)).thenReturn(Mono.just(product));

        Mono<ServerResponse> response = productHandler.createProduct(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void createProduct_returnsBadRequest_whenBodyEmpty() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(Product.class)).thenReturn(Mono.empty());

        Mono<ServerResponse> response = productHandler.createProduct(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void createProduct_returnsBadRequest_onError() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.bodyToMono(Product.class)).thenReturn(Mono.error(new RuntimeException("fail")));

        Mono<ServerResponse> response = productHandler.createProduct(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void updateProductName_returnsProduct_whenUpdated() {
        Product product = new Product();
        product.setName("newName");
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(Product.class)).thenReturn(Mono.just(product));
        when(productUseCase.updateProductName(1L, "newName")).thenReturn(Mono.just(product));

        Mono<ServerResponse> response = productHandler.updateProductName(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void updateProductName_returnsNotFound_whenEmpty() {
        Product product = new Product();
        product.setName("newName");
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(Product.class)).thenReturn(Mono.just(product));
        when(productUseCase.updateProductName(1L, "newName")).thenReturn(Mono.empty());

        Mono<ServerResponse> response = productHandler.updateProductName(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void updateProductName_returnsBadRequest_onError() {
        Product product = new Product();
        product.setName("newName");
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(Product.class)).thenReturn(Mono.just(product));
        when(productUseCase.updateProductName(1L, "newName")).thenReturn(Mono.error(new RuntimeException("fail")));

        Mono<ServerResponse> response = productHandler.updateProductName(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void changeProductAmount_returnsProduct_whenChanged() {
        Product product = new Product();
        product.setStock(10);
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(Product.class)).thenReturn(Mono.just(product));
        when(productUseCase.changueAmount(1L, 10)).thenReturn(Mono.just(product));

        Mono<ServerResponse> response = productHandler.changeProductAmount(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void changeProductAmount_returnsNotFound_whenEmpty() {
        Product product = new Product();
        product.setStock(10);
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(Product.class)).thenReturn(Mono.just(product));
        when(productUseCase.changueAmount(1L, 10)).thenReturn(Mono.empty());

        Mono<ServerResponse> response = productHandler.changeProductAmount(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void changeProductAmount_returnsBadRequest_onError() {
        Product product = new Product();
        product.setStock(10);
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(Product.class)).thenReturn(Mono.just(product));
        when(productUseCase.changueAmount(1L, 10)).thenReturn(Mono.error(new RuntimeException("fail")));

        Mono<ServerResponse> response = productHandler.changeProductAmount(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void deleteProduct_returnsOk_whenDeleted() {
        Product product = new Product();
        product.setBranchId(2L);
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(Product.class)).thenReturn(Mono.just(product));
        when(productUseCase.deleteProduct(2L, 1L)).thenReturn(Mono.empty());

        Mono<ServerResponse> response = productHandler.deleteProduct(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void deleteProduct_returnsBadRequest_onError() {
        Product product = new Product();
        product.setBranchId(2L);
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(request.bodyToMono(Product.class)).thenReturn(Mono.just(product));
        when(productUseCase.deleteProduct(2L, 1L)).thenReturn(Mono.error(new RuntimeException("fail")));

        Mono<ServerResponse> response = productHandler.deleteProduct(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void getProductsWithMaxStockByBranch_returnsProducts() {
        Product product = new Product();
        when(productUseCase.getProductsWithMaxStockByBranch()).thenReturn(Flux.just(product));

        Mono<ServerResponse> response = productHandler.getProductsWithMaxStockByBranch(mock(ServerRequest.class));

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void getProductsWithMaxStockByBranch_returnsBadRequest_onError() {
        when(productUseCase.getProductsWithMaxStockByBranch()).thenReturn(Flux.error(new RuntimeException("fail")));

        Mono<ServerResponse> response = productHandler.getProductsWithMaxStockByBranch(mock(ServerRequest.class));

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void getProductsByFranchiseIdAndMaxStock_returnsProducts() {
        Product product = new Product();
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(productUseCase.getProductsByFranchiseIdAndMaxStock(1L)).thenReturn(Flux.just(product));

        Mono<ServerResponse> response = productHandler.getProductsByFranchiseIdAndMaxStock(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void getProductsByFranchiseIdAndMaxStock_returnsBadRequest_onError() {
        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");
        when(productUseCase.getProductsByFranchiseIdAndMaxStock(1L)).thenReturn(Flux.error(new RuntimeException("fail")));

        Mono<ServerResponse> response = productHandler.getProductsByFranchiseIdAndMaxStock(request);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is4xxClientError())
                .verifyComplete();
    }

}
