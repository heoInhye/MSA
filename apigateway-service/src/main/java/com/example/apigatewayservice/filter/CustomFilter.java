package com.example.apigatewayservice.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

  @Override
  public GatewayFilter apply(Config config) {
    //Custom pre-filter
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      ServerHttpResponse response = exchange.getResponse();

      log.info("Custom pre-filter: request id -> {}", request.getId());

      //Custom post-filter
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        log.info("Custom post-filter: response status code -> {}", response.getStatusCode());
      }));

    };
  }

  public static class Config {
    //put the configuration properties
  }

  public CustomFilter(){
    super(Config.class);
  }

}
