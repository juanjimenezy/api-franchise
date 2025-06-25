package co.com.nequi.franchise.r2dbc.repository;

import co.com.nequi.franchise.r2dbc.entity.FranchiseEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MyReactiveRepository extends ReactiveCrudRepository<FranchiseEntity, Long>, ReactiveQueryByExampleExecutor<FranchiseEntity> {
}
