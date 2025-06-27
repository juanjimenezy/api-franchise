package co.com.nequi.franchise.r2dbc.repository;

import co.com.nequi.franchise.model.product.Product;
import co.com.nequi.franchise.r2dbc.entity.ProductEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductReactiveRepository extends ReactiveCrudRepository<ProductEntity, Long>, ReactiveQueryByExampleExecutor<ProductEntity> {
    Flux<Product> findAllByBranchId(Long idBranch);
}
