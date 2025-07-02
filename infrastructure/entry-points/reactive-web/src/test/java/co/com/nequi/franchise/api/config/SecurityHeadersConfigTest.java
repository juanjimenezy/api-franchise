package co.com.nequi.franchise.api.config;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityHeadersConfigTest {

    @Test
    void filter_addsSecurityHeaders() {
        SecurityHeadersConfig filter = new SecurityHeadersConfig();

        ServerWebExchange exchange = mock(ServerWebExchange.class);
        ServerHttpResponse response = mock(ServerHttpResponse.class);
        HttpHeaders headers = new HttpHeaders();

        when(exchange.getResponse()).thenReturn(response);
        when(response.getHeaders()).thenReturn(headers);

        WebFilterChain chain = mock(WebFilterChain.class);
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        filter.filter(exchange, chain).block();

        assertEquals("default-src 'self'; frame-ancestors 'self'; form-action 'self'", headers.getFirst("Content-Security-Policy"));
        assertEquals("max-age=31536000;", headers.getFirst("Strict-Transport-Security"));
        assertEquals("nosniff", headers.getFirst("X-Content-Type-Options"));
        assertEquals("", headers.getFirst("Server"));
        assertEquals("no-store", headers.getFirst("Cache-Control"));
        assertEquals("no-cache", headers.getFirst("Pragma"));
        assertEquals("strict-origin-when-cross-origin", headers.getFirst("Referrer-Policy"));
    }

}
