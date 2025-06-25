package co.com.nequi.franchise.api.handler;

import co.com.nequi.franchise.api.dto.FranchiseRequestDTO;
import co.com.nequi.franchise.model.franchise.Franchise;
import co.com.nequi.franchise.usecase.franchise.FranchiseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseHandler {

    private final FranchiseUseCase franchiseUseCase;

    public Mono<ServerResponse> getFranchiseById(ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.pathVariable("id"))
                .map(Long::valueOf)
                .flatMap(franchiseUseCase::getFranchise)
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue("Franchise not exist"));
    }

    public Mono<ServerResponse> createFranchise(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Franchise.class)
                .flatMap(franchiseUseCase::saveFranchise)
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> updateFranchiseName(ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.pathVariable("id"))
                .map(Long::valueOf)
                .flatMap(id -> serverRequest.bodyToMono(FranchiseRequestDTO.class)
                        .flatMap(dto -> franchiseUseCase.updateNameFranchise(id, dto.getName())))
                .flatMap(franchise -> ServerResponse.ok().bodyValue(franchise))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue("Franchise not exist"));
    }

}
