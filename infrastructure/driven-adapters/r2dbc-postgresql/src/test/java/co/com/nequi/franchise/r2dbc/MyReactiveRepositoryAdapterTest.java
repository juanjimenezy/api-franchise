package co.com.nequi.franchise.r2dbc;

import co.com.nequi.franchise.model.franchise.Franchise;
import co.com.nequi.franchise.r2dbc.adapter.MyReactiveRepositoryAdapter;
import co.com.nequi.franchise.r2dbc.entity.FranchiseEntity;
import co.com.nequi.franchise.r2dbc.repository.MyReactiveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {

    @InjectMocks
    MyReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    MyReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {
        FranchiseEntity franchise = new FranchiseEntity(1L, "test");
        when(repository.findById(1L)).thenReturn(Mono.just(franchise));
        when(mapper.map(any(Franchise.class), any())).thenReturn("test");
        when(mapper.map("test", Object.class)).thenReturn("test");
        when(mapper.map(any(Franchise.class), any())).thenReturn("test");
        when(mapper.map("test", Object.class)).thenReturn("test");

        Mono<Franchise> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getName().equals("test") && value.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        FranchiseEntity franchise = new FranchiseEntity(1L, "test");
        when(repository.findAll()).thenReturn(Flux.just(franchise));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Flux<Franchise> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getName().equals("test") && value.getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void mustFindByExample() {
        Franchise franchiseObject = new Franchise().toBuilder().id(1L).name("test").build();
        when(repository.findAll(any(Example.class))).thenReturn(Flux.just("test"));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Flux<Franchise> result = repositoryAdapter.findByExample(franchiseObject);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        FranchiseEntity franchise = new FranchiseEntity(1L, "test");
        Franchise franchiseObject = new Franchise().toBuilder().id(1L).name("test").build();
        when(mapper.map(franchiseObject, FranchiseEntity.class)).thenReturn(franchise);
        when(repository.save(franchise)).thenReturn(Mono.just(franchise));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Mono<Franchise> result = repositoryAdapter.save(franchiseObject);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }
}
