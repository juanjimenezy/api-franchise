package co.com.nequi.franchise.model.branch.gateways;

import co.com.nequi.franchise.model.branch.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Flux<Branch> findAll();
    Flux<Branch> findAllByFranchiseId(Long franchiseId);
    Mono<Branch> findById(Long id);
    Mono<Branch> saveBranch(Branch branch);
}
