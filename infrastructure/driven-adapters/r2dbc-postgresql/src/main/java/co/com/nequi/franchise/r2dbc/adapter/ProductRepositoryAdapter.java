package co.com.nequi.franchise.r2dbc.adapter;

import co.com.nequi.franchise.model.product.Product;
import co.com.nequi.franchise.model.product.gateways.ProductRepository;
import co.com.nequi.franchise.r2dbc.entity.ProductEntity;
import co.com.nequi.franchise.r2dbc.helper.ReactiveAdapterOperations;
import co.com.nequi.franchise.r2dbc.repository.ProductReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class ProductRepositoryAdapter extends ReactiveAdapterOperations<Product , ProductEntity, Long, ProductReactiveRepository> implements ProductRepository {

    public ProductRepositoryAdapter(ProductReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Flux<Product> findAllByBranchId(Long idBranch) {
        return repository.findAllByBranchId(idBranch);
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        ProductEntity entity = mapper.map(product, ProductEntity.class);
        return repository.save(entity)
                .map(savedEntity -> mapper.map(savedEntity, Product.class));
    }

    @Override
    public Mono<Void> deleteProduct(Long idProduct) {
        return repository.deleteById(idProduct);
    }
}
