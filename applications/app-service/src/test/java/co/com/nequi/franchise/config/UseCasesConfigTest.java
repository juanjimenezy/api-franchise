package co.com.nequi.franchise.config;

import co.com.nequi.franchise.model.branch.gateways.BranchRepository;
import co.com.nequi.franchise.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.franchise.model.product.gateways.ProductRepository;
import co.com.nequi.franchise.usecase.branch.BranchUseCase;
import co.com.nequi.franchise.usecase.franchise.FranchiseUseCase;
import co.com.nequi.franchise.usecase.product.ProductUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            String[] beanNames = context.getBeanDefinitionNames();

            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }

            assertTrue(useCaseBeanFound, "No beans ending with 'Use Case' were found");
        }
    }

    @Configuration
    @Import(UseCasesConfig.class)
    static class TestConfig {

        @Bean
        public FranchiseRepository franchiseRepository() {
            return Mockito.mock(FranchiseRepository.class);
        }

        @Bean
        public BranchRepository branchRepository() {return Mockito.mock(BranchRepository.class);}

        @Bean
        public ProductRepository productRepository() {return Mockito.mock(ProductRepository.class);}

        @Bean
        public FranchiseUseCase franchiseUseCase(FranchiseRepository franchiseRepository) {
            return new FranchiseUseCase(franchiseRepository);
        }

        @Bean
        public BranchUseCase branchUseCase(FranchiseRepository franchiseRepository, BranchRepository branchRepository) {
            return new BranchUseCase(franchiseRepository, branchRepository);
        }

        @Bean
        public ProductUseCase productUseCase(BranchRepository branchRepository, ProductRepository productRepository) {
            return new ProductUseCase(branchRepository, productRepository);
        }
    }

    static class MyUseCase {
        public String execute() {
            return "MyUseCase Test";
        }
    }
}