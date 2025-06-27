package co.com.nequi.franchise.usecase.product;

import co.com.nequi.franchise.model.branch.gateways.BranchRepository;
import co.com.nequi.franchise.model.product.Product;
import co.com.nequi.franchise.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCase {

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Mono<Product> createProduct(Product product) {
        return branchRepository.findById(product.getBranchId())
                .flatMap(b -> productRepository.saveProduct(product));
    }

    public Mono<Void> deleteProduct(Long idBranch, Long idProduct) {
        return branchRepository.findById(idBranch)
                .flatMap( branch -> productRepository.deleteProduct(idProduct));
    }

    public Mono<Product> changueAmount(Long idProduct, Integer amount ) {
        return productRepository.findById(idProduct)
                .map(p -> {
                    p.setStock(amount);
                    return p;
                })
                .flatMap(productRepository::saveProduct);

    }

    public Mono<Product> updateProductName(Long id, String name) {
        return productRepository.findById(id)
                .flatMap(product -> {
                    product.setName(name);
                    return productRepository.saveProduct(product);
                });
    }

}
