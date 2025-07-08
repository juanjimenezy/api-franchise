package co.com.nequi.franchise.usecase.branch;

import co.com.nequi.franchise.model.branch.Branch;
import co.com.nequi.franchise.model.branch.gateways.BranchRepository;
import co.com.nequi.franchise.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BranchUseCase {
    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    public Mono<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    public Mono<Branch> saveBranch(Branch branch) {
        return franchiseRepository.findById(branch.getFranchiseId())
                .flatMap(franchise -> {
                    Branch newBranch = branch.toBuilder()
                            .franchiseId(franchise.getId())
                            .build();
                    return branchRepository.saveBranch(newBranch);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Franchise not exist")));
    }

    public Mono<Branch> updateBranch(Long id, String branchName) {
        return branchRepository.findById(id)
                .flatMap(existingBranch -> {
                    Branch branch = existingBranch.toBuilder()
                            .name(branchName)
                            .build();
                    return branchRepository.saveBranch(branch);
                });
    }

}
