package co.com.nequi.franchise.usecase.franchise;

import co.com.nequi.franchise.model.franchise.Franchise;
import co.com.nequi.franchise.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class FranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    public Mono<Franchise> getFranchise(Long id) {
        return franchiseRepository.findById(id);
    }

    public Mono<Franchise> saveFranchise(Franchise franchise) {
        return franchiseRepository.saveFranchise(franchise);
    }

    public Mono<Franchise> updateNameFranchise(Long id, String newName) {
        return franchiseRepository.findById(id)
                .flatMap(franchise -> {
                    franchise.setName(newName);
                    return franchiseRepository.saveFranchise(franchise);
                });
    }

}
