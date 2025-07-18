package co.com.nequi.franchise.r2dbc.adapter;

import co.com.nequi.franchise.model.franchise.Franchise;
import co.com.nequi.franchise.model.franchise.gateways.FranchiseRepository;
import co.com.nequi.franchise.r2dbc.repository.FranchiseReactiveRepository;
import co.com.nequi.franchise.r2dbc.entity.FranchiseEntity;
import co.com.nequi.franchise.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class FranchiseRepositoryAdapter extends ReactiveAdapterOperations<Franchise, FranchiseEntity, Long, FranchiseReactiveRepository> implements FranchiseRepository {

    public FranchiseRepositoryAdapter(FranchiseReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Franchise.class));
    }

    @Override
    public Mono<Franchise> findById(Long id) {
        return repository.findById(id).map(entity -> mapper.map(entity, Franchise.class));
    }

    @Override
    public Mono<Franchise> saveFranchise(Franchise franchise) {
        FranchiseEntity entity = mapper.map(franchise, FranchiseEntity.class);
        return repository.save(entity)
                .map(savedEntity -> mapper.map(savedEntity, Franchise.class));
    }
}
