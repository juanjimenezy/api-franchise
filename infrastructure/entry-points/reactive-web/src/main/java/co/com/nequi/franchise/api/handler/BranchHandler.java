package co.com.nequi.franchise.api.handler;

import co.com.nequi.franchise.api.dto.request.BranchRequestDTO;
import co.com.nequi.franchise.api.dto.response.BranchResponseDTO;
import co.com.nequi.franchise.model.branch.Branch;
import co.com.nequi.franchise.usecase.branch.BranchUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class BranchHandler {

    private final BranchUseCase branchUseCase;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> getBranchById(ServerRequest serverRequest) {
        log.info("Retrieving branch by ID: {}", serverRequest.pathVariable("id"));
        return Mono.justOrEmpty(serverRequest.pathVariable("id"))
                .map(Long::valueOf)
                .flatMap(branchUseCase::getBranchById)
                .flatMap(branch -> ServerResponse.ok().bodyValue(toBranchResponseDTO(branch)))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue("Branch not exist"))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue("Error retrieving branch: " + error.getMessage()));
    }

    public Mono<ServerResponse> createBranch(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Branch.class)
                .flatMap(branchUseCase::saveBranch)
                .flatMap(branch -> ServerResponse.ok().bodyValue(toBranchResponseDTO(branch)))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue("Franchise not exist"))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue("Error creating branch: " + error.getMessage()));
    }

    public Mono<ServerResponse> updateBranchName(ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.pathVariable("id"))
                .map(Long::valueOf)
                .flatMap(id -> serverRequest.bodyToMono(BranchRequestDTO.class)
                        .flatMap(dto -> branchUseCase.updateBranch(id, dto.getName())))
                .flatMap(branch -> ServerResponse.ok().bodyValue(toBranchResponseDTO(branch)))
                .switchIfEmpty(ServerResponse.badRequest().bodyValue("Branch not exist"))
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue("Error updating branch: " + error.getMessage()));
    }

    private BranchResponseDTO toBranchResponseDTO(Branch branch){
        return objectMapper.map(branch, BranchResponseDTO.class);
    }

}
