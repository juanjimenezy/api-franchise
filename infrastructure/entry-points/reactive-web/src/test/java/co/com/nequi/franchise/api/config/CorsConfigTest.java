package co.com.nequi.franchise.api.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CorsConfigTest {

    @Test
    void corsWebFilter_createsFilterWithExpectedConfig() throws Exception {
        String origins = "http://localhost,http://example.com";
        CorsConfig corsConfig = new CorsConfig();

        Method method = CorsConfig.class.getDeclaredMethod("corsWebFilter", String.class);
        method.setAccessible(true);
        CorsWebFilter filter = (CorsWebFilter) method.invoke(corsConfig, origins);

        assertNotNull(filter);

        var sourceField = CorsWebFilter.class.getDeclaredField("configSource");
        sourceField.setAccessible(true);
        UrlBasedCorsConfigurationSource source = (UrlBasedCorsConfigurationSource) sourceField.get(filter);

        ServerWebExchange exchange = mock(ServerWebExchange.class);
        ServerHttpRequest request = mock(ServerHttpRequest.class);
        when(exchange.getRequest()).thenReturn(request);
        when(request.getPath()).thenReturn(RequestPath.parse("/test", null));

        CorsConfiguration loadedConfig = source.getCorsConfiguration(exchange);

        assertNotNull(loadedConfig);
        assertTrue(loadedConfig.getAllowCredentials());
        assertEquals(2, loadedConfig.getAllowedOrigins().size());
        assertTrue(loadedConfig.getAllowedOrigins().contains("http://localhost"));
        assertTrue(loadedConfig.getAllowedOrigins().contains("http://example.com"));
        assertEquals(4, loadedConfig.getAllowedMethods().size());
        assertTrue(loadedConfig.getAllowedMethods().containsAll(List.of("POST", "GET", "PUT", "DELETE")));
        assertEquals(1, loadedConfig.getAllowedHeaders().size());
        assertEquals(CorsConfiguration.ALL, loadedConfig.getAllowedHeaders().get(0));
    }

}
