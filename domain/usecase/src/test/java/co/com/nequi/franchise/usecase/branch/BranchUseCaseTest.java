package co.com.nequi.franchise.usecase.branch;

import co.com.nequi.franchise.model.branch.Branch;
import co.com.nequi.franchise.model.branch.gateways.BranchRepository;
import co.com.nequi.franchise.model.franchise.Franchise;
import co.com.nequi.franchise.model.franchise.gateways.FranchiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class BranchUseCaseTest {

    private FranchiseRepository franchiseRepository;
    private BranchRepository branchRepository;
    private BranchUseCase branchUseCase;

    @BeforeEach
    void setUp() {
        franchiseRepository = mock(FranchiseRepository.class);
        branchRepository = mock(BranchRepository.class);
        branchUseCase = new BranchUseCase(franchiseRepository, branchRepository);
    }

    @Test
    void getBranchById_returnsBranch() {
        Branch branch = new Branch();
        branch.setId(1L);
        when(branchRepository.findById(1L)).thenReturn(Mono.just(branch));

        StepVerifier.create(branchUseCase.getBranchById(1L))
                .expectNext(branch)
                .verifyComplete();

        verify(branchRepository).findById(1L);
    }

    @Test
    void saveBranch_savesAndReturnsBranch() {
        Branch branch = new Branch();
        branch.setFranchiseId(10L);

        Franchise franchise = new Franchise();
        franchise.setId(10L);

        when(franchiseRepository.findById(10L)).thenReturn(Mono.just(franchise));
        when(branchRepository.saveBranch(any(Branch.class))).thenReturn(Mono.just(branch));

        StepVerifier.create(branchUseCase.saveBranch(branch))
                .expectNext(branch)
                .verifyComplete();

        verify(franchiseRepository).findById(10L);
        verify(branchRepository).saveBranch(branch);
    }

    @Test
    void updateBranch_updatesAndReturnsBranch() {
        Branch branch = new Branch();
        branch.setId(2L);
        branch.setName("Old Name");

        when(branchRepository.findById(2L)).thenReturn(Mono.just(branch));
        when(branchRepository.saveBranch(any(Branch.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(branchUseCase.updateBranch(2L, "New Name"))
                .expectNextMatches(updated -> "New Name".equals(updated.getName()))
                .verifyComplete();

        verify(branchRepository).findById(2L);
        verify(branchRepository).saveBranch(any(Branch.class));
    }

}
