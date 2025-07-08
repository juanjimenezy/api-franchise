package co.com.nequi.franchise.api.handler;

import co.com.nequi.franchise.api.dto.request.FranchiseRequestDTO;
import co.com.nequi.franchise.api.dto.response.FranchiseResponseDTO;
import co.com.nequi.franchise.model.franchise.Franchise;
import co.com.nequi.franchise.usecase.franchise.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseHandler {

    private static final String FRANCHISE_NOT_EXIST = "Franchise not exist";
    private static final String ERROR_RETRIEVING_FRANCHISE = "Error retrieving franchise: ";

    private final FranchiseUseCase franchiseUseCase;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> getFranchiseById(ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.pathVariable("id"))
                .map(Long::valueOf)
                .flatMap(franchiseUseCase::getFranchise)
                .flatMap(franchise -> ServerResponse.ok().bodyValue(toFranchiseResponseDTO(franchise)))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue(FRANCHISE_NOT_EXIST))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(ERROR_RETRIEVING_FRANCHISE + error.getMessage()));
    }

    public Mono<ServerResponse> createFranchise(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Franchise.class)
                .flatMap(franchiseUseCase::saveFranchise)
                .flatMap(franchise -> ServerResponse.ok().bodyValue(toFranchiseResponseDTO(franchise)))
                .switchIfEmpty(ServerResponse.badRequest().build())
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(ERROR_RETRIEVING_FRANCHISE + error.getMessage()));
    }

    public Mono<ServerResponse> updateFranchiseName(ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.pathVariable("id"))
                .map(Long::valueOf)
                .flatMap(id -> serverRequest.bodyToMono(FranchiseRequestDTO.class)
                        .flatMap(dto -> franchiseUseCase.updateNameFranchise(id, dto.getName())))
                .flatMap(franchise -> ServerResponse.ok().bodyValue(toFranchiseResponseDTO(franchise)))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue(FRANCHISE_NOT_EXIST))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(ERROR_RETRIEVING_FRANCHISE + error.getMessage()));
    }

    private FranchiseResponseDTO toFranchiseResponseDTO(Franchise franchise) {
        return objectMapper.map(franchise, FranchiseResponseDTO.class);
    }

}
