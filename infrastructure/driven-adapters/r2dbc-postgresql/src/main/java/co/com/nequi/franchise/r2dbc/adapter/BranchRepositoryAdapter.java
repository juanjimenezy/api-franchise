package co.com.nequi.franchise.r2dbc.adapter;

import co.com.nequi.franchise.model.branch.Branch;
import co.com.nequi.franchise.model.branch.gateways.BranchRepository;
import co.com.nequi.franchise.r2dbc.entity.BranchEntity;
import co.com.nequi.franchise.r2dbc.helper.ReactiveAdapterOperations;
import co.com.nequi.franchise.r2dbc.repository.BranchReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BranchRepositoryAdapter extends ReactiveAdapterOperations<Branch, BranchEntity, Long, BranchReactiveRepository> implements BranchRepository {

    public BranchRepositoryAdapter(BranchReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Branch.class));
    }

    @Override
    public Flux<Branch> findAllByFranchiseId(Long franchiseId) {
        return repository.findAllByFranchiseId(franchiseId)
                .map(branch -> mapper.map(branch, Branch.class));
    }

    @Override
    public Mono<Branch> saveBranch(Branch branch) {
        BranchEntity entity = mapper.map(branch, BranchEntity.class);
        return repository.save(entity)
                .map(savedEntity -> mapper.map(savedEntity, Branch.class));
    }
}
