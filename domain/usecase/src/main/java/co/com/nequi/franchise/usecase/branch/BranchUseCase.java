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
                    branch.setFranchiseId(franchise.getId());
                    return branchRepository.saveBranch(branch);
                });
    }

    public Mono<Branch> updateBranch(Long id, String branchName) {
        return branchRepository.findById(id)
                .flatMap(existingBranch -> {
                    existingBranch.setName(branchName);
                    return branchRepository.saveBranch(existingBranch);
                });
    }

}
