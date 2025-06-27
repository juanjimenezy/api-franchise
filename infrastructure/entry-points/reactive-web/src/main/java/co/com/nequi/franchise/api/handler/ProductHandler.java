package co.com.nequi.franchise.api.handler;

import co.com.nequi.franchise.model.product.Product;
import co.com.nequi.franchise.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductUseCase productUseCase;

    public Mono<ServerResponse> getProductById(ServerRequest request){
        return Mono.justOrEmpty(request.pathVariable("id"))
                        .map(Long::valueOf)
                        .flatMap(productUseCase::getProductById)
                        .flatMap(product -> ServerResponse.ok().bodyValue(product))
                        .switchIfEmpty(ServerResponse.notFound().build())
                        .onErrorResume(error -> ServerResponse.badRequest().bodyValue("Error retrieving product: " + error.getMessage()));
    }

    public Mono<ServerResponse> createProduct(ServerRequest request){
        return request.bodyToMono(Product.class)
                        .flatMap(productUseCase::createProduct)
                        .flatMap(product -> ServerResponse.ok().bodyValue(product))
                        .switchIfEmpty(ServerResponse.badRequest().build())
                        .onErrorResume(error -> ServerResponse.badRequest().bodyValue("Error creating product: " + error.getMessage()));
    }

    public Mono<ServerResponse> updateProductName(ServerRequest request){
        return Mono.justOrEmpty(request.pathVariable("id"))
                        .map(Long::valueOf)
                        .flatMap(id -> request.bodyToMono(Product.class)
                                .flatMap(product -> productUseCase.updateProductName(id, product.getName())))
                        .flatMap(product -> ServerResponse.ok().bodyValue(product))
                        .switchIfEmpty(ServerResponse.notFound().build())
                        .onErrorResume(error -> ServerResponse.badRequest().bodyValue("Error updating product: " + error.getMessage()));
    }

    public Mono<ServerResponse> changeProductAmount(ServerRequest request) {
        return Mono.justOrEmpty(request.pathVariable("id"))
                .map(Long::valueOf)
                .flatMap(productId -> request.bodyToMono(Product.class)
                        .flatMap(product -> productUseCase.changueAmount(productId, product.getStock())))
                .flatMap(product -> ServerResponse.ok().bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue("Error changing product amount: " + error.getMessage()));
    }

    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
        return Mono.justOrEmpty(request.pathVariable("id"))
                .map(Long::valueOf)
                .flatMap(productId ->
                        request.bodyToMono(Product.class)
                                .map(Product::getBranchId)
                                .flatMap(branchId -> productUseCase.deleteProduct(branchId, productId))
                )
                .then(ServerResponse.ok().build())
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue("Error deleting product: " + error.getMessage()));
    }
}