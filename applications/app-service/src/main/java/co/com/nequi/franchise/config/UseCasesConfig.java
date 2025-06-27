package co.com.nequi.franchise.config;

import co.com.nequi.franchise.model.branch.gateways.BranchRepository;
import co.com.nequi.franchise.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.franchise.model.product.gateways.ProductRepository;
import co.com.nequi.franchise.usecase.branch.BranchUseCase;
import co.com.nequi.franchise.usecase.franchise.FranchiseUseCase;
import co.com.nequi.franchise.usecase.product.ProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.nequi.franchise.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

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
