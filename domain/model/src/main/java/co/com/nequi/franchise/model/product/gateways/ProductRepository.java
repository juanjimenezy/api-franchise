package co.com.nequi.franchise.model.product.gateways;

import co.com.nequi.franchise.model.product.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> findById(Long id);
    Flux<Product> findAllByBranchId(Long idBranch);
    Mono<Product> saveProduct(Product product);
    Mono<Void> deleteProduct(Long idProduct);
}