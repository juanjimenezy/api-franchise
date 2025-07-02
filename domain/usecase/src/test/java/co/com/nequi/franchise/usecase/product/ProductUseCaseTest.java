package co.com.nequi.franchise.usecase.product;

import co.com.nequi.franchise.model.branch.Branch;
import co.com.nequi.franchise.model.branch.gateways.BranchRepository;
import co.com.nequi.franchise.model.product.Product;
import co.com.nequi.franchise.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class ProductUseCaseTest {

    private BranchRepository branchRepository;
    private ProductRepository productRepository;
    private ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        branchRepository = mock(BranchRepository.class);
        productRepository = mock(ProductRepository.class);
        productUseCase = new ProductUseCase(branchRepository, productRepository);
    }

    @Test
    void getProductsByFranchiseIdAndMaxStock_returnsMaxStockProducts() {
        Long franchiseId = 1L;
        Branch branch1 = new Branch();
        branch1.setId(10L);
        Branch branch2 = new Branch();
        branch2.setId(20L);

        Product p1 = new Product();
        p1.setId(100L);
        p1.setStock(5);
        Product p2 = new Product();
        p2.setId(101L);
        p2.setStock(10);

        Product p3 = new Product();
        p3.setId(200L);
        p3.setStock(7);
        Product p4 = new Product();
        p4.setId(201L);
        p4.setStock(3);

        when(branchRepository.findAllByFranchiseId(franchiseId)).thenReturn(Flux.just(branch1, branch2));
        when(productRepository.findAllByBranchId(10L)).thenReturn(Flux.just(p1, p2));
        when(productRepository.findAllByBranchId(20L)).thenReturn(Flux.just(p3, p4));

        StepVerifier.create(productUseCase.getProductsByFranchiseIdAndMaxStock(franchiseId))
                .expectNextMatches(product -> product.getId().equals(101L))
                .expectNextMatches(product -> product.getId().equals(200L))
                .verifyComplete();

        verify(branchRepository).findAllByFranchiseId(franchiseId);
        verify(productRepository).findAllByBranchId(10L);
        verify(productRepository).findAllByBranchId(20L);
    }

    @Test
    void getProductById_returnsProduct() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Mono.just(product));

        StepVerifier.create(productUseCase.getProductById(1L))
                .expectNext(product)
                .verifyComplete();

        verify(productRepository).findById(1L);
    }

    @Test
    void createProduct_savesProductWhenBranchExists() {
        Product product = new Product();
        product.setBranchId(2L);
        Branch branch = new Branch();
        branch.setId(2L);

        when(branchRepository.findById(2L)).thenReturn(Mono.just(branch));
        when(productRepository.saveProduct(product)).thenReturn(Mono.just(product));

        StepVerifier.create(productUseCase.createProduct(product))
                .expectNext(product)
                .verifyComplete();

        verify(branchRepository).findById(2L);
        verify(productRepository).saveProduct(product);
    }

    @Test
    void deleteProduct_deletesWhenBranchExists() {
        Branch branch = new Branch();
        branch.setId(3L);

        when(branchRepository.findById(3L)).thenReturn(Mono.just(branch));
        when(productRepository.deleteProduct(4L)).thenReturn(Mono.empty());

        StepVerifier.create(productUseCase.deleteProduct(3L, 4L))
                .verifyComplete();

        verify(branchRepository).findById(3L);
        verify(productRepository).deleteProduct(4L);
    }

    @Test
    void changueAmount_updatesStockAndSaves() {
        Product product = new Product();
        product.setId(5L);
        product.setStock(1);

        when(productRepository.findById(5L)).thenReturn(Mono.just(product));
        when(productRepository.saveProduct(any(Product.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(productUseCase.changueAmount(5L, 99))
                .expectNextMatches(p -> p.getStock() == 99)
                .verifyComplete();

        verify(productRepository).findById(5L);
        verify(productRepository).saveProduct(any(Product.class));
    }

    @Test
    void getProductsWithMaxStockByBranch_returnsMaxStockProducts() {
        Branch branch1 = new Branch();
        branch1.setId(10L);
        Branch branch2 = new Branch();
        branch2.setId(20L);

        Product p1 = new Product();
        p1.setId(100L);
        p1.setStock(5);
        Product p2 = new Product();
        p2.setId(101L);
        p2.setStock(10);

        Product p3 = new Product();
        p3.setId(200L);
        p3.setStock(7);
        Product p4 = new Product();
        p4.setId(201L);
        p4.setStock(3);

        when(branchRepository.findAll()).thenReturn(Flux.just(branch1, branch2));
        when(productRepository.findAllByBranchId(10L)).thenReturn(Flux.just(p1, p2));
        when(productRepository.findAllByBranchId(20L)).thenReturn(Flux.just(p3, p4));

        StepVerifier.create(productUseCase.getProductsWithMaxStockByBranch())
                .expectNextMatches(product -> product.getId().equals(101L))
                .expectNextMatches(product -> product.getId().equals(200L))
                .verifyComplete();

        verify(branchRepository).findAll();
        verify(productRepository).findAllByBranchId(10L);
        verify(productRepository).findAllByBranchId(20L);
    }

    @Test
    void updateProductName_updatesNameAndSaves() {
        Product product = new Product();
        product.setId(6L);
        product.setName("OldName");

        when(productRepository.findById(6L)).thenReturn(Mono.just(product));
        when(productRepository.saveProduct(any(Product.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(productUseCase.updateProductName(6L, "NewName"))
                .expectNextMatches(p -> "NewName".equals(p.getName()))
                .verifyComplete();

        verify(productRepository).findById(6L);
        verify(productRepository).saveProduct(any(Product.class));
    }

}
