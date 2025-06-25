package co.com.nequi.franchise.r2dbc;

import co.com.nequi.franchise.r2dbc.entity.FranchiseEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MyReactiveRepository extends ReactiveCrudRepository<FranchiseEntity, String>, ReactiveQueryByExampleExecutor<FranchiseEntity> {
    Mono<FranchiseEntity> findById(Long id);
}
