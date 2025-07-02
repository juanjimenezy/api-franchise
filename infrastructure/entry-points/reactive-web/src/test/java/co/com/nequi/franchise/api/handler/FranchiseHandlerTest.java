package co.com.nequi.franchise.api.handler;

import co.com.nequi.franchise.api.dto.FranchiseRequestDTO;
import co.com.nequi.franchise.model.franchise.Franchise;
import co.com.nequi.franchise.usecase.franchise.FranchiseUseCase;
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

class FranchiseHandlerTest {

    @Mock
    private FranchiseUseCase franchiseUseCase;

    @Mock
    private ServerRequest serverRequest;

    private FranchiseHandler franchiseHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        franchiseHandler = new FranchiseHandler(franchiseUseCase);
    }

    @Test
    void getFranchiseById_success() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        Franchise franchise = new Franchise();
        when(franchiseUseCase.getFranchise(1L)).thenReturn(Mono.just(franchise));

        Mono<ServerResponse> response = franchiseHandler.getFranchiseById(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void getFranchiseById_notFound() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        when(franchiseUseCase.getFranchise(1L)).thenReturn(Mono.empty());

        Mono<ServerResponse> response = franchiseHandler.getFranchiseById(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void getFranchiseById_error() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        when(franchiseUseCase.getFranchise(1L)).thenReturn(Mono.error(new RuntimeException("db error")));

        Mono<ServerResponse> response = franchiseHandler.getFranchiseById(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void createFranchise_success() {
        Franchise franchise = new Franchise();
        when(serverRequest.bodyToMono(Franchise.class)).thenReturn(Mono.just(franchise));
        when(franchiseUseCase.saveFranchise(franchise)).thenReturn(Mono.just(franchise));

        Mono<ServerResponse> response = franchiseHandler.createFranchise(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void createFranchise_empty() {
        when(serverRequest.bodyToMono(Franchise.class)).thenReturn(Mono.empty());

        Mono<ServerResponse> response = franchiseHandler.createFranchise(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void createFranchise_error() {
        when(serverRequest.bodyToMono(Franchise.class)).thenReturn(Mono.just(new Franchise()));
        when(franchiseUseCase.saveFranchise(any())).thenReturn(Mono.error(new RuntimeException("error")));

        Mono<ServerResponse> response = franchiseHandler.createFranchise(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void updateFranchiseName_success() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        FranchiseRequestDTO dto = new FranchiseRequestDTO();
        dto.setName("NuevoNombre");
        when(serverRequest.bodyToMono(FranchiseRequestDTO.class)).thenReturn(Mono.just(dto));
        Franchise franchise = new Franchise();
        when(franchiseUseCase.updateNameFranchise(1L, "NuevoNombre")).thenReturn(Mono.just(franchise));

        Mono<ServerResponse> response = franchiseHandler.updateFranchiseName(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void updateFranchiseName_notFound() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        FranchiseRequestDTO dto = new FranchiseRequestDTO();
        dto.setName("NuevoNombre");
        when(serverRequest.bodyToMono(FranchiseRequestDTO.class)).thenReturn(Mono.just(dto));
        when(franchiseUseCase.updateNameFranchise(1L, "NuevoNombre")).thenReturn(Mono.empty());

        Mono<ServerResponse> response = franchiseHandler.updateFranchiseName(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

    @Test
    void updateFranchiseName_error() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        FranchiseRequestDTO dto = new FranchiseRequestDTO();
        dto.setName("NuevoNombre");
        when(serverRequest.bodyToMono(FranchiseRequestDTO.class)).thenReturn(Mono.just(dto));
        when(franchiseUseCase.updateNameFranchise(1L, "NuevoNombre")).thenReturn(Mono.error(new RuntimeException("error")));

        Mono<ServerResponse> response = franchiseHandler.updateFranchiseName(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(r -> r.statusCode().is4xxClientError())
                .verifyComplete();
    }

}
