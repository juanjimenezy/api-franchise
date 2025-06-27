package co.com.nequi.franchise.r2dbc.repository;

import co.com.nequi.franchise.r2dbc.entity.BranchEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BranchReactiveRepository extends ReactiveCrudRepository<BranchEntity, Long>, ReactiveQueryByExampleExecutor<BranchEntity> {
    Flux<BranchEntity> findAllByFranchiseId(Long franchiseId);
}
