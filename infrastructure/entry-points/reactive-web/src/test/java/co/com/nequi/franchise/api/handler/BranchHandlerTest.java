package co.com.nequi.franchise.api.handler;

import co.com.nequi.franchise.api.dto.BranchRequestDTO;
import co.com.nequi.franchise.model.branch.Branch;
import co.com.nequi.franchise.usecase.branch.BranchUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BranchHandlerTest {

    @Mock
    private BranchUseCase branchUseCase;

    @Mock
    private ServerRequest serverRequest;

    private BranchHandler branchHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        branchHandler = new BranchHandler(branchUseCase);
    }

    // getBranchById
    @Test
    void getBranchById_success() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        Branch branch = new Branch();
        when(branchUseCase.getBranchById(1L)).thenReturn(Mono.just(branch));

        Mono<ServerResponse> response = branchHandler.getBranchById(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void getBranchById_notFound() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        when(branchUseCase.getBranchById(1L)).thenReturn(Mono.empty());

        Mono<ServerResponse> response = branchHandler.getBranchById(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void getBranchById_error() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        when(branchUseCase.getBranchById(1L)).thenReturn(Mono.error(new RuntimeException("db error")));

        Mono<ServerResponse> response = branchHandler.getBranchById(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

    // createBranch
    @Test
    void createBranch_success() {
        Branch branch = new Branch();
        when(serverRequest.bodyToMono(Branch.class)).thenReturn(Mono.just(branch));
        when(branchUseCase.saveBranch(branch)).thenReturn(Mono.just(branch));

        Mono<ServerResponse> response = branchHandler.createBranch(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void createBranch_empty() {
        when(serverRequest.bodyToMono(Branch.class)).thenReturn(Mono.empty());

        Mono<ServerResponse> response = branchHandler.createBranch(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void createBranch_error() {
        when(serverRequest.bodyToMono(Branch.class)).thenReturn(Mono.just(new Branch()));
        when(branchUseCase.saveBranch(any())).thenReturn(Mono.error(new RuntimeException("error")));

        Mono<ServerResponse> response = branchHandler.createBranch(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

    // updateBranchName
    @Test
    void updateBranchName_success() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        BranchRequestDTO dto = new BranchRequestDTO();
        dto.setName("NuevoNombre");
        when(serverRequest.bodyToMono(BranchRequestDTO.class)).thenReturn(Mono.just(dto));
        Branch branch = new Branch();
        when(branchUseCase.updateBranch(1L, "NuevoNombre")).thenReturn(Mono.just(branch));

        Mono<ServerResponse> response = branchHandler.updateBranchName(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void updateBranchName_notFound() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        BranchRequestDTO dto = new BranchRequestDTO();
        dto.setName("NuevoNombre");
        when(serverRequest.bodyToMono(BranchRequestDTO.class)).thenReturn(Mono.just(dto));
        when(branchUseCase.updateBranch(1L, "NuevoNombre")).thenReturn(Mono.empty());

        Mono<ServerResponse> response = branchHandler.updateBranchName(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void updateBranchName_error() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        BranchRequestDTO dto = new BranchRequestDTO();
        dto.setName("NuevoNombre");
        when(serverRequest.bodyToMono(BranchRequestDTO.class)).thenReturn(Mono.just(dto));
        when(branchUseCase.updateBranch(1L, "NuevoNombre")).thenReturn(Mono.error(new RuntimeException("error")));

        Mono<ServerResponse> response = branchHandler.updateBranchName(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

}
