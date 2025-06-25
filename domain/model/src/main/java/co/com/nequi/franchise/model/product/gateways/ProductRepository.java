package co.com.nequi.franchise.model.product.gateways;

import co.com.nequi.franchise.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> findById(Long id);
    Mono<Product> saveProduct(Product product);
    void deleteProduct(Long id);
}