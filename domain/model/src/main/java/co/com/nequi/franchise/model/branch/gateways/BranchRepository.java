package co.com.nequi.franchise.model.branch.gateways;

import co.com.nequi.franchise.model.branch.Branch;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> findById(Long id);
    Mono<Branch> saveBranch(Branch branch);
}
