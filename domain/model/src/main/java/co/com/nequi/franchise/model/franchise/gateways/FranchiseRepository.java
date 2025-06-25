package co.com.nequi.franchise.model.franchise.gateways;

import co.com.nequi.franchise.model.franchise.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> saveFranchise(Franchise franchise);
    Mono<Franchise> updateFranchiseName(String franchiseName);
}
