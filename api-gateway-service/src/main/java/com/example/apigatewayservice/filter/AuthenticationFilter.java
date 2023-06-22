package com.example.apigatewayservice.filter;

import com.example.apigatewayservice.exception.MissingAuthHeaderException;
import com.example.apigatewayservice.exception.TokenValidationFailedException;
import com.example.apigatewayservice.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator routeValidator;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(RouteValidator routeValidator, RestTemplate restTemplate, JwtUtil jwtUtil) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    return Mono.error(new MissingAuthHeaderException("missing authorization header"));
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    //restTemplate.getForObject("http://USER-MANAGEMENT-SERVICE/user/validate?token="+authHeader, String.class);
                    jwtUtil.validateToken(authHeader);
                }catch (Exception e) {
                    log.error("unauthorized access",e);
                    return Mono.error(new TokenValidationFailedException("unauthorized access"));
                }
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config{}
}
