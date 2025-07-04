package co.com.nequi.franchise.r2dbc.config;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Statement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@Slf4j
public class R2dbcDatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {


    private final ConnectionFactory connectionFactory;

    public R2dbcDatabaseInitializer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ClassPathResource resource = new ClassPathResource("export.sql");

        String checkSchemaSql = "SELECT schema_name FROM information_schema.schemata WHERE schema_name = 'franchise'";

        Mono.usingWhen(
                connectionFactory.create(),
                connection -> Mono.from(connection.createStatement(checkSchemaSql).execute())
                        .flatMap(result -> Mono.from(result.map((row, metadata) -> row.get("schema_name"))))
                        .hasElement()
                        .flatMap(exists -> {
                            if (exists) {
                                log.info("El esquema 'franchise' ya existe. No se ejecuta export.sql.");
                                return Mono.empty();
                            }
                            try {
                                String sql = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
                                        .lines()
                                        .collect(Collectors.joining("\n"));
                                String[] statements = sql.split(";");
                                return Flux.fromArray(statements)
                                        .map(String::trim)
                                        .filter(s -> !s.isEmpty())
                                        .flatMap(statementSql -> {
                                            Statement statement = connection.createStatement(statementSql);
                                            return Mono.from(statement.execute()).then();
                                        })
                                        .then()
                                        .doOnSuccess(unused -> log.info("✔️ export.sql ejecutado exitosamente al arrancar la aplicación."));
                            } catch (Exception e) {
                                log.info("❌ Error leyendo export.sql: " + e.getMessage());
                                e.printStackTrace();
                                return Mono.empty();
                            }
                        }),
                Connection::close
        ).subscribe(
                unused -> {},
                error -> {
                    log.info("❌ Error ejecutando export.sql: " + error.getMessage());
                    error.printStackTrace();
                }
        );
    }
}
