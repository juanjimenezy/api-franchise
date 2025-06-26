package co.com.nequi.franchise.usecase.franchise;

import co.com.nequi.franchise.model.franchise.Franchise;
import co.com.nequi.franchise.model.franchise.gateways.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class FranchiseUseCaseTest {

    private FranchiseRepository franchiseRepository;
    private FranchiseUseCase franchiseUseCase;

    @BeforeEach
    void setUp() {
        franchiseRepository = Mockito.mock(FranchiseRepository.class);
        franchiseUseCase = new FranchiseUseCase(franchiseRepository);
    }

    @Test
    void getFranchise_returnsFranchise() {
        Franchise franchise = new Franchise();
        franchise.setName("Test");
        Mockito.when(franchiseRepository.findById(1L)).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.getFranchise(1L))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void saveFranchise_savesAndReturnsFranchise() {
        Franchise franchise = new Franchise();
        Mockito.when(franchiseRepository.saveFranchise(franchise)).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.saveFranchise(franchise))
                .expectNext(franchise)
                .verifyComplete();
    }

    @Test
    void updateNameFranchise_updatesNameAndSaves() {
        Franchise franchise = new Franchise();
        franchise.setName("OldName");
        Mockito.when(franchiseRepository.findById(1L)).thenReturn(Mono.just(franchise));
        Mockito.when(franchiseRepository.saveFranchise(franchise)).thenReturn(Mono.just(franchise));

        StepVerifier.create(franchiseUseCase.updateNameFranchise(1L, "NewName"))
                .expectNextMatches(f -> "NewName".equals(f.getName()))
                .verifyComplete();
    }
}
